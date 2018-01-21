package pt.ipleiria.estg.tesp.psi.projsi.sistematrocas.projetosi_android_1718.adaptadores;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.HashMap;

import pt.ipleiria.estg.tesp.psi.projsi.sistematrocas.projetosi_android_1718.R;

/**
 * Created by leona on 04/01/2018.
 */

public class CategoriasAdapter extends RecyclerView.Adapter<CategoriasAdapter.ViewHolder> {

    private HashMap<String, String> categoriasHashMap;

    public static class ViewHolder extends RecyclerView.ViewHolder {

        public View item;

        ViewHolder(View item) {
            super(item);
            this.item = item;
        }
    }

    public CategoriasAdapter(HashMap<String, String> categoriasHashMap) {
        this.categoriasHashMap = categoriasHashMap;
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

    @Override
    public int getItemCount() {
        return categoriasHashMap.size();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_lv_detalhes, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        TextView textViewLabel = holder.item.findViewById(R.id.textViewLabel);
        TextView textViewValue = holder.item.findViewById(R.id.textViewValue);

        Object[] keys = categoriasHashMap.keySet().toArray();
        String key = (String) keys[position];

        textViewLabel.setText(key);
        textViewValue.setText(categoriasHashMap.get(key));
    }
}
