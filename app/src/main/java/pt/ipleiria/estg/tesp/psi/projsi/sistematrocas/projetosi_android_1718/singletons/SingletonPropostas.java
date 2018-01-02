package pt.ipleiria.estg.tesp.psi.projsi.sistematrocas.projetosi_android_1718.singletons;

import android.content.Context;
import android.content.SharedPreferences;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.google.gson.JsonArray;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import pt.ipleiria.estg.tesp.psi.projsi.sistematrocas.projetosi_android_1718.helpers.PropostaBDTable;
import pt.ipleiria.estg.tesp.psi.projsi.sistematrocas.projetosi_android_1718.listeners.PropostasListener;
import pt.ipleiria.estg.tesp.psi.projsi.sistematrocas.projetosi_android_1718.modelos.Proposta;
import pt.ipleiria.estg.tesp.psi.projsi.sistematrocas.projetosi_android_1718.parsers.PropostasParser;

/**
 * Created by leona on 21/11/2017.
 */

public class SingletonPropostas {
    private static SingletonPropostas INSTANCE = null;
    private ArrayList<Proposta> propostas;
    private PropostaBDTable bdTable;
    private Context context;

    private PropostasListener propostasListener;

    public static SingletonPropostas getInstance(Context context) {
        if (INSTANCE == null)
            INSTANCE = new SingletonPropostas(context);

        return INSTANCE;
    }

    private SingletonPropostas(Context context) {
        this.context = context;
        propostas = new ArrayList<>();
        bdTable = new PropostaBDTable(context);
        propostas = bdTable.select();
    }

    public ArrayList<Proposta> getPropostas() {
        return propostas;
    }

    public void getPropostasAnunciosUser() {

        SharedPreferences preferences = context.getSharedPreferences("APP_SETTINGS", Context.MODE_PRIVATE);
        String username = preferences.getString("username", "");

        if (SingletonAPIManager.getInstance(context).ligadoInternet()) {

            JsonObjectRequest propostasAPI = SingletonAPIManager.getInstance(context).pedirAPI("anuncios/propostas/"+username, new SingletonAPIManager.APIJsonResposta() {

                @Override
                public void Sucesso(JSONObject resultado) {
                    try {
                        JSONArray propostasJson = resultado.getJSONArray("Propostas");
                        propostas = PropostasParser.paraObjeto(propostasJson, context);

                        adicionarPropostasLocal(propostas);

                        if (propostasListener != null)
                            propostasListener.onRefreshPropostas(propostas);

                    } catch (JSONException e) {
                            e.printStackTrace();
                        }
                }

                @Override
                public void Erro(VolleyError erro) {
                    if (propostasListener != null)
                        propostasListener.onErrorPropostasAPI("Não foi possível sincronizar as propostas com a API.", erro);
                }
            });

            SingletonAPIManager.getInstance(context).getRequestQueue().add(propostasAPI);

        } else {
            if (propostasListener != null)
                propostasListener.onRefreshPropostas(propostas);
        }
    }

    public void adicionarProposta(Proposta proposta) {

        StringRequest propostasAPI = SingletonAPIManager.getInstance(context).enviarAPI("propostas",
                Request.Method.POST, PropostasParser.paraJson(proposta), new SingletonAPIManager.APIStringResposta() {
                    @Override
                    public void Sucesso(String resposta) {
                        try {
                            Proposta novaProposta = PropostasParser.paraObjeto(new JSONObject(resposta), context);

                            if(adicionarPropostaLocal(novaProposta)) {
                                if (propostasListener != null)
                                    propostasListener.onSuccessPropostasAPI(novaProposta);
                            }

                        } catch (JSONException e) {
                            if (propostasListener != null)
                                propostasListener.onErrorPropostasAPI("Ocorreu um erro no processamento da Proposta.", e);
                        }
                    }

                    @Override
                    public void Erro(VolleyError erro) {
                        if (propostasListener != null)
                            propostasListener.onErrorPropostasAPI("Ocorreu um erro no envio da Proposta para a API.", erro);
                    }
                });

        SingletonAPIManager.getInstance(context).getRequestQueue().add(propostasAPI);
    }

    public void alterarProposta(Proposta proposta) {

        StringRequest propostasAPI = SingletonAPIManager.getInstance(context).enviarAPI("propostas/"+proposta.getId().intValue(),
                Request.Method.PUT, PropostasParser.paraJson(proposta), new SingletonAPIManager.APIStringResposta() {
                    @Override
                    public void Sucesso(String resposta) {
                        try {
                            Proposta altProposta = PropostasParser.paraObjeto(new JSONObject(resposta), context);

                            if (propostasListener != null)
                                propostasListener.onUpdatePropostas(altProposta, 2);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void Erro(VolleyError erro) {
                        Toast.makeText(context, "Não foi possível alterar a proposta", Toast.LENGTH_SHORT).show();
                    }
                });

        SingletonAPIManager.getInstance(context).getRequestQueue().add(propostasAPI);
    }

    public void apagarProposta(final Proposta proposta) {

        StringRequest propostasAPI = SingletonAPIManager.getInstance(context).enviarAPI("propostas/"+proposta.getId().intValue(),
                Request.Method.DELETE, null, new SingletonAPIManager.APIStringResposta() {
                    @Override
                    public void Sucesso(String resposta) {
                        if (propostasListener != null)
                            propostasListener.onUpdatePropostas(proposta, 3);
                    }

                    @Override
                    public void Erro(VolleyError erro) {
                        Toast.makeText(context, "Não foi possível apagar a proposta", Toast.LENGTH_SHORT).show();
                    }
                });

        SingletonAPIManager.getInstance(context).getRequestQueue().add(propostasAPI);
    }

    //------------------------------------------------------------
    //LOCAL A PARTIR DAQUI

    public Integer getPropostasCount(){
        return propostas.size();
    }

    public void adicionarPropostasLocal(ArrayList<Proposta> propostasList)
    {
        for(Proposta proposta:propostasList)
        {
            bdTable.insert(proposta);
        }
    }

    public boolean adicionarPropostaLocal(Proposta proposta)
    {
        Proposta propostaInserida = bdTable.insert(proposta);

        return propostaInserida != null && propostas.add(propostaInserida);
    }

    public boolean removerPropostaLocal(Proposta proposta)
    {
        return bdTable.delete(proposta.getId()) && propostas.remove(proposta);
    }

    public boolean editarPropostaLocal(Proposta proposta)
    {
        if(bdTable.update(proposta)) {
            Proposta novaProposta = propostas.set(proposta.getId().intValue(), proposta);

            return propostas.contains(novaProposta);
        } else {
            return false;
        }
    }

    public Proposta pesquisarPropostaID(Long id)
    {
        for (Proposta proposta:propostas) {
            if (proposta.getId() == id)
                return proposta;
        }

        return null;
    }

    public Proposta pesquisarPropostaPosicao(int i)
    {
        return propostas.get(i);
    }

    public void setPropostasListener(PropostasListener propostasListener) {
        this.propostasListener = propostasListener;
    }
}
