package pt.ipleiria.estg.tesp.psi.projsi.sistematrocas.projetosi_android_1718.singletons;

import android.content.Context;

import java.util.ArrayList;
import java.util.List;

import pt.ipleiria.estg.tesp.psi.projsi.sistematrocas.projetosi_android_1718.helpers.TipoRoupaBDTable;
import pt.ipleiria.estg.tesp.psi.projsi.sistematrocas.projetosi_android_1718.modelos.TipoRoupa;

/**
 * Created by leona on 24/11/2017.
 */

public class SingletonTiposRoupa {
    private static SingletonTiposRoupa INSTANCE = null;
    private ArrayList<TipoRoupa> tiposRoupa;
    private TipoRoupaBDTable bdTable;

    public static SingletonTiposRoupa getInstance(Context context) {
        if (INSTANCE == null)
            INSTANCE = new SingletonTiposRoupa(context);

        return INSTANCE;
    }

    private SingletonTiposRoupa(Context context) {
        tiposRoupa = new ArrayList<>();
        bdTable = new TipoRoupaBDTable(context);
        tiposRoupa = bdTable.select();
    }

    public ArrayList<TipoRoupa> getTiposRoupa() {
        return tiposRoupa;
    }

    public void setTiposRoupa(ArrayList<TipoRoupa> tiposRoupa) {
        this.tiposRoupa = tiposRoupa;
    }

    public boolean adicionarTipo(TipoRoupa tipo)
    {
        TipoRoupa tipoRoupaInserido = bdTable.insert(tipo);

        return tipoRoupaInserido != null && tiposRoupa.add(tipoRoupaInserido);
    }

    public boolean removerTipo(TipoRoupa tipo)
    {
        return bdTable.delete(tipo.getId()) && tiposRoupa.remove(tipo);
    }

    public boolean editarTipo(TipoRoupa tipo)
    {
        if(bdTable.update(tipo)) {
            TipoRoupa novoTipo = tiposRoupa.set(tipo.getId().intValue(), tipo);

            return tiposRoupa.contains(novoTipo);
        } else {
            return false;
        }
    }

    public TipoRoupa pesquisarTipoRoupaID(Long id)
    {
        return tiposRoupa.get(id.intValue());
    }
}
