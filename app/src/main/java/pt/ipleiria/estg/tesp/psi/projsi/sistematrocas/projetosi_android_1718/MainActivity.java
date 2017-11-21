package pt.ipleiria.estg.tesp.psi.projsi.sistematrocas.projetosi_android_1718;

import android.animation.LayoutTransition;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewStub;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TabHost;

import java.util.List;

import pt.ipleiria.estg.tesp.psi.projsi.sistematrocas.projetosi_android_1718.adaptadores.AnunciosAdapter;
import pt.ipleiria.estg.tesp.psi.projsi.sistematrocas.projetosi_android_1718.adaptadores.PropostasAdapter;
import pt.ipleiria.estg.tesp.psi.projsi.sistematrocas.projetosi_android_1718.modelos.GestorAnuncios;
import pt.ipleiria.estg.tesp.psi.projsi.sistematrocas.projetosi_android_1718.modelos.GestorPropostas;

public class MainActivity extends NavDrawerActivity {

    private GestorAnuncios gestorAnuncios;
    private GestorPropostas gestorPropostas;

    private ListView lvAnuncios;
    private ListView lvPropostas;

    private PropostasAdapter propostasAdapter;
    private AnunciosAdapter anunciosAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_main);
        View.inflate(this, R.layout.activity_main, (ViewGroup) findViewById(R.id.app_content));

        //Tab control

        TabHost host = (TabHost)findViewById(R.id.tabMain);
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

        //------------------------------------

        /*if (savedInstanceState == null)
        {*/


            //--------------
            gestorAnuncios = new GestorAnuncios();
            gestorPropostas = new GestorPropostas();

            lvAnuncios = findViewById(R.id.lvAnuncios);
            lvPropostas = findViewById(R.id.lvPropostas);

            //------
            propostasAdapter = new PropostasAdapter(this, gestorPropostas.getListaPropostas());
            lvPropostas.setAdapter(propostasAdapter);

            //------
            anunciosAdapter = new AnunciosAdapter(this, gestorAnuncios.getListaAnuncios());
            lvAnuncios.setAdapter(anunciosAdapter);
            //--------------

        /*}else{
            // this.gestorAnuncios = (GestorAnuncios) savedInstanceState.getSerializable(...);
            // this.gestorPropostas = (GestorPropostas) savedInstanceState.getSerializable(...);
        }*/

    }
}
