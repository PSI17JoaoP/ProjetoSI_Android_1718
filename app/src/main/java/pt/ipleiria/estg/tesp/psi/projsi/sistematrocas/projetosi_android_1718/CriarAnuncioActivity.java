package pt.ipleiria.estg.tesp.psi.projsi.sistematrocas.projetosi_android_1718;

import android.annotation.SuppressLint;
import android.support.annotation.IdRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.NumberPicker;
import android.widget.Spinner;

import java.time.LocalDate;
import java.util.HashMap;

import pt.ipleiria.estg.tesp.psi.projsi.sistematrocas.projetosi_android_1718.forms.FormManager;
import pt.ipleiria.estg.tesp.psi.projsi.sistematrocas.projetosi_android_1718.modelos.Anuncio;
import pt.ipleiria.estg.tesp.psi.projsi.sistematrocas.projetosi_android_1718.modelos.Brinquedo;
import pt.ipleiria.estg.tesp.psi.projsi.sistematrocas.projetosi_android_1718.modelos.Categoria;
import pt.ipleiria.estg.tesp.psi.projsi.sistematrocas.projetosi_android_1718.modelos.Computador;
import pt.ipleiria.estg.tesp.psi.projsi.sistematrocas.projetosi_android_1718.modelos.Eletronica;
import pt.ipleiria.estg.tesp.psi.projsi.sistematrocas.projetosi_android_1718.modelos.Jogo;
import pt.ipleiria.estg.tesp.psi.projsi.sistematrocas.projetosi_android_1718.modelos.Livro;
import pt.ipleiria.estg.tesp.psi.projsi.sistematrocas.projetosi_android_1718.modelos.Roupa;
import pt.ipleiria.estg.tesp.psi.projsi.sistematrocas.projetosi_android_1718.modelos.Smartphone;
import pt.ipleiria.estg.tesp.psi.projsi.sistematrocas.projetosi_android_1718.singletons.SingletonCategorias;

public class CriarAnuncioActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener, View.OnClickListener {

    private HashMap<Integer, String> categoriasHashMap;

