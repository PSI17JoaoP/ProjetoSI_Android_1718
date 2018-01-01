package pt.ipleiria.estg.tesp.psi.projsi.sistematrocas.projetosi_android_1718.singletons;

import pt.ipleiria.estg.tesp.psi.projsi.sistematrocas.projetosi_android_1718.modelos.Categoria;

/**
 * Created by JAPorelo on 31-12-2017.
 * Project ProjetoSI_Android_1718 - pt.ipleiria.estg.tesp.psi.projsi.sistematrocas.projetosi_android_1718.singletons
 */

public interface SingletonActivityAPIResponse {
    void onSuccessEnvioAPI(Categoria categoria);
    void onErrorEnvioAPI(String message, Exception ex);
}
