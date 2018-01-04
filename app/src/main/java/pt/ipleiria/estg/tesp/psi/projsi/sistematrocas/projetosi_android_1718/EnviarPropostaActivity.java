package pt.ipleiria.estg.tesp.psi.projsi.sistematrocas.projetosi_android_1718;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.NumberPicker;
import android.widget.Spinner;
import android.widget.TextView;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;

import pt.ipleiria.estg.tesp.psi.projsi.sistematrocas.projetosi_android_1718.forms.FormManager;
import pt.ipleiria.estg.tesp.psi.projsi.sistematrocas.projetosi_android_1718.listeners.PropostasListener;
import pt.ipleiria.estg.tesp.psi.projsi.sistematrocas.projetosi_android_1718.modelos.Categoria;
import pt.ipleiria.estg.tesp.psi.projsi.sistematrocas.projetosi_android_1718.modelos.Proposta;
import pt.ipleiria.estg.tesp.psi.projsi.sistematrocas.projetosi_android_1718.singletons.SingletonAPIManager;
import pt.ipleiria.estg.tesp.psi.projsi.sistematrocas.projetosi_android_1718.singletons.SingletonActivityAPIResponse;
import pt.ipleiria.estg.tesp.psi.projsi.sistematrocas.projetosi_android_1718.singletons.SingletonCategorias;
import pt.ipleiria.estg.tesp.psi.projsi.sistematrocas.projetosi_android_1718.singletons.SingletonPropostas;

public class EnviarPropostaActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener, View.OnClickListener, PropostasListener {

    private HashMap<Integer, String> categoriasHashMap;
    private Proposta proposta;

    @SuppressLint("UseSparseArrays")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_enviar_proposta);
        Toolbar toolbar = findViewById(R.id.toolbar);
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

        TextView textViewEnviarProposta = findViewById(R.id.textViewEnviarProposta);
        textViewEnviarProposta.setText(textViewEnviarProposta.getText());
        //TODO: Titulo do anúncio passado no Intent.

        Spinner dropDownCategorias = findViewById(R.id.dropDownEnviarPropostaCategorias);

        spinnerCategorias.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        dropDownCategorias.setAdapter(spinnerCategorias);
        dropDownCategorias.setOnItemSelectedListener(this);

        NumberPicker numberPickerCategoria = findViewById(R.id.numberPickerCategoria);

        numberPickerCategoria.setMinValue(1);
        numberPickerCategoria.setMaxValue(99);
        numberPickerCategoria.setValue(1);
        numberPickerCategoria.setWrapSelectorWheel(true);

        FloatingActionButton fab = findViewById(R.id.fabEnviarProposta);
        fab.setOnClickListener(this);
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

        FormManager formManager = new FormManager();

        FragmentManager manager = getSupportFragmentManager();

        if(position > 0) {

            String categoria = categoriasHashMap.get(position);

            try {

                Fragment form = formManager.selectForm(categoria, getResources().getStringArray(R.array.categorias_keys),
                        getApplicationContext());

                if (form != null) {
                    FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();

                    FrameLayout fragmentContainer = findViewById(R.id.fragmentFormCategoria);

                    if (fragmentContainer.getChildCount() == 0) {
                        transaction.add(R.id.fragmentFormCategoria, form, categoria).commit();
                    } else {
                        if (manager.findFragmentByTag(categoria) == null) {
                            transaction.replace(R.id.fragmentFormCategoria, form, categoria).commit();
                        }
                    }
                }

            } catch (ClassNotFoundException e) {
                e.printStackTrace();
                showNotification("Ocorreu um erro na seleção da categoria.");
            }
        } else {

            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();

            FrameLayout fragmentContainer = findViewById(R.id.fragmentFormCategoria);

            if (fragmentContainer.getChildCount() != 0) {
                Fragment fragment = manager.findFragmentById(R.id.fragmentFormCategoria);

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

    @Override
    public void onClick(View view) {

        Spinner dropDownEnviarPropostaCategorias = findViewById(R.id.dropDownEnviarPropostaCategorias);
        NumberPicker numberPickerCategoria = findViewById(R.id.numberPickerCategoria);

        EditText editTextEnviarPropostaNomeCategoria = findViewById(R.id.editTextEnviarPropostaNomeCategoria);
        String nomeCategoria = editTextEnviarPropostaNomeCategoria.getText().toString().trim();

        FrameLayout fragmentContainerForm = findViewById(R.id.fragmentFormCategoria);

        FragmentManager fragmentManager = getSupportFragmentManager();

        if(!nomeCategoria.isEmpty() && fragmentContainerForm.getChildCount() != 0) {

            FormManager formManager = new FormManager();

            Categoria categoria = getCategoria(fragmentManager, formManager, nomeCategoria, dropDownEnviarPropostaCategorias.getSelectedItemPosition());

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

    //Método que obtém a categoria do fragment, através do método getCategoria do objeto do tipo FormManager.
    private Categoria getCategoria(@NonNull FragmentManager fragmentManager, @NonNull FormManager formManager, @NonNull String nomeCategoria, @NonNull Integer categoriaKeyPosition) {

        Categoria categoria = null;

        Fragment fragmentForm = fragmentManager.findFragmentById(R.id.fragmentFormCategoria);

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

    private void getCategoriaProposta(@NonNull final Categoria categoria, @NonNull final Integer quantidade) {

        SingletonCategorias.getInstance(this).adicionarCategoria(categoria, new SingletonActivityAPIResponse() {
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

        //TODO: ID do anúncio passado pelo Intent.

        Proposta proposta = new Proposta(categoria.getId(), quantidade, userID, 3L, "PENDENTE", data);

        saveProposta(proposta);
    }

    private void saveProposta(Proposta proposta) {

        //Guardada como variável global, para ser visivel no onRefreshPropostas.
        this.proposta = proposta;

        SingletonPropostas.getInstance(this).setPropostasListener(this);

        ArrayList<Proposta> propostas = SingletonPropostas.getInstance(this).getPropostas();

        if(!propostas.isEmpty()) {
            if (!propostas.contains(proposta)) {
                SingletonPropostas.getInstance(this).adicionarProposta(proposta);
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
            SingletonPropostas.getInstance(this).adicionarProposta(proposta);
        }
    }
}
