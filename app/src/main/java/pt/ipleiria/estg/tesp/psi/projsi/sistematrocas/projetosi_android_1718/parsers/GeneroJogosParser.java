package pt.ipleiria.estg.tesp.psi.projsi.sistematrocas.projetosi_android_1718.parsers;

import android.content.Context;

import com.google.gson.FieldNamingPolicy;
import com.google.gson.GsonBuilder;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import pt.ipleiria.estg.tesp.psi.projsi.sistematrocas.projetosi_android_1718.modelos.GeneroJogo;

/**
 * Created by leona on 22/12/2017.
 */

public class GeneroJogosParser {

    private static GsonBuilder gson = new GsonBuilder().setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES);

    public static GeneroJogo paraObjeto(JSONObject object, Context context)
    {
        return gson.create().fromJson(object.toString(), GeneroJogo.class);
    }

    public static ArrayList<GeneroJogo> paraObjeto(JSONArray objects, Context context)
    {
        ArrayList<GeneroJogo> generoJogos = new ArrayList<>();

        for (int i = 0; i < objects.length(); i++)
        {
            try {
                JSONObject object = objects.getJSONObject(i);

                GeneroJogo generoJogo = gson.create().fromJson(object.toString(), GeneroJogo.class);
                generoJogos.add(generoJogo);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        return generoJogos;
    }

    public static String paraJson(GeneroJogo generoJogo)
    {
        return gson.create().toJson(generoJogo);
    }

}
