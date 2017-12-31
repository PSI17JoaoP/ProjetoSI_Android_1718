package pt.ipleiria.estg.tesp.psi.projsi.sistematrocas.projetosi_android_1718.forms;

import pt.ipleiria.estg.tesp.psi.projsi.sistematrocas.projetosi_android_1718.R;
import pt.ipleiria.estg.tesp.psi.projsi.sistematrocas.projetosi_android_1718.modelos.GeneroJogo;
import pt.ipleiria.estg.tesp.psi.projsi.sistematrocas.projetosi_android_1718.modelos.Jogo;
import pt.ipleiria.estg.tesp.psi.projsi.sistematrocas.projetosi_android_1718.singletons.SingletonGenerosJogo;

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

import java.util.ArrayList;

/**
 * Created by JAPorelo on 16-12-2017.
 * Project ProjetoSI_Android_1718 - pt.ipleiria.estg.tesp.psi.projsi.sistematrocas.projetosi_android_1718.forms
 */

public class JogosFragment extends Fragment {

    private EditText editTextFaixaEtariaJogos;
    private TextInputEditText textInputEditTextEditoraJogos;
    private EditText editTextDescricaoJogos;
    private TextInputEditText textInputEditTextProdutoraJogos;
    private Spinner spinnerGeneroJogos;

    public JogosFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_jogos, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        editTextFaixaEtariaJogos = view.findViewById(R.id.editTextFaixaEtariaJogos);
        textInputEditTextEditoraJogos = view.findViewById(R.id.textInputEditTextEditoraJogos);
        editTextDescricaoJogos = view.findViewById(R.id.editTextDescricaoJogos);
        textInputEditTextProdutoraJogos = view.findViewById(R.id.textInputEditTextProdutoraJogos);

        ArrayList<GeneroJogo> generoJogos = SingletonGenerosJogo.getInstance(getContext()).getGeneroJogos();

        //Inserção manual para efeito de desenvolvimento.
        /*generoJogos.add(new GeneroJogo(1L, "RTS"));
        generoJogos.add(new GeneroJogo(2L, "Ação"));
        generoJogos.add(new GeneroJogo(3L, "Estratégia"));
        generoJogos.add(new GeneroJogo(4L, "RPG"));
        generoJogos.add(new GeneroJogo(5L, "Puzzle"));
        generoJogos.add(new GeneroJogo(5L, "FPS"));*/

        ArrayAdapter<GeneroJogo> spinnerGeneros = new ArrayAdapter<>(getContext(),
                R.layout.custom_spinner_item,
                generoJogos);

        spinnerGeneroJogos = view.findViewById(R.id.spinnerGeneroJogos);

        spinnerGeneros.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerGeneroJogos.setAdapter(spinnerGeneros);
    }

    @Override
    public void onDestroyView() {
        editTextFaixaEtariaJogos = null;
        textInputEditTextEditoraJogos = null;
        editTextDescricaoJogos = null;
        textInputEditTextProdutoraJogos = null;
        spinnerGeneroJogos = null;
        super.onDestroyView();
    }

    public Jogo getCategoria(String nome) throws RuntimeException {

        String faixaEtaria = editTextFaixaEtariaJogos.getText().toString().trim();
        String editora = textInputEditTextEditoraJogos.getText().toString().trim();
        String descricao = editTextDescricaoJogos.getText().toString().trim();
        String produtora = textInputEditTextProdutoraJogos.getText().toString().trim();
        GeneroJogo genero = (GeneroJogo) spinnerGeneroJogos.getSelectedItem();

        if(!nome.isEmpty() && !faixaEtaria.isEmpty() && !editora.isEmpty() && !descricao.isEmpty() && !produtora.isEmpty() && genero != null) {

            try {
                return new Jogo(nome, editora, Integer.valueOf(faixaEtaria), descricao, genero.getId(), produtora);
            } catch (NumberFormatException e) {
                e.printStackTrace();
                throw new RuntimeException("Ocorreu um erro no processamento do formulário.\nVerifique a faixa etária do formulário de Jogos correspondente.");
            }
        }

        return null;
    }
}
