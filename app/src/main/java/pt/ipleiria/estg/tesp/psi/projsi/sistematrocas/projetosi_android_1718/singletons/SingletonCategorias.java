package pt.ipleiria.estg.tesp.psi.projsi.sistematrocas.projetosi_android_1718.singletons;

import android.content.Context;
import android.support.annotation.NonNull;

import com.android.volley.Request;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;

import pt.ipleiria.estg.tesp.psi.projsi.sistematrocas.projetosi_android_1718.helpers.BrinquedoBDTable;
import pt.ipleiria.estg.tesp.psi.projsi.sistematrocas.projetosi_android_1718.helpers.CategoriaBDTable;
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
import pt.ipleiria.estg.tesp.psi.projsi.sistematrocas.projetosi_android_1718.parsers.JogosParser;

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

    public Categoria adicionarCategoria(final Categoria categoria)
    {
        String categoriaNome = categoria.getClass().getName();

        switch (categoriaNome) {

            case "Jogo":

                Jogo jogo = (Jogo) categoria;
                Brinquedo brinquedoJogo = new Brinquedo(jogo.getNome(), jogo.getEditora(), jogo.getFaixaEtaria(), jogo.getDescricao());
                Categoria categoriaJogo = new Categoria(jogo.getNome());

                final Jogo[] jogoSucesso = {null};

                if (SingletonAPIManager.getInstance(context).ligadoInternet()) {

                    final String bodyString = "[\"Categoria\": \"jogos\", \"CategoriaMae\": " + CategoriasParser.paraJson(categoriaJogo) +
                            ", \"CategoriaFilha\": " + BrinquedosParser.paraJson(brinquedoJogo) +
                            "\"CategoriaNeta\": " + JogosParser.paraJson(jogo) + "";

                    StringRequest JogoPOST = SingletonAPIManager.getInstance(context).enviarAPI(
                            "categorias/",
                            Request.Method.POST,
                            bodyString,
                            new SingletonAPIManager.APIStringResposta() {

                                @Override
                                public void Sucesso(String resposta) {

                                    try {
                                        JSONObject jsonObject = new JSONObject(resposta);

                                        Long id = jsonObject.getLong("ID");

                                        JogoBDTable jogoBDTable = new JogoBDTable(context);

                                        Jogo jogoInserido = jogoBDTable.insert((Jogo) categoria);

                                        if (jogoInserido != null && categorias.add(jogoInserido)) {
                                            jogoSucesso[0] = jogoInserido;
                                        }

                                    } catch (JSONException ex) {
                                        throw new RuntimeException("Ocorreu um erro no envio das categorias dos formulários.");
                                    }
                                }

                                @Override
                                public void Erro(VolleyError erro) {

                                }
                            }
                    );

                    SingletonAPIManager.getInstance(context).getRequestQueue().add(JogoPOST);
                }

                return jogoSucesso[0];

            case "Brinquedo":

                /*BrinquedoBDTable brinquedoBDTable = new BrinquedoBDTable(context);

                Brinquedo brinquedoInserido = brinquedoBDTable.insert((Brinquedo) categoria);

                if(brinquedoInserido != null && categorias.add(brinquedoInserido)) {
                    return brinquedoInserido;
                }*/

            case "Computador":

                /*ComputadorBDTable computadorBDTable = new ComputadorBDTable(context);

                Computador computadorInserido = computadorBDTable.insert((Computador) categoria);

                if(computadorInserido != null && categorias.add(computadorInserido)) {
                    return computadorInserido;
                }*/

            case "Smartphone":

                /*SmartphoneBDTable smartphoneBDTable = new SmartphoneBDTable(context);

                Smartphone smartphoneInserido = smartphoneBDTable.insert((Smartphone) categoria);

                if(smartphoneInserido != null && categorias.add(smartphoneInserido)) {
                    return smartphoneInserido;
                }*/

            case "Eletronica":

                /*EletronicaBDTable eletronicaBDTable = new EletronicaBDTable(context);

                Eletronica eletronicaInserida = eletronicaBDTable.insert((Eletronica) categoria);

                if(eletronicaInserida != null && categorias.add(eletronicaInserida)) {
                    return eletronicaInserida;
                }*/

            case "Roupa":

                /*RoupaBDTable roupaBDTable = new RoupaBDTable(context);

                Roupa roupaInserida = roupaBDTable.insert((Roupa) categoria);

                if(roupaInserida != null && categorias.add(roupaInserida)) {
                    return roupaInserida;
                }*/

            case "Livro":

                /*LivroBDTable livroBDTable = new LivroBDTable(context);

                Livro livroInserido = livroBDTable.insert((Livro) categoria);

                if(livroInserido != null && categorias.add(livroInserido)) {
                    return livroInserido;
                }*/

            default:

                return null;
        }
    }

    public boolean removerCategoria(Categoria categoria)
    {
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

    public boolean editarCategoria(Categoria categoria)
    {
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
