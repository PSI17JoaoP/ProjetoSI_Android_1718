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

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;

import pt.ipleiria.estg.tesp.psi.projsi.sistematrocas.projetosi_android_1718.forms.FormManager;
import pt.ipleiria.estg.tesp.psi.projsi.sistematrocas.projetosi_android_1718.listeners.AnunciosListener;
import pt.ipleiria.estg.tesp.psi.projsi.sistematrocas.projetosi_android_1718.modelos.Anuncio;
import pt.ipleiria.estg.tesp.psi.projsi.sistematrocas.projetosi_android_1718.modelos.Categoria;

import pt.ipleiria.estg.tesp.psi.projsi.sistematrocas.projetosi_android_1718.singletons.SingletonActivityAPIResponse;
import pt.ipleiria.estg.tesp.psi.projsi.sistematrocas.projetosi_android_1718.singletons.SingletonAnuncios;
import pt.ipleiria.estg.tesp.psi.projsi.sistematrocas.projetosi_android_1718.singletons.SingletonCategorias;

public class CriarAnuncioActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener, View.OnClickListener, AnunciosListener {

    private HashMap<Integer, String> categoriasHashMap;

    private Anuncio anuncio;

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
        numberPickerCategoriaTroco.setWrapSelectorWheel(true);

        NumberPicker numberPickerCategoriaPor = findViewById(R.id.numberPickerCategoriaPor);

        numberPickerCategoriaPor.setMinValue(1);
        numberPickerCategoriaPor.setMaxValue(99);
        numberPickerCategoriaPor.setValue(1);
        numberPickerCategoriaPor.setWrapSelectorWheel(true);
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

        Spinner dropDownCategoriasTroco = findViewById(R.id.dropDownCategoriasTroco);
        Spinner dropDownCategoriasPor = findViewById(R.id.dropDownCategoriasPor);

        FrameLayout fragmentContainerFormTroco = findViewById(R.id.fragmentFormCategoriaTroco);
        FrameLayout fragmentContainerFormPor = findViewById(R.id.fragmentFormCategoriaPor);

        String anuncioTitulo = editTextAnuncioTitulo.getText().toString().trim();
        String nomeCategoriaTroco = editTextNomeCategoriaTroco.getText().toString().trim();
        String nomeCategoriaPor = editTextNomeCategoriaPor.getText().toString().trim();

        FragmentManager fragmentManager = getSupportFragmentManager();

