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

import pt.ipleiria.estg.tesp.psi.projsi.sistematrocas.projetosi_android_1718.R;


public class DetalhesDialogFragment extends DialogFragment
{
    Long idAnuncio;

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

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState)
    {
        View v = inflater.inflate(R.layout.fragment_detalhes_dialog, container, false);

        getDialog().setTitle(R.string.detalhes_dialog);

        TextView nome= v.findViewById(R.id.dialog_nome);
        TextView telefone= v.findViewById(R.id.dialog_telefone);
        TextView regiao= v.findViewById(R.id.dialog_regiao);

        nome.setText("Teste");
        telefone.setText("910000000");
        regiao.setText("Leiria");

        return v;
    }
}
