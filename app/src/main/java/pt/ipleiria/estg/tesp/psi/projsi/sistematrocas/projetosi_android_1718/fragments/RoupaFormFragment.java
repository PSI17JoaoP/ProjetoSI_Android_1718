package pt.ipleiria.estg.tesp.psi.projsi.sistematrocas.projetosi_android_1718.fragments;

import pt.ipleiria.estg.tesp.psi.projsi.sistematrocas.projetosi_android_1718.R;
import pt.ipleiria.estg.tesp.psi.projsi.sistematrocas.projetosi_android_1718.listeners.TiposRoupaListener;
import pt.ipleiria.estg.tesp.psi.projsi.sistematrocas.projetosi_android_1718.modelos.Roupa;
import pt.ipleiria.estg.tesp.psi.projsi.sistematrocas.projetosi_android_1718.modelos.TipoRoupa;
import pt.ipleiria.estg.tesp.psi.projsi.sistematrocas.projetosi_android_1718.singletons.SingletonTiposRoupa;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputEditText;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;

/**
 * Created by JAPorelo on 16-12-2017.
 * Project ProjetoSI_Android_1718 - pt.ipleiria.estg.tesp.psi.projsi.sistematrocas.projetosi_android_1718.fragments
 */


public class RoupaFormFragment extends Fragment implements TiposRoupaListener {

    private TextInputEditText textInputEditTextMarcaRoupa;
    private TextInputEditText textInputEditTextTamanhoRoupa;
    private Spinner spinnerTipoRoupa;

    public RoupaFormFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_roupa_form, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        textInputEditTextMarcaRoupa = view.findViewById(R.id.textInputEditTextMarcaRoupa);
        textInputEditTextTamanhoRoupa = view.findViewById(R.id.textInputEditTextTamanhoRoupa);
        spinnerTipoRoupa = view.findViewById(R.id.spinnerTipoRoupa);

        SingletonTiposRoupa.getInstance(getContext()).setTiposRoupaListener(this);

        ArrayList<TipoRoupa> tiposRoupa = SingletonTiposRoupa.getInstance(getContext()).getTiposRoupa();

        if(!tiposRoupa.isEmpty()) {
            ArrayAdapter<TipoRoupa> spinnerTamanhos = new ArrayAdapter<>(getContext(),
                    R.layout.custom_spinner_item,
                    tiposRoupa);

            spinnerTamanhos.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spinnerTipoRoupa.setAdapter(spinnerTamanhos);
        }
    }

    @Override
    public void onDestroyView() {
        textInputEditTextMarcaRoupa = null;
        textInputEditTextTamanhoRoupa = null;
        spinnerTipoRoupa = null;
        super.onDestroyView();
    }

    public Roupa getCategoria(String nome) {

        String marca = textInputEditTextMarcaRoupa.getText().toString().trim();
        String tamanho = textInputEditTextTamanhoRoupa.getText().toString().trim();
        TipoRoupa tipo = (TipoRoupa) spinnerTipoRoupa.getSelectedItem();

        if(!nome.isEmpty() && !marca.isEmpty() && !tamanho.isEmpty() && tipo != null) {
            return new Roupa(nome, marca, tamanho, tipo.getId());
        }

        return null;
    }

    @Override
    public void onErrorTiposRoupaAPI(String message, Exception ex) {
        ex.printStackTrace();
        Toast.makeText(getContext(), message, Toast.LENGTH_LONG).show();
    }

    @Override
    public void onRefreshTiposRoupa(ArrayList<TipoRoupa> tiposRoupa, Context context) {
        ArrayAdapter<TipoRoupa> spinnerTamanhos = new ArrayAdapter<>(getContext(),
                R.layout.custom_spinner_item,
                tiposRoupa);

        spinnerTamanhos.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerTipoRoupa.setAdapter(spinnerTamanhos);
    }
}
