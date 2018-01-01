package pt.ipleiria.estg.tesp.psi.projsi.sistematrocas.projetosi_android_1718.singletons;

import android.content.Context;
import android.content.SharedPreferences;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import pt.ipleiria.estg.tesp.psi.projsi.sistematrocas.projetosi_android_1718.helpers.AnuncioBDTable;
import pt.ipleiria.estg.tesp.psi.projsi.sistematrocas.projetosi_android_1718.listeners.AnunciosListener;
import pt.ipleiria.estg.tesp.psi.projsi.sistematrocas.projetosi_android_1718.modelos.Anuncio;
import pt.ipleiria.estg.tesp.psi.projsi.sistematrocas.projetosi_android_1718.parsers.AnunciosParser;

/**
 * Created by leona on 21/11/2017.
 */

public class SingletonAnuncios {
    private static SingletonAnuncios INSTANCE = null;
    private ArrayList<Anuncio> anuncios;
    private AnuncioBDTable bdTable;
    private Context context;

    private AnunciosListener anunciosListener;

    public static synchronized SingletonAnuncios getInstance(Context context) {
        if (INSTANCE == null)
            INSTANCE = new SingletonAnuncios(context);

        return INSTANCE;
    }

    private SingletonAnuncios(Context context) {
        this.context = context;
        anuncios = new ArrayList<>();
        bdTable = new AnuncioBDTable(context);
        //anuncios = bdTable.select();
        getAnunciosAPI();
    }

    public ArrayList<Anuncio> getAnuncios() {
        return anuncios;
    }

    private void getAnunciosAPI() {

        if (anuncios.isEmpty())
        {
            if (SingletonAPIManager.getInstance(context).ligadoInternet())
            {
                JsonArrayRequest anunciosAPI = SingletonAPIManager.getInstance(context).pedirVariosAPI("anuncios", new SingletonAPIManager.APIJsonArrayResposta() {
                    @Override
                    public void Sucesso(JSONArray resultados) {
                        anuncios = AnunciosParser.paraObjeto(resultados, context);

                        adicionarAnunciosLocal(anuncios);

                        /*
                        if (anunciosListener != null)
                            anunciosListener.onRefreshAnuncios(anuncios);
                        */
                    }

                    @Override
                    public void Erro(VolleyError erro) {
                        if (anunciosListener != null)
                            anunciosListener.onErrorAnunciosAPI("Não foi possível sincronizar os anúncios com a API - " + erro.networkResponse.statusCode, erro);
                    }
                });

                SingletonAPIManager.getInstance(context).getRequestQueue().add(anunciosAPI);
            } else {
                if (anunciosListener != null)
                    anunciosListener.onRefreshAnuncios(anuncios);
            }
        }
    }

    public void getAnunciosSugeridos() {

        SharedPreferences preferences = context.getSharedPreferences("APP_SETTINGS", Context.MODE_PRIVATE);
        String username = preferences.getString("username", "");

        if (SingletonAPIManager.getInstance(context).ligadoInternet())
        {
            JsonArrayRequest anunciosAPI = SingletonAPIManager.getInstance(context).pedirVariosAPI("anuncios/sugeridos/"+username, new SingletonAPIManager.APIJsonArrayResposta() {
                @Override
                public void Sucesso(JSONArray resultados) {

                    ArrayList<Anuncio> anunciosAPI = AnunciosParser.paraObjeto(resultados, context);

                    adicionarAnunciosLocal(anunciosAPI);

                    if (anunciosListener != null)
                        anunciosListener.onRefreshAnuncios(anunciosAPI);
                }

                @Override
                public void Erro(VolleyError erro) {
                    if (anunciosListener != null)
                        anunciosListener.onErrorAnunciosAPI("Não foi possível sincronizar os anúncios com a API - " + erro.networkResponse.statusCode, erro);
                }
            });

            SingletonAPIManager.getInstance(context).getRequestQueue().add(anunciosAPI);
        }
    }

