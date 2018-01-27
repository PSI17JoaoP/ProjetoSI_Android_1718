package pt.ipleiria.estg.tesp.psi.projsi.sistematrocas.projetosi_android_1718.singletons;

import android.content.Context;
import android.content.SharedPreferences;

import com.android.volley.Request;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.google.gson.FieldNamingPolicy;
import com.google.gson.GsonBuilder;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import pt.ipleiria.estg.tesp.psi.projsi.sistematrocas.projetosi_android_1718.helpers.AnuncioBDTable;
import pt.ipleiria.estg.tesp.psi.projsi.sistematrocas.projetosi_android_1718.listeners.AnunciosListener;
import pt.ipleiria.estg.tesp.psi.projsi.sistematrocas.projetosi_android_1718.listeners.ImagesListener;
import pt.ipleiria.estg.tesp.psi.projsi.sistematrocas.projetosi_android_1718.modelos.Anuncio;
import pt.ipleiria.estg.tesp.psi.projsi.sistematrocas.projetosi_android_1718.modelos.ImageManager;
import pt.ipleiria.estg.tesp.psi.projsi.sistematrocas.projetosi_android_1718.parsers.AnunciosParser;

/**
 * Created by leona on 21/11/2017.
 */

public class SingletonAnuncios {
    private static SingletonAnuncios INSTANCE = null;
    private ArrayList<Anuncio> anuncios;
    private AnuncioBDTable bdTable;

    private SharedPreferences preferences;

    private AnunciosListener anunciosListener;
    private ImagesListener imagesListener;

    public static synchronized SingletonAnuncios getInstance(Context context) {
        if (INSTANCE == null)
            INSTANCE = new SingletonAnuncios(context);

        return INSTANCE;
    }

    private SingletonAnuncios(Context context) {
        preferences = context.getSharedPreferences("APP_SETTINGS", Context.MODE_PRIVATE);
        anuncios = new ArrayList<>();
        bdTable = new AnuncioBDTable(context);
        anuncios = bdTable.select();
        getAnunciosAPI(context);
    }

    public ArrayList<Anuncio> getAnuncios() {
        return anuncios;
    }

    private void getAnunciosAPI(final Context context) {

        if(anuncios.isEmpty()) {

            if (SingletonAPIManager.getInstance(context).ligadoInternet(context)) {

                JsonArrayRequest anunciosAPI = SingletonAPIManager.getInstance(context).pedirVariosAPI("anuncios", context, new SingletonAPIManager.APIJsonArrayResposta() {
                    @Override
                    public void Sucesso(JSONArray resultados) {
                        anuncios = AnunciosParser.paraObjeto(resultados, context);

                        adicionarAnunciosLocal(anuncios);

                        if (anunciosListener != null)
                            anunciosListener.onRefreshAnuncios(anuncios);
                    }

                    @Override
                    public void Erro(VolleyError erro) {
                        if (anunciosListener != null) {
                            Integer code = 0;

                            if (erro.networkResponse != null)
                                code = erro.networkResponse.statusCode;

                            anunciosListener.onErrorAnunciosAPI("Não foi possível sincronizar os anúncios com a API - " + code, erro);
                        }
                    }
                });

                SingletonAPIManager.getInstance(context).getRequestQueue(context).add(anunciosAPI);
            }
        } else {
            if (anunciosListener != null)
                anunciosListener.onRefreshAnuncios(anuncios);
        }
    }

    public void getAnunciosUser(final Context context) {

        Long userId = preferences.getLong("id", 0);

        if (SingletonAPIManager.getInstance(context).ligadoInternet(context)) {

            JsonObjectRequest anunciosUserAPI = SingletonAPIManager.getInstance(context).pedirAPI("clientes/" + String.valueOf(userId) + "/anuncios", context, new SingletonAPIManager.APIJsonResposta() {
                @Override
                public void Sucesso(JSONObject resultado) {

                    try {
                        JSONArray anunciosAPI = resultado.getJSONArray("Anuncios");

                        if(anunciosAPI.length() > 0) {
                            ArrayList<Anuncio> anunciosUser = AnunciosParser.paraObjeto(anunciosAPI, context);

                            if (anunciosListener != null)
                                anunciosListener.onRefreshAnuncios(anunciosUser);
                        }

                    } catch (JSONException e) {
                        if (anunciosListener != null)
                            anunciosListener.onErrorAnunciosAPI("Ocorreu um erro no processamento dos seus anúncios.", e);
                    }
                }

                @Override
                public void Erro(VolleyError erro) {
                    if (anunciosListener != null)
                        anunciosListener.onErrorAnunciosAPI("Não foi possível pedir os seus anúncios à API.", erro);
                }
            });

            SingletonAPIManager.getInstance(context).getRequestQueue(context).add(anunciosUserAPI);
        } else {

            ArrayList<Anuncio> anunciosUser = bdTable.select(" WHERE " + AnuncioBDTable.ID_USER_ANUNCIO + " = ? AND " + AnuncioBDTable.ESTADO_ANUNCIO + " = ?", new String[]{String.valueOf(userId), "ATIVO"});

            if (anunciosListener != null)
                anunciosListener.onRefreshAnuncios(anunciosUser);
        }
    }

