package pt.ipleiria.estg.tesp.psi.projsi.sistematrocas.projetosi_android_1718.singletons;

import android.content.Context;
import android.content.SharedPreferences;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import pt.ipleiria.estg.tesp.psi.projsi.sistematrocas.projetosi_android_1718.helpers.AnuncioBDTable;
import pt.ipleiria.estg.tesp.psi.projsi.sistematrocas.projetosi_android_1718.listeners.AnunciosListener;
import pt.ipleiria.estg.tesp.psi.projsi.sistematrocas.projetosi_android_1718.listeners.CategoriasListener;
import pt.ipleiria.estg.tesp.psi.projsi.sistematrocas.projetosi_android_1718.modelos.Anuncio;
import pt.ipleiria.estg.tesp.psi.projsi.sistematrocas.projetosi_android_1718.modelos.Brinquedo;
import pt.ipleiria.estg.tesp.psi.projsi.sistematrocas.projetosi_android_1718.modelos.Categoria;
import pt.ipleiria.estg.tesp.psi.projsi.sistematrocas.projetosi_android_1718.modelos.Computador;
import pt.ipleiria.estg.tesp.psi.projsi.sistematrocas.projetosi_android_1718.modelos.Eletronica;
import pt.ipleiria.estg.tesp.psi.projsi.sistematrocas.projetosi_android_1718.modelos.Jogo;
import pt.ipleiria.estg.tesp.psi.projsi.sistematrocas.projetosi_android_1718.modelos.Livro;
import pt.ipleiria.estg.tesp.psi.projsi.sistematrocas.projetosi_android_1718.modelos.Roupa;
import pt.ipleiria.estg.tesp.psi.projsi.sistematrocas.projetosi_android_1718.modelos.Smartphone;
import pt.ipleiria.estg.tesp.psi.projsi.sistematrocas.projetosi_android_1718.parsers.AnunciosParser;
import pt.ipleiria.estg.tesp.psi.projsi.sistematrocas.projetosi_android_1718.parsers.BrinquedosParser;
import pt.ipleiria.estg.tesp.psi.projsi.sistematrocas.projetosi_android_1718.parsers.CategoriasParser;
import pt.ipleiria.estg.tesp.psi.projsi.sistematrocas.projetosi_android_1718.parsers.ComputadoresParser;
import pt.ipleiria.estg.tesp.psi.projsi.sistematrocas.projetosi_android_1718.parsers.EletronicaParser;
import pt.ipleiria.estg.tesp.psi.projsi.sistematrocas.projetosi_android_1718.parsers.JogosParser;
import pt.ipleiria.estg.tesp.psi.projsi.sistematrocas.projetosi_android_1718.parsers.LivrosParser;
import pt.ipleiria.estg.tesp.psi.projsi.sistematrocas.projetosi_android_1718.parsers.RoupasParser;
import pt.ipleiria.estg.tesp.psi.projsi.sistematrocas.projetosi_android_1718.parsers.SmartphonesParser;

/**
 * Created by leona on 21/11/2017.
 */

public class SingletonAnuncios {
    private static SingletonAnuncios INSTANCE = null;
    private ArrayList<Anuncio> anuncios;
    private AnuncioBDTable bdTable;
    private Context context;

    private AnunciosListener anunciosListener;
    private CategoriasListener categoriasListener;

    public static synchronized SingletonAnuncios getInstance(Context context) {
        if (INSTANCE == null)
            INSTANCE = new SingletonAnuncios(context);

        return INSTANCE;
    }

    private SingletonAnuncios(Context context) {
        this.context = context;
        anuncios = new ArrayList<>();
        bdTable = new AnuncioBDTable(context);
        carregar();
        /*
        anuncios = bdTable.select();
        getAnunciosAPI();
        */
    }

    private void carregar()
    {
        if (SingletonAPIManager.getInstance(context).ligadoInternet())
        {
            getAnunciosAPI();
        }else
        {
            anuncios = bdTable.select();
        }
    }

    public ArrayList<Anuncio> getAnuncios() {
        return anuncios;
    }

