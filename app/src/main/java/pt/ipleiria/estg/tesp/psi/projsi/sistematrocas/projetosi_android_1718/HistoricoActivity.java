package pt.ipleiria.estg.tesp.psi.projsi.sistematrocas.projetosi_android_1718;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TabHost;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

import pt.ipleiria.estg.tesp.psi.projsi.sistematrocas.projetosi_android_1718.adaptadores.AnunciosAdapter;
import pt.ipleiria.estg.tesp.psi.projsi.sistematrocas.projetosi_android_1718.adaptadores.AnunciosHAdapter;
import pt.ipleiria.estg.tesp.psi.projsi.sistematrocas.projetosi_android_1718.adaptadores.PropostasAdapter;
import pt.ipleiria.estg.tesp.psi.projsi.sistematrocas.projetosi_android_1718.adaptadores.PropostasHAdapter;
import pt.ipleiria.estg.tesp.psi.projsi.sistematrocas.projetosi_android_1718.listeners.AnunciosListener;
import pt.ipleiria.estg.tesp.psi.projsi.sistematrocas.projetosi_android_1718.listeners.PropostasListener;
import pt.ipleiria.estg.tesp.psi.projsi.sistematrocas.projetosi_android_1718.modelos.Anuncio;
import pt.ipleiria.estg.tesp.psi.projsi.sistematrocas.projetosi_android_1718.modelos.Proposta;
import pt.ipleiria.estg.tesp.psi.projsi.sistematrocas.projetosi_android_1718.singletons.SingletonAnuncios;
import pt.ipleiria.estg.tesp.psi.projsi.sistematrocas.projetosi_android_1718.singletons.SingletonPropostas;

public class HistoricoActivity extends NavDrawerActivity implements AnunciosListener, PropostasListener {

    private ListView lvAnuncios;
    private ListView lvPropostas;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        View.inflate(this, R.layout.activity_historico, (ViewGroup) findViewById(R.id.app_content));

        //Tab Control
        TabHost host = findViewById(R.id.tabHistorico);
        host.setup();

        //Tab 1
        TabHost.TabSpec spec = host.newTabSpec("Anúncios");
        spec.setContent(R.id.tab1H);
        spec.setIndicator("Anúncios");
        host.addTab(spec);

        //Tab 2
        spec = host.newTabSpec("Propostas");
        spec.setContent(R.id.tab2H);
        spec.setIndicator("Propostas");
        host.addTab(spec);

        lvAnuncios = findViewById(R.id.lvHAnuncios);
        lvPropostas = findViewById(R.id.lvHPropostas);

        SingletonAnuncios.getInstance(getApplicationContext()).setAnunciosListener(this);
        SingletonPropostas.getInstance(getApplicationContext()).setPropostasListener(this);


        SingletonAnuncios.getInstance(getApplicationContext()).getAnunciosUser(this);
        SingletonPropostas.getInstance(getApplicationContext()).getPropostasUser(this);
    }

    @Override
    public void onSuccessAnunciosAPI(Anuncio anuncio) {

    }

    @Override
    public void onErrorAnunciosAPI(String message, Exception ex) {

    }

    @Override
    public void onRefreshAnuncios(ArrayList<Anuncio> anuncios) {
        Collections.reverse(anuncios);
        AnunciosHAdapter anunciosAdapter = new AnunciosHAdapter(this, anuncios);
        lvAnuncios.setAdapter(anunciosAdapter);
    }

    @Override
    public void onSuccessPropostasAPI(Proposta proposta) {

    }

    @Override
    public void onErrorPropostasAPI(String message, Exception ex) {

    }

    @Override
    public void onRefreshPropostas(ArrayList<Proposta> propostas) {
        Collections.reverse(propostas);
        PropostasHAdapter propostasAdapter = new PropostasHAdapter(this, propostas);
        lvPropostas.setAdapter(propostasAdapter);
    }
}
