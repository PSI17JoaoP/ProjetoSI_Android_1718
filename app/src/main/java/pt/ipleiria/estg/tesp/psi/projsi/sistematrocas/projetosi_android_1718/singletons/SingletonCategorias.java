package pt.ipleiria.estg.tesp.psi.projsi.sistematrocas.projetosi_android_1718.singletons;

import android.content.Context;

import java.util.ArrayList;
import java.util.List;

import pt.ipleiria.estg.tesp.psi.projsi.sistematrocas.projetosi_android_1718.helpers.CategoriaBDTable;
import pt.ipleiria.estg.tesp.psi.projsi.sistematrocas.projetosi_android_1718.modelos.Categoria;

/**
 * Created by leona on 23/11/2017.
 */

public class SingletonCategorias {
    private static SingletonCategorias INSTANCE = null;
    private ArrayList<Categoria> categorias;
    private CategoriaBDTable bdTable;

    public static SingletonCategorias getInstance(Context context) {
        if (INSTANCE == null)
            INSTANCE = new SingletonCategorias(context);

        return INSTANCE;
    }

    private SingletonCategorias(Context context) {
        categorias = new ArrayList<>();
        bdTable = new CategoriaBDTable(context);
        categorias = bdTable.select();
    }

    public ArrayList<Categoria> getCategorias() {
        return categorias;
    }

    public void setCategorias(ArrayList<Categoria> categorias) {
        this.categorias = categorias;
    }

    public boolean adicionarCategoria(Categoria categoria)
    {
        Categoria categoriaInserida = bdTable.insert(categoria);

        return categoriaInserida != null && categorias.add(categoriaInserida);
    }

    public boolean removerCategoria(Categoria categoria)
    {
        return bdTable.delete(categoria.getId()) && categorias.remove(categoria);
    }

    public boolean editarCategoria(Categoria categoria)
    {
        if(bdTable.update(categoria)) {
            Categoria novaCategoria = categorias.set(categoria.getId().intValue(), categoria);

            return categorias.contains(novaCategoria);
        } else {
            return false;
        }
    }

    public Categoria pesquisarCategoriaID(Long id)
    {
        return categorias.get(id.intValue());
    }

}
