package pt.ipleiria.estg.tesp.psi.projsi.sistematrocas.projetosi_android_1718.singletons;

import android.content.Context;
import android.widget.Toast;

import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;

import org.json.JSONArray;

import java.util.ArrayList;
import java.util.List;

import pt.ipleiria.estg.tesp.psi.projsi.sistematrocas.projetosi_android_1718.helpers.TipoRoupaBDTable;
import pt.ipleiria.estg.tesp.psi.projsi.sistematrocas.projetosi_android_1718.modelos.TipoRoupa;
import pt.ipleiria.estg.tesp.psi.projsi.sistematrocas.projetosi_android_1718.parsers.TiposRoupaParser;

/**
 * Created by leona on 24/11/2017.
 */

public class SingletonTiposRoupa {
    private Context context;
    private static SingletonTiposRoupa INSTANCE = null;
    private ArrayList<TipoRoupa> tiposRoupa;
    private TipoRoupaBDTable bdTable;

    public static SingletonTiposRoupa getInstance(Context context) {
        if (INSTANCE == null)
            INSTANCE = new SingletonTiposRoupa(context);

        return INSTANCE;
    }

    private SingletonTiposRoupa(Context context) {
        this.context = context;
        tiposRoupa = new ArrayList<>();
        bdTable = new TipoRoupaBDTable(context);
        getTiposRoupaAPI();
    }

    private void getTiposRoupaAPI() {

        tiposRoupa = bdTable.select();

        if(tiposRoupa.isEmpty()) {

            if (SingletonAPIManager.getInstance(context).ligadoInternet()) {
                JsonArrayRequest tiposAPI = SingletonAPIManager.getInstance(context).pedirVariosAPI("categorias/tipos", new SingletonAPIManager.APIJsonArrayResposta() {
                    @Override
                    public void Sucesso(JSONArray resultados) {
                        tiposRoupa = TiposRoupaParser.paraObjeto(resultados, context);

                        getTiposRoupaBD(tiposRoupa);
                    }

                    @Override
                    public void Erro(VolleyError erro) {
                        Toast.makeText(context, "Não foi possível sincronizar os tipos de roupa com a API - " +
                                erro.networkResponse.statusCode, Toast.LENGTH_SHORT).show();
                    }
                });

                SingletonAPIManager.getInstance(context).getRequestQueue().add(tiposAPI);
            }
        }
    }

    private void getTiposRoupaBD(ArrayList<TipoRoupa> tiposRoupa) {
        for (TipoRoupa tipoRoupa : tiposRoupa) {
            bdTable.insert(tipoRoupa);
        }
    }

    public ArrayList<TipoRoupa> getTiposRoupa() {
        return tiposRoupa;
    }

    public void setTiposRoupa(ArrayList<TipoRoupa> tiposRoupa) {
        this.tiposRoupa = tiposRoupa;
    }

    public boolean adicionarTipo(TipoRoupa tipo)
    {
        TipoRoupa tipoRoupaInserido = bdTable.insert(tipo);

        return tipoRoupaInserido != null && tiposRoupa.add(tipoRoupaInserido);
    }

    public boolean removerTipo(TipoRoupa tipo)
    {
        return bdTable.delete(tipo.getId()) && tiposRoupa.remove(tipo);
    }

    public boolean editarTipo(TipoRoupa tipo)
    {
        if(bdTable.update(tipo)) {
            TipoRoupa novoTipo = tiposRoupa.set(tipo.getId().intValue(), tipo);

            return tiposRoupa.contains(novoTipo);
        } else {
            return false;
        }
    }

    public TipoRoupa pesquisarTipoRoupaID(Long id)
    {
        return tiposRoupa.get(id.intValue());
    }
}
