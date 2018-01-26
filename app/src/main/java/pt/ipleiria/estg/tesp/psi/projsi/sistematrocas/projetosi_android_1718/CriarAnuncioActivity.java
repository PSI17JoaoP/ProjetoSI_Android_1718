package pt.ipleiria.estg.tesp.psi.projsi.sistematrocas.projetosi_android_1718;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.provider.MediaStore;
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
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.NumberPicker;
import android.widget.Spinner;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.ImageViewTarget;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;

import pt.ipleiria.estg.tesp.psi.projsi.sistematrocas.projetosi_android_1718.fragments.MyFragmentManager;
import pt.ipleiria.estg.tesp.psi.projsi.sistematrocas.projetosi_android_1718.listeners.AnunciosListener;
import pt.ipleiria.estg.tesp.psi.projsi.sistematrocas.projetosi_android_1718.listeners.ImagesListener;
import pt.ipleiria.estg.tesp.psi.projsi.sistematrocas.projetosi_android_1718.modelos.Anuncio;
import pt.ipleiria.estg.tesp.psi.projsi.sistematrocas.projetosi_android_1718.modelos.Categoria;

import pt.ipleiria.estg.tesp.psi.projsi.sistematrocas.projetosi_android_1718.modelos.ImageManager;
import pt.ipleiria.estg.tesp.psi.projsi.sistematrocas.projetosi_android_1718.singletons.SingletonActivityAPIResponse;
import pt.ipleiria.estg.tesp.psi.projsi.sistematrocas.projetosi_android_1718.singletons.SingletonAnuncios;
import pt.ipleiria.estg.tesp.psi.projsi.sistematrocas.projetosi_android_1718.singletons.SingletonCategorias;
import pt.ipleiria.estg.tesp.psi.projsi.sistematrocas.projetosi_android_1718.singletons.SingletonPropostas;

public class CriarAnuncioActivity extends NavDrawerActivity implements AdapterView.OnItemSelectedListener, AnunciosListener, ImagesListener {

    private HashMap<Integer, String> categoriasHashMap;
    private FragmentManager fragmentManager;
    private Anuncio anuncio;
    private ArrayList<String> imagensAnuncio;

    private static final int REQUEST_IMAGE_CAPTURE = 1;

    private static final int WIDTH_TO_DOWNSAMPLE = 1200;
    private static final int HEIGHT_TO_DOWNSAMPLE = 1200;

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

        //----------------------------------Botões Flutuantes--------------------------------------
        FloatingActionButton fabCriarAnuncio = findViewById(R.id.fabCriarAnuncio);
        fabCriarAnuncio.setOnClickListener(new View.OnClickListener() {
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
        });

        imagensAnuncio = new ArrayList<>();

