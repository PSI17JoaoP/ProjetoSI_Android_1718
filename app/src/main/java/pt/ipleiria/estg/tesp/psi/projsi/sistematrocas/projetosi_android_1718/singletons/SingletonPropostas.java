package pt.ipleiria.estg.tesp.psi.projsi.sistematrocas.projetosi_android_1718.singletons;

import android.content.Context;
import android.content.SharedPreferences;

import com.android.volley.Request;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import pt.ipleiria.estg.tesp.psi.projsi.sistematrocas.projetosi_android_1718.helpers.PropostaBDTable;
import pt.ipleiria.estg.tesp.psi.projsi.sistematrocas.projetosi_android_1718.listeners.CategoriasListener;
import pt.ipleiria.estg.tesp.psi.projsi.sistematrocas.projetosi_android_1718.listeners.PropostasListener;
import pt.ipleiria.estg.tesp.psi.projsi.sistematrocas.projetosi_android_1718.modelos.Brinquedo;
import pt.ipleiria.estg.tesp.psi.projsi.sistematrocas.projetosi_android_1718.modelos.Computador;
import pt.ipleiria.estg.tesp.psi.projsi.sistematrocas.projetosi_android_1718.modelos.Eletronica;
import pt.ipleiria.estg.tesp.psi.projsi.sistematrocas.projetosi_android_1718.modelos.Jogo;
import pt.ipleiria.estg.tesp.psi.projsi.sistematrocas.projetosi_android_1718.modelos.Livro;
import pt.ipleiria.estg.tesp.psi.projsi.sistematrocas.projetosi_android_1718.modelos.Proposta;
import pt.ipleiria.estg.tesp.psi.projsi.sistematrocas.projetosi_android_1718.modelos.Roupa;
import pt.ipleiria.estg.tesp.psi.projsi.sistematrocas.projetosi_android_1718.modelos.Smartphone;
import pt.ipleiria.estg.tesp.psi.projsi.sistematrocas.projetosi_android_1718.parsers.BrinquedosParser;
import pt.ipleiria.estg.tesp.psi.projsi.sistematrocas.projetosi_android_1718.parsers.ComputadoresParser;
import pt.ipleiria.estg.tesp.psi.projsi.sistematrocas.projetosi_android_1718.parsers.EletronicaParser;
import pt.ipleiria.estg.tesp.psi.projsi.sistematrocas.projetosi_android_1718.parsers.JogosParser;
import pt.ipleiria.estg.tesp.psi.projsi.sistematrocas.projetosi_android_1718.parsers.LivrosParser;
import pt.ipleiria.estg.tesp.psi.projsi.sistematrocas.projetosi_android_1718.parsers.PropostasParser;
import pt.ipleiria.estg.tesp.psi.projsi.sistematrocas.projetosi_android_1718.parsers.RoupasParser;
import pt.ipleiria.estg.tesp.psi.projsi.sistematrocas.projetosi_android_1718.parsers.SmartphonesParser;

/**
 * Created by leona on 21/11/2017.
 */

public class SingletonPropostas {
    private static SingletonPropostas INSTANCE = null;
    private ArrayList<Proposta> propostas;
    private PropostaBDTable bdTable;

    private PropostasListener propostasListener;
    private CategoriasListener categoriasListener;

    public static SingletonPropostas getInstance(Context context) {
        if (INSTANCE == null)
            INSTANCE = new SingletonPropostas(context);

        return INSTANCE;
    }

    private SingletonPropostas(Context context) {
        propostas = new ArrayList<>();
        bdTable = new PropostaBDTable(context);
        propostas = bdTable.select();
        getPropostasAnunciosUser(context);
    }

    public ArrayList<Proposta> getPropostas() {
        return propostas;
    }

    public void getPropostasAnunciosUser(final Context context) {

        if(propostas.isEmpty()) {

            SharedPreferences preferences = context.getSharedPreferences("APP_SETTINGS", Context.MODE_PRIVATE);
            String username = preferences.getString("username", "");

            if (SingletonAPIManager.getInstance(context).ligadoInternet(context)) {

                JsonObjectRequest propostasAPI = SingletonAPIManager.getInstance(context).pedirAPI("anuncios/propostas/" + username, context, new SingletonAPIManager.APIJsonResposta() {

                    @Override
                    public void Sucesso(JSONObject resultado) {
                        try {
                            JSONArray propostasJson = resultado.getJSONArray("Propostas");

                            if (propostasJson.length() > 0) {

                                if(propostas.isEmpty()) {
                                    propostas = PropostasParser.paraObjeto(propostasJson, context);
                                    adicionarPropostasLocal(propostas);
                                }

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

    //------------------------------------------------------------
    //LOCAL A PARTIR DAQUI

    public void adicionarPropostasLocal(ArrayList<Proposta> propostas) {
        for(Proposta proposta : propostas) {
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

    public boolean editarPropostaLocal(Proposta proposta, int posicao) {
        if(bdTable.update(proposta)) {
            propostas.set(posicao, proposta);
        }

        return propostas.contains(proposta);
    }

    public Proposta pesquisarPropostaID(Long id) {
        for (Proposta proposta : propostas) {
            if (proposta.getId().toString().equals(id.toString()))
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

    public void setCategoriasListener(CategoriasListener categoriasListener) {
        this.categoriasListener = categoriasListener;
    }
}
