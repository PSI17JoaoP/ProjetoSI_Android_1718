package pt.ipleiria.estg.tesp.psi.projsi.sistematrocas.projetosi_android_1718.parsers;

import android.content.Context;

import com.google.gson.FieldNamingPolicy;
import com.google.gson.GsonBuilder;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import pt.ipleiria.estg.tesp.psi.projsi.sistematrocas.projetosi_android_1718.modelos.Cliente;

/**
 * Created by leona on 22/12/2017.
 */

public class ClientesParser {

    private static GsonBuilder gson = new GsonBuilder().setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES);

    public static Cliente paraObjeto(JSONObject object, Context context)
    {
        return gson.create().fromJson(object.toString(), Cliente.class);
    }

    public static ArrayList<Cliente> paraObjeto(JSONArray objects, Context context)
    {
        ArrayList<Cliente> clientes = new ArrayList<>();

        for (int i = 0; i < objects.length(); i++)
        {
            try {
                JSONObject object = objects.getJSONObject(i);

                Cliente cliente = gson.create().fromJson(object.toString(), Cliente.class);
                clientes.add(cliente);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        return clientes;
    }

    public static String paraJson(Cliente cliente)
    {
        return gson.create().toJson(cliente);
    }

}