    public void getAnunciosSugeridos(final Context context) {

        String username = preferences.getString("username", "");

        if (SingletonAPIManager.getInstance(context).ligadoInternet(context)) {

            JsonArrayRequest anunciosAPI = SingletonAPIManager.getInstance(context).pedirVariosAPI("anuncios/sugeridos/"+username, context, new SingletonAPIManager.APIJsonArrayResposta() {
                @Override
                public void Sucesso(JSONArray resultados) {

                    ArrayList<Anuncio> anunciosAPI = AnunciosParser.paraObjeto(resultados, context);

                    if (anunciosListener != null)
                        anunciosListener.onRefreshAnuncios(anunciosAPI);
                }

                @Override
                public void Erro(VolleyError erro) {
                    if (anunciosListener != null)
                        anunciosListener.onErrorAnunciosAPI("Não foi possível pedir os anúncios sugeridos à API.", erro);
                }
            });

            SingletonAPIManager.getInstance(context).getRequestQueue(context).add(anunciosAPI);
        }
    }

    public void getImagensAnuncio(final Long id, final Context context) {

        if(SingletonAPIManager.getInstance(context).ligadoInternet(context)) {

            JsonObjectRequest anunciosUserAPI = SingletonAPIManager.getInstance(context).pedirAPI("anuncios/" + String.valueOf(id) + "/imagens", context, new SingletonAPIManager.APIJsonResposta() {
                @Override
                public void Sucesso(JSONObject resultado) {

                    try {

                        JSONArray imagensJSON = resultado.getJSONArray("Imagens");

                        if(imagensJSON.length() > 0) {

                            ArrayList<byte[]> imagens = new ArrayList<>();

                            for (int contador = 0; contador < imagensJSON.length(); contador++)
                            {
                                String imagemBase64 = (String) imagensJSON.get(contador);
                                byte[] imagemBytes = ImageManager.fromBase64(imagemBase64);

                                imagens.add(imagemBytes);
                            }

                            if (!imagens.isEmpty()) {
                                if (imagesListener != null)
                                    imagesListener.OnSucessoImagensAPI(imagens);
                            }
                        }

                    } catch (JSONException e) {
                        if (imagesListener != null)
                            imagesListener.OnErrorImagensAPI("Ocorreu um erro no processamento das imagens.", e);
                    }
                }

                @Override
                public void Erro(VolleyError erro) {
                    if (imagesListener != null)
                        imagesListener.OnErrorImagensAPI("Não foi possível pedir as imagens do(s) anúncio(s) à API.", erro);
                }
            });

            SingletonAPIManager.getInstance(context).getRequestQueue(context).add(anunciosUserAPI);
        }
    }

