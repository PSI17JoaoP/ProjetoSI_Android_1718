package pt.ipleiria.estg.tesp.psi.projsi.sistematrocas.projetosi_android_1718.singletons;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Base64;

import com.android.volley.Request;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import pt.ipleiria.estg.tesp.psi.projsi.sistematrocas.projetosi_android_1718.helpers.PropostaBDTable;
import pt.ipleiria.estg.tesp.psi.projsi.sistematrocas.projetosi_android_1718.listeners.ImagesListener;
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

    private SharedPreferences preferences;

    private PropostasListener propostasListener;
    private ImagesListener imagesListener;

    public static SingletonPropostas getInstance(Context context) {
        if (INSTANCE == null)
            INSTANCE = new SingletonPropostas(context);

        return INSTANCE;
    }

    private SingletonPropostas(Context context) {
        preferences = context.getSharedPreferences("APP_SETTINGS", Context.MODE_PRIVATE);
        propostas = new ArrayList<>();
        bdTable = new PropostaBDTable(context);
        propostas = bdTable.select(" WHERE " + PropostaBDTable.ESTADO_PROPOSTA + " = ?", new String[] {"PENDENTE"});
        getPropostasAnunciosUser(context);
    }

    public ArrayList<Proposta> getPropostas() {
        return propostas;
    }

    public void getPropostasAnunciosUser(final Context context) {

        if(propostas.isEmpty()) {

            String username = preferences.getString("username", "");

            if (SingletonAPIManager.getInstance(context).ligadoInternet(context)) {

                JsonObjectRequest propostasAPI = SingletonAPIManager.getInstance(context).pedirAPI("anuncios/propostas/" + username, context, new SingletonAPIManager.APIJsonResposta() {

                    @Override
                    public void Sucesso(JSONObject resultado) {
                        try {
                            JSONArray propostasJson = resultado.getJSONArray("Propostas");

                            if (propostasJson.length() > 0) {

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

                SingletonAPIManager.getInstance(context).getRequestQueue(context).add(propostasAPI);
            }
        } else {
            if (propostasListener != null)
                propostasListener.onRefreshPropostas(propostas);
        }
    }

    public void getImagensProposta(final Long id, final Context context) {

        if(SingletonAPIManager.getInstance(context).ligadoInternet(context)) {

            JsonObjectRequest imagensProposta = SingletonAPIManager.getInstance(context).pedirAPI("propostas/" + String.valueOf(id) + "/imagens", context, new SingletonAPIManager.APIJsonResposta() {
                @Override
                public void Sucesso(JSONObject resultado) {

                    try {

                        JSONArray imagensJSON = resultado.getJSONArray("Imagens");

                        if(imagensJSON.length() > 0) {

                            ArrayList<byte[]> imagens = new ArrayList<>();

                            for (int primeiroContador = 0; primeiroContador < imagensJSON.length(); primeiroContador++)
                            {
                                String imagemBase64 = (String) imagensJSON.get(primeiroContador);
                                byte[] imagemBytes = Base64.decode(imagemBase64, Base64.DEFAULT);

                                imagens.add(imagemBytes);
                            }

                            if (!imagens.isEmpty()) {
                                if (imagesListener != null)
                                    imagesListener.OnSucessoObterImagens(imagens);
                            }
                        }

                    } catch (JSONException e) {
                        if (imagesListener != null)
                            imagesListener.OnErrorObterImagens("Ocorreu um erro no processamento das imagens.", e);
                    }
                }

                @Override
                public void Erro(VolleyError erro) {
                    if (imagesListener != null)
                        imagesListener.OnErrorObterImagens("Não foi possível pedir as imagens da(s) proposta(s) à API.", erro);
                }
            });

            SingletonAPIManager.getInstance(context).getRequestQueue(context).add(imagensProposta);
        }
    }

    public void adicionarProposta(Proposta proposta, final Context context) {

        if (SingletonAPIManager.getInstance(context).ligadoInternet(context)) {

            StringRequest propostasAPI = SingletonAPIManager.getInstance(context).enviarAPI("propostas", context,
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

            SingletonAPIManager.getInstance(context).getRequestQueue(context).add(propostasAPI);
        }
    }

    public void alterarProposta(final Proposta proposta, final Context context) {

        if (SingletonAPIManager.getInstance(context).ligadoInternet(context)) {

            StringRequest propostasAPI = SingletonAPIManager.getInstance(context).enviarAPI("propostas/" + proposta.getId().intValue(),
                    context, Request.Method.PUT, PropostasParser.paraJson(proposta), new SingletonAPIManager.APIStringResposta() {
                        @Override
                        public void Sucesso(String resposta) {
                            try {
                                Proposta propostaAlterada = PropostasParser.paraObjeto(new JSONObject(resposta), context);
                                Integer posicao = propostas.indexOf(proposta);

                                if (editarPropostaLocal(propostaAlterada, posicao)) {
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

            SingletonAPIManager.getInstance(context).getRequestQueue(context).add(propostasAPI);
        }
    }

    public void apagarProposta(final Proposta proposta, Context context) {

        if (SingletonAPIManager.getInstance(context).ligadoInternet(context)) {

            StringRequest propostasAPI = SingletonAPIManager.getInstance(context).enviarAPI("propostas/" + proposta.getId().intValue(),
                    context, Request.Method.DELETE, null, new SingletonAPIManager.APIStringResposta() {
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

            SingletonAPIManager.getInstance(context).getRequestQueue(context).add(propostasAPI);
        }
    }

    private void adicionarPropostasLocal(ArrayList<Proposta> propostas) {
        for(Proposta proposta : propostas) {
            bdTable.insert(proposta);
        }
    }

    private boolean adicionarPropostaLocal(Proposta proposta) {
        Proposta propostaInserida = bdTable.insert(proposta);

        return propostaInserida != null && propostas.add(propostaInserida);
    }

    private boolean removerPropostaLocal(Proposta proposta) {
        return bdTable.delete(proposta.getId()) && propostas.remove(proposta);
    }

    private boolean editarPropostaLocal(Proposta proposta, int posicao) {
        if(bdTable.update(proposta)) {
            propostas.set(posicao, proposta);
        }

        return propostas.contains(proposta);
    }

    public Proposta pesquisarPropostaPorID(Long id) {
        for (Proposta proposta : propostas) {
            if (proposta.getId().toString().equals(id.toString()))
                return proposta;
        }

        return null;
    }

    public Proposta pesquisarPropostaPorPosicao(int i) {
        return propostas.get(i);
    }

    public Integer getPropostasCount(){
        return propostas.size();
    }

    public void setPropostasListener(PropostasListener propostasListener) {
        this.propostasListener = propostasListener;
    }

    public void setImagesListener(ImagesListener imagesListener) {
        this.imagesListener = imagesListener;
    }
}
