package pt.ipleiria.estg.tesp.psi.projsi.sistematrocas.projetosi_android_1718.listeners;

import android.content.Context;

import java.util.ArrayList;

import pt.ipleiria.estg.tesp.psi.projsi.sistematrocas.projetosi_android_1718.modelos.TipoRoupa;

/**
 * Created by JAPorelo on 31-12-2017.
 * Project ProjetoSI_Android_1718 - pt.ipleiria.estg.tesp.psi.projsi.sistematrocas.projetosi_android_1718.listeners
 */

public interface TiposRoupaListener {
    void onErrorTiposRoupaAPI(String message, Exception ex);
    void onRefreshTiposRoupa(ArrayList<TipoRoupa> tiposRoupa, Context context);
    void onUpdateTiposRoupa(TipoRoupa tipoRoupa, int acao);
}
