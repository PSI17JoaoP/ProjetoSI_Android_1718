package pt.ipleiria.estg.tesp.psi.projsi.sistematrocas.projetosi_android_1718;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.NumberPicker;
import android.widget.Spinner;

import java.util.HashMap;

public class CriarAnuncioActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_criar_anuncio);
        Toolbar toolbar = findViewById(R.id.toolbarCriarAnuncio);
        setSupportActionBar(toolbar);

        /*String[] keys = getResources().getStringArray(R.array.categorias_keys);
        String[] values = getResources().getStringArray(R.array.categorias_values);

        HashMap<String, String> map = new HashMap<>();

        for (int contador = 0; contador < keys.length; contador++) {
            map.put(keys[contador], values[contador]);
        }*/

        /*ArrayAdapter<HashMap<String, String>> spinnerAdapterTroco = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item);
        spinnerAdapterTroco.add(map);*/

        /*ArrayAdapter<HashMap<String, String>> spinnerAdapterPor = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item);
        spinnerAdapterPor.add(map);*/



        //Array Adapter das categorias guardadas nos recursos XML
        ArrayAdapter<CharSequence> spinnerCategorias = ArrayAdapter.createFromResource(this,
                R.array.categorias_values,
                R.layout.custom_spinner_item);

        Spinner dropDownCategoriasTroco = findViewById(R.id.dropDownCategoriasTroco);

        spinnerCategorias.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        dropDownCategoriasTroco.setAdapter(spinnerCategorias);

        dropDownCategoriasTroco.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        Spinner dropDownCategoriasPor = findViewById(R.id.dropDownCategoriasPor);

        spinnerCategorias.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        dropDownCategoriasPor.setAdapter(spinnerCategorias);

        dropDownCategoriasPor.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        NumberPicker numberPickerCategoriaTroco = findViewById(R.id.numberPickerCategoriaTroco);

        numberPickerCategoriaTroco.setMinValue(1);
        numberPickerCategoriaTroco.setMaxValue(99);
        numberPickerCategoriaTroco.setWrapSelectorWheel(true);

        NumberPicker numberPickerCategoriaPor = findViewById(R.id.numberPickerCategoriaPor);

        numberPickerCategoriaPor.setMinValue(1);
        numberPickerCategoriaPor.setMaxValue(99);
        numberPickerCategoriaPor.setWrapSelectorWheel(true);

        FloatingActionButton fabCriarAnuncio = findViewById(R.id.fabCriarAnuncio);

        fabCriarAnuncio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
    }
}
