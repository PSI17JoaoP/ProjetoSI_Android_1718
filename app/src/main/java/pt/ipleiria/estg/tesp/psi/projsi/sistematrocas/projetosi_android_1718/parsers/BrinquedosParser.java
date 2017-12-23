package pt.ipleiria.estg.tesp.psi.projsi.sistematrocas.projetosi_android_1718.parsers;

import android.content.Context;

import com.google.gson.FieldNamingPolicy;
import com.google.gson.GsonBuilder;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import pt.ipleiria.estg.tesp.psi.projsi.sistematrocas.projetosi_android_1718.modelos.Brinquedo;

/**
 * Created by leona on 22/12/2017.
 */

public class BrinquedosParser {

    private static GsonBuilder gson = new GsonBuilder().setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES);

    public static Brinquedo paraObjeto(JSONObject object, Context context)
    {
        return gson.create().fromJson(object.toString(), Brinquedo.class);
    }

    public static ArrayList<Brinquedo> paraObjeto(JSONArray objects, Context context)
    {
        ArrayList<Brinquedo> brinquedos = new ArrayList<>();

        for (int i = 0; i < objects.length(); i++)
        {
            try {
                JSONObject object = objects.getJSONObject(i);

                Brinquedo brinquedo = gson.create().fromJson(object.toString(), Brinquedo.class);
                brinquedos.add(brinquedo);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        return brinquedos;
    }

    public static String paraJson(Brinquedo brinquedo)
    {
        return gson.create().toJson(brinquedo);
    }

}
