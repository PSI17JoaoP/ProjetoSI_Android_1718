package pt.ipleiria.estg.tesp.psi.projsi.sistematrocas.projetosi_android_1718.parsers;

import android.content.Context;

import com.google.gson.FieldNamingPolicy;
import com.google.gson.GsonBuilder;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import pt.ipleiria.estg.tesp.psi.projsi.sistematrocas.projetosi_android_1718.modelos.Livro;

/**
 * Created by leona on 22/12/2017.
 */

public class LivrosParser {

    private static GsonBuilder gson = new GsonBuilder().setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES);

    public static Livro paraObjeto(JSONObject object, Context context)
    {
        return gson.create().fromJson(object.toString(), Livro.class);
    }

    public static ArrayList<Livro> paraObjeto(JSONArray objects, Context context)
    {
        ArrayList<Livro> livros = new ArrayList<>();

        for (int i = 0; i < objects.length(); i++)
        {
            try {
                JSONObject object = objects.getJSONObject(i);

                Livro livro = gson.create().fromJson(object.toString(), Livro.class);
                livros.add(livro);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        return livros;
    }

    public static String paraJson(Livro livro)
    {
        return gson.create().toJson(livro);
    }

}
