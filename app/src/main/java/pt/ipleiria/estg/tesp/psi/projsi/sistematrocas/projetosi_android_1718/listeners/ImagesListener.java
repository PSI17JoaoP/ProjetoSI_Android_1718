package pt.ipleiria.estg.tesp.psi.projsi.sistematrocas.projetosi_android_1718.listeners;

import java.util.ArrayList;

/**
 * Created by JAPorelo on 24-01-2018.
 * Project ProjetoSI_Android_1718 - pt.ipleiria.estg.tesp.psi.projsi.sistematrocas.projetosi_android_1718.listeners
 */

public interface ImagesListener {
    void OnSucessoImagensAPI(ArrayList<byte[]> imagensBytes);
    void OnErrorImagensAPI(String message, Exception ex);
}
