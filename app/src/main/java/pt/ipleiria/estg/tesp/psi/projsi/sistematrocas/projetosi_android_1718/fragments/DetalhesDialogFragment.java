package pt.ipleiria.estg.tesp.psi.projsi.sistematrocas.projetosi_android_1718.fragments;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import pt.ipleiria.estg.tesp.psi.projsi.sistematrocas.projetosi_android_1718.R;
import pt.ipleiria.estg.tesp.psi.projsi.sistematrocas.projetosi_android_1718.listeners.ContatoListener;
import pt.ipleiria.estg.tesp.psi.projsi.sistematrocas.projetosi_android_1718.singletons.SingletonClientes;


public class DetalhesDialogFragment extends DialogFragment implements ContatoListener
{
    Long idAnuncio;

    TextView txtNome;
    TextView txtTelefone;
    TextView txtRegiao;

    public static DetalhesDialogFragment newInstance(Long id)
    {
        DetalhesDialogFragment dialog = new DetalhesDialogFragment();


        Bundle args = new Bundle();
        args.putLong("anuncio", id);
        dialog.setArguments(args);

        return dialog;
    }

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        idAnuncio = getArguments().getLong("anuncio");

        setStyle(DialogFragment.STYLE_NORMAL, android.R.style.Theme_Holo_Light_Dialog);

        SingletonClientes.getInstance(getActivity().getApplicationContext()).setContatoListener(this);
        SingletonClientes.getInstance(getActivity().getApplicationContext()).getClienteContato(idAnuncio,getActivity().getApplicationContext());

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState)
    {
        View v = inflater.inflate(R.layout.fragment_detalhes_dialog, container, false);

        getDialog().setTitle(R.string.detalhes_dialog);


        txtNome= v.findViewById(R.id.dialog_nome);
        txtTelefone= v.findViewById(R.id.dialog_telefone);
        txtRegiao= v.findViewById(R.id.dialog_regiao);
/*
        nome.setText("Teste");
        telefone.setText("910000000");
        regiao.setText("Leiria");
        */

        return v;
    }

    @Override
    public void onSuccess(String nome, String telefone, String regiao)
    {
        txtNome.setText(nome);
        txtTelefone.setText(telefone);
        txtRegiao.setText(regiao);
    }

    @Override
    public void OnError(String message, Exception ex) {
        Toast.makeText(getActivity().getApplicationContext(), message, Toast.LENGTH_SHORT).show();
    }
}
