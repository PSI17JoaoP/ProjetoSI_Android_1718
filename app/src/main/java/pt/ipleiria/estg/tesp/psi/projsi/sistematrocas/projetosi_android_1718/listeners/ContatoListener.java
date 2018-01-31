package pt.ipleiria.estg.tesp.psi.projsi.sistematrocas.projetosi_android_1718.listeners;

/**
 * Created by leona on 31/01/2018.
 */

public interface ContatoListener {
    void onSuccess(String nome, String telefone, String regiao);
    void OnError(String message, Exception ex);
}
