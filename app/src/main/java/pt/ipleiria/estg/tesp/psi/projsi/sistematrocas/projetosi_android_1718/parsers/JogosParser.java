package pt.ipleiria.estg.tesp.psi.projsi.sistematrocas.projetosi_android_1718.parsers;

import android.content.Context;

import com.google.gson.FieldNamingPolicy;
import com.google.gson.GsonBuilder;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import pt.ipleiria.estg.tesp.psi.projsi.sistematrocas.projetosi_android_1718.modelos.Jogo;

/**
 * Created by leona on 22/12/2017.
 */

public class JogosParser {

    private static GsonBuilder gson = new GsonBuilder().setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES);

    public static Jogo paraObjeto(JSONObject object, Context context)
    {
        return gson.create().fromJson(object.toString(), Jogo.class);
    }

    public static ArrayList<Jogo> paraObjeto(JSONArray objects, Context context)
    {
        ArrayList<Jogo> jogos = new ArrayList<>();

        for (int i = 0; i < objects.length(); i++)
        {
            try {
                JSONObject object = objects.getJSONObject(i);

                Jogo jogo = gson.create().fromJson(object.toString(), Jogo.class);
                jogos.add(jogo);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        return jogos;
    }

    public static String paraJson(Jogo jogo)
    {
        return gson.create().toJson(jogo);
    }

}
