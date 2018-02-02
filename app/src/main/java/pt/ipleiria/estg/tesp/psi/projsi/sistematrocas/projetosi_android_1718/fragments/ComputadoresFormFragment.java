package pt.ipleiria.estg.tesp.psi.projsi.sistematrocas.projetosi_android_1718.fragments;

import pt.ipleiria.estg.tesp.psi.projsi.sistematrocas.projetosi_android_1718.R;
import pt.ipleiria.estg.tesp.psi.projsi.sistematrocas.projetosi_android_1718.modelos.Computador;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputEditText;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;

/**
 * Created by JAPorelo on 16-12-2017.
 * Project ProjetoSI_Android_1718 - pt.ipleiria.estg.tesp.psi.projsi.sistematrocas.projetosi_android_1718.fragments
 */

public class ComputadoresFormFragment extends Fragment {

    private TextInputEditText textInputEditTextMarcaComputadores;
    private EditText editTextDescricaoComputadores;
    private TextInputEditText textInputEditTextProcessadorComputadores;
    private TextInputEditText textInputEditTextRAMComputadores;
    private TextInputEditText textInputEditTextHDDComputadores;
    private TextInputEditText textInputEditTextGPUComputadores;
    private TextInputEditText textInputEditTextOSComputadores;
    private Spinner spinnerPortatil;

    public ComputadoresFormFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_computadores_form, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        textInputEditTextMarcaComputadores = view.findViewById(R.id.textInputEditTextMarcaComputadores);
        editTextDescricaoComputadores = view.findViewById(R.id.editTextDescricaoComputadores);
        textInputEditTextProcessadorComputadores = view.findViewById(R.id.textInputEditTextProcessadorComputadores);
        textInputEditTextRAMComputadores = view.findViewById(R.id.textInputEditTextRAMComputadores);
        textInputEditTextHDDComputadores = view.findViewById(R.id.textInputEditTextHDDComputadores);
        textInputEditTextGPUComputadores = view.findViewById(R.id.textInputEditTextGPUComputadores);
        textInputEditTextOSComputadores = view.findViewById(R.id.textInputEditTextOSComputadores);

        String[] boolArray = {"Sim", "Não"};

        ArrayAdapter<String> spinnerBoolPortatil = new ArrayAdapter<String>(getActivity().getApplicationContext(),
                R.layout.custom_spinner_item,
                boolArray);

        spinnerPortatil = view.findViewById(R.id.spinnerPortatil);

        spinnerBoolPortatil.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerPortatil.setAdapter(spinnerBoolPortatil);

        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public void onDestroyView() {
        textInputEditTextMarcaComputadores = null;
        editTextDescricaoComputadores = null;
        textInputEditTextProcessadorComputadores = null;
        textInputEditTextRAMComputadores = null;
        textInputEditTextHDDComputadores = null;
        textInputEditTextGPUComputadores = null;
        textInputEditTextOSComputadores = null;
        spinnerPortatil = null;
        super.onDestroyView();
    }

    public Computador getCategoria(String nome) {
        String marca = textInputEditTextMarcaComputadores.getText().toString().trim();
        String descricao = editTextDescricaoComputadores.getText().toString().trim();
        String cpu = textInputEditTextProcessadorComputadores.getText().toString().trim();
        String ram = textInputEditTextRAMComputadores.getText().toString().trim();
        String hdd = textInputEditTextHDDComputadores.getText().toString().trim();
        String gpu = textInputEditTextGPUComputadores.getText().toString().trim();
        String os = textInputEditTextOSComputadores.getText().toString().trim();
        String portatil = (String) spinnerPortatil.getSelectedItem();

        if(!nome.isEmpty() && !marca.isEmpty() && !descricao.isEmpty() && !cpu.isEmpty()
                && !ram.isEmpty() && !hdd.isEmpty() && !gpu.isEmpty()
                && !os.isEmpty() && portatil != null) {

            if(portatil.equals("Sim")) {
                return new Computador(nome, descricao, marca, cpu, ram, hdd, gpu, os, 1);
            } else {
                return new Computador(nome, descricao, marca, cpu, ram, hdd, gpu, os, 0);
            }
        }

        return null;
    }
}
