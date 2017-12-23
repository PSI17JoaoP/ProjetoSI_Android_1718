package pt.ipleiria.estg.tesp.psi.projsi.sistematrocas.projetosi_android_1718.parsers;

import android.content.Context;

import com.google.gson.FieldNamingPolicy;
import com.google.gson.GsonBuilder;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import pt.ipleiria.estg.tesp.psi.projsi.sistematrocas.projetosi_android_1718.modelos.Smartphone;

/**
 * Created by leona on 22/12/2017.
 */

public class SmartphonesParser {
    private static GsonBuilder gson = new GsonBuilder().setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES);

    public static Smartphone paraObjeto(JSONObject object, Context context)
    {
        return gson.create().fromJson(object.toString(), Smartphone.class);
    }

    public static ArrayList<Smartphone> paraObjeto(JSONArray objects, Context context)
    {
        ArrayList<Smartphone> smartphones = new ArrayList<>();

        for (int i = 0; i < objects.length(); i++)
        {
            try {
                JSONObject object = objects.getJSONObject(i);

                Smartphone smartphone = gson.create().fromJson(object.toString(), Smartphone.class);
                smartphones.add(smartphone);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        return smartphones;
    }

    public static String paraJson(Smartphone smartphone)
    {
        return gson.create().toJson(smartphone);
    }

}
