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

/**
 * Created by leona on 07/11/2017.
 */

public class AnunciosAdapter extends BaseAdapter {
    private List<Anuncio> listaAnuncios;
    private Context context;
    private LayoutInflater inflater;

    public AnunciosAdapter( Context context, List<Anuncio> listaAnuncios) {
        this.listaAnuncios = listaAnuncios;
        this.context = context;
    }

    @Override
    public int getCount() {
        return listaAnuncios.size();
    }

    @Override
    public Object getItem(int i) {
        return listaAnuncios.get(i);
    }

    @Override
    public long getItemId(int i) {
        return listaAnuncios.get(i).getId();
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        View item = view;

        if (view == null)
        {
            if (inflater == null)
            {
                inflater = LayoutInflater.from(context);
            }

            item = inflater.inflate(R.layout.item_lv_anuncios, viewGroup, false);
        }

        TextView txtOfertaTitle = item.findViewById(R.id.txtOfertaA);
        TextView txtPropostaTitle = item.findViewById(R.id.txtPropostaA);

        Anuncio anuncio = listaAnuncios.get(i);

        txtOfertaTitle.setText(anuncio.getTitulo());
        txtPropostaTitle.setText(""+anuncio.getCatReceber());

        return item;
    }
}