    public void adicionarAnuncio(Anuncio anuncio) {

        if (SingletonAPIManager.getInstance(context).ligadoInternet()) {

            final StringRequest adicionarAPI = SingletonAPIManager.getInstance(context).enviarAPI("anuncios",
                    Request.Method.POST, AnunciosParser.paraJson(anuncio), new SingletonAPIManager.APIStringResposta() {
                        @Override
                        public void Sucesso(String resposta) {
                            try {

                                Anuncio novoAnuncio = AnunciosParser.paraObjeto(new JSONObject(resposta), context);

                                if (adicionarAnuncioLocal(novoAnuncio)) {
                                    if (anunciosListener != null)
                                        anunciosListener.onSuccessAnunciosAPI(novoAnuncio);
                                }

                            } catch (JSONException e) {
                                if (anunciosListener != null)
                                    anunciosListener.onErrorAnunciosAPI("Ocorreu um erro no processamento do Anúncio.", e);
                            }
                        }

                        @Override
                        public void Erro(VolleyError erro) {
                            if (anunciosListener != null)
                                anunciosListener.onErrorAnunciosAPI("Ocorreu um erro no envio do Anúncio para a API.", erro);
                        }
                    });

            SingletonAPIManager.getInstance(context).getRequestQueue().add(adicionarAPI);
        }
    }

    public void alterarAnuncio(Anuncio anuncio) {

        if (SingletonAPIManager.getInstance(context).ligadoInternet()) {

            StringRequest alterarAPI = SingletonAPIManager.getInstance(context).enviarAPI("anuncios/" + anuncio.getId().intValue(),
                    Request.Method.PUT, AnunciosParser.paraJson(anuncio), new SingletonAPIManager.APIStringResposta() {
                        @Override
                        public void Sucesso(String resposta) {
                            try {

                                Anuncio anuncioAlterado = AnunciosParser.paraObjeto(new JSONObject(resposta), context);

                                if (editarAnuncioLocal(anuncioAlterado)) {
                                    if (anunciosListener != null)
                                        anunciosListener.onUpdateAnuncios(anuncioAlterado, 2);
                                }

                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }

                        @Override
                        public void Erro(VolleyError erro) {
                            Toast.makeText(context, "Não foi possível alterar o anúncio", Toast.LENGTH_SHORT).show();
                        }
                    });

            SingletonAPIManager.getInstance(context).getRequestQueue().add(alterarAPI);
        }
    }

    public void apagarAnuncio(final Anuncio anuncio) {

        if (SingletonAPIManager.getInstance(context).ligadoInternet()) {

            StringRequest alterarAPI = SingletonAPIManager.getInstance(context).enviarAPI("anuncios/" + anuncio.getId().intValue(),
                    Request.Method.DELETE, null, new SingletonAPIManager.APIStringResposta() {
                        @Override
                        public void Sucesso(String resposta) {

                            if (removerAnuncioLocal(anuncio)) {
                                if (anunciosListener != null)
                                    anunciosListener.onUpdateAnuncios(anuncio, 3);
                            }
                        }

                        @Override
                        public void Erro(VolleyError erro) {
                            Toast.makeText(context, "Não foi possível apagar o anúncio", Toast.LENGTH_SHORT).show();
                        }
                    });

            SingletonAPIManager.getInstance(context).getRequestQueue().add(alterarAPI);
        }
    }

    //------------------------------------------------------------
    //LOCAL A PARTIR DAQUI

    public void adicionarAnunciosLocal(ArrayList<Anuncio> anuncioList) {

        for(Anuncio anuncio : anuncioList)
        {
            bdTable.insert(anuncio);
        }
    }

    public boolean adicionarAnuncioLocal(Anuncio anuncio) {
        Anuncio anuncioInserido = bdTable.insert(anuncio);

        return anuncioInserido != null && anuncios.add(anuncioInserido);
    }

    public boolean removerAnuncioLocal(Anuncio anuncio) {
        return bdTable.delete(anuncio.getId()) && anuncios.remove(anuncio);
    }

    public boolean editarAnuncioLocal(Anuncio anuncio) {
        if(bdTable.update(anuncio)) {
            Anuncio novoAnuncio = anuncios.set(anuncio.getId().intValue(), anuncio);

            return anuncios.contains(novoAnuncio);
        } else {
            return false;
        }
    }

    public Anuncio pesquisarAnuncioID(Long id) {
        for (Anuncio anuncio : anuncios) {
            if (anuncio.getId() == id) {
                return anuncio;
            }
        }

        return null;
    }

    public Anuncio pesquisarAnuncioPosicao(Integer i) {
        return anuncios.get(i);
    }

    public Integer getAnunciosCount() {
        return anuncios.size();
    }

    public void setAnunciosListener(AnunciosListener anunciosListener) {
        this.anunciosListener = anunciosListener;
    }
}
