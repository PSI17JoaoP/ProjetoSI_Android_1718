package pt.ipleiria.estg.tesp.psi.projsi.sistematrocas.projetosi_android_1718;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.annotation.IdRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.NumberPicker;
import android.widget.Spinner;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;

import pt.ipleiria.estg.tesp.psi.projsi.sistematrocas.projetosi_android_1718.fragments.MyFragmentManager;
import pt.ipleiria.estg.tesp.psi.projsi.sistematrocas.projetosi_android_1718.listeners.AnunciosListener;
import pt.ipleiria.estg.tesp.psi.projsi.sistematrocas.projetosi_android_1718.modelos.Anuncio;
import pt.ipleiria.estg.tesp.psi.projsi.sistematrocas.projetosi_android_1718.modelos.Categoria;

import pt.ipleiria.estg.tesp.psi.projsi.sistematrocas.projetosi_android_1718.singletons.SingletonActivityAPIResponse;
import pt.ipleiria.estg.tesp.psi.projsi.sistematrocas.projetosi_android_1718.singletons.SingletonAnuncios;
import pt.ipleiria.estg.tesp.psi.projsi.sistematrocas.projetosi_android_1718.singletons.SingletonCategorias;

public class CriarAnuncioActivity extends NavDrawerActivity implements AdapterView.OnItemSelectedListener, View.OnClickListener, AnunciosListener {

    private HashMap<Integer, String> categoriasHashMap;
    private FragmentManager fragmentManager;
    private Anuncio anuncio;

