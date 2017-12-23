package pt.ipleiria.estg.tesp.psi.projsi.sistematrocas.projetosi_android_1718.parsers;

import android.content.Context;

import com.google.gson.FieldNamingPolicy;
import com.google.gson.GsonBuilder;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import pt.ipleiria.estg.tesp.psi.projsi.sistematrocas.projetosi_android_1718.modelos.CategoriaPreferida;

/**
 * Created by leona on 22/12/2017.
 */

public class CategoriasPreferidasParser {

    private static GsonBuilder gson = new GsonBuilder().setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES);

    public static CategoriaPreferida paraObjeto(JSONObject object, Context context)
    {
        return gson.create().fromJson(object.toString(), CategoriaPreferida.class);
    }

    public static ArrayList<CategoriaPreferida> paraObjeto(JSONArray objects, Context context)
    {
        ArrayList<CategoriaPreferida> categorias = new ArrayList<>();

        for (int i = 0; i < objects.length(); i++)
        {
            try {
                JSONObject object = objects.getJSONObject(i);

                CategoriaPreferida categoria = gson.create().fromJson(object.toString(), CategoriaPreferida.class);
                categorias.add(categoria);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        return categorias;
    }

    public static String paraJson(CategoriaPreferida categoria)
    {
        return gson.create().toJson(categoria);
    }

}
