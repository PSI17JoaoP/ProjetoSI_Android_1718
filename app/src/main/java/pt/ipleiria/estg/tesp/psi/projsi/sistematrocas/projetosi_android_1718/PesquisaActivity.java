package pt.ipleiria.estg.tesp.psi.projsi.sistematrocas.projetosi_android_1718;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;

import java.util.ArrayList;
import java.util.HashMap;

import pt.ipleiria.estg.tesp.psi.projsi.sistematrocas.projetosi_android_1718.adaptadores.AnunciosAdapter;
import pt.ipleiria.estg.tesp.psi.projsi.sistematrocas.projetosi_android_1718.listeners.AnunciosListener;
import pt.ipleiria.estg.tesp.psi.projsi.sistematrocas.projetosi_android_1718.modelos.Anuncio;
import pt.ipleiria.estg.tesp.psi.projsi.sistematrocas.projetosi_android_1718.singletons.SingletonAnuncios;

public class PesquisaActivity extends NavDrawerActivity implements AnunciosListener{

    private ListView lvPesquisa;
    private HashMap<Integer, String> categoriasHashMap;

    private AnunciosAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        View.inflate(this, R.layout.activity_pesquisa, (ViewGroup) findViewById(R.id.app_content));

        SingletonAnuncios.getInstance(this).setAnunciosListener(this);

        //---------------------------------Dropdowns (Categorias)---------------------------
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

        Spinner dropdownCategorias = findViewById(R.id.spinnerCategorias);

        spinnerCategorias.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        dropdownCategorias.setAdapter(spinnerCategorias);

        //---------------------------------Dropdowns (Região)---------------------------
        ArrayAdapter<CharSequence> spinnerRegioes = new ArrayAdapter<CharSequence>(this,
                R.layout.custom_spinner_item,
                getResources().getStringArray(R.array.regioes_values));

        Spinner dropdownRegioes = findViewById(R.id.spinnerRegioes);

        spinnerRegioes.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        dropdownRegioes.setAdapter(spinnerRegioes);
        //------------------------------------------------------------------------------

        lvPesquisa = findViewById(R.id.lvPesquisa);
        adapter = new AnunciosAdapter(this, SingletonAnuncios.getInstance(this).getAnuncios());
        lvPesquisa.setAdapter(adapter);
    }

    @Override
    public void onSuccessAnunciosAPI(Anuncio anuncio) {

    }

    @Override
    public void onErrorAnunciosAPI(String message, Exception ex) {

    }

    @Override
    public void onRefreshAnuncios(ArrayList<Anuncio> anuncios) {
        adapter.refresh(anuncios);
    }

    public void pesquisa(View view) {

        EditText editTextTitulo = findViewById(R.id.editTextTitulo);
        Spinner spinnerCategorias = findViewById(R.id.spinnerCategorias);
        Spinner spinnerRegioes = findViewById(R.id.spinnerRegioes);

        String titulo = editTextTitulo.getText().toString().trim();
        String categoria = categoriasHashMap.get(spinnerCategorias.getSelectedItemPosition()).toLowerCase();
        if (categoria.equals("n/a"))
            categoria = null;
        String[] regioesKeys = getResources().getStringArray(R.array.regioes_keys);
        String regiao =  regioesKeys[spinnerRegioes.getSelectedItemPosition()];
        if (regiao.equals("n/a"))
            regiao = null;

        SingletonAnuncios.getInstance(this).pesquisarAnuncios(titulo, regiao, categoria);
    }
}
