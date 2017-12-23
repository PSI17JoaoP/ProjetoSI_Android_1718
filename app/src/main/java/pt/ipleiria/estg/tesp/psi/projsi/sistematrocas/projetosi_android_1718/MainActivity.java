package pt.ipleiria.estg.tesp.psi.projsi.sistematrocas.projetosi_android_1718;

import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TabHost;

import java.util.ArrayList;

import pt.ipleiria.estg.tesp.psi.projsi.sistematrocas.projetosi_android_1718.adaptadores.AnunciosAdapter;
import pt.ipleiria.estg.tesp.psi.projsi.sistematrocas.projetosi_android_1718.adaptadores.PropostasAdapter;
import pt.ipleiria.estg.tesp.psi.projsi.sistematrocas.projetosi_android_1718.listeners.AnunciosListener;
import pt.ipleiria.estg.tesp.psi.projsi.sistematrocas.projetosi_android_1718.listeners.PropostasListener;
import pt.ipleiria.estg.tesp.psi.projsi.sistematrocas.projetosi_android_1718.modelos.Anuncio;
import pt.ipleiria.estg.tesp.psi.projsi.sistematrocas.projetosi_android_1718.modelos.Proposta;
import pt.ipleiria.estg.tesp.psi.projsi.sistematrocas.projetosi_android_1718.singletons.SingletonAnuncios;
import pt.ipleiria.estg.tesp.psi.projsi.sistematrocas.projetosi_android_1718.singletons.SingletonPropostas;

public class MainActivity extends NavDrawerActivity implements AnunciosListener, PropostasListener{

    private ListView lvAnuncios;
    private ListView lvPropostas;

    private PropostasAdapter propostasAdapter;
    private AnunciosAdapter anunciosAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_main);
        View.inflate(this, R.layout.activity_main, (ViewGroup) findViewById(R.id.app_content));

        //Tab Control
        TabHost host = findViewById(R.id.tabMain);
        host.setup();

        //Tab 1
        TabHost.TabSpec spec = host.newTabSpec("Propostas");
        spec.setContent(R.id.tab1);
        spec.setIndicator("Propostas");
        host.addTab(spec);

        //Tab 2
        spec = host.newTabSpec("Anúncios Sugeridos");
        spec.setContent(R.id.tab2);
        spec.setIndicator("Anúncios Sugeridos");
        host.addTab(spec);

        lvAnuncios = findViewById(R.id.lvAnuncios);
        lvPropostas = findViewById(R.id.lvPropostas);
        //------------------------------------

        if (savedInstanceState == null)
        {

            SingletonAnuncios.getInstance(this).setAnunciosListener(this);
            SingletonAnuncios.getInstance(this).getAnunciosSugeridos();
/*
            SingletonPropostas.getInstance(this).setPropostasListener(this);
            SingletonPropostas.getInstance(this).getPropostas();
*/

        }else{
            /*this.gestorAnuncios =  ... savedInstanceState.getSerializable(...);
            this.gestorPropostas =  ... savedInstanceState.getSerializable(...);*/
        }

    }

    @Override
    public void onRefreshAnuncios(ArrayList<Anuncio> anuncios) {
        anunciosAdapter = new AnunciosAdapter(this, anuncios);
        lvAnuncios.setAdapter(anunciosAdapter);
    }

    @Override
    public void onUpdateAnuncios(Anuncio anuncio, int acao) {

    }

    @Override
    public void onRefreshPropostas(ArrayList<Proposta> propostas) {
        propostasAdapter = new PropostasAdapter(this, propostas);
        lvPropostas.setAdapter(propostasAdapter);
    }

    @Override
    public void onUpdatePropostas(Proposta proposta, int acao) {

    }
}
