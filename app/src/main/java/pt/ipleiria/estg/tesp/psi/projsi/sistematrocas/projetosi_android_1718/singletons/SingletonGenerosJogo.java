package pt.ipleiria.estg.tesp.psi.projsi.sistematrocas.projetosi_android_1718.singletons;

import java.util.ArrayList;
import java.util.List;

import pt.ipleiria.estg.tesp.psi.projsi.sistematrocas.projetosi_android_1718.modelos.GeneroJogo;

/**
 * Created by leona on 24/11/2017.
 */

public class SingletonGenerosJogo {
    private static SingletonGenerosJogo INSTANCE = null;
    private List<GeneroJogo> generoJogos;

    public static SingletonGenerosJogo getInstance() {
        if (INSTANCE == null)
            INSTANCE = new SingletonGenerosJogo();

        return INSTANCE;
    }

    private SingletonGenerosJogo() {
        generoJogos = new ArrayList<>();

    }

    public List<GeneroJogo> getGeneroJogos() {
        return generoJogos;
    }

    public void setGeneroJogos(List<GeneroJogo> generoJogos) {
        this.generoJogos = generoJogos;
    }

    public boolean addGenero(GeneroJogo genero)
    {
        return generoJogos.add(genero);
    }

    public boolean removeGenero(GeneroJogo genero)
    {
        return generoJogos.remove(genero);
    }

    public boolean editGenero(GeneroJogo oldGenero, GeneroJogo newGenero)
    {
        GeneroJogo updatedGenero = generoJogos.set(generoJogos.indexOf(oldGenero), newGenero);

        if (generoJogos.contains(updatedGenero))
            return true;
        else
            return false;
    }

    public GeneroJogo searchGeneroJogosID(Integer id)
    {
        return generoJogos.get(id);
    }
}
