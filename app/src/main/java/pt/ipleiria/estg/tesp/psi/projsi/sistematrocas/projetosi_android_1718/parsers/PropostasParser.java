package pt.ipleiria.estg.tesp.psi.projsi.sistematrocas.projetosi_android_1718.parsers;

import android.content.Context;

import com.google.gson.FieldNamingPolicy;
import com.google.gson.GsonBuilder;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import pt.ipleiria.estg.tesp.psi.projsi.sistematrocas.projetosi_android_1718.modelos.Proposta;

/**
 * Created by leona on 22/12/2017.
 */

public class PropostasParser {
    private static GsonBuilder gson = new GsonBuilder().setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES);

    public static Proposta paraObjeto(JSONObject object, Context context)
    {
        return gson.create().fromJson(object.toString(), Proposta.class);
    }

    public static ArrayList<Proposta> paraObjeto(JSONArray objects, Context context)
    {
        ArrayList<Proposta> propostas = new ArrayList<>();

        for (int i = 0; i < objects.length(); i++)
        {
            try {
                JSONObject object = objects.getJSONObject(i);

                Proposta proposta = gson.create().fromJson(object.toString(), Proposta.class);
                propostas.add(proposta);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        return propostas;
    }

    public static String paraJson(Proposta proposta)
    {
        return gson.create().toJson(proposta);
    }

}
