package pt.ipleiria.estg.tesp.psi.projsi.sistematrocas.projetosi_android_1718.parsers;

import android.content.Context;

import com.google.gson.FieldNamingPolicy;
import com.google.gson.GsonBuilder;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import pt.ipleiria.estg.tesp.psi.projsi.sistematrocas.projetosi_android_1718.modelos.Eletronica;

/**
 * Created by leona on 22/12/2017.
 */

public class EletronicaParser {

    private static GsonBuilder gson = new GsonBuilder().setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES);

    public static Eletronica paraObjeto(JSONObject object, Context context)
    {
        return gson.create().fromJson(object.toString(), Eletronica.class);
    }

    public static ArrayList<Eletronica> paraObjeto(JSONArray objects, Context context)
    {
        ArrayList<Eletronica> eletronicas = new ArrayList<>();

        for (int i = 0; i < objects.length(); i++)
        {
            try {
                JSONObject object = objects.getJSONObject(i);

                Eletronica eletronica = gson.create().fromJson(object.toString(), Eletronica.class);
                eletronicas.add(eletronica);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        return eletronicas;
    }

    public static String paraJson(Eletronica eletronica)
    {
        return gson.create().toJson(eletronica);
    }

}
