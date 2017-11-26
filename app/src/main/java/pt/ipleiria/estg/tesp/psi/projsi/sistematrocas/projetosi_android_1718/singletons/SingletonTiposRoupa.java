package pt.ipleiria.estg.tesp.psi.projsi.sistematrocas.projetosi_android_1718.singletons;

import java.util.ArrayList;
import java.util.List;

import pt.ipleiria.estg.tesp.psi.projsi.sistematrocas.projetosi_android_1718.modelos.TipoRoupa;

/**
 * Created by leona on 24/11/2017.
 */

public class SingletonTiposRoupa {
    private static SingletonTiposRoupa INSTANCE = null;
    private List<TipoRoupa> tiposRoupa;

    public static SingletonTiposRoupa getInstance() {
        if (INSTANCE == null)
            INSTANCE = new SingletonTiposRoupa();

        return INSTANCE;
    }

    private SingletonTiposRoupa() {
        tiposRoupa = new ArrayList<>();
    }

    public List<TipoRoupa> getTiposRoupa() {
        return tiposRoupa;
    }

    public void setTiposRoupa(List<TipoRoupa> tiposRoupa) {
        this.tiposRoupa = tiposRoupa;
    }

    public boolean addTipo(TipoRoupa tipo)
    {
        return tiposRoupa.add(tipo);
    }

    public boolean removeTipo(TipoRoupa tipo)
    {
        return tiposRoupa.remove(tipo);
    }

    public boolean editTipo(TipoRoupa oldTipo, TipoRoupa newTipo)
    {
        TipoRoupa updatedTipo = tiposRoupa.set(tiposRoupa.indexOf(oldTipo), newTipo);

        if (tiposRoupa.contains(updatedTipo))
            return true;
        else
            return false;
    }

    public TipoRoupa searchTipoRoupaID(Integer id)
    {
        return tiposRoupa.get(id);
    }
}
