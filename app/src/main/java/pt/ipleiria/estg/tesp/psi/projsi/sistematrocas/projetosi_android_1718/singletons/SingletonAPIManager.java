package pt.ipleiria.estg.tesp.psi.projsi.sistematrocas.projetosi_android_1718.singletons;

import android.content.Context;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

public class SingletonAPIManager {

    private static SingletonAPIManager INSTANCE = null;
    private RequestQueue requestQueue;
    private static Context context;

    public static synchronized SingletonAPIManager getInstance(Context contexto) {

        if (INSTANCE == null) {
            INSTANCE = new SingletonAPIManager(contexto);
        }

        return INSTANCE;
    }

    private SingletonAPIManager(Context contexto) {
        context = contexto;
        requestQueue = getRequestQueue();
    }

    public RequestQueue getRequestQueue() {

        if (requestQueue == null) {
            requestQueue = Volley.newRequestQueue(context.getApplicationContext());
        }

        return requestQueue;
    }
}
