package pt.ipleiria.estg.tesp.psi.projsi.sistematrocas.projetosi_android_1718.singletons;

import android.content.Context;

import com.android.volley.Request;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

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
    private Context context;
    private static SingletonCategorias INSTANCE = null;
    private ArrayList<Categoria> categorias;

    public static SingletonCategorias getInstance(Context context) {
        if (INSTANCE == null)
            INSTANCE = new SingletonCategorias(context);

        return INSTANCE;
    }

    private SingletonCategorias(Context context) {
        this.context = context;
        categorias = new ArrayList<>();
    }

    public ArrayList<Categoria> getCategorias() {
        return categorias;
    }

    public void setCategorias(ArrayList<Categoria> categorias) {
        this.categorias = categorias;
    }

    public synchronized void adicionarCategoria(final Categoria categoria, final SingletonActivityAPIResponse interfaceSA) {

        //SharedPreferences preferences = getSharedPreferences("APP_SETTINGS", Context.MODE_PRIVATE);
        //String pin = preferences.getString("pin", "");

        //Para efeito de desenvolvimento.
        String pin = "MPW7P";


        if(!pin.isEmpty()) {

            String categoriaClassPath = categoria.getClass().getName();

            String categoriaClass = categoriaClassPath.substring(categoriaClassPath.lastIndexOf(".") + 1);

            switch (categoriaClass) {

                case "Jogo":

                    Jogo jogo = (Jogo) categoria;
                    Brinquedo brinquedoJogo = new Brinquedo(jogo.getNome(), jogo.getEditora(), jogo.getFaixaEtaria(), jogo.getDescricao());
                    Categoria categoriaJogo = new Categoria(jogo.getNome());

                    if (SingletonAPIManager.getInstance(context).ligadoInternet()) {

                        //TEST ONLY: REMOVER ASAP
                        SingletonAPIManager.getInstance(context).setAuth(pin);

                        final String bodyString = "{\"Categoria\":\"jogos\",\"CategoriaMae\":" + CategoriasParser.paraJson(categoriaJogo) +
                                ",\"CategoriaFilha\":" + BrinquedosParser.paraJson(brinquedoJogo) +
                                ",\"CategoriaNeta\":" + JogosParser.paraJson(jogo) + "}";

                        StringRequest jogoPOST = SingletonAPIManager.getInstance(context).enviarAPI(
                                "categorias",
                                Request.Method.POST,
                                bodyString,
                                new SingletonAPIManager.APIStringResposta() {

                                    @Override
                                    public void Sucesso(String resposta) {

                                        try {
                                            JSONObject jsonObject = new JSONObject(resposta);

                                            Long id = jsonObject.getLong("ID");
                                            categoria.setId(id);

                                            if (SingletonCategorias.getInstance(context).adicionarCategoriaLocal(categoria)) {
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

                        SingletonAPIManager.getInstance(context).getRequestQueue().add(jogoPOST);
                    }

                    break;

                case "Brinquedo":

                    Brinquedo brinquedo = (Brinquedo) categoria;
                    Categoria categoriaBrinquedo = new Categoria(brinquedo.getNome());

                    if (SingletonAPIManager.getInstance(context).ligadoInternet()) {

                        SingletonAPIManager.getInstance(context).setAuth(pin);

                        final String bodyString = "{\"Categoria\":\"brinquedos\",\"CategoriaMae\":" + CategoriasParser.paraJson(categoriaBrinquedo) +
                                ",\"CategoriaFilha\":" + BrinquedosParser.paraJson(brinquedo) + "}";

                        StringRequest brinquedoPOST = SingletonAPIManager.getInstance(context).enviarAPI(
                                "categorias",
                                Request.Method.POST,
                                bodyString,
                                new SingletonAPIManager.APIStringResposta() {

                                    @Override
                                    public void Sucesso(String resposta) {

                                        try {
                                            JSONObject jsonObject = new JSONObject(resposta);

                                            Long id = jsonObject.getLong("ID");
                                            categoria.setId(id);

                                            if (SingletonCategorias.getInstance(context).adicionarCategoriaLocal(categoria)) {
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

                        SingletonAPIManager.getInstance(context).getRequestQueue().add(brinquedoPOST);
                    }

                    break;

                case "Computador":

                    Computador computador = (Computador) categoria;
                    Eletronica eletronicaComputador = new Eletronica(computador.getNome(), computador.getDescricao(), computador.getMarca());
                    Categoria categoriaComputador = new Categoria(computador.getNome());

                    if (SingletonAPIManager.getInstance(context).ligadoInternet()) {

                        SingletonAPIManager.getInstance(context).setAuth(pin);

                        final String bodyString = "{\"Categoria\":\"computadores\",\"CategoriaMae\":" + CategoriasParser.paraJson(categoriaComputador) +
                                ",\"CategoriaFilha\":" + EletronicaParser.paraJson(eletronicaComputador) +
                                ",\"CategoriaNeta\":" + ComputadoresParser.paraJson(computador) + "}";

                        StringRequest computadorPOST = SingletonAPIManager.getInstance(context).enviarAPI(
                                "categorias",
                                Request.Method.POST,
                                bodyString,
                                new SingletonAPIManager.APIStringResposta() {

                                    @Override
                                    public void Sucesso(String resposta) {

                                        try {
                                            JSONObject jsonObject = new JSONObject(resposta);

                                            Long id = jsonObject.getLong("ID");
                                            categoria.setId(id);

                                            if (SingletonCategorias.getInstance(context).adicionarCategoriaLocal(categoria)) {
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

                        SingletonAPIManager.getInstance(context).getRequestQueue().add(computadorPOST);
                    }

                    break;

                case "Smartphone":

                    Smartphone smartphone = (Smartphone) categoria;
                    Eletronica smartphoneEletronica = new Eletronica(smartphone.getNome(), smartphone.getDescricao(), smartphone.getMarca());
                    Categoria categoriaSmartphone = new Categoria(smartphone.getNome());

                    if (SingletonAPIManager.getInstance(context).ligadoInternet()) {

                        SingletonAPIManager.getInstance(context).setAuth(pin);

                        final String bodyString = "{\"Categoria\":\"smartphones\",\"CategoriaMae\":" + CategoriasParser.paraJson(categoriaSmartphone) +
                                ",\"CategoriaFilha\":" + EletronicaParser.paraJson(smartphoneEletronica) +
                                ",\"CategoriaNeta\":" + SmartphonesParser.paraJson(smartphone) + "}";

                        StringRequest smartphonePOST = SingletonAPIManager.getInstance(context).enviarAPI(
                                "categorias",
                                Request.Method.POST,
                                bodyString,
                                new SingletonAPIManager.APIStringResposta() {

                                    @Override
                                    public void Sucesso(String resposta) {

                                        try {
                                            JSONObject jsonObject = new JSONObject(resposta);

                                            Long id = jsonObject.getLong("ID");
                                            categoria.setId(id);

                                            if (SingletonCategorias.getInstance(context).adicionarCategoriaLocal(categoria)) {
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

                        SingletonAPIManager.getInstance(context).getRequestQueue().add(smartphonePOST);
                    }

                    break;

                case "Eletronica":

                    Eletronica eletronica = (Eletronica) categoria;
                    Categoria categoriaEletronica = new Categoria(eletronica.getNome());

                    if (SingletonAPIManager.getInstance(context).ligadoInternet()) {

                        SingletonAPIManager.getInstance(context).setAuth(pin);

                        final String bodyString = "{\"Categoria\":\"eletronica\",\"CategoriaMae\":" + CategoriasParser.paraJson(categoriaEletronica) +
                                ",\"CategoriaFilha\":" + EletronicaParser.paraJson(eletronica) + "}";

                        StringRequest eletronicaPOST = SingletonAPIManager.getInstance(context).enviarAPI(
                                "categorias",
                                Request.Method.POST,
                                bodyString,
                                new SingletonAPIManager.APIStringResposta() {

                                    @Override
                                    public void Sucesso(String resposta) {

                                        try {
                                            JSONObject jsonObject = new JSONObject(resposta);

                                            Long id = jsonObject.getLong("ID");
                                            categoria.setId(id);

                                            if (SingletonCategorias.getInstance(context).adicionarCategoriaLocal(categoria)) {
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

                        SingletonAPIManager.getInstance(context).getRequestQueue().add(eletronicaPOST);
                    }

                    break;

                case "Roupa":

                    Roupa roupa = (Roupa) categoria;
                    Categoria categoriaRoupa = new Categoria(roupa.getNome());

                    if (SingletonAPIManager.getInstance(context).ligadoInternet()) {

                        SingletonAPIManager.getInstance(context).setAuth(pin);

                        final String bodyString = "{\"Categoria\":\"roupa\",\"CategoriaMae\":" + CategoriasParser.paraJson(categoriaRoupa) +
                                ",\"CategoriaFilha\":" + RoupasParser.paraJson(roupa) + "}";

                        StringRequest roupaPOST = SingletonAPIManager.getInstance(context).enviarAPI(
                                "categorias",
                                Request.Method.POST,
                                bodyString,
                                new SingletonAPIManager.APIStringResposta() {

                                    @Override
                                    public void Sucesso(String resposta) {

                                        try {
                                            JSONObject jsonObject = new JSONObject(resposta);

                                            Long id = jsonObject.getLong("ID");
                                            categoria.setId(id);

                                            if (SingletonCategorias.getInstance(context).adicionarCategoriaLocal(categoria)) {
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

                        SingletonAPIManager.getInstance(context).getRequestQueue().add(roupaPOST);
                    }

                    break;

                case "Livro":

                    Livro livro = (Livro) categoria;
                    Categoria categoriaLivro = new Categoria(livro.getNome());

                    if (SingletonAPIManager.getInstance(context).ligadoInternet()) {

                        SingletonAPIManager.getInstance(context).setAuth(pin);

                        final String bodyString = "{\"Categoria\":\"livros\",\"CategoriaMae\":" + CategoriasParser.paraJson(categoriaLivro) +
                                ",\"CategoriaFilha\":" + LivrosParser.paraJson(livro) + "}";

                        StringRequest livroPOST = SingletonAPIManager.getInstance(context).enviarAPI(
                                "categorias",
                                Request.Method.POST,
                                bodyString,
                                new SingletonAPIManager.APIStringResposta() {

                                    @Override
                                    public void Sucesso(String resposta) {

                                        try {
                                            JSONObject jsonObject = new JSONObject(resposta);

                                            Long id = jsonObject.getLong("ID");
                                            categoria.setId(id);

                                            if (SingletonCategorias.getInstance(context).adicionarCategoriaLocal(categoria)) {
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

                        SingletonAPIManager.getInstance(context).getRequestQueue().add(livroPOST);
                    }

                    break;
            }
        }
    }

    private boolean adicionarCategoriaLocal(Categoria categoria) {

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

    public boolean removerCategoria(Categoria categoria) {

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

    public boolean editarCategoria(Categoria categoria) {

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

    public Categoria pesquisarCategoriaID(Long id)
    {
        return categorias.get(id.intValue());
    }
}
