package pt.ipleiria.estg.tesp.psi.projsi.sistematrocas.projetosi_android_1718.singletons;

import android.content.Context;
import android.widget.Toast;

import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;

import org.json.JSONArray;

import java.util.ArrayList;
import java.util.List;

import pt.ipleiria.estg.tesp.psi.projsi.sistematrocas.projetosi_android_1718.helpers.GeneroJogoBDTable;
import pt.ipleiria.estg.tesp.psi.projsi.sistematrocas.projetosi_android_1718.listeners.GenerosJogosListener;
import pt.ipleiria.estg.tesp.psi.projsi.sistematrocas.projetosi_android_1718.modelos.GeneroJogo;
import pt.ipleiria.estg.tesp.psi.projsi.sistematrocas.projetosi_android_1718.parsers.GeneroJogosParser;

/**
 * Created by leona on 24/11/2017.
 */

public class SingletonGenerosJogo {
    private Context context;
    private static SingletonGenerosJogo INSTANCE = null;
    private ArrayList<GeneroJogo> generosJogos;
    private GeneroJogoBDTable bdTable;

    private GenerosJogosListener generosJogosListener;

    public static SingletonGenerosJogo getInstance(Context context) {
        if (INSTANCE == null)
            INSTANCE = new SingletonGenerosJogo(context);

        return INSTANCE;
    }

    private SingletonGenerosJogo(Context context) {
        this.context = context;
        generosJogos = new ArrayList<>();
        bdTable = new GeneroJogoBDTable(context);
        generosJogos = bdTable.select();
        getGenerosJogosAPI();
    }

    private void getGenerosJogosAPI() {

        if(generosJogos.isEmpty()) {

            if (SingletonAPIManager.getInstance(context).ligadoInternet()) {

                JsonArrayRequest generosAPI = SingletonAPIManager.getInstance(context).pedirVariosAPI("categorias/generos", new SingletonAPIManager.APIJsonArrayResposta() {
                    @Override
                    public void Sucesso(JSONArray resultados) {
                        generosJogos = GeneroJogosParser.paraObjeto(resultados, context);
                        adicionarTiposRoupaBD(generosJogos);

                        if (generosJogosListener != null)
                            generosJogosListener.onRefreshGenerosJogos(generosJogos, context);
                    }

                    @Override
                    public void Erro(VolleyError erro) {
                        if (generosJogosListener != null)
                            generosJogosListener.onErrorGenerosJogosAPI("Ocorreu um erro na sincronização dos géneros de jogo.", erro);
                    }
                });

                SingletonAPIManager.getInstance(context).getRequestQueue().add(generosAPI);
            }
        }
    }

    private void adicionarTiposRoupaBD(ArrayList<GeneroJogo> generosJogos) {
        for (GeneroJogo generoJogo : generosJogos) {
            bdTable.insert(generoJogo);
        }
    }

    public ArrayList<GeneroJogo> getGeneroJogos() {
        return generosJogos;
    }

    public void setGeneroJogos(ArrayList<GeneroJogo> generosJogos) {
        this.generosJogos = generosJogos;
    }

    public boolean adicionarGenero(GeneroJogo genero) {
        GeneroJogo generoInserido = bdTable.insert(genero);

        return generoInserido != null && generosJogos.add(generoInserido);
    }

    public boolean removerGenero(GeneroJogo genero) {
        return bdTable.delete(genero.getId()) && generosJogos.remove(genero);
    }

    public boolean editarGenero(GeneroJogo generoJogo) {
        if(bdTable.update(generoJogo)) {
            GeneroJogo novoGenero = generosJogos.set(generoJogo.getId().intValue(), generoJogo);

            return generosJogos.contains(novoGenero);
        } else {
            return false;
        }
    }

    public GeneroJogo pesquisarGeneroJogosID(Long id)
    {
        for (GeneroJogo genero : generosJogos)
        {
            if (genero.getId().toString().equals(id.toString()))
            {
                return genero;
            }
        }

        return null;
    }

    public void setGenerosJogosListener(GenerosJogosListener generosJogosListener) {
        this.generosJogosListener = generosJogosListener;
    }
}
