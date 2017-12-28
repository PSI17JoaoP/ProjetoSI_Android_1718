package pt.ipleiria.estg.tesp.psi.projsi.sistematrocas.projetosi_android_1718;

import android.annotation.SuppressLint;
import android.support.annotation.IdRes;
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

import java.util.HashMap;

import pt.ipleiria.estg.tesp.psi.projsi.sistematrocas.projetosi_android_1718.forms.BrinquedosFragment;
import pt.ipleiria.estg.tesp.psi.projsi.sistematrocas.projetosi_android_1718.forms.ComputadoresFragment;
import pt.ipleiria.estg.tesp.psi.projsi.sistematrocas.projetosi_android_1718.forms.EletronicaFragment;
import pt.ipleiria.estg.tesp.psi.projsi.sistematrocas.projetosi_android_1718.forms.FormManager;
import pt.ipleiria.estg.tesp.psi.projsi.sistematrocas.projetosi_android_1718.forms.JogosFragment;
import pt.ipleiria.estg.tesp.psi.projsi.sistematrocas.projetosi_android_1718.forms.LivrosFragment;
import pt.ipleiria.estg.tesp.psi.projsi.sistematrocas.projetosi_android_1718.forms.RoupaFragment;
import pt.ipleiria.estg.tesp.psi.projsi.sistematrocas.projetosi_android_1718.forms.SmartphonesFragment;
import pt.ipleiria.estg.tesp.psi.projsi.sistematrocas.projetosi_android_1718.helpers.BrinquedoBDTable;
import pt.ipleiria.estg.tesp.psi.projsi.sistematrocas.projetosi_android_1718.modelos.Brinquedo;
import pt.ipleiria.estg.tesp.psi.projsi.sistematrocas.projetosi_android_1718.modelos.Categoria;
import pt.ipleiria.estg.tesp.psi.projsi.sistematrocas.projetosi_android_1718.singletons.SingletonBrinquedos;

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
                showNotification("Ocorreu um erro na seleção da categoria");
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
        EditText nomeCategoriaPor = findViewById(R.id.editTextCriarAnuncioPorNomeCategoria);

        FragmentManager manager = getSupportFragmentManager();

        FrameLayout fragmentContainerFormTroco = findViewById(R.id.fragmentFormCategoriaTroco);

        if(!editTextAnuncioTitulo.getText().toString().trim().isEmpty() && !editTextNomeCategoriaTroco.getText().toString().trim().isEmpty()
                && fragmentContainerFormTroco.getChildCount() != 0) {

            String anuncioTitulo = editTextAnuncioTitulo.getText().toString().trim();
            String nomeCategoriaTroco = editTextNomeCategoriaTroco.getText().toString().trim();

            Fragment fragmentFormTroco = manager.findFragmentById(R.id.fragmentFormCategoriaTroco);

            if (fragmentFormTroco instanceof LivrosFragment) {

                LivrosFragment fragmentLivrosFormTroco = (LivrosFragment) fragmentFormTroco;

            } else if(fragmentFormTroco instanceof RoupaFragment) {

                RoupaFragment fragmentRoupaFormTroco = (RoupaFragment) fragmentFormTroco;

            } else if(fragmentFormTroco instanceof EletronicaFragment) {

                EletronicaFragment fragmentEletronicaFormTroco = (EletronicaFragment) fragmentFormTroco;

            } else if(fragmentFormTroco instanceof BrinquedosFragment) {

                BrinquedosFragment fragmentBrinquedosFormTroco = (BrinquedosFragment) fragmentFormTroco;

                Brinquedo novoBrinquedo = fragmentBrinquedosFormTroco.getBrinquedo(nomeCategoriaTroco);

                System.out.println("####" + novoBrinquedo);

            } else if(fragmentFormTroco instanceof ComputadoresFragment) {

                ComputadoresFragment fragmentComputadoresFormTroco = (ComputadoresFragment) fragmentFormTroco;

            } else if(fragmentFormTroco instanceof SmartphonesFragment) {

                SmartphonesFragment fragmentSmartphonesFormTroco = (SmartphonesFragment) fragmentFormTroco;

            } else if(fragmentFormTroco instanceof JogosFragment) {

                JogosFragment fragmentJogosFormTroco = (JogosFragment) fragmentFormTroco;

            }
        }
    }
}
