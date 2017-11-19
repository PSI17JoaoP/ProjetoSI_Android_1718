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


        LinearLayout layout = findViewById(R.id.layoutMain);
        layout.getLayoutTransition().enableTransitionType(LayoutTransition.CHANGING);

        //Animar lvPropostas
        View lblPropostas = findViewById(R.id.lblPropostas);

        lblPropostas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ViewGroup.LayoutParams params = lvPropostas.getLayoutParams();
                params.height = params.height == 0 ? ViewGroup.LayoutParams.WRAP_CONTENT : 0;
                lvPropostas.setLayoutParams(params);
            }
        });

        //Animar lvAnuncios
        View lblAnuncios = findViewById(R.id.lblAnuncios);

        lblAnuncios.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ViewGroup.LayoutParams params = lvAnuncios.getLayoutParams();
                params.height = params.height == 0 ? ViewGroup.LayoutParams.WRAP_CONTENT : 0;
                lvAnuncios.setLayoutParams(params);
            }
        });




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
