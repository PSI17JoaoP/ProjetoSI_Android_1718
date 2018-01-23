package pt.ipleiria.estg.tesp.psi.projsi.sistematrocas.projetosi_android_1718.singletons;

import android.content.Context;

import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;

import org.json.JSONArray;

import java.util.ArrayList;

import pt.ipleiria.estg.tesp.psi.projsi.sistematrocas.projetosi_android_1718.helpers.TipoRoupaBDTable;
import pt.ipleiria.estg.tesp.psi.projsi.sistematrocas.projetosi_android_1718.listeners.TiposRoupaListener;
import pt.ipleiria.estg.tesp.psi.projsi.sistematrocas.projetosi_android_1718.modelos.TipoRoupa;
import pt.ipleiria.estg.tesp.psi.projsi.sistematrocas.projetosi_android_1718.parsers.TiposRoupaParser;

/**
 * Created by leona on 24/11/2017.
 */

public class SingletonTiposRoupa {
    private static SingletonTiposRoupa INSTANCE = null;
    private ArrayList<TipoRoupa> tiposRoupa;
    private TipoRoupaBDTable bdTable;

    private TiposRoupaListener tiposRoupaListener;

    public static SingletonTiposRoupa getInstance(Context context) {
        if (INSTANCE == null)
            INSTANCE = new SingletonTiposRoupa(context);

        return INSTANCE;
    }

    private SingletonTiposRoupa(Context context) {
        tiposRoupa = new ArrayList<>();
        bdTable = new TipoRoupaBDTable(context);
        tiposRoupa = bdTable.select();
        getTiposRoupaAPI(context);
    }

    public ArrayList<TipoRoupa> getTiposRoupa() {
        return tiposRoupa;
    }

    private void getTiposRoupaAPI(final Context context) {

        if (tiposRoupa.isEmpty()) {

            if (SingletonAPIManager.getInstance(context).ligadoInternet(context)) {

                JsonArrayRequest tiposAPI = SingletonAPIManager.getInstance(context).pedirVariosAPI("categorias/tipos", context, new SingletonAPIManager.APIJsonArrayResposta() {
                    @Override
                    public void Sucesso(JSONArray resultados) {
                        tiposRoupa = TiposRoupaParser.paraObjeto(resultados, context);
                        adicionarTiposRoupaLocal(tiposRoupa);

                        if (tiposRoupaListener != null)
                            tiposRoupaListener.onRefreshTiposRoupa(tiposRoupa, context);
                    }

                    @Override
                    public void Erro(VolleyError erro) {
                        if (tiposRoupaListener != null)
                            tiposRoupaListener.onErrorTiposRoupaAPI("Ocorreu um erro na sincronização dos tipos de roupa.", erro);
                    }
                });

                SingletonAPIManager.getInstance(context).getRequestQueue(context).add(tiposAPI);
            }
        }
    }

    private void adicionarTiposRoupaLocal(ArrayList<TipoRoupa> tiposRoupa) {
        for (TipoRoupa tipoRoupa : tiposRoupa) {
            bdTable.insert(tipoRoupa);
        }
    }

    public boolean adicionarTipoRoupaLocal(TipoRoupa tipo) {
        TipoRoupa tipoRoupaInserido = bdTable.insert(tipo);

        return tipoRoupaInserido != null && tiposRoupa.add(tipoRoupaInserido);
    }

    public boolean removerTipoRoupaLocal(TipoRoupa tipo) {
        return bdTable.delete(tipo.getId()) && tiposRoupa.remove(tipo);
    }

    public boolean editarTipoRoupaLocal(TipoRoupa tipo) {
        if(bdTable.update(tipo)) {
            TipoRoupa novoTipo = tiposRoupa.set(tipo.getId().intValue(), tipo);

            return tiposRoupa.contains(novoTipo);
        } else {
            return false;
        }
    }

    public TipoRoupa pesquisarTipoRoupaPorID(Long id) {
        for (TipoRoupa tipo : tiposRoupa) {
            if (tipo.getId().toString().equals(id.toString())) {
                return tipo;
            }
        }

        return null;
    }

    public void setTiposRoupaListener(TiposRoupaListener tiposRoupaListener) {
        this.tiposRoupaListener = tiposRoupaListener;
    }
}
