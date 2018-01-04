package pt.ipleiria.estg.tesp.psi.projsi.sistematrocas.projetosi_android_1718.adaptadores;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import pt.ipleiria.estg.tesp.psi.projsi.sistematrocas.projetosi_android_1718.R;

/**
 * Created by leona on 04/01/2018.
 */

public class CategoriasAdapter extends BaseAdapter {
    private ArrayList<String> listaLabels;
    private ArrayList<String> listaValores;
    private Context context;
    private LayoutInflater inflater;

    public CategoriasAdapter(ArrayList<String> listaLabels, ArrayList<String> listaValores, Context context) {
        this.listaLabels = listaLabels;
        this.listaValores = listaValores;
        this.context = context;
    }

    @Override
    public int getCount() {
        return listaLabels.size();
    }

    @Override
    public Object getItem(int i) {
        return listaValores.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup)
    {
        View item = view;

        if (view == null)
        {
            if (inflater == null)
            {
                inflater = LayoutInflater.from(context);
            }

            item = inflater.inflate(R.layout.item_lv_detalhes, viewGroup, false);
        }

        TextView txtLabel = item.findViewById(R.id.txtDetalhesLabel);
        TextView txtValor = item.findViewById(R.id.txtDetalhesValor);

        txtLabel.setText(listaLabels.get(i).toString());
        txtValor.setText(listaValores.get(i).toString());

        return item;
    }
}
