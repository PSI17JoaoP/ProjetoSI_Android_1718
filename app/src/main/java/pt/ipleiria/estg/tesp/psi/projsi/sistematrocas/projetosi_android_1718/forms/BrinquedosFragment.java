package pt.ipleiria.estg.tesp.psi.projsi.sistematrocas.projetosi_android_1718.forms;

import pt.ipleiria.estg.tesp.psi.projsi.sistematrocas.projetosi_android_1718.R;
import pt.ipleiria.estg.tesp.psi.projsi.sistematrocas.projetosi_android_1718.modelos.Brinquedo;

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
 * Project ProjetoSI_Android_1718 - pt.ipleiria.estg.tesp.psi.projsi.sistematrocas.projetosi_android_1718.forms
 */

public class BrinquedosFragment extends Fragment {

    private TextInputEditText textInputEditTextEditoraBrinquedos;
    private EditText editTextFaixaEtariaBrinquedos;
    private EditText editTextDescricaoBrinquedos;

    public BrinquedosFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_brinquedos, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        textInputEditTextEditoraBrinquedos = view.findViewById(R.id.textInputEditTextEditoraBrinquedos);
        editTextFaixaEtariaBrinquedos = view.findViewById(R.id.editTextFaixaEtariaBrinquedos);
        editTextDescricaoBrinquedos = view.findViewById(R.id.editTextDescricaoBrinquedos);
    }

    @Override
    public void onDestroyView() {
        textInputEditTextEditoraBrinquedos = null;
        editTextFaixaEtariaBrinquedos = null;
        editTextDescricaoBrinquedos = null;
        super.onDestroyView();
    }

    public Brinquedo getCategoria(String nome) throws NumberFormatException {

        String editora = textInputEditTextEditoraBrinquedos.getText().toString().trim();
        String faixaEtaria = editTextFaixaEtariaBrinquedos.getText().toString().trim();
        String descricao = editTextDescricaoBrinquedos.getText().toString().trim();

        if(!nome.isEmpty() && !editora.isEmpty() && !faixaEtaria.isEmpty() && !descricao.isEmpty()) {
            try {
                return new Brinquedo(nome, editora, Integer.valueOf(faixaEtaria), descricao);
            } catch (NumberFormatException e) {
                e.printStackTrace();
                throw new RuntimeException("Ocorreu um erro no processamento do formulário.\nVerifique a faixa etária do formulário de Brinquedos correspondente.");
            }
        }

        return null;
    }
}
