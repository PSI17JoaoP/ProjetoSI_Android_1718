package pt.ipleiria.estg.tesp.psi.projsi.sistematrocas.projetosi_android_1718.singletons;

import android.content.Context;

import com.android.volley.Request;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import pt.ipleiria.estg.tesp.psi.projsi.sistematrocas.projetosi_android_1718.helpers.BrinquedoBDTable;
import pt.ipleiria.estg.tesp.psi.projsi.sistematrocas.projetosi_android_1718.helpers.ComputadorBDTable;
import pt.ipleiria.estg.tesp.psi.projsi.sistematrocas.projetosi_android_1718.helpers.EletronicaBDTable;
import pt.ipleiria.estg.tesp.psi.projsi.sistematrocas.projetosi_android_1718.helpers.JogoBDTable;
import pt.ipleiria.estg.tesp.psi.projsi.sistematrocas.projetosi_android_1718.helpers.LivroBDTable;
import pt.ipleiria.estg.tesp.psi.projsi.sistematrocas.projetosi_android_1718.helpers.RoupaBDTable;
import pt.ipleiria.estg.tesp.psi.projsi.sistematrocas.projetosi_android_1718.helpers.SmartphoneBDTable;
import pt.ipleiria.estg.tesp.psi.projsi.sistematrocas.projetosi_android_1718.listeners.CategoriasListener;
import pt.ipleiria.estg.tesp.psi.projsi.sistematrocas.projetosi_android_1718.modelos.Brinquedo;
import pt.ipleiria.estg.tesp.psi.projsi.sistematrocas.projetosi_android_1718.modelos.Categoria;
import pt.ipleiria.estg.tesp.psi.projsi.sistematrocas.projetosi_android_1718.modelos.Computador;
import pt.ipleiria.estg.tesp.psi.projsi.sistematrocas.projetosi_android_1718.modelos.Eletronica;
import pt.ipleiria.estg.tesp.psi.projsi.sistematrocas.projetosi_android_1718.modelos.Jogo;
import pt.ipleiria.estg.tesp.psi.projsi.sistematrocas.projetosi_android_1718.modelos.Livro;
import pt.ipleiria.estg.tesp.psi.projsi.sistematrocas.projetosi_android_1718.modelos.Roupa;
import pt.ipleiria.estg.tesp.psi.projsi.sistematrocas.projetosi_android_1718.modelos.Smartphone;
import pt.ipleiria.estg.tesp.psi.projsi.sistematrocas.projetosi_android_1718.parsers.BrinquedosParser;
import pt.ipleiria.estg.tesp.psi.projsi.sistematrocas.projetosi_android_1718.parsers.CategoriasParser;
import pt.ipleiria.estg.tesp.psi.projsi.sistematrocas.projetosi_android_1718.parsers.ComputadoresParser;
import pt.ipleiria.estg.tesp.psi.projsi.sistematrocas.projetosi_android_1718.parsers.EletronicaParser;
import pt.ipleiria.estg.tesp.psi.projsi.sistematrocas.projetosi_android_1718.parsers.JogosParser;
import pt.ipleiria.estg.tesp.psi.projsi.sistematrocas.projetosi_android_1718.parsers.LivrosParser;
import pt.ipleiria.estg.tesp.psi.projsi.sistematrocas.projetosi_android_1718.parsers.RoupasParser;
import pt.ipleiria.estg.tesp.psi.projsi.sistematrocas.projetosi_android_1718.parsers.SmartphonesParser;

/**
 * Created by leona on 23/11/2017.
 */

public class SingletonCategorias {
    private static SingletonCategorias INSTANCE = null;
    private ArrayList<Categoria> categorias;

    private CategoriasListener categoriasListener;

    public static SingletonCategorias getInstance() {
        if (INSTANCE == null)
            INSTANCE = new SingletonCategorias();

        return INSTANCE;
    }

    private SingletonCategorias() {
        categorias = new ArrayList<>();
    }

    public ArrayList<Categoria> getCategorias() {
        return categorias;
    }