    public void adicionarAnuncio(Anuncio anuncio, final Context context) {

        if (SingletonAPIManager.getInstance(context).ligadoInternet(context)) {

            final StringRequest adicionarAPI = SingletonAPIManager.getInstance(context).enviarAPI("anuncios", context,
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

            SingletonAPIManager.getInstance(context).getRequestQueue(context).add(adicionarAPI);
        }
    }

    public void enviarImagensAnuncio(final Long id, final ArrayList<String> imagensBase64, final Context context) {

        GsonBuilder gson = new GsonBuilder().setFieldNamingPolicy(FieldNamingPolicy.IDENTITY);

        JSONObject imagens = new JSONObject();

        try {

            imagens.put("Imagens", new JSONArray(imagensBase64));

            String imagensString = gson.create().toJson(imagens);

            if(SingletonAPIManager.getInstance(context).ligadoInternet(context)) {

                StringRequest enviarImagensAnuncio = SingletonAPIManager.getInstance(context).enviarAPI("anuncios/" + String.valueOf(id) + "/imagens", context,
                        Request.Method.POST, imagensString, new SingletonAPIManager.APIStringResposta() {
                            @Override
                            public void Sucesso(String resposta) {

                                try {
                                    ArrayList<byte[]> imagens = new ArrayList<>();

                                    JSONArray imagensJSON = new JSONArray(resposta);

                                    for (int contador = 0; contador < imagensJSON.length(); contador++) {
                                        JSONObject imagemJSON = imagensJSON.getJSONObject(contador);
                                        String imagemBase64 = imagemJSON.getString(id + "_" + contador + ".png");
                                        byte[] imagemBytes = ImageManager.fromBase64(imagemBase64);
                                        imagens.add(imagemBytes);
                                    }

                                    if (!imagens.isEmpty()) {
                                        if(imagesListener != null)
                                            imagesListener.OnSucessoImagensAPI(imagens);
                                    }

                                } catch (JSONException e) {
                                    if(imagesListener != null)
                                        imagesListener.OnErrorImagensAPI("Ocorreu um erro no processamento das imagens devolvidas pela API.", e);
                                }
                            }

                            @Override
                            public void Erro(VolleyError erro) {
                                if(imagesListener != null)
                                    imagesListener.OnErrorImagensAPI("Não foi possivél enviar as imagens do anúncio para a API.\nAPI: " + erro.getMessage(), erro);
                            }
                        });

                SingletonAPIManager.getInstance(context).getRequestQueue(context).add(enviarImagensAnuncio);
            }

        } catch (JSONException e) {
            if(imagesListener != null)
                imagesListener.OnErrorImagensAPI("Ocorreu um erro no processamento das imagens a enviar para a API.", e);
        }
    }

    public void alterarAnuncio(final Anuncio anuncio, final Context context) {

        if (SingletonAPIManager.getInstance(context).ligadoInternet(context)) {

            JSONObject object = new JSONObject();

            try {
                object.put("estado", anuncio.getEstado());

                StringRequest alterarAPI = SingletonAPIManager.getInstance(context).enviarAPI("anuncios/" + anuncio.getId().intValue(), context,
                        Request.Method.PUT, object.toString(), new SingletonAPIManager.APIStringResposta() {
                            @Override
                            public void Sucesso(String resposta) {
                                try {
                                    Anuncio anuncioAlterado = AnunciosParser.paraObjeto(new JSONObject(resposta), context);
                                    Integer posicao = anuncios.indexOf(anuncio);

                                    if (editarAnuncioLocal(anuncioAlterado, posicao)) {
                                        if (anunciosListener != null)
                                            anunciosListener.onSuccessAnunciosAPI(anuncioAlterado);
                                    }

                                } catch (JSONException e) {
                                    if (anunciosListener != null)
                                        anunciosListener.onErrorAnunciosAPI("Ocorreu um erro no processamento do anúncio alterado.", e);
                                }
                            }

                            @Override
                            public void Erro(VolleyError erro) {
                                if (anunciosListener != null)
                                    anunciosListener.onErrorAnunciosAPI("Não foi possível alterar o anúncio.", erro);
                            }
                        });

                SingletonAPIManager.getInstance(context).getRequestQueue(context).add(alterarAPI);

            } catch (JSONException e) {
                if (anunciosListener != null)
                    anunciosListener.onErrorAnunciosAPI("Ocorreu um erro no processamento do anúncio alterado.", e);
            }
        }
    }

    public void apagarAnuncio(final Anuncio anuncio, Context context) {

        if (SingletonAPIManager.getInstance(context).ligadoInternet(context)) {

            StringRequest apagarAPI = SingletonAPIManager.getInstance(context).enviarAPI("anuncios/" + anuncio.getId().intValue(), context,
                    Request.Method.DELETE, null, new SingletonAPIManager.APIStringResposta() {
                        @Override
                        public void Sucesso(String resposta) {

                            if (removerAnuncioLocal(anuncio)) {
                                if (anunciosListener != null)
                                    anunciosListener.onSuccessAnunciosAPI(anuncio);
                            }
                        }

                        @Override
                        public void Erro(VolleyError erro) {
                            if (anunciosListener != null)
                                anunciosListener.onErrorAnunciosAPI("Não foi possível apagar o anúncio.", erro);
                        }
                    });

            SingletonAPIManager.getInstance(context).getRequestQueue(context).add(apagarAPI);
        }
    }

    private void adicionarAnunciosLocal(ArrayList<Anuncio> anuncioList) {
        for(Anuncio anuncio : anuncioList) {
            bdTable.insert(anuncio);
        }
    }

    private boolean adicionarAnuncioLocal(Anuncio anuncio) {
        Anuncio anuncioInserido = bdTable.insert(anuncio);

        return anuncioInserido != null && anuncios.add(anuncioInserido);
    }

    private boolean removerAnuncioLocal(Anuncio anuncio) {
        return bdTable.delete(anuncio.getId()) && anuncios.remove(anuncio);
    }

    private boolean editarAnuncioLocal(Anuncio anuncio, int posicao) {
        if(bdTable.update(anuncio)) {
            anuncios.set(posicao, anuncio);
        }

        return anuncios.contains(anuncio);
    }

    public Anuncio pesquisarAnuncioPorID(Long id) {
        for (Anuncio anuncio : anuncios) {
            if (anuncio.getId().toString().equals(id.toString())) {
                return anuncio;
            }
        }

        return null;
    }

    public Anuncio pesquisarAnuncioPorPosicao(Integer i) {
        return anuncios.get(i);
    }

    public Integer getAnunciosCount() {
        return anuncios.size();
    }

    public void setAnunciosListener(AnunciosListener anunciosListener) {
        this.anunciosListener = anunciosListener;
    }

    public void setImagesListener(ImagesListener imagesListener) {
        this.imagesListener = imagesListener;
    }
}