    @SuppressLint("UseSparseArrays")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_criar_anuncio);
        Toolbar toolbar = findViewById(R.id.toolbarCriarAnuncio);
        setSupportActionBar(toolbar);

        String[] categoriasValues = getResources().getStringArray(R.array.categorias_values);
        String[] categoriasKeys = getResources().getStringArray(R.array.categorias_keys);

        categoriasHashMap = new HashMap<>();

        for (int cont = 0; cont < categoriasKeys.length; cont++) {
                categoriasHashMap.put(cont, categoriasKeys[cont]);
        }

        //Array Adapter das categorias guardadas nos recursos XML
        ArrayAdapter<CharSequence> spinnerCategorias = new ArrayAdapter<CharSequence>(this,
                R.layout.custom_spinner_item,
                categoriasValues);

        //---------------------------------Dropdowns (Spinners)---------------------------
        Spinner dropDownCategoriasTroco = findViewById(R.id.dropDownCategoriasTroco);

        spinnerCategorias.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        dropDownCategoriasTroco.setAdapter(spinnerCategorias);
        dropDownCategoriasTroco.setOnItemSelectedListener(this);

        Spinner dropDownCategoriasPor = findViewById(R.id.dropDownCategoriasPor);

        spinnerCategorias.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        dropDownCategoriasPor.setAdapter(spinnerCategorias);
        dropDownCategoriasPor.setOnItemSelectedListener(this);

        //--------------------------------------------------------------------------------

        //------------------------------------Number Pickers-------------------------------------
        NumberPicker numberPickerCategoriaTroco = findViewById(R.id.numberPickerCategoriaTroco);

        numberPickerCategoriaTroco.setMinValue(1);
        numberPickerCategoriaTroco.setMaxValue(99);
        numberPickerCategoriaTroco.setValue(1);
        numberPickerCategoriaTroco.setWrapSelectorWheel(false);

        NumberPicker numberPickerCategoriaPor = findViewById(R.id.numberPickerCategoriaPor);

        numberPickerCategoriaPor.setMinValue(1);
        numberPickerCategoriaPor.setMaxValue(99);
        numberPickerCategoriaPor.setValue(1);
        numberPickerCategoriaPor.setWrapSelectorWheel(false);
        //---------------------------------------------------------------------------------------

        //----------------------------------Botão Flutuante--------------------------------------
        FloatingActionButton fabCriarAnuncio = findViewById(R.id.fabCriarAnuncio);
        fabCriarAnuncio.setOnClickListener(this);
        //---------------------------------------------------------------------------------------
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id)  {

        switch (parent.getId()) {
            case R.id.dropDownCategoriasTroco:

                printForm(position, R.id.fragmentFormCategoriaTroco);

                break;

            case R.id.dropDownCategoriasPor:

                printForm(position, R.id.fragmentFormCategoriaPor);

                break;
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent)  {

    }

    private void showNotification(String message) {
        Snackbar snackbar = Snackbar
                .make(findViewById(R.id.coordinatorLayoutCriarAnuncio), message, Snackbar.LENGTH_LONG);

        snackbar.show();
    }

    private void printForm(Integer position, @IdRes int containerId) {

        FormManager formManager = new FormManager();

        FragmentManager manager = getSupportFragmentManager();

        if(position > 0) {

            String categoria = categoriasHashMap.get(position);

            try {

                Fragment form = formManager.selectForm(categoria, getResources().getStringArray(R.array.categorias_keys),
                        getApplicationContext());

                if (form != null) {
                    FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();

                    FrameLayout fragmentContainer = findViewById(containerId);

                    if (fragmentContainer.getChildCount() == 0) {
                        transaction.add(containerId, form, categoria).commit();
                    } else {
                        if (manager.findFragmentByTag(categoria) == null) {
                            transaction.replace(containerId, form, categoria).commit();
                        }
                    }
                }

            } catch (ClassNotFoundException e) {
                e.printStackTrace();
                showNotification("Ocorreu um erro na seleção da categoria.");
            }
        } else {

            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();

            FrameLayout fragmentContainer = findViewById(containerId);

            if (fragmentContainer.getChildCount() != 0) {
                Fragment fragment = manager.findFragmentById(containerId);

                if(fragment != null) {
                    transaction.remove(fragment).commit();
                }
            }
        }
    }

    @Override
    public void onClick(View view) {

        EditText editTextAnuncioTitulo = findViewById(R.id.editTextCriarAnuncioTitulo);
        EditText editTextNomeCategoriaTroco = findViewById(R.id.editTextCriarAnuncioTrocoNomeCategoria);
        EditText editTextNomeCategoriaPor = findViewById(R.id.editTextCriarAnuncioPorNomeCategoria);

        NumberPicker numberPickerCategoriaTroco = findViewById(R.id.numberPickerCategoriaTroco);
        NumberPicker numberPickerCategoriaPor = findViewById(R.id.numberPickerCategoriaPor);

        FrameLayout fragmentContainerFormTroco = findViewById(R.id.fragmentFormCategoriaTroco);
        FrameLayout fragmentContainerFormPor = findViewById(R.id.fragmentFormCategoriaPor);

        String anuncioTitulo = editTextAnuncioTitulo.getText().toString().trim();
        String nomeCategoriaTroco = editTextNomeCategoriaTroco.getText().toString().trim();
        String nomeCategoriaPor = editTextNomeCategoriaPor.getText().toString().trim();

        FragmentManager fragmentManager = getSupportFragmentManager();

        if(!anuncioTitulo.isEmpty()) {

            if(!nomeCategoriaTroco.isEmpty() && fragmentContainerFormTroco.getChildCount() != 0) {

                if (!nomeCategoriaPor.isEmpty() && fragmentContainerFormPor.getChildCount() != 0) {

                    Anuncio novoAnuncio = criarAnuncio(fragmentManager, anuncioTitulo,
                            R.id.fragmentFormCategoriaTroco, nomeCategoriaTroco, numberPickerCategoriaTroco.getValue(),
                            R.id.fragmentFormCategoriaPor, nomeCategoriaPor, numberPickerCategoriaPor.getValue());
                }

                else if(nomeCategoriaPor.isEmpty() && fragmentContainerFormPor.getChildCount() != 0) {
                    showNotification("Preencha o nome do bem a receber.\nSe não pretender receber um bem especifico, deselecione a categoria do bem a receber.");
                }

                else if(!nomeCategoriaPor.isEmpty() && fragmentContainerFormPor.getChildCount() == 0) {
                    showNotification("Selecione a categoria do bem a receber.\nSe não pretender receber um bem especifico, remova o nome do bem a receber.");
                }

                else {
                    Anuncio novoAnuncio = criarAnuncio(fragmentManager, anuncioTitulo,
                            R.id.fragmentFormCategoriaTroco, nomeCategoriaTroco, numberPickerCategoriaTroco.getValue(),
                            null, null, null);
                }
            }

            else if(nomeCategoriaTroco.isEmpty()) {
                showNotification("Preencha o nome do bem a trocar.");
            }

            else if(fragmentContainerFormTroco.getChildCount() == 0) {
                showNotification("Selecione a categoria do bem a oferecer.");
            }
        }

        else {
            showNotification("Preencha o titulo do anúncio.");
        }
    }

    //Método que obtém o anúncio, juntamente com as categorias que obtém dos fragmentos, e regista-o na BD e API.
    private Anuncio criarAnuncio(@NonNull FragmentManager fragmentManager, @NonNull String anuncioTitulo,
                                @IdRes @NonNull Integer containerIdFormTroco, @NonNull String nomeCategoriaTroco, @NonNull Integer quantidadeCategoriaTroco,
                                @IdRes @Nullable Integer containerIdFormPor, @Nullable String nomeCategoriaPor, @Nullable Integer quantidadeCategoriaPor) {

        Anuncio novoAnuncio = null;

        FormManager formManager = new FormManager();

        if(containerIdFormPor != null && nomeCategoriaPor != null && quantidadeCategoriaPor != null) {
            Categoria categoriaTroco = getCategoria(fragmentManager, formManager, containerIdFormTroco, nomeCategoriaTroco);
            Categoria categoriaPor = getCategoria(fragmentManager, formManager, containerIdFormPor, nomeCategoriaPor);

            novoAnuncio = getCategoriaTroco(anuncioTitulo, categoriaTroco, quantidadeCategoriaTroco, categoriaPor, quantidadeCategoriaPor);
        }

        else if(containerIdFormPor == null && nomeCategoriaPor == null && quantidadeCategoriaPor == null) {
            Categoria categoriaTroco = getCategoria(fragmentManager, formManager, containerIdFormTroco, nomeCategoriaTroco);

            novoAnuncio = getCategoriaTroco(anuncioTitulo, categoriaTroco, quantidadeCategoriaTroco, null, null);
        }

        return novoAnuncio;
    }

    //Método que obtém a categoria do fragment, através do método getCategoria do objeto do tipo FormManager.
    private Categoria getCategoria(@NonNull FragmentManager fragmentManager, @NonNull FormManager formManager,
                                   @IdRes @NonNull Integer containerIdForm, @NonNull String nomeCategoria) {

        Categoria categoria = null;

        Fragment fragmentForm = fragmentManager.findFragmentById(containerIdForm);

        try {
            categoria = formManager.getCategoria(fragmentManager, fragmentForm, getResources().getStringArray(R.array.categorias_keys), nomeCategoria, getApplicationContext());
        }

        catch (RuntimeException ex) {
            showNotification(ex.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
            showNotification("Ocorreu um erro inesperado no processamento dos formulários.");
        }

        return categoria;
    }

    private Anuncio getCategoriaTroco(@NonNull String anuncioTitulo,
                                      @NonNull Categoria categoriaTroco, @NonNull Integer quantidadeTroco,
                                      @Nullable Categoria categoriaPor, @Nullable Integer quantidadePor) {

        Anuncio novoAnuncio = null;

        String categoriaTrocoNome = categoriaTroco.getClass().getName();

        switch (categoriaTrocoNome) {

            case "Jogo":

                Jogo jogoTroco = (Jogo) SingletonCategorias.getInstance(this).adicionarCategoria(categoriaTroco);

                if(jogoTroco != null) {
                    if (categoriaPor != null && quantidadePor != null) {
                        novoAnuncio = getCategoriaPor(anuncioTitulo, jogoTroco, quantidadeTroco, categoriaPor, quantidadePor);
                    } else if (categoriaPor == null && quantidadePor == null) {
                        novoAnuncio = getAnuncio(anuncioTitulo, jogoTroco, quantidadeTroco, null, null);
                    }
                }

            case "Brinquedo":

                /*if(categoriaPor != null && quantidadePor != null) {
                    getCategoriaPor(anuncioTitulo, categoriaTroco, quantidadeTroco, categoriaPor, quantidadePor);
                } else if (categoriaPor == null && quantidadePor == null){

                }*/

            case "Computador":

                /*if(categoriaPor != null && quantidadePor != null) {
                    getCategoriaPor(anuncioTitulo, categoriaTroco, quantidadeTroco, categoriaPor, quantidadePor);
                } else if (categoriaPor == null && quantidadePor == null){

                }*/

            case "Smartphone":

                /*if(categoriaPor != null && quantidadePor != null) {
                    getCategoriaPor(anuncioTitulo, categoriaTroco, quantidadeTroco, categoriaPor, quantidadePor);
                } else if (categoriaPor == null && quantidadePor == null){

                }*/

            case "Eletronica":

                /*if(categoriaPor != null && quantidadePor != null) {
                    getCategoriaPor(anuncioTitulo, categoriaTroco, quantidadeTroco, categoriaPor, quantidadePor);
                } else if (categoriaPor == null && quantidadePor == null){

                }*/

            case "Roupa":

                /*if(categoriaPor != null && quantidadePor != null) {
                    getCategoriaPor(anuncioTitulo, categoriaTroco, quantidadeTroco, categoriaPor, quantidadePor);
                } else if (categoriaPor == null && quantidadePor == null){

                }*/

            case "Livro":

                /*if(categoriaPor != null && quantidadePor != null) {
                    getCategoriaPor(anuncioTitulo, categoriaTroco, quantidadeTroco, categoriaPor, quantidadePor);
                } else if (categoriaPor == null && quantidadePor == null){

                }*/
        }

        return novoAnuncio;
    }

    private Anuncio getCategoriaPor(@NonNull String anuncioTitulo,
                                    @NonNull Categoria categoriaTroco, @NonNull Integer quantidadeTroco,
                                    @NonNull Categoria categoriaPor, @NonNull Integer quantidadePor) {

        Anuncio novoAnuncio = null;

        String categoriaPorNome = categoriaPor.getClass().getName();

        switch (categoriaPorNome) {

            case "Jogo":

                Jogo jogoPor = (Jogo) SingletonCategorias.getInstance(this).adicionarCategoria(categoriaPor);

                if(jogoPor != null) {
                    novoAnuncio = getAnuncio(anuncioTitulo, categoriaTroco, quantidadeTroco, jogoPor, quantidadePor);
                }

            case "Brinquedo":

            case "Computador":

            case "Smartphone":

            case "Eletronica":

            case "Roupa":

            case "Livro":
        }

        return novoAnuncio;
    }

    private Anuncio getAnuncio(@NonNull String anuncioTitulo,
                               @NonNull Categoria categoriaTroco, @NonNull Integer quantidadeTroco,
                               @Nullable Categoria categoriaPor, @Nullable Integer quantidadePor) {

        if (categoriaPor != null && quantidadePor != null) {

            //TODO: Falta arranjar o ID do User, cujo deve ser passado no intent para esta activity. De momento, está um ID estático.
            //TODO: Por alguma razão, não consigo utilizar a classe LocalDate para arranjar a data atual.
            //TODO: Se calhar vai-se ter de eliminar os comentários, visto que até faria mais sentido não estar no Anúncio.
            return new Anuncio(anuncioTitulo, 1L, categoriaTroco.getId(), quantidadeTroco, categoriaPor.getId(), quantidadePor, "ABERTO", , );
        } else if (categoriaPor == null && quantidadePor == null) {
            return new Anuncio(anuncioTitulo, 1L, categoriaTroco.getId(), quantidadeTroco, null, null, "ABERTO", , );
        }
    }
}
