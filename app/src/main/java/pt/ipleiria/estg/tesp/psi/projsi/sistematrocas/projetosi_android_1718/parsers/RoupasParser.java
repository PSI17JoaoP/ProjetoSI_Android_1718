package pt.ipleiria.estg.tesp.psi.projsi.sistematrocas.projetosi_android_1718.parsers;

import android.content.Context;

import com.google.gson.FieldNamingPolicy;
import com.google.gson.GsonBuilder;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import pt.ipleiria.estg.tesp.psi.projsi.sistematrocas.projetosi_android_1718.modelos.Roupa;

/**
 * Created by leona on 22/12/2017.
 */

public class RoupasParser {

    private static GsonBuilder gson = new GsonBuilder().setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES);

    public static Roupa paraObjeto(JSONObject object, Context context)
    {
        return gson.create().fromJson(object.toString(), Roupa.class);
    }

    public static ArrayList<Roupa> paraObjeto(JSONArray objects, Context context)
    {
        ArrayList<Roupa> roupas = new ArrayList<>();

        for (int i = 0; i < objects.length(); i++)
        {
            try {
                JSONObject object = objects.getJSONObject(i);

                Roupa roupa = gson.create().fromJson(object.toString(), Roupa.class);
                roupas.add(roupa);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        return roupas;
    }

    public static String paraJson(Roupa roupa)
    {
        return gson.create().toJson(roupa);
    }

}
