package pt.ipleiria.estg.tesp.psi.projsi.sistematrocas.projetosi_android_1718;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;

import pt.ipleiria.estg.tesp.psi.projsi.sistematrocas.projetosi_android_1718.adaptadores.AnunciosAdapter;
import pt.ipleiria.estg.tesp.psi.projsi.sistematrocas.projetosi_android_1718.listeners.AnunciosListener;
import pt.ipleiria.estg.tesp.psi.projsi.sistematrocas.projetosi_android_1718.modelos.Anuncio;
import pt.ipleiria.estg.tesp.psi.projsi.sistematrocas.projetosi_android_1718.singletons.SingletonAnuncios;

public class MeusAnunciosActivity extends NavDrawerActivity implements AnunciosListener{

    private ListView listaAnuncios;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_meus_anuncios);
        View.inflate(this, R.layout.activity_meus_anuncios, (ViewGroup) findViewById(R.id.app_content));

        listaAnuncios = findViewById(R.id.listMeusAnuncios);

        SingletonAnuncios.getInstance(getApplicationContext()).setAnunciosListener(this);
        SingletonAnuncios.getInstance(getApplicationContext()).getAnunciosUser(getApplicationContext());

        listaAnuncios.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Anuncio anuncio = (Anuncio) parent.getItemAtPosition(position);

                if(anuncio != null) {
                    Intent intent = new Intent(getApplicationContext(), DetalhesAnuncioActivity.class);
                    intent.putExtra(DetalhesAnuncioActivity.ID_ANUNCIO, anuncio.getId());
                    startActivity(intent);
                }
            }
        });
    }

    private void showNotification(String message) {
        Snackbar snackbar = Snackbar
                .make(findViewById(R.id.coordinatorLayoutMeusAnuncios), message, Snackbar.LENGTH_LONG);

        snackbar.show();
    }

    public void onClickCriarAnuncio(View view) {
        Intent intent = new Intent(getApplicationContext(), CriarAnuncioActivity.class);
        startActivity(intent);
    }

    @Override
    public void onSuccessAnunciosAPI(Anuncio anuncio) {

    }

    @Override
    public void onErrorAnunciosAPI(String message, Exception ex) {
        ex.printStackTrace();
        showNotification(message);
    }

    @Override
    public void onRefreshAnuncios(ArrayList<Anuncio> anuncios) {
        AnunciosAdapter anunciosAdapter = new AnunciosAdapter(this, anuncios);
        listaAnuncios.setAdapter(anunciosAdapter);
        anunciosAdapter.refresh(anuncios);
    }
}