    public void getCategoriasAnuncio(Long id, final String tipo, final Context context) {

        if (SingletonAPIManager.getInstance(context).ligadoInternet(context)) {

            JsonObjectRequest pedido = SingletonAPIManager.getInstance(context).pedirAPI("anuncios/" + id + "/categoria" + tipo, context, new SingletonAPIManager.APIJsonResposta() {
                @Override
                public void Sucesso(JSONObject resultado) {
                    try {
                        JSONObject categorias = resultado.getJSONObject("Categorias");

                        String tipoCategoria = resultado.getString("Flag");

                        JSONObject base = categorias.getJSONObject("Base");
                        JSONArray filha = categorias.getJSONArray("Filhas");

                        JSONObject cat = new JSONObject();

                        switch (tipoCategoria) {
                            case "Brinquedos":

                                cat.put("id", base.get("id"));
                                cat.put("nome", base.get("nome"));
                                cat.put("editora", filha.getJSONObject(0).get("editora"));
                                cat.put("faixa_etaria", filha.getJSONObject(0).get("faixa_etaria"));
                                cat.put("descricao", filha.getJSONObject(0).get("descricao"));

                                Brinquedo categoriaB = BrinquedosParser.paraObjeto(cat, context);

                                if(categoriasListener != null)
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

                                if(categoriasListener != null)
                                    categoriasListener.onObterCategoria(categoriaJ, tipoCategoria, tipo);

                                break;

                            case "Eletrónica":

                                cat.put("id", base.get("id"));
                                cat.put("nome", base.get("nome"));
                                cat.put("descricao", filha.getJSONObject(0).get("descricao"));
                                cat.put("marca", filha.getJSONObject(0).get("marca"));

                                Eletronica categoriaE = EletronicaParser.paraObjeto(cat, context);

                                if(categoriasListener != null)
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

                                if(categoriasListener != null)
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

                                if(categoriasListener != null)
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

                                if(categoriasListener != null)
                                    categoriasListener.onObterCategoria(categoriaL, tipoCategoria, tipo);

                                break;

                            case "Roupa":
                                cat.put("id", base.get("id"));
                                cat.put("nome", base.get("nome"));
                                cat.put("marca", filha.getJSONObject(0).get("marca"));
                                cat.put("tamanho", filha.getJSONObject(0).get("tamanho"));
                                cat.put("id_tipo", filha.getJSONObject(0).get("id_tipo"));

                                Roupa categoriaR = RoupasParser.paraObjeto(cat, context);

                                if(categoriasListener != null)
                                    categoriasListener.onObterCategoria(categoriaR, tipoCategoria, tipo);

                                break;

                        }

                    } catch (JSONException e) {
                        if(categoriasListener != null)
                            categoriasListener.onErroObterCategoria("Ocorreu um erro no processamento da categoria.", e);
                    }
                }

                @Override
                public void Erro(VolleyError erro) {
                    if(categoriasListener != null)
                        categoriasListener.onErroObterCategoria("Não foi possivél obter a categoria.", erro);
                }
            });

            SingletonAPIManager.getInstance(context).getRequestQueue(context).add(pedido);
        }
    }