        FloatingActionButton fabImagens = findViewById(R.id.fabCriarAnuncioImagens);
        fabImagens.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(imagensAnuncio.size() <= 3) {

                    Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

                    if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
                        startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
                    }
                }
            }
        });
        //---------------------------------------------------------------------------------------
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {

            Bundle extras = data.getExtras();

            if(extras != null) {

                Bitmap imagem = (Bitmap) extras.get("data");

                if (imagem != null) {

                    try
                    {
                        byte[] imagemBytes = ImageManager.toByteArray(imagem);

                        byte[] downsampledImagemBytes = ImageManager.downscaleImage(imagemBytes, WIDTH_TO_DOWNSAMPLE, HEIGHT_TO_DOWNSAMPLE);

                        String imagemBase64 = ImageManager.toBase64(downsampledImagemBytes);
                        imagensAnuncio.add(imagemBase64);

                        LinearLayout linearLayoutImagens = findViewById(R.id.linearLayoutCriarAnuncioImagensAnuncio);

                        ImageView imageViewImagem = new ImageView(getApplicationContext());
                        imageViewImagem.setId(imagensAnuncio.size() + 1);

                        linearLayoutImagens.addView(imageViewImagem);

                        Glide.with(getApplicationContext())
                                .load(downsampledImagemBytes)
                                .asBitmap()
                                .override(240, ImageViewTarget.SIZE_ORIGINAL)
                                .into(imageViewImagem);

                    } catch (RuntimeException e) {
                        showNotification(e.getMessage());
                    }
                }
            }
        }
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


    /**
     * Imprime um formulário de preenchimento de dados, cuja é implementado através de um Fragment, num respetivo container.
     *
     * @param position Posição da categoria.
     * @param containerId ID do container do fragmento.
     */
    private void printForm(Integer position, @IdRes int containerId) {

        if(position > 0) {

            String categoria = categoriasHashMap.get(position);

            try {

                Fragment form = MyFragmentManager.getFormFragment(categoria, getResources().getStringArray(R.array.categorias_keys),
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

    /**
     * Método que obtém o anúncio, juntamente com as categorias que obtém dos fragmentos, e regista-o na BD e API.
     *
     * @param anuncioTitulo Titulo do anuncio.
     * @param containerIdFormTroco ID do container do fragmento do bem a trocar.
     * @param nomeCategoriaTroco Nome da categoria do bem a trocar.
     * @param categoriaTrocoKeyPosition Posição da key do HashMap da categoria do bem a trocar.
     * @param quantidadeCategoriaTroco Quantidade do bem a trocar.
     * @param containerIdFormPor ID do container do fragmento do bem a receber.
     * @param nomeCategoriaPor Nome da categoria do bem a receber.
     * @param categoriaPorKeyPosition Posição da key do HashMap da categoria do bem a receber.
     * @param quantidadeCategoriaPor Quantidade do bem a receber.
     */
    private void criarAnuncio(String anuncioTitulo, Integer containerIdFormTroco, String nomeCategoriaTroco, Integer categoriaTrocoKeyPosition, Integer quantidadeCategoriaTroco,
                                 Integer containerIdFormPor, String nomeCategoriaPor, Integer categoriaPorKeyPosition, Integer quantidadeCategoriaPor) {

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

    /**
     * Método que obtém a categoria do fragment, através do método getCategoria da classe MyFragmentManager.
     *
     * @param containerIdForm ID do container do fragmento da categoria do bem.
     * @param nomeCategoria Nome da categoria do bem.
     * @param categoriaKeyPosition Posição da key do HashMap da categoria do bem.
     * @return Categoria obtida.
     */
    private Categoria getCategoria(Integer containerIdForm, String nomeCategoria, Integer categoriaKeyPosition) {

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

    /**
     *
     * @param anuncioTitulo Titulo do anúncio.
     * @param categoriaTroco Categoria do bem a trocar.
     * @param quantidadeTroco Quantidade do bem a trocar.
     * @param categoriaPor Categoria do bem a receber.
     * @param quantidadePor Quantidade do bem a receber.
     */
    private void getCategoriaTroco(final String anuncioTitulo, final Categoria categoriaTroco, final Integer quantidadeTroco, final Categoria categoriaPor, final Integer quantidadePor) {

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

    /**
     *
     * @param anuncioTitulo Titulo do anúncio.
     * @param categoriaTroco Categoria do bem a trocar.
     * @param quantidadeTroco Quantidade do bem a trocar.
     * @param categoriaPor Categoria do bem a receber.
     * @param quantidadePor Quantidade do bem a receber.
     */
    private void getCategoriaPor(final String anuncioTitulo, final Categoria categoriaTroco, final Integer quantidadeTroco, final Categoria categoriaPor, final Integer quantidadePor) {

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

    /**
     *
     * @param anuncioTitulo Titulo do anúncio.
     * @param categoriaTroco Categoria do bem a trocar.
     * @param quantidadeTroco Quantidade do bem a trocar.
     * @param categoriaPor Categoria do bem a receber.
     * @param quantidadePor Quantidade do bem a receber.
     */
    private void getAnuncio(String anuncioTitulo, final Categoria categoriaTroco, Integer quantidadeTroco, final Categoria categoriaPor, Integer quantidadePor) {

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

    /**
     *
     * @param anuncio Anuncio obtido.
     */
    private void saveAnuncio(final Anuncio anuncio) {

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

    private void showNotification(String message) {
        Snackbar snackbar = Snackbar
                .make(findViewById(R.id.coordinatorLayoutCriarAnuncio), message, Snackbar.LENGTH_LONG);

        snackbar.show();
    }

    @Override
    public void onSuccessAnunciosAPI(Anuncio anuncio) {

        showNotification("Criação do anúncio " + anuncio.getTitulo() + " com sucesso.");

        SingletonAnuncios.getInstance(getApplicationContext()).setImagesListener(this);
        SingletonAnuncios.getInstance(getApplicationContext()).enviarImagensAnuncio(anuncio.getId(), imagensAnuncio, getApplicationContext());
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

    @Override
    public void OnSucessoImagensAPI(ArrayList<byte[]> imagensBytes) {
        Intent intent = new Intent(getApplicationContext(), MeusAnunciosActivity.class);
        startActivity(intent);
    }

    @Override
    public void OnErrorImagensAPI(String message, Exception ex) {
        ex.printStackTrace();
        showNotification(message);
    }
}
