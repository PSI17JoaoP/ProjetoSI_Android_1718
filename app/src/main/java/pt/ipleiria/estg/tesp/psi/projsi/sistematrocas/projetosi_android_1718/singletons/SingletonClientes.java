package pt.ipleiria.estg.tesp.psi.projsi.sistematrocas.projetosi_android_1718.singletons;

import java.util.ArrayList;
import java.util.List;

import pt.ipleiria.estg.tesp.psi.projsi.sistematrocas.projetosi_android_1718.modelos.Cliente;

/**
 * Created by leona on 24/11/2017.
 */

public class SingletonClientes {
    private static SingletonClientes INSTANCE = null;
    private List<Cliente> clientes;

    public static SingletonClientes getInstance() {
        if (INSTANCE == null)
            INSTANCE = new SingletonClientes();

        return INSTANCE;
    }

    private SingletonClientes() {
        clientes = new ArrayList<>();
    }

    public List<Cliente> getClientes() {
        return clientes;
    }

    public void setClientes(List<Cliente> clientes) {
        this.clientes = clientes;
    }

    public boolean addCliente(Cliente cliente)
    {
        return clientes.add(cliente);
    }

    public boolean removeCliente(Cliente cliente)
    {
        return clientes.remove(cliente);
    }

    public boolean editCliente(Cliente oldCliente, Cliente newCliente)
    {
        Cliente updatedCliente = clientes.set(clientes.indexOf(oldCliente), newCliente);

        return clientes.contains(updatedCliente);
    }

    public Cliente searchClientesID(Long id)
    {
        return clientes.get(id.intValue());
    }
}
