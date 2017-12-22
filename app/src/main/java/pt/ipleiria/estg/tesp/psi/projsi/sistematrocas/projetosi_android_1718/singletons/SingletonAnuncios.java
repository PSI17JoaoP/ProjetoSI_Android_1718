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
    }

    public void getAnuncios() {
        if (anuncios.size() == 0)
        {
            if (SingletonAPIManager.getInstance(context).ligadoInternet())
            {
                JsonArrayRequest anunciosAPI = SingletonAPIManager.getInstance(context).pedirVariosAPI("anuncios", new SingletonAPIManager.APIJsonArrayResposta() {
                    @Override
                    public void Sucesso(JSONArray resultados) {
                        anuncios = AnunciosParser.paraObjeto(resultados, context);

                        adicionarAnunciosLocal(anuncios);

                        if (anunciosListener != null)
                            anunciosListener.onRefreshAnuncios(anuncios);
                    }

                    @Override
                    public void Erro(VolleyError erro) {
                        Toast.makeText(context, "Não foi possível sincronizar os anúncios com a API - " + erro.networkResponse.statusCode, Toast.LENGTH_SHORT).show();
                    }
                });

                SingletonAPIManager.getInstance(context).getRequestQueue().add(anunciosAPI);
            } else {
                anuncios = bdTable.select();

                if (anunciosListener != null)
                    anunciosListener.onRefreshAnuncios(anuncios);
            }
        }
    }

    public void getAnunciosSugeridos(){
        SharedPreferences preferences = context.getSharedPreferences("APP_SETTINGS", Context.MODE_PRIVATE);
        String username = preferences.getString("username", "");

        if (SingletonAPIManager.getInstance(context).ligadoInternet())
        {
            JsonArrayRequest anunciosAPI = SingletonAPIManager.getInstance(context).pedirVariosAPI("anuncios/sugeridos/"+username, new SingletonAPIManager.APIJsonArrayResposta() {
                @Override
                public void Sucesso(JSONArray resultados) {
                    anuncios = AnunciosParser.paraObjeto(resultados, context);

                    adicionarAnunciosLocal(anuncios);

                    if (anunciosListener != null)
                        anunciosListener.onRefreshAnuncios(anuncios);
                }

                @Override
                public void Erro(VolleyError erro) {
                    Toast.makeText(context, "Não foi possível sincronizar os anúncios com a API - " + erro.networkResponse.statusCode, Toast.LENGTH_SHORT).show();
                }
            });

            SingletonAPIManager.getInstance(context).getRequestQueue().add(anunciosAPI);
        }
    }

    public void adicionarAnuncio(Anuncio anuncio)
    {
        StringRequest adicionarAPI = SingletonAPIManager.getInstance(context).enviarAPI("anuncios/",
                Request.Method.POST, AnunciosParser.paraJson(anuncio), new SingletonAPIManager.APIStringResposta() {
                    @Override
                    public void Sucesso(String resposta) {
                        try {
                            Anuncio novoAnuncio = AnunciosParser.paraObjeto(new JSONObject(resposta), context);

                            if (anunciosListener != null)
                                anunciosListener.onUpdateAnuncios(novoAnuncio, 1);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void Erro(VolleyError erro) {
                        Toast.makeText(context, "Não foi possível adicionar o anúncio", Toast.LENGTH_SHORT).show();
                    }
                });

        SingletonAPIManager.getInstance(context).getRequestQueue().add(adicionarAPI);
    }

    public void alterarAnuncio(Anuncio anuncio)
    {
        StringRequest alterarAPI = SingletonAPIManager.getInstance(context).enviarAPI("anuncios/" + anuncio.getId().intValue(),
                Request.Method.PUT, AnunciosParser.paraJson(anuncio), new SingletonAPIManager.APIStringResposta() {
                    @Override
                    public void Sucesso(String resposta) {
                        try {
                            Anuncio altAnuncio = AnunciosParser.paraObjeto(new JSONObject(resposta), context);

                            if (anunciosListener != null)
                                anunciosListener.onUpdateAnuncios(altAnuncio, 2);
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

    public void apagarAnuncio(final Anuncio anuncio)
    {
        StringRequest alterarAPI = SingletonAPIManager.getInstance(context).enviarAPI("anuncios/" + anuncio.getId().intValue(),
                Request.Method.DELETE, null, new SingletonAPIManager.APIStringResposta() {
                    @Override
                    public void Sucesso(String resposta) {
                        if (anunciosListener != null)
                            anunciosListener.onUpdateAnuncios(anuncio, 3);
                    }

                    @Override
                    public void Erro(VolleyError erro) {
                        Toast.makeText(context, "Não foi possível apagar o anúncio", Toast.LENGTH_SHORT).show();
                    }
                });

        SingletonAPIManager.getInstance(context).getRequestQueue().add(alterarAPI);
    }

    //------------------------------------------------------------
    //LOCAL A PARTIR DAQUI

    public Integer getAnunciosCount()
    {
        return anuncios.size();
    }

    public void adicionarAnunciosLocal(ArrayList<Anuncio> anuncioList)
    {
        for(Anuncio anuncio:anuncioList)
        {
            bdTable.insert(anuncio);
        }
    }

    public boolean adicionarAnuncioLocal(Anuncio anuncio)
    {
        Anuncio anuncioInserido = bdTable.insert(anuncio);

        return anuncioInserido != null && anuncios.add(anuncioInserido);
    }

    public boolean removerAnuncioLocal(Anuncio anuncio)
    {
        return bdTable.delete(anuncio.getId()) && anuncios.remove(anuncio);
    }

    public boolean editarAnuncioLocal(Anuncio anuncio)
    {
        if(bdTable.update(anuncio)) {
            Anuncio novoAnuncio = anuncios.set(anuncio.getId().intValue(), anuncio);

            return anuncios.contains(novoAnuncio);
        } else {
            return false;
        }
    }

    public Anuncio pesquisarAnuncioID(Long id)
    {
        for (Anuncio anuncio:anuncios) {
            if (anuncio.getId() == id)
                return anuncio;
        }

        return null;
    }

    public Anuncio pesquisarAnuncioPosicao(Integer i)
    {
        return anuncios.get(i);
    }

    public void setAnunciosListener(AnunciosListener anunciosListener) {
        this.anunciosListener = anunciosListener;
    }
}
