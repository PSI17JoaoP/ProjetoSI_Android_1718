package pt.ipleiria.estg.tesp.psi.projsi.sistematrocas.projetosi_android_1718.parsers;

import android.content.Context;

import com.google.gson.FieldNamingPolicy;
import com.google.gson.GsonBuilder;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import pt.ipleiria.estg.tesp.psi.projsi.sistematrocas.projetosi_android_1718.modelos.Anuncio;

/**
 * Created by leona on 22/12/2017.
 */

public class AnunciosParser {

    private static GsonBuilder gson = new GsonBuilder().setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES);

    public static Anuncio paraObjeto(JSONObject object, Context context)
    {
        return gson.create().fromJson(object.toString(), Anuncio.class);
    }

    public static ArrayList<Anuncio> paraObjeto(JSONArray objects, Context context)
    {
        ArrayList<Anuncio> anuncios = new ArrayList<>();

        for (int i = 0; i < objects.length(); i++)
        {
            try {
                JSONObject object = objects.getJSONObject(i);

                Anuncio anuncio = gson.create().fromJson(object.toString(), Anuncio.class);
                anuncios.add(anuncio);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        return anuncios;
    }

    public static String paraJson(Anuncio anuncio)
    {
        return gson.create().toJson(anuncio);
    }

}
