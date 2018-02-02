package pt.ipleiria.estg.tesp.psi.projsi.sistematrocas.projetosi_android_1718.fragments;

import pt.ipleiria.estg.tesp.psi.projsi.sistematrocas.projetosi_android_1718.R;
import pt.ipleiria.estg.tesp.psi.projsi.sistematrocas.projetosi_android_1718.listeners.GenerosJogosListener;
import pt.ipleiria.estg.tesp.psi.projsi.sistematrocas.projetosi_android_1718.modelos.GeneroJogo;
import pt.ipleiria.estg.tesp.psi.projsi.sistematrocas.projetosi_android_1718.modelos.Jogo;
import pt.ipleiria.estg.tesp.psi.projsi.sistematrocas.projetosi_android_1718.singletons.SingletonGenerosJogo;

import android.content.Context;
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
import android.widget.Toast;

import java.util.ArrayList;

/**
 * Created by JAPorelo on 16-12-2017.
 * Project ProjetoSI_Android_1718 - pt.ipleiria.estg.tesp.psi.projsi.sistematrocas.projetosi_android_1718.fragments
 */

public class JogosFormFragment extends Fragment implements GenerosJogosListener {

    private EditText editTextFaixaEtariaJogos;
    private TextInputEditText textInputEditTextEditoraJogos;
    private EditText editTextDescricaoJogos;
    private TextInputEditText textInputEditTextProdutoraJogos;
    private Spinner spinnerGeneroJogos;

    public JogosFormFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_jogos_form, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        editTextFaixaEtariaJogos = view.findViewById(R.id.editTextFaixaEtariaJogos);
        textInputEditTextEditoraJogos = view.findViewById(R.id.textInputEditTextEditoraJogos);
        editTextDescricaoJogos = view.findViewById(R.id.editTextDescricaoJogos);
        textInputEditTextProdutoraJogos = view.findViewById(R.id.textInputEditTextProdutoraJogos);
        spinnerGeneroJogos = view.findViewById(R.id.spinnerGeneroJogos);

        SingletonGenerosJogo.getInstance(getActivity().getApplicationContext()).setGenerosJogosListener(this);

        ArrayList<GeneroJogo> generosJogos = SingletonGenerosJogo.getInstance(getActivity().getApplicationContext()).getGeneroJogos();

        if(!generosJogos.isEmpty()) {
            ArrayAdapter<GeneroJogo> spinnerGeneros = new ArrayAdapter<>(getActivity().getApplicationContext(),
                    R.layout.custom_spinner_item,
                    generosJogos);

            spinnerGeneros.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spinnerGeneroJogos.setAdapter(spinnerGeneros);
        }
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

    @Override
    public void onErrorGenerosJogosAPI(String message, Exception ex) {
        ex.printStackTrace();
        Toast.makeText(getActivity().getApplicationContext(), message, Toast.LENGTH_LONG).show();
    }

    @Override
    public void onRefreshGenerosJogos(ArrayList<GeneroJogo> generosJogos, Context context) {
        ArrayAdapter<GeneroJogo> spinnerGeneros = new ArrayAdapter<>(getActivity().getApplicationContext(),
                R.layout.custom_spinner_item,
                generosJogos);

        spinnerGeneros.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerGeneroJogos.setAdapter(spinnerGeneros);
    }
}
