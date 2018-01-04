package pt.ipleiria.estg.tesp.psi.projsi.sistematrocas.projetosi_android_1718.singletons;

import android.content.Context;
import android.content.SharedPreferences;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

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

                        if(propostasJson.length() > 0) {
                            propostas = PropostasParser.paraObjeto(propostasJson, context);
                            adicionarPropostasLocal(propostas);

                            if (propostasListener != null)
                                propostasListener.onRefreshPropostas(propostas);
                        }

                    } catch (JSONException e) {
                        if (propostasListener != null)
                            propostasListener.onErrorPropostasAPI("Ocorreu um erro no processamento das propostas recebidas da API.", e);
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

            ArrayList<Proposta> propostaUser = bdTable.select(" WHERE " + PropostaBDTable.ESTADO_PROPOSTA + " = ?", new String[]{"ABERTO"});

            if (propostasListener != null)
                propostasListener.onRefreshPropostas(propostaUser);
        }
    }

    public void adicionarProposta(Proposta proposta) {

        if (SingletonAPIManager.getInstance(context).ligadoInternet()) {

            StringRequest propostasAPI = SingletonAPIManager.getInstance(context).enviarAPI("propostas",
                    Request.Method.POST, PropostasParser.paraJson(proposta), new SingletonAPIManager.APIStringResposta() {
                        @Override
                        public void Sucesso(String resposta) {
                            try {
                                Proposta novaProposta = PropostasParser.paraObjeto(new JSONObject(resposta), context);

                                if (adicionarPropostaLocal(novaProposta)) {
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
    }

    public void alterarProposta(Proposta proposta) {

        if (SingletonAPIManager.getInstance(context).ligadoInternet()) {

            StringRequest propostasAPI = SingletonAPIManager.getInstance(context).enviarAPI("propostas/" + proposta.getId().intValue(),
                    Request.Method.PUT, PropostasParser.paraJson(proposta), new SingletonAPIManager.APIStringResposta() {
                        @Override
                        public void Sucesso(String resposta) {
                            try {
                                Proposta propostaAlterada = PropostasParser.paraObjeto(new JSONObject(resposta), context);

                                if (editarPropostaLocal(propostaAlterada)) {
                                    if (propostasListener != null)
                                        propostasListener.onSuccessPropostasAPI(propostaAlterada);
                                }

                            } catch (JSONException e) {
                                if (propostasListener != null)
                                    propostasListener.onErrorPropostasAPI("Ocorreu um erro no processamento da proposta alterada.", e);
                            }
                        }

                        @Override
                        public void Erro(VolleyError erro) {
                            if (propostasListener != null)
                                propostasListener.onErrorPropostasAPI("Não foi possível alterar a proposta.", erro);
                        }
                    });

            SingletonAPIManager.getInstance(context).getRequestQueue().add(propostasAPI);
        }
    }

    public void apagarProposta(final Proposta proposta) {

        if (SingletonAPIManager.getInstance(context).ligadoInternet()) {

            StringRequest propostasAPI = SingletonAPIManager.getInstance(context).enviarAPI("propostas/" + proposta.getId().intValue(),
                    Request.Method.DELETE, null, new SingletonAPIManager.APIStringResposta() {
                        @Override
                        public void Sucesso(String resposta) {

                            if (removerPropostaLocal(proposta)) {
                                if (propostasListener != null)
                                    propostasListener.onSuccessPropostasAPI(proposta);
                            }
                        }

                        @Override
                        public void Erro(VolleyError erro) {
                            if (propostasListener != null)
                                propostasListener.onErrorPropostasAPI("Não foi possível apagar a proposta.", erro);
                        }
                    });

            SingletonAPIManager.getInstance(context).getRequestQueue().add(propostasAPI);
        }
    }

    //------------------------------------------------------------
    //LOCAL A PARTIR DAQUI

    public void adicionarPropostasLocal(ArrayList<Proposta> propostasList) {

        for(Proposta proposta:propostasList)
        {
            bdTable.insert(proposta);
        }
    }

    public boolean adicionarPropostaLocal(Proposta proposta) {
        Proposta propostaInserida = bdTable.insert(proposta);

        return propostaInserida != null && propostas.add(propostaInserida);
    }

    public boolean removerPropostaLocal(Proposta proposta) {
        return bdTable.delete(proposta.getId()) && propostas.remove(proposta);
    }

    public boolean editarPropostaLocal(Proposta proposta) {

        if(bdTable.update(proposta)) {
            Proposta novaProposta = propostas.set(proposta.getId().intValue(), proposta);

            return propostas.contains(novaProposta);
        } else {
            return false;
        }
    }

    public Proposta pesquisarPropostaID(Long id) {

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

    public Integer getPropostasCount(){
        return propostas.size();
    }

    public void setPropostasListener(PropostasListener propostasListener) {
        this.propostasListener = propostasListener;
    }
}
