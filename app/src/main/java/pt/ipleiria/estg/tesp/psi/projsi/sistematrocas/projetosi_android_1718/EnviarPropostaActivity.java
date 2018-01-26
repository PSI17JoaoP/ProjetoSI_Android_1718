package pt.ipleiria.estg.tesp.psi.projsi.sistematrocas.projetosi_android_1718;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
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
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;

import pt.ipleiria.estg.tesp.psi.projsi.sistematrocas.projetosi_android_1718.fragments.MyFragmentManager;
import pt.ipleiria.estg.tesp.psi.projsi.sistematrocas.projetosi_android_1718.listeners.PropostasListener;
import pt.ipleiria.estg.tesp.psi.projsi.sistematrocas.projetosi_android_1718.modelos.Categoria;
import pt.ipleiria.estg.tesp.psi.projsi.sistematrocas.projetosi_android_1718.modelos.Proposta;
import pt.ipleiria.estg.tesp.psi.projsi.sistematrocas.projetosi_android_1718.singletons.SingletonActivityAPIResponse;
import pt.ipleiria.estg.tesp.psi.projsi.sistematrocas.projetosi_android_1718.singletons.SingletonCategorias;
import pt.ipleiria.estg.tesp.psi.projsi.sistematrocas.projetosi_android_1718.singletons.SingletonPropostas;

public class EnviarPropostaActivity extends NavDrawerActivity implements AdapterView.OnItemSelectedListener, PropostasListener {

    private HashMap<Integer, String> categoriasHashMap;
    private FragmentManager fragmentManager;
    private Proposta proposta;
    private ArrayList<String> imagensProposta;

    private static final int REQUEST_IMAGE_CAPTURE = 1;