    private void getAnunciosAPI()
    {
        JsonArrayRequest anunciosAPI = SingletonAPIManager.getInstance(context).pedirVariosAPI("anuncios", new SingletonAPIManager.APIJsonArrayResposta() {
            @Override
            public void Sucesso(JSONArray resultados) {
                anuncios = AnunciosParser.paraObjeto(resultados, context);

                adicionarAnunciosLocal(anuncios);

                /*if (anunciosListener != null)
                    anunciosListener.onRefreshAnuncios(anuncios);*/
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

        SingletonAPIManager.getInstance(context).getRequestQueue().add(anunciosAPI);
    }

    public void getCategoriasAnuncio(Long id, final String tipo)
    {
        JsonObjectRequest pedido = SingletonAPIManager.getInstance(context).pedirAPI("anuncios/" + id + "/categoria"+tipo, new SingletonAPIManager.APIJsonResposta() {
            @Override
            public void Sucesso(JSONObject resultado)
            {
                try {
                    JSONObject categorias = resultado.getJSONObject("Categorias");

                    String tipoCategoria = resultado.getString("Flag");

                    JSONObject base = categorias.getJSONObject("Base");
                    JSONArray filha = categorias.getJSONArray("Filhas");

                    Categoria categoriaBase = CategoriasParser.paraObjeto(base, context);

                    JSONObject cat = new JSONObject();

                    switch (tipoCategoria)
                    {
                        case "Brinquedos":
                            cat.put("id", base.get("id"));
                            cat.put("nome", base.get("nome"));
                            cat.put("editora", filha.getJSONObject(0).get("editora"));
                            cat.put("faixa_etaria", filha.getJSONObject(0).get("faixa_etaria"));
                            cat.put("descricao", filha.getJSONObject(0).get("descricao"));

                            Brinquedo categoriaB = BrinquedosParser.paraObjeto(cat, context);
                            categoriasListener.onObterCategoria(categoriaB, tipoCategoria, tipo);
                            break;
                        case "Jogos":
                            cat.put("id", base.get("id"));
                            cat.put("nome", base.get("nome"));
                            cat.put("editora", filha.getJSONObject(0).get("editora"));
                            cat.put("faixa_etaria", filha.getJSONObject(0).get("faixa_etaria"));
                            cat.put("descricao", filha.getJSONObject(0).get("descricao"));
                            cat.put("id_genero", filha.getJSONObject(1).get("id_genero"));
                            cat.put("produtora", filha.getJSONObject(1).get("produtora"));

                            Jogo categoriaJ = JogosParser.paraObjeto(cat, context);
                            categoriasListener.onObterCategoria(categoriaJ, tipoCategoria, tipo);
                            break;
                        case "Eletrónica":
                            cat.put("id", base.get("id"));
                            cat.put("nome", base.get("nome"));
                            cat.put("descricao", filha.getJSONObject(0).get("descricao"));
                            cat.put("marca", filha.getJSONObject(0).get("marca"));

                            Eletronica categoriaE = EletronicaParser.paraObjeto(cat, context);
                            categoriasListener.onObterCategoria(categoriaE, tipoCategoria, tipo);
                            break;
                        case "Computadores":
                            cat.put("id", base.get("id"));
                            cat.put("nome", base.get("nome"));
                            cat.put("descricao", filha.getJSONObject(0).get("descricao"));
                            cat.put("marca", filha.getJSONObject(0).get("marca"));
                            cat.put("processador", filha.getJSONObject(1).get("processador"));
                            cat.put("ram", filha.getJSONObject(1).get("ram"));
                            cat.put("hdd", filha.getJSONObject(1).get("hdd"));
                            cat.put("gpu", filha.getJSONObject(1).get("gpu"));
                            cat.put("os", filha.getJSONObject(1).get("os"));
                            cat.put("portatil", filha.getJSONObject(1).get("portatil"));

                            Computador categoriaC = ComputadoresParser.paraObjeto(cat, context);
                            categoriasListener.onObterCategoria(categoriaC, tipoCategoria, tipo);
                            break;
                        case "Smartphones":
                            cat.put("id", base.get("id"));
                            cat.put("nome", base.get("nome"));
                            cat.put("descricao", filha.getJSONObject(0).get("descricao"));
                            cat.put("marca", filha.getJSONObject(0).get("marca"));
                            cat.put("processador", filha.getJSONObject(1).get("processador"));
                            cat.put("ram", filha.getJSONObject(1).get("ram"));
                            cat.put("hdd", filha.getJSONObject(1).get("hdd"));
                            cat.put("os", filha.getJSONObject(1).get("os"));
                            cat.put("tamanho", filha.getJSONObject(1).get("tamanho"));

                            Smartphone categoriaS = SmartphonesParser.paraObjeto(cat, context);
                            categoriasListener.onObterCategoria(categoriaS, tipoCategoria, tipo);
                            break;
                        case "Livros":
                            cat.put("id", base.get("id"));
                            cat.put("nome", base.get("nome"));
                            cat.put("titulo", filha.getJSONObject(0).get("titulo"));
                            cat.put("editora", filha.getJSONObject(0).get("editora"));
                            cat.put("autor", filha.getJSONObject(0).get("autor"));
                            cat.put("isbn", filha.getJSONObject(0).get("isbn"));

                            Livro categoriaL = LivrosParser.paraObjeto(cat, context);
                            categoriasListener.onObterCategoria(categoriaL, tipoCategoria, tipo);
                            break;
                        case "Roupa":
                            cat.put("id", base.get("id"));
                            cat.put("nome", base.get("nome"));
                            cat.put("marca", filha.getJSONObject(0).get("marca"));
                            cat.put("tamanho", filha.getJSONObject(0).get("tamanho"));
                            cat.put("id_tipo", filha.getJSONObject(0).get("id_tipo"));

                            Roupa categoriaR = RoupasParser.paraObjeto(cat, context);
                            categoriasListener.onObterCategoria(categoriaR, tipoCategoria, tipo);
                            break;
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void Erro(VolleyError erro) {
                if (anunciosListener != null)
                {
                    Integer code = 0;
                    if (erro.networkResponse != null)
                            code = erro.networkResponse.statusCode;
                    anunciosListener.onErrorAnunciosAPI("Não foi possível obter os dados do bem do anúncio - " + code, erro);
                }
            }
        });

        SingletonAPIManager.getInstance(context).getRequestQueue().add(pedido);
    }

    public void getAnunciosUser() {

        SharedPreferences preferences = context.getSharedPreferences("APP_SETTINGS", Context.MODE_PRIVATE);
        Long userId = preferences.getLong("id", 0);

        if (SingletonAPIManager.getInstance(context).ligadoInternet()) {

            JsonObjectRequest anunciosUserAPI = SingletonAPIManager.getInstance(context).pedirAPI("clientes/" + String.valueOf(userId) + "/anuncios", new SingletonAPIManager.APIJsonResposta() {
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

            SingletonAPIManager.getInstance(context).getRequestQueue().add(anunciosUserAPI);
        } else {

            ArrayList<Anuncio> anunciosUser = bdTable.select(" WHERE " + AnuncioBDTable.ID_USER_ANUNCIO + " = ? AND " + AnuncioBDTable.ESTADO_ANUNCIO + " = ?", new String[]{String.valueOf(userId), "ABERTO"});

            if (anunciosListener != null)
                anunciosListener.onRefreshAnuncios(anunciosUser);
        }
    }

    public void getAnunciosSugeridos() {

        SharedPreferences preferences = context.getSharedPreferences("APP_SETTINGS", Context.MODE_PRIVATE);
        String username = preferences.getString("username", "");

        if (SingletonAPIManager.getInstance(context).ligadoInternet()) {

            JsonArrayRequest anunciosAPI = SingletonAPIManager.getInstance(context).pedirVariosAPI("anuncios/sugeridos/"+username, new SingletonAPIManager.APIJsonArrayResposta() {
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

    public void alterarAnuncio(final Anuncio anuncio) {

        if (SingletonAPIManager.getInstance(context).ligadoInternet()) {
            JSONObject object = new JSONObject();
            try {
                object.put("estado", anuncio.getEstado());

                StringRequest alterarAPI = SingletonAPIManager.getInstance(context).enviarAPI("anuncios/" + anuncio.getId().intValue(),
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

                SingletonAPIManager.getInstance(context).getRequestQueue().add(alterarAPI);

            } catch (JSONException e) {
                if (anunciosListener != null)
                    anunciosListener.onErrorAnunciosAPI("Ocorreu um erro no processamento do anúncio alterado.", e);
            }
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
                                    anunciosListener.onSuccessAnunciosAPI(anuncio);
                            }
                        }

                        @Override
                        public void Erro(VolleyError erro) {
                            if (anunciosListener != null)
                                anunciosListener.onErrorAnunciosAPI("Não foi possível apagar o anúncio.", erro);
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

    public boolean editarAnuncioLocal(Anuncio anuncio, int posicao) {
        if(bdTable.update(anuncio)) {
            anuncios.set(posicao, anuncio);
        }

        return anuncios.contains(anuncio);
    }

    public Anuncio pesquisarAnuncioID(Long id)
    {
        for (Anuncio anuncio : anuncios)
        {
            if (anuncio.getId().toString().equals(id.toString()))
            {
                anunciosListener.onSuccessAnunciosAPI(anuncio);
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

    public void setCategoriasListener(CategoriasListener categoriasListener)
    {
        this.categoriasListener = categoriasListener;
    }
}