    public void getCategoriasProposta(Long id, final Context context) {

        if(SingletonAPIManager.getInstance(context).ligadoInternet(context)) {

            JsonObjectRequest getCategoriasProposta = SingletonAPIManager.getInstance(context).pedirAPI("propostas/" + id + "/categorias", context, new SingletonAPIManager.APIJsonResposta() {

                @Override
                public void Sucesso(JSONObject resultado) {

                    try {
                        JSONObject categorias = resultado.getJSONObject("Categorias");

                        String categoriaNome = resultado.getString("Categoria");

                        JSONObject categoriaBase = categorias.getJSONObject("Base");
                        JSONArray categoriaFilha = categorias.getJSONArray("Filhas");

                        JSONObject categoria = new JSONObject();

                        switch (categoriaNome)
                        {
                            case "Brinquedos":

                                categoria.put("id", categoriaBase.get("id"));
                                categoria.put("nome", categoriaBase.get("nome"));
                                categoria.put("editora", categoriaFilha.getJSONObject(0).get("editora"));
                                categoria.put("faixa_etaria", categoriaFilha.getJSONObject(0).get("faixa_etaria"));
                                categoria.put("descricao", categoriaFilha.getJSONObject(0).get("descricao"));

                                Brinquedo brinquedo = BrinquedosParser.paraObjeto(categoria, context);

                                if(categoriasListener != null)
                                    categoriasListener.onObterCategoria(brinquedo, categoriaNome, null);

                                break;

                            case "Jogos":

                                categoria.put("id", categoriaBase.get("id"));
                                categoria.put("nome", categoriaBase.get("nome"));
                                categoria.put("editora", categoriaFilha.getJSONObject(0).get("editora"));
                                categoria.put("faixa_etaria", categoriaFilha.getJSONObject(0).get("faixa_etaria"));
                                categoria.put("descricao", categoriaFilha.getJSONObject(0).get("descricao"));
                                categoria.put("id_genero", categoriaFilha.getJSONObject(1).get("id_genero"));
                                categoria.put("produtora", categoriaFilha.getJSONObject(1).get("produtora"));

                                Jogo jogo = JogosParser.paraObjeto(categoria, context);

                                if(categoriasListener != null)
                                    categoriasListener.onObterCategoria(jogo, categoriaNome, null);

                                break;

                            case "Eletrónica":

                                categoria.put("id", categoriaBase.get("id"));
                                categoria.put("nome", categoriaBase.get("nome"));
                                categoria.put("descricao", categoriaFilha.getJSONObject(0).get("descricao"));
                                categoria.put("marca", categoriaFilha.getJSONObject(0).get("marca"));

                                Eletronica eletronica = EletronicaParser.paraObjeto(categoria, context);

                                if(categoriasListener != null)
                                    categoriasListener.onObterCategoria(eletronica, categoriaNome, null);

                                break;

                            case "Computadores":

                                categoria.put("id", categoriaBase.get("id"));
                                categoria.put("nome", categoriaBase.get("nome"));
                                categoria.put("descricao", categoriaFilha.getJSONObject(0).get("descricao"));
                                categoria.put("marca", categoriaFilha.getJSONObject(0).get("marca"));
                                categoria.put("processador", categoriaFilha.getJSONObject(1).get("processador"));
                                categoria.put("ram", categoriaFilha.getJSONObject(1).get("ram"));
                                categoria.put("hdd", categoriaFilha.getJSONObject(1).get("hdd"));
                                categoria.put("gpu", categoriaFilha.getJSONObject(1).get("gpu"));
                                categoria.put("os", categoriaFilha.getJSONObject(1).get("os"));
                                categoria.put("portatil", categoriaFilha.getJSONObject(1).get("portatil"));

                                Computador computador = ComputadoresParser.paraObjeto(categoria, context);

                                if(categoriasListener != null)
                                    categoriasListener.onObterCategoria(computador, categoriaNome, null);

                                break;

                            case "Smartphones":

                                categoria.put("id", categoriaBase.get("id"));
                                categoria.put("nome", categoriaBase.get("nome"));
                                categoria.put("descricao", categoriaFilha.getJSONObject(0).get("descricao"));
                                categoria.put("marca", categoriaFilha.getJSONObject(0).get("marca"));
                                categoria.put("processador", categoriaFilha.getJSONObject(1).get("processador"));
                                categoria.put("ram", categoriaFilha.getJSONObject(1).get("ram"));
                                categoria.put("hdd", categoriaFilha.getJSONObject(1).get("hdd"));
                                categoria.put("os", categoriaFilha.getJSONObject(1).get("os"));
                                categoria.put("tamanho", categoriaFilha.getJSONObject(1).get("tamanho"));

                                Smartphone smartphone = SmartphonesParser.paraObjeto(categoria, context);

                                if(categoriasListener != null)
                                    categoriasListener.onObterCategoria(smartphone, categoriaNome, null);

                                break;

                            case "Livros":

                                categoria.put("id", categoriaBase.get("id"));
                                categoria.put("nome", categoriaBase.get("nome"));
                                categoria.put("titulo", categoriaFilha.getJSONObject(0).get("titulo"));
                                categoria.put("editora", categoriaFilha.getJSONObject(0).get("editora"));
                                categoria.put("autor", categoriaFilha.getJSONObject(0).get("autor"));
                                categoria.put("isbn", categoriaFilha.getJSONObject(0).get("isbn"));

                                Livro livro = LivrosParser.paraObjeto(categoria, context);

                                if(categoriasListener != null)
                                    categoriasListener.onObterCategoria(livro, categoriaNome, null);

                                break;

                            case "Roupa":

                                categoria.put("id", categoriaBase.get("id"));
                                categoria.put("nome", categoriaBase.get("nome"));
                                categoria.put("marca", categoriaFilha.getJSONObject(0).get("marca"));
                                categoria.put("tamanho", categoriaFilha.getJSONObject(0).get("tamanho"));
                                categoria.put("id_tipo", categoriaFilha.getJSONObject(0).get("id_tipo"));

                                Roupa roupa = RoupasParser.paraObjeto(categoria, context);

                                if(categoriasListener != null)
                                    categoriasListener.onObterCategoria(roupa, categoriaNome, null);

                                break;
                        }

                    } catch (JSONException e) {
                        if(categoriasListener != null)
                            categoriasListener.onErroObterCategoria("Ocorreu um erro no processamento da categoria.", e);
                    }
                }

                @Override
                public void Erro(VolleyError erro) {
                    if(categoriasListener != null)
                        categoriasListener.onErroObterCategoria("Não foi possivél obter a categoria.", erro);
                }
            });

            SingletonAPIManager.getInstance(context).getRequestQueue(context).add(getCategoriasProposta);
        }
    }

