package pt.ipleiria.estg.tesp.psi.projsi.sistematrocas.projetosi_android_1718.listeners;

import pt.ipleiria.estg.tesp.psi.projsi.sistematrocas.projetosi_android_1718.modelos.Cliente;

/**
 * Created by JAPorelo on 23-01-2018.
 * Project ProjetoSI_Android_1718 - pt.ipleiria.estg.tesp.psi.projsi.sistematrocas.projetosi_android_1718.listeners
 */

public interface ClientesListener {

    void OnSucessoObterCliente(Cliente cliente);
    void OnErroObterCliente(String message, Exception ex);
}
