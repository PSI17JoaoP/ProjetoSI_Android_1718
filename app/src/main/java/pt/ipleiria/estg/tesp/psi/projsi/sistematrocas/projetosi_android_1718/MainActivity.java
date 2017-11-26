package pt.ipleiria.estg.tesp.psi.projsi.sistematrocas.projetosi_android_1718;

import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TabHost;

import pt.ipleiria.estg.tesp.psi.projsi.sistematrocas.projetosi_android_1718.adaptadores.AnunciosAdapter;
import pt.ipleiria.estg.tesp.psi.projsi.sistematrocas.projetosi_android_1718.adaptadores.PropostasAdapter;
import pt.ipleiria.estg.tesp.psi.projsi.sistematrocas.projetosi_android_1718.singletons.SingletonAnuncios;
import pt.ipleiria.estg.tesp.psi.projsi.sistematrocas.projetosi_android_1718.singletons.SingletonPropostas;

public class MainActivity extends NavDrawerActivity {

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


            lvAnuncios = findViewById(R.id.lvAnuncios);
            lvPropostas = findViewById(R.id.lvPropostas);


            propostasAdapter = new PropostasAdapter(this, SingletonPropostas.getInstance().getPropostas());
            lvPropostas.setAdapter(propostasAdapter);

            anunciosAdapter = new AnunciosAdapter(this, SingletonAnuncios.getInstance().getAnuncios());
            lvAnuncios.setAdapter(anunciosAdapter);
            //--------------

        /*}else{
            // this.gestorAnuncios =  ... savedInstanceState.getSerializable(...);
            // this.gestorPropostas =  ... savedInstanceState.getSerializable(...);
        }*/

    }
}
