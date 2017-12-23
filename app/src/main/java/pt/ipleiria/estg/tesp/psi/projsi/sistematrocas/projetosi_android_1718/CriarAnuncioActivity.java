package pt.ipleiria.estg.tesp.psi.projsi.sistematrocas.projetosi_android_1718;

import android.annotation.SuppressLint;
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
import android.widget.FrameLayout;
import android.widget.NumberPicker;
import android.widget.Spinner;

import java.util.HashMap;

import pt.ipleiria.estg.tesp.psi.projsi.sistematrocas.projetosi_android_1718.forms.FormSelector;

public class CriarAnuncioActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

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
        //numberPickerCategoriaTroco.setWrapSelectorWheel(false);

        NumberPicker numberPickerCategoriaPor = findViewById(R.id.numberPickerCategoriaPor);

        numberPickerCategoriaPor.setMinValue(1);
        numberPickerCategoriaPor.setMaxValue(99);
        //numberPickerCategoriaPor.setWrapSelectorWheel(false);
        //---------------------------------------------------------------------------------------

        //----------------------------------Botão Flutuante--------------------------------------
        FloatingActionButton fabCriarAnuncio = findViewById(R.id.fabCriarAnuncio);

        fabCriarAnuncio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
        //---------------------------------------------------------------------------------------
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id)  {

        FormSelector formSelector = new FormSelector();

        FragmentManager manager = getSupportFragmentManager();

        if(position > 0) {

            switch (parent.getId()) {
                case R.id.dropDownCategoriasTroco:

                    String categoriaTroco = categoriasHashMap.get(position);

                    try {

                        Fragment form = formSelector.selectForm(categoriaTroco, getResources().getStringArray(R.array.categorias_keys),
                                getApplicationContext());

                        if(form != null) {
                            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();

                            FrameLayout fragmentContainer = findViewById(R.id.fragmentFormCategoriaTroco);

                            if (fragmentContainer.getChildCount() == 0) {
                                transaction.add(R.id.fragmentFormCategoriaTroco, form, categoriaTroco).commit();
                            } else {
                                if (manager.findFragmentByTag(categoriaTroco) == null) {
                                    transaction.replace(R.id.fragmentFormCategoriaTroco, form, categoriaTroco).commit();
                                }
                            }
                        }

                    } catch (ClassNotFoundException e) {
                        e.printStackTrace();
                        showNotification();
                    }

                    break;

                case R.id.dropDownCategoriasPor:

                    String categoriaPor = categoriasHashMap.get(position);

                        try {

                            Fragment form = formSelector.selectForm(categoriaPor, getResources().getStringArray(R.array.categorias_keys),
                                    getApplicationContext());

                            if(form != null) {
                                FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();

                                FrameLayout fragmentContainer = findViewById(R.id.fragmentFormCategoriaPor);

                                if (fragmentContainer.getChildCount() == 0) {
                                    transaction.add(R.id.fragmentFormCategoriaPor, form, categoriaPor).commit();
                                } else {
                                    if (manager.findFragmentByTag(categoriaPor) == null) {
                                        transaction.replace(R.id.fragmentFormCategoriaTroco, form, categoriaPor).commit();
                                    }
                                }
                            }

                        } catch (ClassNotFoundException e) {
                            e.printStackTrace();
                            showNotification();
                        }

                    break;
            }
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent)  {

    }

    private void showNotification() {
        Snackbar snackbar = Snackbar
                .make(findViewById(R.id.coordinatorLayoutCriarAnuncio), "Ocorreu um erro na seleção da categoria", Snackbar.LENGTH_LONG);

        snackbar.show();
    }
}
