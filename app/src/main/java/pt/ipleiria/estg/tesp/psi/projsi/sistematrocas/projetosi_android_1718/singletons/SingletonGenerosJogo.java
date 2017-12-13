package pt.ipleiria.estg.tesp.psi.projsi.sistematrocas.projetosi_android_1718.singletons;

import android.content.Context;

import java.util.ArrayList;
import java.util.List;

import pt.ipleiria.estg.tesp.psi.projsi.sistematrocas.projetosi_android_1718.helpers.GeneroJogoBDTable;
import pt.ipleiria.estg.tesp.psi.projsi.sistematrocas.projetosi_android_1718.modelos.GeneroJogo;

/**
 * Created by leona on 24/11/2017.
 */

public class SingletonGenerosJogo {
    private static SingletonGenerosJogo INSTANCE = null;
    private ArrayList<GeneroJogo> generosJogos;
    private GeneroJogoBDTable bdTable;

    public static SingletonGenerosJogo getInstance(Context context) {
        if (INSTANCE == null)
            INSTANCE = new SingletonGenerosJogo(context);

        return INSTANCE;
    }

    private SingletonGenerosJogo(Context context) {
        generosJogos = new ArrayList<>();
        bdTable = new GeneroJogoBDTable(context);
        generosJogos = bdTable.select();
    }

    public ArrayList<GeneroJogo> getGeneroJogos() {
        return generosJogos;
    }

    public void setGeneroJogos(ArrayList<GeneroJogo> generosJogos) {
        this.generosJogos = generosJogos;
    }

    public boolean adicionarGenero(GeneroJogo genero)
    {
        GeneroJogo generoInserido = bdTable.insert(genero);

        return generoInserido != null && generosJogos.add(generoInserido);
    }

    public boolean removerGenero(GeneroJogo genero)
    {
        return bdTable.delete(genero.getId()) && generosJogos.remove(genero);
    }

    public boolean editarGenero(GeneroJogo generoJogo)
    {
        if(bdTable.update(generoJogo)) {
            GeneroJogo novoGenero = generosJogos.set(generoJogo.getId().intValue(), generoJogo);

            return generosJogos.contains(novoGenero);
        } else {
            return false;
        }
    }

    public GeneroJogo pesquisarGeneroJogosID(Long id)
    {
        return generosJogos.get(id.intValue());
    }
}