    public synchronized void adicionarCategoria(final Categoria categoria, final Context context, final SingletonActivityAPIResponse interfaceSA) {

        String categoriaClassPath = categoria.getClass().getName();

        String categoriaClass = categoriaClassPath.substring(categoriaClassPath.lastIndexOf(".") + 1);

        switch (categoriaClass) {

            case "Jogo":

                Jogo jogo = (Jogo) categoria;
                Brinquedo brinquedoJogo = new Brinquedo(jogo.getNome(), jogo.getEditora(), jogo.getFaixaEtaria(), jogo.getDescricao());
                Categoria categoriaJogo = new Categoria(jogo.getNome());

                if (SingletonAPIManager.getInstance(context).ligadoInternet(context)) {

                    final String bodyString = "{\"Categoria\":\"jogos\",\"CategoriaMae\":" + CategoriasParser.paraJson(categoriaJogo) +
                            ",\"CategoriaFilha\":" + BrinquedosParser.paraJson(brinquedoJogo) +
                            ",\"CategoriaNeta\":" + JogosParser.paraJson(jogo) + "}";

                    StringRequest jogoPOST = SingletonAPIManager.getInstance(context).enviarAPI(
                            "categorias",
                            context,
                            Request.Method.POST,
                            bodyString,
                            new SingletonAPIManager.APIStringResposta() {

                                @Override
                                public void Sucesso(String resposta) {

                                    try {
                                        JSONObject jsonObject = new JSONObject(resposta);

                                        Long id = jsonObject.getLong("ID");
                                        categoria.setId(id);

                                        if (SingletonCategorias.getInstance().adicionarCategoriaLocal(categoria, context)) {
                                            interfaceSA.onSuccessEnvioAPI(categoria);
                                        }

                                    } catch (JSONException ex) {
                                        interfaceSA.onErrorEnvioAPI("Ocorreu um erro no processamento do Jogo.", ex);
                                    }
                                }

                                @Override
                                public void Erro(VolleyError erro) {
                                    interfaceSA.onErrorEnvioAPI("Ocorreu um erro no envio do Jogo para a API.", erro);
                                }
                            }
                    );

                    SingletonAPIManager.getInstance(context).getRequestQueue(context).add(jogoPOST);
                }

                break;

            case "Brinquedo":

                Brinquedo brinquedo = (Brinquedo) categoria;
                Categoria categoriaBrinquedo = new Categoria(brinquedo.getNome());

                if (SingletonAPIManager.getInstance(context).ligadoInternet(context)) {

                    final String bodyString = "{\"Categoria\":\"brinquedos\",\"CategoriaMae\":" + CategoriasParser.paraJson(categoriaBrinquedo) +
                            ",\"CategoriaFilha\":" + BrinquedosParser.paraJson(brinquedo) + "}";

                    StringRequest brinquedoPOST = SingletonAPIManager.getInstance(context).enviarAPI(
                            "categorias",
                            context,
                            Request.Method.POST,
                            bodyString,
                            new SingletonAPIManager.APIStringResposta() {

                                @Override
                                public void Sucesso(String resposta) {

                                    try {
                                        JSONObject jsonObject = new JSONObject(resposta);

                                        Long id = jsonObject.getLong("ID");
                                        categoria.setId(id);

                                        if (SingletonCategorias.getInstance().adicionarCategoriaLocal(categoria, context)) {
                                            interfaceSA.onSuccessEnvioAPI(categoria);
                                        }

                                    } catch (JSONException ex) {
                                        interfaceSA.onErrorEnvioAPI("Ocorreu um erro no processamento do Brinquedo.", ex);
                                    }
                                }

                                @Override
                                public void Erro(VolleyError erro) {
                                    interfaceSA.onErrorEnvioAPI("Ocorreu um erro no envio do Brinquedo para a API.", erro);
                                }
                            }
                    );

                    SingletonAPIManager.getInstance(context).getRequestQueue(context).add(brinquedoPOST);
                }

                break;

            case "Computador":

                Computador computador = (Computador) categoria;
                Eletronica eletronicaComputador = new Eletronica(computador.getNome(), computador.getDescricao(), computador.getMarca());
                Categoria categoriaComputador = new Categoria(computador.getNome());

                if (SingletonAPIManager.getInstance(context).ligadoInternet(context)) {

                    final String bodyString = "{\"Categoria\":\"computadores\",\"CategoriaMae\":" + CategoriasParser.paraJson(categoriaComputador) +
                            ",\"CategoriaFilha\":" + EletronicaParser.paraJson(eletronicaComputador) +
                            ",\"CategoriaNeta\":" + ComputadoresParser.paraJson(computador) + "}";

                    StringRequest computadorPOST = SingletonAPIManager.getInstance(context).enviarAPI(
                            "categorias",
                            context,
                            Request.Method.POST,
                            bodyString,
                            new SingletonAPIManager.APIStringResposta() {

                                @Override
                                public void Sucesso(String resposta) {

                                    try {
                                        JSONObject jsonObject = new JSONObject(resposta);

                                        Long id = jsonObject.getLong("ID");
                                        categoria.setId(id);

                                        if (SingletonCategorias.getInstance().adicionarCategoriaLocal(categoria, context)) {
                                            interfaceSA.onSuccessEnvioAPI(categoria);
                                        }

                                    } catch (JSONException ex) {
                                        interfaceSA.onErrorEnvioAPI("Ocorreu um erro no processamento do Computador.", ex);
                                    }
                                }

                                @Override
                                public void Erro(VolleyError erro) {
                                    interfaceSA.onErrorEnvioAPI("Ocorreu um erro no envio do Computador para a API.", erro);
                                }
                            }
                    );

                    SingletonAPIManager.getInstance(context).getRequestQueue(context).add(computadorPOST);
                }

                break;

            case "Smartphone":

                Smartphone smartphone = (Smartphone) categoria;
                Eletronica smartphoneEletronica = new Eletronica(smartphone.getNome(), smartphone.getDescricao(), smartphone.getMarca());
                Categoria categoriaSmartphone = new Categoria(smartphone.getNome());

                if (SingletonAPIManager.getInstance(context).ligadoInternet(context)) {

                    final String bodyString = "{\"Categoria\":\"smartphones\",\"CategoriaMae\":" + CategoriasParser.paraJson(categoriaSmartphone) +
                            ",\"CategoriaFilha\":" + EletronicaParser.paraJson(smartphoneEletronica) +
                            ",\"CategoriaNeta\":" + SmartphonesParser.paraJson(smartphone) + "}";

                    StringRequest smartphonePOST = SingletonAPIManager.getInstance(context).enviarAPI(
                            "categorias",
                            context,
                            Request.Method.POST,
                            bodyString,
                            new SingletonAPIManager.APIStringResposta() {

                                @Override
                                public void Sucesso(String resposta) {

                                    try {
                                        JSONObject jsonObject = new JSONObject(resposta);

                                        Long id = jsonObject.getLong("ID");
                                        categoria.setId(id);

                                        if (SingletonCategorias.getInstance().adicionarCategoriaLocal(categoria, context)) {
                                            interfaceSA.onSuccessEnvioAPI(categoria);
                                        }

                                    } catch (JSONException ex) {
                                        interfaceSA.onErrorEnvioAPI("Ocorreu um erro no processamento do Smartphone.", ex);
                                    }
                                }

                                @Override
                                public void Erro(VolleyError erro) {
                                    interfaceSA.onErrorEnvioAPI("Ocorreu um erro no envio do Smartphone para a API.", erro);
                                }
                            }
                    );

                    SingletonAPIManager.getInstance(context).getRequestQueue(context).add(smartphonePOST);
                }

                break;

            case "Eletronica":

                Eletronica eletronica = (Eletronica) categoria;
                Categoria categoriaEletronica = new Categoria(eletronica.getNome());

                if (SingletonAPIManager.getInstance(context).ligadoInternet(context)) {

                    final String bodyString = "{\"Categoria\":\"eletronica\",\"CategoriaMae\":" + CategoriasParser.paraJson(categoriaEletronica) +
                            ",\"CategoriaFilha\":" + EletronicaParser.paraJson(eletronica) + "}";

                    StringRequest eletronicaPOST = SingletonAPIManager.getInstance(context).enviarAPI(
                            "categorias",
                            context,
                            Request.Method.POST,
                            bodyString,
                            new SingletonAPIManager.APIStringResposta() {

                                @Override
                                public void Sucesso(String resposta) {

                                    try {
                                        JSONObject jsonObject = new JSONObject(resposta);

                                        Long id = jsonObject.getLong("ID");
                                        categoria.setId(id);

                                        if (SingletonCategorias.getInstance().adicionarCategoriaLocal(categoria, context)) {
                                            interfaceSA.onSuccessEnvioAPI(categoria);
                                        }

                                    } catch (JSONException ex) {
                                        interfaceSA.onErrorEnvioAPI("Ocorreu um erro no processamento da Eletronica.", ex);
                                    }
                                }

                                @Override
                                public void Erro(VolleyError erro) {
                                    interfaceSA.onErrorEnvioAPI("Ocorreu um erro no envio da Eletronica para a API.", erro);
                                }
                            }
                    );

                    SingletonAPIManager.getInstance(context).getRequestQueue(context).add(eletronicaPOST);
                }

                break;

            case "Roupa":

                Roupa roupa = (Roupa) categoria;
                Categoria categoriaRoupa = new Categoria(roupa.getNome());

                if (SingletonAPIManager.getInstance(context).ligadoInternet(context)) {

                    final String bodyString = "{\"Categoria\":\"roupa\",\"CategoriaMae\":" + CategoriasParser.paraJson(categoriaRoupa) +
                            ",\"CategoriaFilha\":" + RoupasParser.paraJson(roupa) + "}";

                    StringRequest roupaPOST = SingletonAPIManager.getInstance(context).enviarAPI(
                            "categorias",
                            context,
                            Request.Method.POST,
                            bodyString,
                            new SingletonAPIManager.APIStringResposta() {

                                @Override
                                public void Sucesso(String resposta) {

                                    try {
                                        JSONObject jsonObject = new JSONObject(resposta);

                                        Long id = jsonObject.getLong("ID");
                                        categoria.setId(id);

                                        if (SingletonCategorias.getInstance().adicionarCategoriaLocal(categoria, context)) {
                                            interfaceSA.onSuccessEnvioAPI(categoria);
                                        }

                                    } catch (JSONException ex) {
                                        interfaceSA.onErrorEnvioAPI("Ocorreu um erro no processamento da Roupa.", ex);
                                    }
                                }

                                @Override
                                public void Erro(VolleyError erro) {
                                    interfaceSA.onErrorEnvioAPI("Ocorreu um erro no envio da Roupa para a API.", erro);
                                }
                            }
                    );

                    SingletonAPIManager.getInstance(context).getRequestQueue(context).add(roupaPOST);
                }

                break;

            case "Livro":

                Livro livro = (Livro) categoria;
                Categoria categoriaLivro = new Categoria(livro.getNome());

                if (SingletonAPIManager.getInstance(context).ligadoInternet(context)) {

                    final String bodyString = "{\"Categoria\":\"livros\",\"CategoriaMae\":" + CategoriasParser.paraJson(categoriaLivro) +
                            ",\"CategoriaFilha\":" + LivrosParser.paraJson(livro) + "}";

                    StringRequest livroPOST = SingletonAPIManager.getInstance(context).enviarAPI(
                            "categorias",
                            context,
                            Request.Method.POST,
                            bodyString,
                            new SingletonAPIManager.APIStringResposta() {

                                @Override
                                public void Sucesso(String resposta) {

                                    try {
                                        JSONObject jsonObject = new JSONObject(resposta);

                                        Long id = jsonObject.getLong("ID");
                                        categoria.setId(id);

                                        if (SingletonCategorias.getInstance().adicionarCategoriaLocal(categoria, context)) {
                                            interfaceSA.onSuccessEnvioAPI(categoria);
                                        }

                                    } catch (JSONException ex) {
                                        interfaceSA.onErrorEnvioAPI("Ocorreu um erro no processamento do Livro.", ex);
                                    }
                                }

                                @Override
                                public void Erro(VolleyError erro) {
                                    interfaceSA.onErrorEnvioAPI("Ocorreu um erro no envio do Livro para a API.", erro);
                                }
                            }
                    );

                    SingletonAPIManager.getInstance(context).getRequestQueue(context).add(livroPOST);
                }

                break;
        }
    }

