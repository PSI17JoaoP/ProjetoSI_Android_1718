package pt.ipleiria.estg.tesp.psi.projsi.sistematrocas.projetosi_android_1718.forms;

import pt.ipleiria.estg.tesp.psi.projsi.sistematrocas.projetosi_android_1718.R;
import pt.ipleiria.estg.tesp.psi.projsi.sistematrocas.projetosi_android_1718.modelos.Smartphone;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputEditText;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Spinner;

/**
 * Created by JAPorelo on 16-12-2017.
 * Project ProjetoSI_Android_1718 - pt.ipleiria.estg.tesp.psi.projsi.sistematrocas.projetosi_android_1718.forms
 */

public class SmartphonesFragment extends Fragment {

    private TextInputEditText textInputEditTextMarcaSmartphones;
    private EditText editTextDescricaoSmartphones;
    private TextInputEditText textInputEditTextProcessadorSmartphones;
    private TextInputEditText textInputEditTextRAMSmartphones;
    private TextInputEditText textInputEditTextHDDSmartphones;
    private TextInputEditText textInputEditTextOSSmartphones;
    private TextInputEditText textInputEditTextTamanhoSmartphones;

    public SmartphonesFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_smartphones, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        textInputEditTextMarcaSmartphones = view.findViewById(R.id.textInputEditTextMarcaSmartphones);
        editTextDescricaoSmartphones = view.findViewById(R.id.editTextDescricaoSmartphones);
        textInputEditTextProcessadorSmartphones = view.findViewById(R.id.textInputEditTextProcessadorSmartphones);
        textInputEditTextRAMSmartphones = view.findViewById(R.id.textInputEditTextRAMSmartphones);
        textInputEditTextHDDSmartphones = view.findViewById(R.id.textInputEditTextHDDSmartphones);
        textInputEditTextOSSmartphones = view.findViewById(R.id.textInputEditTextOSSmartphones);
        textInputEditTextTamanhoSmartphones = view.findViewById(R.id.textInputEditTextTamanhoSmartphones);
    }

    @Override
    public void onDestroyView() {
        textInputEditTextMarcaSmartphones = null;
        editTextDescricaoSmartphones = null;
        textInputEditTextProcessadorSmartphones = null;
        textInputEditTextRAMSmartphones = null;
        textInputEditTextHDDSmartphones = null;
        textInputEditTextOSSmartphones = null;
        textInputEditTextTamanhoSmartphones = null;
        super.onDestroyView();
    }

    public Smartphone getCategoria(String nome) {

        String marca = textInputEditTextMarcaSmartphones.getText().toString().trim();
        String descricao = editTextDescricaoSmartphones.getText().toString().trim();
        String cpu = textInputEditTextProcessadorSmartphones.getText().toString().trim();
        String ram = textInputEditTextRAMSmartphones.getText().toString().trim();
        String hdd = textInputEditTextHDDSmartphones.getText().toString().trim();
        String os = textInputEditTextOSSmartphones.getText().toString().trim();
        String tamanho = textInputEditTextTamanhoSmartphones.getText().toString().trim();

        if(!nome.isEmpty() && !marca.isEmpty() && !descricao.isEmpty() && !cpu.isEmpty()
                && !ram.isEmpty() && !hdd.isEmpty() && !os.isEmpty()
                && !tamanho.isEmpty()) {

            return new Smartphone(nome, descricao, marca, cpu, ram, hdd, os, tamanho);
        }

        return null;
    }
}