        if(!anuncioTitulo.isEmpty()) {

            if(!nomeCategoriaTroco.isEmpty() && fragmentContainerFormTroco.getChildCount() != 0) {

                if (!nomeCategoriaPor.isEmpty() && fragmentContainerFormPor.getChildCount() != 0) {

                    criarAnuncio(fragmentManager, anuncioTitulo,
                            R.id.fragmentFormCategoriaTroco, nomeCategoriaTroco, dropDownCategoriasTroco.getSelectedItemPosition(), numberPickerCategoriaTroco.getValue(),
                            R.id.fragmentFormCategoriaPor, nomeCategoriaPor, dropDownCategoriasPor.getSelectedItemPosition(), numberPickerCategoriaPor.getValue());
                }

                else if(nomeCategoriaPor.isEmpty() && fragmentContainerFormPor.getChildCount() != 0) {
                    showNotification("Preencha o nome do bem a receber.\nSe não pretender receber um bem especifico,\ndeselecione a categoria do bem a receber.");
                }

                else if(!nomeCategoriaPor.isEmpty() && fragmentContainerFormPor.getChildCount() == 0) {
                    showNotification("Selecione a categoria do bem a receber.\nSe não pretender receber um bem especifico,\nremova o nome do bem a receber.");
                }

                else {
                    criarAnuncio(fragmentManager, anuncioTitulo,
                            R.id.fragmentFormCategoriaTroco, nomeCategoriaTroco, dropDownCategoriasTroco.getSelectedItemPosition(), numberPickerCategoriaTroco.getValue(),
                            null, null, null, null);
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
    private void criarAnuncio(@NonNull FragmentManager fragmentManager, @NonNull String anuncioTitulo,
                                @IdRes @NonNull Integer containerIdFormTroco, @NonNull String nomeCategoriaTroco, @NonNull Integer categoriaTrocoKeyPosition, @NonNull Integer quantidadeCategoriaTroco,
                                @IdRes @Nullable Integer containerIdFormPor, @Nullable String nomeCategoriaPor, @Nullable Integer categoriaPorKeyPosition, @Nullable Integer quantidadeCategoriaPor) {

        FormManager formManager = new FormManager();

        if(containerIdFormPor != null && nomeCategoriaPor != null && quantidadeCategoriaPor != null && categoriaPorKeyPosition != null) {
            Categoria categoriaTroco = getCategoria(fragmentManager, formManager, containerIdFormTroco, nomeCategoriaTroco, categoriaTrocoKeyPosition);
            Categoria categoriaPor = getCategoria(fragmentManager, formManager, containerIdFormPor, nomeCategoriaPor, categoriaPorKeyPosition);

            getCategoriaTroco(anuncioTitulo, categoriaTroco, quantidadeCategoriaTroco, categoriaPor, quantidadeCategoriaPor);
        }

        else if(containerIdFormPor == null && nomeCategoriaPor == null && quantidadeCategoriaPor == null) {
            Categoria categoriaTroco = getCategoria(fragmentManager, formManager, containerIdFormTroco, nomeCategoriaTroco, categoriaTrocoKeyPosition);

            getCategoriaTroco(anuncioTitulo, categoriaTroco, quantidadeCategoriaTroco, null, null);
        }
    }

    //Método que obtém a categoria do fragment, através do método getCategoria do objeto do tipo FormManager.
    private Categoria getCategoria(@NonNull FragmentManager fragmentManager, @NonNull FormManager formManager,
                                   @IdRes @NonNull Integer containerIdForm, @NonNull String nomeCategoria, @NonNull Integer categoriaKeyPosition) {

        Categoria categoria = null;

        Fragment fragmentForm = fragmentManager.findFragmentById(containerIdForm);

        String categoriaKey = categoriasHashMap.get(categoriaKeyPosition);

        try {
            categoria = formManager.getCategoria(fragmentForm, getResources().getStringArray(R.array.categorias_keys), categoriaKey, nomeCategoria, getApplicationContext());
        }
        catch (RuntimeException ex) {
            showNotification(ex.getMessage());
        }
        catch (Exception e) {
            e.printStackTrace();
            showNotification("Ocorreu um erro inesperado no processamento dos formulários.");
        }

        return categoria;
    }

    private void getCategoriaTroco(@NonNull final String anuncioTitulo,
                                      @NonNull final Categoria categoriaTroco, @NonNull final Integer quantidadeTroco,
                                      @Nullable final Categoria categoriaPor, @Nullable final Integer quantidadePor) {

        SingletonCategorias.getInstance(this).adicionarCategoria(categoriaTroco, new SingletonActivityAPIResponse() {
            @Override
            public void onSuccessEnvioAPI(Categoria categoria) {
                if (categoriaPor != null && quantidadePor != null) {
                    getCategoriaPor(anuncioTitulo, categoria, quantidadeTroco, categoriaPor, quantidadePor);
                } else if (categoriaPor == null && quantidadePor == null) {
                    getAnuncio(anuncioTitulo, categoria, quantidadeTroco, null, null);
                }
            }

            @Override
            public void onErrorEnvioAPI(String message, Exception ex) {
                ex.printStackTrace();
                showNotification(message);
            }
        });
    }

    private void getCategoriaPor(@NonNull final String anuncioTitulo,
                                 @NonNull final Categoria categoriaTroco, @NonNull final Integer quantidadeTroco,
                                 @NonNull final Categoria categoriaPor, @NonNull final Integer quantidadePor) {

        SingletonCategorias.getInstance(this).adicionarCategoria(categoriaPor, new SingletonActivityAPIResponse() {
            @Override
            public void onSuccessEnvioAPI(Categoria categoria) {
                getAnuncio(anuncioTitulo, categoriaTroco, quantidadeTroco, categoria, quantidadePor);
            }

            @Override
            public void onErrorEnvioAPI(String message, Exception ex) {
                ex.printStackTrace();
                showNotification(message);
            }
        });
    }

    private void getAnuncio(@NonNull String anuncioTitulo,
                               @NonNull Categoria categoriaTroco, @NonNull Integer quantidadeTroco,
                               @Nullable Categoria categoriaPor, @Nullable Integer quantidadePor) {

        Anuncio anuncio = null;

        if (categoriaPor != null && quantidadePor != null) {

            //TODO: ID do user autenticado passado pelo Intent.
            //TODO: Implementação dos comentários.

            anuncio = new Anuncio(anuncioTitulo, 1L, categoriaTroco.getId(), quantidadeTroco, categoriaPor.getId(), quantidadePor, "ABERTO", Calendar.getInstance().getTime().toString(), "PLACEHOLDER");
        } else if (categoriaPor == null && quantidadePor == null) {
            anuncio = new Anuncio(anuncioTitulo, 1L, categoriaTroco.getId(), quantidadeTroco, null, null, "ABERTO", Calendar.getInstance().getTime().toString(), "PLACEHOLDER");
        }

        if (anuncio != null) {
            saveAnuncio(anuncio);
        }
    }

    private void saveAnuncio(@NonNull Anuncio anuncio) {

        //Guardada como variável global, para ser visivel no onRefreshAnuncios.
        this.anuncio = anuncio;

        SingletonAnuncios.getInstance(this).setAnunciosListener(this);

        ArrayList<Anuncio> anuncios = SingletonAnuncios.getInstance(this).getAnuncios();

        if(!anuncios.isEmpty()) {
            if (!anuncios.contains(anuncio)) {
                SingletonAnuncios.getInstance(this).adicionarAnuncio(anuncio);
            }
        }

        //TODO: Intent para a actividade dos visualização dos anúncios do user autenticado.
    }

    @Override
    public void onSuccessAnunciosAPI(Anuncio anuncio) {
        showNotification("SUCCESS - Envio do anúncio" + anuncio.getTitulo() + "para a API");
    }

    @Override
    public void onErrorAnunciosAPI(String message, Exception ex) {
        ex.printStackTrace();
        showNotification(message);
    }

    @Override
    public void onRefreshAnuncios(ArrayList<Anuncio> anuncios) {
        if(!anuncios.contains(anuncio)) {
            SingletonAnuncios.getInstance(this).adicionarAnuncio(anuncio);
        }
    }

    @Override
    public void onUpdateAnuncios(Anuncio anuncio, int acao) {

    }
}