    private boolean adicionarCategoriaLocal(Categoria categoria, Context context) {

        String categoriaClassPath = categoria.getClass().getName();

        String categoriaClass = categoriaClassPath.substring(categoriaClassPath.lastIndexOf(".") + 1);

        switch (categoriaClass) {

            case "Jogo":

                JogoBDTable jogoBDTable = new JogoBDTable(context);

                Jogo jogoInserido = jogoBDTable.insert((Jogo) categoria);

                if (jogoInserido != null) {
                    categorias.add(jogoInserido);
                    return categorias.contains(jogoInserido);
                }

                break;

            case "Brinquedo":

                BrinquedoBDTable brinquedoBDTable = new BrinquedoBDTable(context);

                Brinquedo brinquedoInserido = brinquedoBDTable.insert((Brinquedo) categoria);

                if (brinquedoInserido != null) {
                    categorias.add(brinquedoInserido);
                    return categorias.contains(brinquedoInserido);
                }

                break;

            case "Computador":

                ComputadorBDTable computadorBDTable = new ComputadorBDTable(context);

                Computador computadorInserido = computadorBDTable.insert((Computador) categoria);

                if (computadorInserido != null) {
                    categorias.add(computadorInserido);
                    return categorias.contains(computadorInserido);
                }

                break;

            case "Smartphone":

                SmartphoneBDTable smartphoneBDTable = new SmartphoneBDTable(context);

                Smartphone smartphoneInserido = smartphoneBDTable.insert((Smartphone) categoria);

                if (smartphoneInserido != null) {
                    categorias.add(smartphoneInserido);
                    return categorias.contains(smartphoneInserido);
                }

                break;

            case "Eletronica":

                EletronicaBDTable eletronicaBDTable = new EletronicaBDTable(context);

                Eletronica eletronicaInserida = eletronicaBDTable.insert((Eletronica) categoria);

                if (eletronicaInserida != null) {
                    categorias.add(eletronicaInserida);
                    return categorias.contains(eletronicaInserida);
                }

                break;

            case "Roupa":


                RoupaBDTable roupaBDTable = new RoupaBDTable(context);

                Roupa roupaInserida = roupaBDTable.insert((Roupa) categoria);

                if (roupaInserida != null) {
                    categorias.add(roupaInserida);
                    return categorias.contains(roupaInserida);
                }

                break;

            case "Livro":

                LivroBDTable livroBDTable = new LivroBDTable(context);

                Livro livroInserido = livroBDTable.insert((Livro) categoria);

                if (livroInserido != null) {
                    categorias.add(livroInserido);
                    return categorias.contains(livroInserido);
                }

                break;
        }

        return false;
    }

