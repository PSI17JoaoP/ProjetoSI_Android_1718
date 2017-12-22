package pt.ipleiria.estg.tesp.psi.projsi.sistematrocas.projetosi_android_1718.parsers;

import android.content.Context;

import com.google.gson.FieldNamingPolicy;
import com.google.gson.GsonBuilder;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import pt.ipleiria.estg.tesp.psi.projsi.sistematrocas.projetosi_android_1718.modelos.Computador;

/**
 * Created by leona on 22/12/2017.
 */

public class ComputadoresParser {

    private static GsonBuilder gson = new GsonBuilder().setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES);

    public static Computador paraObjeto(JSONObject object, Context context)
    {
        return gson.create().fromJson(object.toString(), Computador.class);
    }

    public static ArrayList<Computador> paraObjeto(JSONArray objects, Context context)
    {
        ArrayList<Computador> computadores = new ArrayList<>();

        for (int i = 0; i < objects.length(); i++)
        {
            try {
                JSONObject object = objects.getJSONObject(i);

                Computador computador = gson.create().fromJson(object.toString(), Computador.class);
                computadores.add(computador);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        return computadores;
    }

    public static String paraJson(Computador computador)
    {
        return gson.create().toJson(computador);
    }

}
