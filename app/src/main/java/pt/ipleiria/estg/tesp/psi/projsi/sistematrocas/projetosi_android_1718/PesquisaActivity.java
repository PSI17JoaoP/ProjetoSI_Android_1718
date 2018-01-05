package pt.ipleiria.estg.tesp.psi.projsi.sistematrocas.projetosi_android_1718;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import java.util.ArrayList;

import pt.ipleiria.estg.tesp.psi.projsi.sistematrocas.projetosi_android_1718.adaptadores.AnunciosAdapter;
import pt.ipleiria.estg.tesp.psi.projsi.sistematrocas.projetosi_android_1718.listeners.AnunciosListener;
import pt.ipleiria.estg.tesp.psi.projsi.sistematrocas.projetosi_android_1718.modelos.Anuncio;
import pt.ipleiria.estg.tesp.psi.projsi.sistematrocas.projetosi_android_1718.singletons.SingletonAnuncios;

public class PesquisaActivity extends NavDrawerActivity implements AnunciosListener{

    private AnunciosAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        View.inflate(this, R.layout.activity_pesquisa, (ViewGroup) findViewById(R.id.app_content));

        ListView lvPesquisa = findViewById(R.id.lvPesquisa);
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

    }

    public void pesquisa(View view) {
        //Campos AQUI

        //Placeholder
        String titulo = "teste";
        String regiao = "leiria";
        String categoria = "brinquedos";


    }
}
