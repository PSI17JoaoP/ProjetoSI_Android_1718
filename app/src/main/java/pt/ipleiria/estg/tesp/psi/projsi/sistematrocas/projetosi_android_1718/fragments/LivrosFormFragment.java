package pt.ipleiria.estg.tesp.psi.projsi.sistematrocas.projetosi_android_1718.fragments;

import pt.ipleiria.estg.tesp.psi.projsi.sistematrocas.projetosi_android_1718.R;
import pt.ipleiria.estg.tesp.psi.projsi.sistematrocas.projetosi_android_1718.modelos.Livro;

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

public class LivrosFormFragment extends Fragment {

    private TextInputEditText textInputEditTextTituloLivros;
    private TextInputEditText textInputEditTextEditoraLivros;
    private TextInputEditText textInputEditTextAutorLivros;
    private EditText editTextISBNLivros;

    public LivrosFormFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_livros_form, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        textInputEditTextTituloLivros = view.findViewById(R.id.textInputEditTextTituloLivros);
        textInputEditTextEditoraLivros = view.findViewById(R.id.textInputEditTextEditoraLivros);
        textInputEditTextAutorLivros = view.findViewById(R.id.textInputEditTextAutorLivros);
        editTextISBNLivros = view.findViewById(R.id.editTextISBNLivros);
    }

    @Override
    public void onDestroyView() {
        textInputEditTextTituloLivros = null;
        textInputEditTextEditoraLivros = null;
        textInputEditTextAutorLivros = null;
        editTextISBNLivros = null;
        super.onDestroyView();
    }

    public Livro getCategoria(String nome) throws NumberFormatException {

        String titulo = textInputEditTextTituloLivros.getText().toString().trim();
        String editora = textInputEditTextEditoraLivros.getText().toString().trim();
        String autor = textInputEditTextAutorLivros.getText().toString().trim();
        String isbn = editTextISBNLivros.getText().toString().trim();

        if(!nome.isEmpty() && !titulo.isEmpty() && !editora.isEmpty() && !autor.isEmpty() && !isbn.isEmpty()) {
            try {
                return new Livro(nome, titulo, editora, autor, Integer.valueOf(isbn));
            } catch (NumberFormatException e) {
                e.printStackTrace();
                throw new RuntimeException("Ocorreu um erro no processamento do formulário.\nVerifique o ISBN do formulário de Livros correspondente.");
            }
        }

        return null;
    }
}