    public boolean removerCategoriaLocal(Categoria categoria, Context context) {

        String categoriaNome = categoria.getClass().getName();

        switch (categoriaNome) {

            case "Jogo":

                JogoBDTable jogoBDTable = new JogoBDTable(context);

                return jogoBDTable.delete(categoria.getId()) && categorias.remove(categoria);

            case "Brinquedo":

                BrinquedoBDTable brinquedoBDTable = new BrinquedoBDTable(context);

                return brinquedoBDTable.delete(categoria.getId()) && categorias.remove(categoria);

            case "Computador":

                ComputadorBDTable computadorBDTable = new ComputadorBDTable(context);

                return computadorBDTable.delete(categoria.getId()) && categorias.remove(categoria);

            case "Smartphone":

                SmartphoneBDTable smartphoneBDTable = new SmartphoneBDTable(context);

                return smartphoneBDTable.delete(categoria.getId()) && categorias.remove(categoria);

            case "Eletronica":

                EletronicaBDTable eletronicaBDTable = new EletronicaBDTable(context);

                return eletronicaBDTable.delete(categoria.getId()) && categorias.remove(categoria);

            case "Roupa":

                RoupaBDTable roupaBDTable = new RoupaBDTable(context);

                return roupaBDTable.delete(categoria.getId()) && categorias.remove(categoria);

            case "Livro":

                LivroBDTable livroBDTable = new LivroBDTable(context);

                return livroBDTable.delete(categoria.getId()) && categorias.remove(categoria);

            default:

                return false;
        }
    }

