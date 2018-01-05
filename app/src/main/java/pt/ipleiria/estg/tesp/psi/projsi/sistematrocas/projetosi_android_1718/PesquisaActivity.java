package pt.ipleiria.estg.tesp.psi.projsi.sistematrocas.projetosi_android_1718;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;

import java.util.ArrayList;

import pt.ipleiria.estg.tesp.psi.projsi.sistematrocas.projetosi_android_1718.adaptadores.AnunciosAdapter;
import pt.ipleiria.estg.tesp.psi.projsi.sistematrocas.projetosi_android_1718.listeners.AnunciosListener;
import pt.ipleiria.estg.tesp.psi.projsi.sistematrocas.projetosi_android_1718.modelos.Anuncio;
import pt.ipleiria.estg.tesp.psi.projsi.sistematrocas.projetosi_android_1718.singletons.SingletonAnuncios;

public class PesquisaActivity extends NavDrawerActivity implements AnunciosListener{

    private ListView lvPesquisa;

    private AnunciosAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        View.inflate(this, R.layout.activity_pesquisa, (ViewGroup) findViewById(R.id.app_content));

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
        String categoria = (String) spinnerCategorias.getSelectedItem();
        String regiao = (String) spinnerRegioes.getSelectedItem();

        SingletonAnuncios.getInstance(this).setAnunciosListener(this);

        SingletonAnuncios.getInstance(this).pesquisarAnuncios(titulo, regiao, categoria);
    }
}
