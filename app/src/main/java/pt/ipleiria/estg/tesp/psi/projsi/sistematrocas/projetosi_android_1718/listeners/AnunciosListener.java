package pt.ipleiria.estg.tesp.psi.projsi.sistematrocas.projetosi_android_1718.listeners;

import java.util.ArrayList;

import pt.ipleiria.estg.tesp.psi.projsi.sistematrocas.projetosi_android_1718.modelos.Anuncio;

/**
 * Created by leona on 22/12/2017.
 */

public interface AnunciosListener {
    void onSuccessAnunciosAPI(Anuncio anuncio);
    void onErrorAnunciosAPI(String message, Exception ex);
    void onRefreshAnuncios(ArrayList<Anuncio> anuncios);
}
