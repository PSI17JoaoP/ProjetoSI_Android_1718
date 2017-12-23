package pt.ipleiria.estg.tesp.psi.projsi.sistematrocas.projetosi_android_1718.parsers;

import android.content.Context;

import com.google.gson.FieldNamingPolicy;
import com.google.gson.GsonBuilder;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import pt.ipleiria.estg.tesp.psi.projsi.sistematrocas.projetosi_android_1718.modelos.User;

/**
 * Created by leona on 22/12/2017.
 */

public class UsersParser {
    private static GsonBuilder gson = new GsonBuilder().setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES);

    public static User paraObjeto(JSONObject object, Context context)
    {
        return gson.create().fromJson(object.toString(), User.class);
    }

    public static ArrayList<User> paraObjeto(JSONArray objects, Context context)
    {
        ArrayList<User> users = new ArrayList<>();

        for (int i = 0; i < objects.length(); i++)
        {
            try {
                JSONObject object = objects.getJSONObject(i);

                User user = gson.create().fromJson(object.toString(), User.class);
                users.add(user);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        return users;
    }

    public static String paraJson(User user)
    {
        return gson.create().toJson(user);
    }

}