    @SuppressLint("UseSparseArrays")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_enviar_proposta);
        View.inflate(this, R.layout.activity_enviar_proposta, (ViewGroup) findViewById(R.id.app_content));

        String[] categoriasValues = getResources().getStringArray(R.array.categorias_values);
        String[] categoriasKeys = getResources().getStringArray(R.array.categorias_keys);

        categoriasHashMap = new HashMap<>();

        for (int cont = 0; cont < categoriasKeys.length; cont++) {
            categoriasHashMap.put(cont, categoriasKeys[cont]);
        }

        fragmentManager = getSupportFragmentManager();

        //Array Adapter das categorias guardadas nos recursos XML
        ArrayAdapter<CharSequence> spinnerCategorias = new ArrayAdapter<CharSequence>(this,
                R.layout.custom_spinner_item,
                categoriasValues);

        TextView textViewEnviarProposta = findViewById(R.id.textViewEnviarProposta);

        if(getIntent().hasExtra(DetalhesAnuncioActivity.TITULO_ANUNCIO)) {
            String titulo = getIntent().getStringExtra(DetalhesAnuncioActivity.TITULO_ANUNCIO);
            String textViewEnviarPropostaTitulo = textViewEnviarProposta.getText() + " " + titulo;
            textViewEnviarProposta.setText(textViewEnviarPropostaTitulo);
        }

        Spinner dropDownCategorias = findViewById(R.id.dropDownEnviarPropostaCategorias);

        spinnerCategorias.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        dropDownCategorias.setAdapter(spinnerCategorias);
        dropDownCategorias.setOnItemSelectedListener(this);

        NumberPicker numberPickerCategoria = findViewById(R.id.numberPickerCategoria);

        numberPickerCategoria.setMinValue(1);
        numberPickerCategoria.setMaxValue(99);
        numberPickerCategoria.setValue(1);
        numberPickerCategoria.setWrapSelectorWheel(true);

        FloatingActionButton fabEnviarProposta = findViewById(R.id.fabEnviarProposta);
        fabEnviarProposta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Spinner dropDownEnviarPropostaCategorias = findViewById(R.id.dropDownEnviarPropostaCategorias);
                NumberPicker numberPickerCategoria = findViewById(R.id.numberPickerCategoria);

                EditText editTextEnviarPropostaNomeCategoria = findViewById(R.id.editTextEnviarPropostaNomeCategoria);
                String nomeCategoria = editTextEnviarPropostaNomeCategoria.getText().toString().trim();

                FrameLayout fragmentContainerForm = findViewById(R.id.fragmentFormCategoria);

                if(!nomeCategoria.isEmpty() && fragmentContainerForm.getChildCount() != 0) {

                    Categoria categoria = getCategoria(nomeCategoria, dropDownEnviarPropostaCategorias.getSelectedItemPosition());

                    getCategoriaProposta(categoria, numberPickerCategoria.getValue());
                }

                else if(nomeCategoria.isEmpty()) {
                    showNotification("Preencha o nome do bem a propôr.");
                }

                else if(fragmentContainerForm.getChildCount() == 0) {
                    showNotification("Selecione a categoria do bem a propôr.");
                }

                else {
                    showNotification("O nome e categoria do bem não podem estar vazios.");
                }
            }
        });

        imagensProposta = new ArrayList<>();

        FloatingActionButton fabImagens = findViewById(R.id.fabEnviarPropostaImagens);
        fabImagens.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(imagensProposta.size() <= 3) {

                    Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

                    if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
                        startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
                    }
                }
            }
        });
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
                        ByteArrayOutputStream stream = new ByteArrayOutputStream();
                        imagem.compress(Bitmap.CompressFormat.PNG, 100, stream);

                        byte[] imagemBytes = stream.toByteArray();

                        stream.flush();
                        stream.close();

                        String imagemBase64 = MyFragmentManager.getImagemBase64(imagemBytes);
                        imagensProposta.add(imagemBase64);

                        LinearLayout linearLayoutImagens = findViewById(R.id.linearLayoutEnviarPropostaImagensProposta);

                        ImageView imageViewImagem = new ImageView(getApplicationContext());
                        imageViewImagem.setId(imagensProposta.size() + 1);

                        linearLayoutImagens.addView(imageViewImagem);

                        Glide.with(getApplicationContext())
                                .load(imagemBytes)
                                .asBitmap()
                                .override(240, 240)
                                .into(imageViewImagem);

                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

        if(position > 0) {

            String categoria = categoriasHashMap.get(position);

            try {

                Fragment form = MyFragmentManager.getFormFragment(categoria, getResources().getStringArray(R.array.categorias_keys),
                        getApplicationContext());

                if (form != null) {
                    FragmentTransaction transaction = fragmentManager.beginTransaction();

                    FrameLayout fragmentContainer = findViewById(R.id.fragmentFormCategoria);

                    if (fragmentContainer.getChildCount() == 0) {
                        transaction.add(R.id.fragmentFormCategoria, form, categoria).commit();
                    } else {
                        if (fragmentManager.findFragmentByTag(categoria) == null) {
                            transaction.replace(R.id.fragmentFormCategoria, form, categoria).commit();
                        }
                    }
                }

            } catch (ClassNotFoundException e) {
                e.printStackTrace();
                showNotification("Ocorreu um erro na seleção da categoria.");
            }
        } else {

            FragmentTransaction transaction = fragmentManager.beginTransaction();

            FrameLayout fragmentContainer = findViewById(R.id.fragmentFormCategoria);

            if (fragmentContainer.getChildCount() != 0) {
                Fragment fragment = fragmentManager.findFragmentById(R.id.fragmentFormCategoria);

                if(fragment != null) {
                    transaction.remove(fragment).commit();
                }
            }
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }

    private void showNotification(String message) {
        Snackbar snackbar = Snackbar
                .make(findViewById(R.id.coordinatorLayoutEnviarProposta), message, Snackbar.LENGTH_LONG);

        snackbar.show();
    }

    //Método que obtém a categoria do fragment, através do método getCategoria da classe MyFragmentManager.
    private Categoria getCategoria(@NonNull String nomeCategoria, @NonNull Integer categoriaKeyPosition) {

        Categoria categoria = null;

        Fragment fragmentForm = fragmentManager.findFragmentById(R.id.fragmentFormCategoria);

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

    private void getCategoriaProposta(@NonNull final Categoria categoria, @NonNull final Integer quantidade) {

        SingletonCategorias.getInstance().adicionarCategoria(categoria, getApplicationContext(), new SingletonActivityAPIResponse() {
            @Override
            public void onSuccessEnvioAPI(Categoria categoria) {
                getProposta(categoria, quantidade);
            }

            @Override
            public void onErrorEnvioAPI(String message, Exception ex) {
                ex.printStackTrace();
                showNotification(message);
            }
        });
    }

    private void getProposta(@NonNull final Categoria categoria, @NonNull final Integer quantidade) {

        SharedPreferences preferences = getSharedPreferences("APP_SETTINGS", Context.MODE_PRIVATE);
        Long userID = preferences.getLong("id", 0);

        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-M-dd");
        String data = dateFormat.format(calendar.getTime());

        if(getIntent().hasExtra(DetalhesAnuncioActivity.ID_ANUNCIO)) {
            Long anuncioId = getIntent().getLongExtra(DetalhesAnuncioActivity.ID_ANUNCIO, 0L);
            Proposta proposta = new Proposta(categoria.getId(), quantidade, userID, anuncioId, "PENDENTE", data);
            saveProposta(proposta);
        }
    }

    private void saveProposta(Proposta proposta) {

        //Guardada como variável global, para ser visivel no onRefreshPropostas.
        this.proposta = proposta;

        SingletonPropostas.getInstance(getApplicationContext()).setPropostasListener(this);

        ArrayList<Proposta> propostas = SingletonPropostas.getInstance(getApplicationContext()).getPropostas();

        if(!propostas.isEmpty()) {
            if (!propostas.contains(proposta)) {
                SingletonPropostas.getInstance(getApplicationContext()).adicionarProposta(proposta, getApplicationContext());
            }
        }
    }

    @Override
    public void onSuccessPropostasAPI(Proposta proposta) {
        showNotification("SUCESSO - Envio da proposta para a API.");
    }

    @Override
    public void onErrorPropostasAPI(String message, Exception ex) {
        ex.printStackTrace();
        showNotification(message);
    }

    @Override
    public void onRefreshPropostas(ArrayList<Proposta> propostas) {
        if (!propostas.contains(proposta)) {
            SingletonPropostas.getInstance(getApplicationContext()).adicionarProposta(proposta, getApplicationContext());
        }
    }
}
