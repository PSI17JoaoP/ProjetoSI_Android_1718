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
 * Created by leona on 29/01/2018.
 */

public class PropostasHAdapter extends BaseAdapter {

    private List<Proposta> listaPropostas;
    private Context context;
    private LayoutInflater inflater;

    public PropostasHAdapter( Context context, List<Proposta> listaPropostas) {
        this.listaPropostas = listaPropostas;
        this.context = context;
    }

    @Override
    public int getCount() {
        return listaPropostas.size();
    }

    @Override
    public Object getItem(int i) {
        return listaPropostas.get(i);
    }

    @Override
    public long getItemId(int i) {
        return listaPropostas.get(i).getId();
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {

        View item = view;

        if (view == null) {
            if (inflater == null)
                inflater = LayoutInflater.from(context);

            item = inflater.inflate(R.layout.item_lv_h_propostas, viewGroup, false);
        }

        TextView txtTitulo = item.findViewById(R.id.txtHTituloProposto);
        TextView txtDataProposto = item.findViewById(R.id.txtHDataProposto);
        TextView txtEstado = item.findViewById(R.id.txtHEstado);


        Proposta proposta = listaPropostas.get(i);
        Anuncio anuncio = SingletonAnuncios.getInstance(context).pesquisarAnuncioPorID(proposta.getIdAnuncio());

        txtTitulo.setText(anuncio.getTitulo());
        txtDataProposto.setText(proposta.getDataProposta());

        txtEstado.setText(proposta.getEstado());

        return item;
    }

    public void refresh(List<Proposta> propostas) {
        this.listaPropostas = propostas;
        notifyDataSetChanged();
    }
}
