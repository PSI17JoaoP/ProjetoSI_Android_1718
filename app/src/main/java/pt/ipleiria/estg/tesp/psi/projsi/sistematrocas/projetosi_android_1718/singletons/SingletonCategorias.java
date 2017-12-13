package pt.ipleiria.estg.tesp.psi.projsi.sistematrocas.projetosi_android_1718.singletons;

import java.util.ArrayList;
import java.util.List;

import pt.ipleiria.estg.tesp.psi.projsi.sistematrocas.projetosi_android_1718.modelos.Categoria;

/**
 * Created by leona on 23/11/2017.
 */

public class SingletonCategorias {
    private static SingletonCategorias INSTANCE = null;
    private List<Categoria> categorias;

    public static SingletonCategorias getInstance() {
        if (INSTANCE == null)
            INSTANCE = new SingletonCategorias();

        return INSTANCE;
    }

    private SingletonCategorias() {
        categorias = new ArrayList<>();
    }

    public List<Categoria> getCategorias() {
        return categorias;
    }

    public void setCategorias(List<Categoria> categorias) {
        this.categorias = categorias;
    }

    public boolean addCategoria(Categoria categoria)
    {
        return categorias.add(categoria);
    }

    public boolean removeCategoria(Categoria categoria)
    {
        return categorias.remove(categoria);
    }

    public boolean editCategoria(Categoria oldCategoria, Categoria newCategoria)
    {
        Categoria updatedCategoria = categorias.set(categorias.indexOf(oldCategoria), newCategoria);

        return categorias.contains(updatedCategoria);
    }

    public Categoria searchCategoriaID(Long id)
    {
        return categorias.get(id.intValue());
    }

}
