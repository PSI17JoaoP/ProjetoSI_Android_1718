package pt.ipleiria.estg.tesp.psi.projsi.sistematrocas.projetosi_android_1718.adaptadores;

import android.content.Context;
import android.support.design.widget.Snackbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import pt.ipleiria.estg.tesp.psi.projsi.sistematrocas.projetosi_android_1718.R;
import pt.ipleiria.estg.tesp.psi.projsi.sistematrocas.projetosi_android_1718.listeners.AnunciosListener;
import pt.ipleiria.estg.tesp.psi.projsi.sistematrocas.projetosi_android_1718.listeners.PropostasListener;
import pt.ipleiria.estg.tesp.psi.projsi.sistematrocas.projetosi_android_1718.modelos.Anuncio;
import pt.ipleiria.estg.tesp.psi.projsi.sistematrocas.projetosi_android_1718.modelos.Proposta;
import pt.ipleiria.estg.tesp.psi.projsi.sistematrocas.projetosi_android_1718.singletons.SingletonAnuncios;
import pt.ipleiria.estg.tesp.psi.projsi.sistematrocas.projetosi_android_1718.singletons.SingletonPropostas;


/**
 * Created by leona on 06/11/2017.
 */

public class PropostasAdapter extends BaseAdapter implements AnunciosListener, PropostasListener{

    private List<Proposta> listaPropostas;
    private Context context;
    private LayoutInflater inflater;

    private Proposta proposta;
    private Anuncio anuncioProposta;

    public PropostasAdapter(Context context, List<Proposta> listaPropostas) {
        this.context = context;
        this.listaPropostas = listaPropostas;
    }

    @Override
    public int getCount() {
        return this.listaPropostas.size();
    }

    @Override
    public Object getItem(int i) {
        return this.listaPropostas.get(i);
    }

    @Override
    public long getItemId(int i) {
        return this.listaPropostas.get(i).getId();
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {

        View item = view;

        if (view == null) {

            if (inflater == null) {
                inflater = LayoutInflater.from(context);
            }

            item = inflater.inflate(R.layout.item_lv_propostas, viewGroup, false);
        }

        TextView txtOfertaTitle = item.findViewById(R.id.txtOferta);
        TextView txtPropostaTitle = item.findViewById(R.id.txtProposta);

        final Button btnAceitar = item.findViewById(R.id.btnAceitar);
        final Button btnRecusar = item.findViewById(R.id.btnRecusar);

        proposta = listaPropostas.get(i);
        anuncioProposta = SingletonAnuncios.getInstance(context).pesquisarAnuncioID(proposta.getIdAnuncio());

        SingletonPropostas.getInstance(context).setPropostasListener(this);
        SingletonAnuncios.getInstance(context).setAnunciosListener(this);

        btnAceitar.setTag(i);
        btnAceitar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Integer position = (Integer) btnAceitar.getTag();
                Proposta proposta = listaPropostas.get(position);

                proposta.setEstado("ACEITE");
                anuncioProposta.setEstado("FECHADO");

                SingletonPropostas.getInstance(context).alterarProposta(proposta);
            }
        });

        btnRecusar.setTag(i);
        btnRecusar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Integer position = (Integer) btnRecusar.getTag();
                Proposta proposta = listaPropostas.get(position);

                proposta.setEstado("RECUSADO");
                anuncioProposta.setEstado("FECHADO");

                SingletonPropostas.getInstance(context).alterarProposta(proposta);
            }
        });

        txtPropostaTitle.setText(String.valueOf(proposta.getCatProposto()));

        if (anuncioProposta != null) {
            txtOfertaTitle.setText(anuncioProposta.getTitulo());
        } else {
            txtOfertaTitle.setText(String.valueOf(proposta.getIdAnuncio()));
        }

        return item;
    }

    @Override
    public void onSuccessAnunciosAPI(Anuncio anuncio) {
        Toast.makeText(context, "A proposta ao anúncio " + anuncio.getTitulo() +
                " foi " + proposta.getEstado().toLowerCase() + ".", Toast.LENGTH_LONG).show();
    }

    @Override
    public void onErrorAnunciosAPI(String message, Exception ex) {
        ex.printStackTrace();
        Toast.makeText(context, message, Toast.LENGTH_LONG).show();
    }

    @Override
    public void onRefreshAnuncios(ArrayList<Anuncio> anuncios) {

    }

    @Override
    public void onSuccessPropostasAPI(Proposta proposta) {
        if(proposta != null) {
            SingletonAnuncios.getInstance(context).alterarAnuncio(anuncioProposta);
        }
    }

    @Override
    public void onErrorPropostasAPI(String message, Exception ex) {
        ex.printStackTrace();
        Toast.makeText(context, message, Toast.LENGTH_LONG).show();
    }

    @Override
    public void onRefreshPropostas(ArrayList<Proposta> propostas) {

    }
}
