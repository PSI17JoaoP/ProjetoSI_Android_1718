package pt.ipleiria.estg.tesp.psi.projsi.sistematrocas.projetosi_android_1718.singletons;

import android.content.Context;

import java.util.ArrayList;

import pt.ipleiria.estg.tesp.psi.projsi.sistematrocas.projetosi_android_1718.helpers.BrinquedoBDTable;
import pt.ipleiria.estg.tesp.psi.projsi.sistematrocas.projetosi_android_1718.modelos.Brinquedo;

/**
 * Created by JAPorelo on 27-12-2017.
 * Project ProjetoSI_Android_1718 - pt.ipleiria.estg.tesp.psi.projsi.sistematrocas.projetosi_android_1718.singletons
 */

public class SingletonBrinquedos {
    private static SingletonBrinquedos INSTANCE = null;
    private ArrayList<Brinquedo> brinquedos;
    private BrinquedoBDTable bdTable;

    public static SingletonBrinquedos getInstance(Context context) {
        if (INSTANCE == null)
            INSTANCE = new SingletonBrinquedos(context);

        return INSTANCE;
    }

    private SingletonBrinquedos(Context context) {
        brinquedos = new ArrayList<>();
        bdTable = new BrinquedoBDTable(context);
        brinquedos = bdTable.select();
    }

    public ArrayList<Brinquedo> getBrinquedos() {
        return brinquedos;
    }

    public void setBrinquedos(ArrayList<Brinquedo> brinquedos) {
        this.brinquedos = brinquedos;
    }

    public boolean adicionarBrinquedo(Brinquedo brinquedo)
    {
        Brinquedo brinquedoInserido = bdTable.insert(brinquedo);

        return brinquedoInserido != null && brinquedos.add(brinquedoInserido);
    }

    public boolean removerBrinquedo(Brinquedo brinquedo)
    {
        return bdTable.delete(brinquedo.getId()) && brinquedos.remove(brinquedo);
    }

    public boolean editarBrinquedo(Brinquedo brinquedo)
    {
        if(bdTable.update(brinquedo)) {
            Brinquedo novoBrinquedo = brinquedos.set(brinquedo.getId().intValue(), brinquedo);

            return brinquedos.contains(novoBrinquedo);
        } else {
            return false;
        }
    }

    public Brinquedo pesquisarBrinquedoID(Long id)
    {
        return brinquedos.get(id.intValue());
    }
}