    @SuppressLint("UseSparseArrays")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_criar_anuncio);
        View.inflate(this, R.layout.activity_criar_anuncio, (ViewGroup) findViewById(R.id.app_content));

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

        fragmentManager = getSupportFragmentManager();

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

        if(position > 0) {

            String categoria = categoriasHashMap.get(position);

            try {

                Fragment form = MyFragmentManager.getFragment(categoria, "Form", getResources().getStringArray(R.array.categorias_keys),
                        getApplicationContext());

                if (form != null) {
                    FragmentTransaction transaction = fragmentManager.beginTransaction();

                    FrameLayout fragmentContainer = findViewById(containerId);

                    if (fragmentContainer.getChildCount() == 0) {
                        transaction.add(containerId, form, categoria).commit();
                    } else {
                        if (fragmentManager.findFragmentByTag(categoria) == null) {
                            transaction.replace(containerId, form, categoria).commit();
                        }
                    }
                }

            } catch (ClassNotFoundException e) {
                e.printStackTrace();
                showNotification("Ocorreu um erro na seleção da categoria.");
            }
        } else {

            FragmentTransaction transaction = fragmentManager.beginTransaction();

            FrameLayout fragmentContainer = findViewById(containerId);

            if (fragmentContainer.getChildCount() != 0) {
                Fragment fragment = fragmentManager.findFragmentById(containerId);

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

        if(!anuncioTitulo.isEmpty()) {

            if(!nomeCategoriaTroco.isEmpty() && fragmentContainerFormTroco.getChildCount() != 0) {

                if (!nomeCategoriaPor.isEmpty() && fragmentContainerFormPor.getChildCount() != 0) {

                    criarAnuncio(anuncioTitulo,  R.id.fragmentFormCategoriaTroco, nomeCategoriaTroco, dropDownCategoriasTroco.getSelectedItemPosition(), numberPickerCategoriaTroco.getValue(),
                            R.id.fragmentFormCategoriaPor, nomeCategoriaPor, dropDownCategoriasPor.getSelectedItemPosition(), numberPickerCategoriaPor.getValue());
                }

                else if(nomeCategoriaPor.isEmpty() && fragmentContainerFormPor.getChildCount() != 0) {
                    showNotification("Preencha o nome do bem a receber.\nSe não pretender receber um bem especifico,\ndeselecione a categoria do bem a receber.");
                }

                else if(!nomeCategoriaPor.isEmpty() && fragmentContainerFormPor.getChildCount() == 0) {
                    showNotification("Selecione a categoria do bem a receber.\nSe não pretender receber um bem especifico,\nremova o nome do bem a receber.");
                }

                else {
                    criarAnuncio(anuncioTitulo, R.id.fragmentFormCategoriaTroco, nomeCategoriaTroco, dropDownCategoriasTroco.getSelectedItemPosition(), numberPickerCategoriaTroco.getValue(),
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
    private void criarAnuncio(@NonNull String anuncioTitulo, @IdRes @NonNull Integer containerIdFormTroco, @NonNull String nomeCategoriaTroco, @NonNull Integer categoriaTrocoKeyPosition, @NonNull Integer quantidadeCategoriaTroco,
                                @IdRes @Nullable Integer containerIdFormPor, @Nullable String nomeCategoriaPor, @Nullable Integer categoriaPorKeyPosition, @Nullable Integer quantidadeCategoriaPor) {

        if(containerIdFormPor != null && nomeCategoriaPor != null && quantidadeCategoriaPor != null && categoriaPorKeyPosition != null) {
            Categoria categoriaTroco = getCategoria(containerIdFormTroco, nomeCategoriaTroco, categoriaTrocoKeyPosition);
            Categoria categoriaPor = getCategoria(containerIdFormPor, nomeCategoriaPor, categoriaPorKeyPosition);

            getCategoriaTroco(anuncioTitulo, categoriaTroco, quantidadeCategoriaTroco, categoriaPor, quantidadeCategoriaPor);
        }

        else if(containerIdFormPor == null && nomeCategoriaPor == null && quantidadeCategoriaPor == null) {
            Categoria categoriaTroco = getCategoria(containerIdFormTroco, nomeCategoriaTroco, categoriaTrocoKeyPosition);

            getCategoriaTroco(anuncioTitulo, categoriaTroco, quantidadeCategoriaTroco, null, null);
        }
    }

    //Método que obtém a categoria do fragment, através do método getCategoria da classe MyFragmentManager.
    private Categoria getCategoria(@IdRes @NonNull Integer containerIdForm, @NonNull String nomeCategoria, @NonNull Integer categoriaKeyPosition) {

        Categoria categoria = null;

        Fragment fragmentForm = fragmentManager.findFragmentById(containerIdForm);

        String categoriaKey = categoriasHashMap.get(categoriaKeyPosition);

        try {
            categoria = MyFragmentManager.getCategoria(fragmentForm, getResources().getStringArray(R.array.categorias_keys), categoriaKey, nomeCategoria, getApplicationContext());
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

        SingletonCategorias.getInstance().adicionarCategoria(categoriaTroco, getApplicationContext(), new SingletonActivityAPIResponse() {
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

        SingletonCategorias.getInstance().adicionarCategoria(categoriaPor, getApplicationContext(), new SingletonActivityAPIResponse() {
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

        SharedPreferences preferences = getSharedPreferences("APP_SETTINGS", Context.MODE_PRIVATE);
        Long ID = preferences.getLong("id", 0);

        Calendar c = Calendar.getInstance();
        SimpleDateFormat df = new SimpleDateFormat("yyyy-M-dd");
        String data = df.format(c.getTime());

        if (categoriaPor != null && quantidadePor != null) {
            anuncio = new Anuncio(anuncioTitulo, ID, categoriaTroco.getId(), quantidadeTroco, categoriaPor.getId(), quantidadePor, "ATIVO", data , "PLACEHOLDER");
        } else if (categoriaPor == null && quantidadePor == null) {
            anuncio = new Anuncio(anuncioTitulo, ID, categoriaTroco.getId(), quantidadeTroco, null, null, "ATIVO", data, "PLACEHOLDER");
        }

        if (anuncio != null) {
            saveAnuncio(anuncio);
        }
    }

    private void saveAnuncio(@NonNull Anuncio anuncio) {

        //Guardada como variável global, para ser visivel no onRefreshAnuncios.
        this.anuncio = anuncio;

        SingletonAnuncios.getInstance(getApplicationContext()).setAnunciosListener(this);

        ArrayList<Anuncio> anuncios = SingletonAnuncios.getInstance(getApplicationContext()).getAnuncios();

        if(!anuncios.isEmpty()) {
            if (!anuncios.contains(anuncio)) {
                SingletonAnuncios.getInstance(getApplicationContext()).adicionarAnuncio(anuncio, getApplicationContext());
            }
        }
    }

    @Override
    public void onSuccessAnunciosAPI(Anuncio anuncio) {
        showNotification("Criação do anúncio " + anuncio.getTitulo() + " com sucesso.");
        Intent intent = new Intent(getApplicationContext(), MeusAnunciosActivity.class);
        startActivity(intent);
    }

    @Override
    public void onErrorAnunciosAPI(String message, Exception ex) {
        ex.printStackTrace();
        showNotification(message);
    }

    @Override
    public void onRefreshAnuncios(ArrayList<Anuncio> anuncios) {
        if(!anuncios.contains(anuncio)) {
            SingletonAnuncios.getInstance(getApplicationContext()).adicionarAnuncio(anuncio, getApplicationContext());
        }
    }
}
