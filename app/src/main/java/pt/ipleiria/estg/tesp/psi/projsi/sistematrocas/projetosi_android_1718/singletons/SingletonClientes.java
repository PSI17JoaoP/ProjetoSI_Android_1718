package pt.ipleiria.estg.tesp.psi.projsi.sistematrocas.projetosi_android_1718.singletons;

import android.content.Context;

import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONObject;

import java.util.ArrayList;

import pt.ipleiria.estg.tesp.psi.projsi.sistematrocas.projetosi_android_1718.helpers.ClienteBDTable;
import pt.ipleiria.estg.tesp.psi.projsi.sistematrocas.projetosi_android_1718.listeners.ClientesListener;
import pt.ipleiria.estg.tesp.psi.projsi.sistematrocas.projetosi_android_1718.modelos.Cliente;
import pt.ipleiria.estg.tesp.psi.projsi.sistematrocas.projetosi_android_1718.parsers.ClientesParser;

/**
 * Created by leona on 24/11/2017.
 */

public class SingletonClientes {
    private static SingletonClientes INSTANCE = null;
    private ArrayList<Cliente> clientes;
    private ClienteBDTable bdTable;

    private ClientesListener clientesListener;

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

    public void getClienteAPI(Long userId, final Context context) {

        if(SingletonAPIManager.getInstance(context).ligadoInternet(context)) {

            JsonObjectRequest getCliente = SingletonAPIManager.getInstance(context).pedirAPI("clientes/" + userId, context, new SingletonAPIManager.APIJsonResposta() {
                @Override
                public void Sucesso(JSONObject resultado) {

                    Cliente cliente = ClientesParser.paraObjeto(resultado, context);

                    if(clientesListener != null)
                        clientesListener.OnSucessoObterCliente(cliente);
                }

                @Override
                public void Erro(VolleyError erro) {
                    if(clientesListener != null)
                        clientesListener.OnErroObterCliente("Não foi possivél obter os dados do cliente.", erro);
                }
            });

            SingletonAPIManager.getInstance(context).getRequestQueue(context).add(getCliente);
        }
    }

    public boolean adicionarClienteLocal(Cliente cliente) {

        Cliente clienteInserido = bdTable.insert(cliente);

        return clienteInserido != null && clientes.add(clienteInserido);
    }

    public boolean removerClienteLocal(Cliente cliente) {
        return bdTable.delete(cliente.getIdUser()) && clientes.remove(cliente);
    }

    public boolean editarClienteLocal(Cliente cliente) {

        if(bdTable.update(cliente)) {
            Cliente novoCliente = clientes.set(cliente.getIdUser().intValue(), cliente);

            return clientes.contains(novoCliente);
        } else {
            return false;
        }
    }

    public Cliente pesquisarClientesID(Long id) {
        return clientes.get(id.intValue());
    }

    public void setClientesListener(ClientesListener clientesListener) {
        this.clientesListener = clientesListener;
    }
}
