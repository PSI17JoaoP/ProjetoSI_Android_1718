package pt.ipleiria.estg.tesp.psi.projsi.sistematrocas.projetosi_android_1718.fragments;

import pt.ipleiria.estg.tesp.psi.projsi.sistematrocas.projetosi_android_1718.R;
import pt.ipleiria.estg.tesp.psi.projsi.sistematrocas.projetosi_android_1718.modelos.Eletronica;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputEditText;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

/**
 * Created by JAPorelo on 16-12-2017.
 * Project ProjetoSI_Android_1718 - pt.ipleiria.estg.tesp.psi.projsi.sistematrocas.projetosi_android_1718.fragments
 */

public class EletronicaFormFragment extends Fragment {

    private TextInputEditText textInputEditTextMarcaEletronica;
    private EditText editTextDescricaoEletronica;

    public EletronicaFormFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_eletronica_form, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        textInputEditTextMarcaEletronica = view.findViewById(R.id.textInputEditTextMarcaEletronica);
        editTextDescricaoEletronica = view.findViewById(R.id.editTextDescricaoEletronica);
    }

    @Override
    public void onDestroyView() {
        textInputEditTextMarcaEletronica = null;
        editTextDescricaoEletronica = null;
        super.onDestroyView();
    }

    public Eletronica getCategoria(String nome) {

        String marca = textInputEditTextMarcaEletronica.getText().toString().trim();
        String descricao = editTextDescricaoEletronica.getText().toString().trim();

        if(!nome.isEmpty() && !marca.isEmpty() && !descricao.isEmpty()) {
            return new Eletronica(nome, descricao, marca);
        }

        return null;
    }
}
