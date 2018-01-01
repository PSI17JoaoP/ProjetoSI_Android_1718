package pt.ipleiria.estg.tesp.psi.projsi.sistematrocas.projetosi_android_1718.singletons;

import android.content.Context;
import android.widget.Toast;

import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;

import org.json.JSONArray;

import java.util.ArrayList;
import java.util.List;

import pt.ipleiria.estg.tesp.psi.projsi.sistematrocas.projetosi_android_1718.helpers.TipoRoupaBDTable;
import pt.ipleiria.estg.tesp.psi.projsi.sistematrocas.projetosi_android_1718.listeners.TiposRoupaListener;
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

    private TiposRoupaListener tiposRoupaListener;

    public static SingletonTiposRoupa getInstance(Context context) {
        if (INSTANCE == null)
            INSTANCE = new SingletonTiposRoupa(context);

        return INSTANCE;
    }

    private SingletonTiposRoupa(Context context) {
        this.context = context;
        tiposRoupa = new ArrayList<>();
        bdTable = new TipoRoupaBDTable(context);
        tiposRoupa = bdTable.select();
        getTiposRoupaAPI();
    }

    private void getTiposRoupaAPI() {

        //SharedPreferences preferences = getSharedPreferences("APP_SETTINGS", Context.MODE_PRIVATE);
        //String pin = preferences.getString("pin", "");

        //Para efeito de desenvolvimento.
        String pin = "MPW7P";

        if (tiposRoupa.isEmpty()) {

            if (SingletonAPIManager.getInstance(context).ligadoInternet()) {

                SingletonAPIManager.getInstance(context).setAuth(pin);

                JsonArrayRequest tiposAPI = SingletonAPIManager.getInstance(context).pedirVariosAPI("categorias/tipos", new SingletonAPIManager.APIJsonArrayResposta() {
                    @Override
                    public void Sucesso(JSONArray resultados) {
                        tiposRoupa = TiposRoupaParser.paraObjeto(resultados, context);
                        adicionarTiposRoupaBD(tiposRoupa);

                        if (tiposRoupaListener != null)
                            tiposRoupaListener.onRefreshTiposRoupa(tiposRoupa, context);
                    }

                    @Override
                    public void Erro(VolleyError erro) {
                        if (tiposRoupaListener != null)
                            tiposRoupaListener.onErrorTiposRoupaAPI("Ocorreu um erro na sincronização dos tipos de roupa.", erro);
                    }
                });

                SingletonAPIManager.getInstance(context).getRequestQueue().add(tiposAPI);
            }
        }
    }

    private void adicionarTiposRoupaBD(ArrayList<TipoRoupa> tiposRoupa) {
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

    public boolean adicionarTipo(TipoRoupa tipo) {
        TipoRoupa tipoRoupaInserido = bdTable.insert(tipo);

        return tipoRoupaInserido != null && tiposRoupa.add(tipoRoupaInserido);
    }

    public boolean removerTipo(TipoRoupa tipo) {
        return bdTable.delete(tipo.getId()) && tiposRoupa.remove(tipo);
    }

    public boolean editarTipo(TipoRoupa tipo) {
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

    public void setTiposRoupaListener(TiposRoupaListener tiposRoupaListener) {
        this.tiposRoupaListener = tiposRoupaListener;
    }
}