    public boolean editarCategoriaLocal(Categoria categoria, Context context) {

        String categoriaNome = categoria.getClass().getName();

        switch (categoriaNome) {

            case "Jogo":

                JogoBDTable jogoBDTable = new JogoBDTable(context);

                if (jogoBDTable.update((Jogo) categoria)) {
                    Categoria novaCategoria = categorias.set(categoria.getId().intValue(), categoria);

                    return categorias.contains(novaCategoria);
                }

                break;

            case "Brinquedo":

                BrinquedoBDTable brinquedoBDTable = new BrinquedoBDTable(context);

                if (brinquedoBDTable.update((Brinquedo) categoria)) {
                    Categoria novaCategoria = categorias.set(categoria.getId().intValue(), categoria);

                    return categorias.contains(novaCategoria);
                }

                break;

            case "Computador":

                ComputadorBDTable computadorBDTable = new ComputadorBDTable(context);

                if (computadorBDTable.update((Computador) categoria)) {
                    Categoria novaCategoria = categorias.set(categoria.getId().intValue(), categoria);

                    return categorias.contains(novaCategoria);
                }

                break;

            case "Smartphone":

                SmartphoneBDTable smartphoneBDTable = new SmartphoneBDTable(context);

                if (smartphoneBDTable.update((Smartphone) categoria)) {
                    Categoria novaCategoria = categorias.set(categoria.getId().intValue(), categoria);

                    return categorias.contains(novaCategoria);
                }

                break;

            case "Eletronica":

                EletronicaBDTable eletronicaBDTable = new EletronicaBDTable(context);

                if (eletronicaBDTable.update((Eletronica) categoria)) {
                    Categoria novaCategoria = categorias.set(categoria.getId().intValue(), categoria);

                    return categorias.contains(novaCategoria);
                }

                break;

            case "Roupa":

                RoupaBDTable roupaBDTable = new RoupaBDTable(context);

                if (roupaBDTable.update((Roupa) categoria)) {
                    Categoria novaCategoria = categorias.set(categoria.getId().intValue(), categoria);

                    return categorias.contains(novaCategoria);
                }

                break;

            case "Livro":

                LivroBDTable livroBDTable = new LivroBDTable(context);

                if (livroBDTable.update((Livro) categoria)) {
                    Categoria novaCategoria = categorias.set(categoria.getId().intValue(), categoria);

                    return categorias.contains(novaCategoria);
                }

                break;

            default:

                return false;
        }

        return false;
    }

    public Categoria pesquisarCategoriaPorID(Long id) {
        return categorias.get(id.intValue());
    }

    public void setCategoriasListener(CategoriasListener categoriasListener) {
        this.categoriasListener = categoriasListener;
    }
}
