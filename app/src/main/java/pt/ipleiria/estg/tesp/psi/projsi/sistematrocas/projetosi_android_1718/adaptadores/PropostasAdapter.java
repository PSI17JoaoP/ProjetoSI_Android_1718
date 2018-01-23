package pt.ipleiria.estg.tesp.psi.projsi.sistematrocas.projetosi_android_1718.adaptadores;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

import pt.ipleiria.estg.tesp.psi.projsi.sistematrocas.projetosi_android_1718.R;
import pt.ipleiria.estg.tesp.psi.projsi.sistematrocas.projetosi_android_1718.modelos.Anuncio;
import pt.ipleiria.estg.tesp.psi.projsi.sistematrocas.projetosi_android_1718.modelos.Proposta;
import pt.ipleiria.estg.tesp.psi.projsi.sistematrocas.projetosi_android_1718.singletons.SingletonAnuncios;

/**
 * Created by leona on 06/11/2017.
 */

public class PropostasAdapter extends BaseAdapter {
    private List<Proposta> listaPropostas;
    private Context context;
    private LayoutInflater inflater;

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
            if (inflater == null)
                inflater = LayoutInflater.from(context);

            item = inflater.inflate(R.layout.item_lv_propostas, viewGroup, false);
        }

        TextView txtOfertaTitle = item.findViewById(R.id.txtOferta);
        TextView txtPropostaTitle = item.findViewById(R.id.txtProposta);

        Proposta proposta = listaPropostas.get(i);
        txtPropostaTitle.setText(String.valueOf(proposta.getCatProposto()));

        Anuncio anuncioProposta = SingletonAnuncios.getInstance(context).pesquisarAnuncioPorID(proposta.getIdAnuncio());

        if (anuncioProposta != null) {
            txtOfertaTitle.setText(anuncioProposta.getTitulo());
        } else {
            txtOfertaTitle.setText(String.valueOf(proposta.getIdAnuncio()));
        }

        return item;
    }
}
