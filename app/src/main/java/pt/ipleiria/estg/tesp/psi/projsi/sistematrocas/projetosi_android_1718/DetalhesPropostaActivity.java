package pt.ipleiria.estg.tesp.psi.projsi.sistematrocas.projetosi_android_1718;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import pt.ipleiria.estg.tesp.psi.projsi.sistematrocas.projetosi_android_1718.listeners.AnunciosListener;
import pt.ipleiria.estg.tesp.psi.projsi.sistematrocas.projetosi_android_1718.listeners.PropostasListener;
import pt.ipleiria.estg.tesp.psi.projsi.sistematrocas.projetosi_android_1718.modelos.Anuncio;
import pt.ipleiria.estg.tesp.psi.projsi.sistematrocas.projetosi_android_1718.modelos.Proposta;
import pt.ipleiria.estg.tesp.psi.projsi.sistematrocas.projetosi_android_1718.singletons.SingletonAnuncios;
import pt.ipleiria.estg.tesp.psi.projsi.sistematrocas.projetosi_android_1718.singletons.SingletonPropostas;

public class DetalhesPropostaActivity extends NavDrawerActivity implements AnunciosListener, PropostasListener{

    private Anuncio anuncioProposta;
    private Proposta proposta;

    public static final String ID_PROPOSTA = "ID";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_detalhes_proposta);
        View.inflate(this, R.layout.activity_detalhes_proposta, (ViewGroup) findViewById(R.id.app_content));

        FloatingActionButton fabAceitar = findViewById(R.id.fabAceitar);
        FloatingActionButton fabRecusar = findViewById(R.id.fabRecusar);

        if(getIntent().hasExtra(ID_PROPOSTA)) {

            Long propostaId = getIntent().getLongExtra(ID_PROPOSTA, 0);
            proposta = SingletonPropostas.getInstance(getApplicationContext()).pesquisarPropostaID(propostaId);

            if(proposta != null) {
                anuncioProposta = SingletonAnuncios.getInstance(getApplicationContext()).pesquisarAnuncioID(proposta.getIdAnuncio());

                SingletonPropostas.getInstance(getApplicationContext()).setPropostasListener(this);
                SingletonAnuncios.getInstance(getApplicationContext()).setAnunciosListener(this);

                fabAceitar.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        proposta.setEstado("ACEITE");
                        anuncioProposta.setEstado("FECHADO");

                        SingletonPropostas.getInstance(getApplicationContext()).alterarProposta(proposta, getApplicationContext());
                    }
                });

                fabRecusar.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        proposta.setEstado("RECUSADA");
                        anuncioProposta.setEstado("FECHADO");

                        SingletonPropostas.getInstance(getApplicationContext()).alterarProposta(proposta, getApplicationContext());
                    }
                });
            }
        }
    }

    private void showNotification(String message) {
        Snackbar snackbar = Snackbar
                .make(findViewById(R.id.coordinatorLayoutDetalhesProposta), message, Snackbar.LENGTH_LONG);

        snackbar.show();
    }

    @Override
    public void onSuccessAnunciosAPI(Anuncio anuncio) {
        showNotification("A proposta ao anúncio " + anuncio.getTitulo() + " foi " + proposta.getEstado().toLowerCase() + ".");
    }

    @Override
    public void onErrorAnunciosAPI(String message, Exception ex) {
        ex.printStackTrace();
        showNotification(message);
    }

    @Override
    public void onRefreshAnuncios(ArrayList<Anuncio> anuncios) {

    }

    @Override
    public void onSuccessPropostasAPI(Proposta proposta) {
        if(proposta != null) {
            SingletonAnuncios.getInstance(getApplicationContext()).alterarAnuncio(anuncioProposta, getApplicationContext());
        }
    }

    @Override
    public void onErrorPropostasAPI(String message, Exception ex) {
        ex.printStackTrace();
        showNotification(message);
    }

    @Override
    public void onRefreshPropostas(ArrayList<Proposta> propostas) {

    }
}
