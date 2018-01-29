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
 * Created by leona on 29/01/2018.
 */

public class AnunciosHAdapter extends BaseAdapter{
    private List<Anuncio> listaAnuncios;
    private Context context;
    private LayoutInflater inflater;

    public AnunciosHAdapter( Context context, List<Anuncio> listaAnuncios) {
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

        if (view == null) {
            if (inflater == null)
                inflater = LayoutInflater.from(context);

            item = inflater.inflate(R.layout.item_lv_h_anuncios, viewGroup, false);
        }

        TextView txtTitulo = item.findViewById(R.id.txtHTitulo);
        TextView txtDataCriacao = item.findViewById(R.id.txtHDataCriacao);
        TextView txtDataConclusao = item.findViewById(R.id.txtHDataConclusao);

        Anuncio anuncio = listaAnuncios.get(i);

        txtTitulo.setText(anuncio.getTitulo());
        txtDataCriacao.setText(anuncio.getDataCriacao());

        if (anuncio.getDataConclusao() != null)
            txtDataConclusao.setText(anuncio.getDataConclusao());

        return item;
    }

    public void refresh(List<Anuncio> anuncios) {
        this.listaAnuncios = anuncios;
        notifyDataSetChanged();
    }
}
