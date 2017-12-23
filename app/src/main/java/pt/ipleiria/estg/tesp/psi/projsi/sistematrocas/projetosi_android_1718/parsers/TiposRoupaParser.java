package pt.ipleiria.estg.tesp.psi.projsi.sistematrocas.projetosi_android_1718.parsers;

import android.content.Context;

import com.google.gson.FieldNamingPolicy;
import com.google.gson.GsonBuilder;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import pt.ipleiria.estg.tesp.psi.projsi.sistematrocas.projetosi_android_1718.modelos.TipoRoupa;

/**
 * Created by leona on 22/12/2017.
 */

public class TiposRoupaParser {
    private static GsonBuilder gson = new GsonBuilder().setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES);

    public static TipoRoupa paraObjeto(JSONObject object, Context context)
    {
        return gson.create().fromJson(object.toString(), TipoRoupa.class);
    }

    public static ArrayList<TipoRoupa> paraObjeto(JSONArray objects, Context context)
    {
        ArrayList<TipoRoupa> tipoRoupas = new ArrayList<>();

        for (int i = 0; i < objects.length(); i++)
        {
            try {
                JSONObject object = objects.getJSONObject(i);

                TipoRoupa tipoRoupa = gson.create().fromJson(object.toString(), TipoRoupa.class);
                tipoRoupas.add(tipoRoupa);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        return tipoRoupas;
    }

    public static String paraJson(TipoRoupa tipoRoupa)
    {
        return gson.create().toJson(tipoRoupa);
    }

}
