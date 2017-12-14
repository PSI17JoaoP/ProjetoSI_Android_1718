package pt.ipleiria.estg.tesp.psi.projsi.sistematrocas.projetosi_android_1718.singletons;

import android.content.Context;

import java.util.ArrayList;
import java.util.List;

import pt.ipleiria.estg.tesp.psi.projsi.sistematrocas.projetosi_android_1718.helpers.ClienteBDTable;
import pt.ipleiria.estg.tesp.psi.projsi.sistematrocas.projetosi_android_1718.modelos.Cliente;

/**
 * Created by leona on 24/11/2017.
 */

public class SingletonClientes {
    private static SingletonClientes INSTANCE = null;
    private ArrayList<Cliente> clientes;
    private ClienteBDTable bdTable;

    public static SingletonClientes getInstance(Context context) {
        if (INSTANCE == null)
            INSTANCE = new SingletonClientes(context);

        return INSTANCE;
    }

    private SingletonClientes(Context context) {
        clientes = new ArrayList<>();
        bdTable = new ClienteBDTable(context);
        clientes = bdTable.select();
    }

    public ArrayList<Cliente> getClientes() {
        return clientes;
    }

    public void setClientes(ArrayList<Cliente> clientes) {
        this.clientes = clientes;
    }

    public boolean adicionarCliente(Cliente cliente)
    {
        Cliente clienteInserido = bdTable.insert(cliente);

        return clienteInserido != null && clientes.add(clienteInserido);
    }

    public boolean removerCliente(Cliente cliente)
    {
        return bdTable.delete(cliente.getIdUser()) && clientes.remove(cliente);
    }

    public boolean editarCliente(Cliente cliente)
    {
        if(bdTable.update(cliente)) {
            Cliente novoCliente = clientes.set(cliente.getIdUser().intValue(), cliente);

            return clientes.contains(novoCliente);
        } else {
            return false;
        }
    }

    public Cliente pesquisarClientesID(Long id)
    {
        return clientes.get(id.intValue());
    }
}
