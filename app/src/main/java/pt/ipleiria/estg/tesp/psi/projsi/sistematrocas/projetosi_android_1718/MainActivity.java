package pt.ipleiria.estg.tesp.psi.projsi.sistematrocas.projetosi_android_1718;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
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

    private static final int RC_ANUNCIO = 1;
    private static final int RC_PROPOSTA = 2;
    public static final int RC_SUCESSO = 200;
    public static final int RC_ERRO = 400;

    private PropostasAdapter propostasAdapter;

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

        //List Views
        lvAnuncios = findViewById(R.id.lvAnuncios);
        lvPropostas = findViewById(R.id.lvPropostas);

        lvAnuncios.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Anuncio anuncio = (Anuncio) parent.getItemAtPosition(position);

                if(anuncio != null) {
                    Intent intent = new Intent(getApplicationContext(), DetalhesAnuncioActivity.class);
                    intent.putExtra(DetalhesAnuncioActivity.ID_ANUNCIO, anuncio.getId());
                    startActivityForResult(intent, RC_ANUNCIO);
                }
            }
        });

        lvPropostas.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Proposta proposta = (Proposta) parent.getItemAtPosition(position);

                if(proposta != null) {
                    Intent intent = new Intent(getApplicationContext(), DetalhesPropostaActivity.class);
                    intent.putExtra(DetalhesPropostaActivity.ID_PROPOSTA, proposta.getId());
                    startActivityForResult(intent, RC_PROPOSTA);
                }
            }
        });

        if (savedInstanceState == null) {
            SingletonAnuncios.getInstance(getApplicationContext()).setAnunciosListener(this);
            SingletonAnuncios.getInstance(getApplicationContext()).getAnunciosSugeridos(getApplicationContext());

            SingletonPropostas.getInstance(getApplicationContext()).setPropostasListener(this);
            SingletonPropostas.getInstance(getApplicationContext()).getPropostasAnunciosUser(getApplicationContext());
        }

        /*else {
            this.gestorAnuncios =  ... savedInstanceState.getSerializable(...);
            this.gestorPropostas =  ... savedInstanceState.getSerializable(...);
        }*/
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        if (requestCode == RC_ANUNCIO)
        {
            if (resultCode == RC_SUCESSO)
            {
                Snackbar.make(findViewById(R.id.coordinatorLayoutMain), R.string.msg_envio_proposta, Snackbar.LENGTH_SHORT).show();
            }else if(resultCode == RC_ERRO)
            {
                Snackbar.make(findViewById(R.id.coordinatorLayoutMain), R.string.msg_enviar_proposta, Snackbar.LENGTH_SHORT).show();
            }
        }else if (requestCode == RC_PROPOSTA)
        {
            if (resultCode == RC_SUCESSO)
            {
                Snackbar.make(findViewById(R.id.coordinatorLayoutMain), "A proposta ao anúncio " + data.getStringExtra("titulo") + " foi " + data.getStringExtra("estado") + ".", Snackbar.LENGTH_LONG).show();
                Proposta proposta = SingletonPropostas.getInstance(this).pesquisarPropostaPorID(data.getLongExtra("id", 0));
                propostasAdapter.remover(proposta);
            }else if(resultCode == RC_ERRO)
            {
                Snackbar.make(findViewById(R.id.coordinatorLayoutMain), R.string.msg_erro_aceitar_recusar, Snackbar.LENGTH_SHORT).show();
            }
        }
    }

    private void showNotification(String message) {
        Snackbar snackbar = Snackbar
                .make(findViewById(R.id.coordinatorLayoutMain), message, Snackbar.LENGTH_LONG);

        snackbar.show();
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
        lvAnuncios.setAdapter(anunciosAdapter);
    }

    @Override
    public void onSuccessPropostasAPI(Proposta proposta) {

    }

    @Override
    public void onErrorPropostasAPI(String message, Exception ex) {
        ex.printStackTrace();
        showNotification(message);
    }

    @Override
    public void onRefreshPropostas(ArrayList<Proposta> propostas) {
        propostasAdapter = new PropostasAdapter(this, propostas);
        lvPropostas.setAdapter(propostasAdapter);
    }
}
