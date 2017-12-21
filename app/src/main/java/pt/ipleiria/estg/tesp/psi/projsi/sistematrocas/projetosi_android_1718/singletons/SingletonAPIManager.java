package pt.ipleiria.estg.tesp.psi.projsi.sistematrocas.projetosi_android_1718.singletons;

import android.content.Context;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Base64;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.HttpHeaderParser;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

public class SingletonAPIManager {

    private static SingletonAPIManager INSTANCE = null;
    private RequestQueue requestQueue;
    private static Context context;

    SharedPreferences preferences;

    private static final String baseURL = "http://10.0.2.2:8888/";
    private static String auth = null;

    public static synchronized SingletonAPIManager getInstance(Context contexto) {

        if (INSTANCE == null) {
            INSTANCE = new SingletonAPIManager(contexto);
        }

        return INSTANCE;
    }

    private SingletonAPIManager(Context contexto) {
        context = contexto;
        requestQueue = getRequestQueue();

        preferences = contexto.getSharedPreferences("APP_SETTINGS", Context.MODE_PRIVATE);
    }

    /**
     * Devolve a queue onde os pedidos são executados/guardados em cache
     * @return RequestQueue
     */
    public RequestQueue getRequestQueue() {

        if (requestQueue == null) {
            requestQueue = Volley.newRequestQueue(context.getApplicationContext());
        }

        return requestQueue;
    }

    public void setAuth(String pin)
    {
        byte[] authBytes = (pin+":").getBytes();
        auth = Base64.encodeToString(authBytes, Base64.DEFAULT);
    }

    /**
     * Verifica o estado de ligação à internet
     * @return true/false
     */
    public boolean ligadoInternet()
    {
        ConnectivityManager connManager = (ConnectivityManager)context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo internet = null;

        if (connManager != null) {
            internet = connManager.getActiveNetworkInfo();
        }

        return (internet != null && internet.isConnectedOrConnecting());
    }

    public interface APIJsonResposta {
        void Sucesso(JSONObject resultado);
        void Erro(VolleyError erro);
    }

    public interface APIJsonArrayResposta {
        void Sucesso(JSONArray resultados);
        void Erro(VolleyError erro);
    }

    public interface APIStringResposta {
        void Sucesso(String resposta);
        void Erro(VolleyError erro);
    }


    /**
     * Recebe o URI para fazer o pedido GET a 1 objeto e implementa a função 'usar'
     * para trabalhar a resposta
     * @param url O url do pedido
     * @param usar Interface de Resposta
     * @return mixed
     */
    public JsonObjectRequest pedirAPI(String url, final APIJsonResposta usar)
    {
        if (ligadoInternet())
        {
            JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
                Request.Method.GET, baseURL+url,null,
                new Response.Listener<JSONObject>()
                {
                    @Override
                    public void onResponse(JSONObject response) {
                        usar.Sucesso(response);
                    }
                },
                new Response.ErrorListener()
                {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        usar.Erro(error);
                    }
                }
            ) {
                @Override
                public Map<String, String> getHeaders() throws AuthFailureError {
                    Map<String, String> params = new HashMap<>();
                    params.put("Accept", "application/json");
                    params.put("Authorization", "Basic " + auth);
                    return params;
                }
            };

            return jsonObjectRequest;
        }

        return null;
    }

    /**
     * Recebe o URI para fazer o pedido GET a vários objetos e implementa a função 'usar'
     * para trabalhar a resposta
     * @param url O url do pedido
     * @param usar Interface de Resposta
     * @return mixed
     */
    public JsonArrayRequest pedirVariosAPI(String url, final APIJsonArrayResposta usar)
    {
        if (ligadoInternet())
        {
            JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(
                Request.Method.GET, baseURL+url, null,
                new Response.Listener<JSONArray>()
                {
                    @Override
                    public void onResponse(JSONArray response) {
                        usar.Sucesso(response);
                    }
                },
                new Response.ErrorListener()
                {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        usar.Erro(error);
                    }
                }
            ){
                @Override
                public Map<String, String> getHeaders() throws AuthFailureError {
                    Map<String, String> params = new HashMap<>();
                    params.put("Accept", "application/json");
                    params.put("Authorization", "Basic " + auth);
                    return params;
                }
            };

            return jsonArrayRequest;
        }

        return null;
    }

    /**
     * Recebe o URI, o tipo de pedido (exemplo: Request.Methdd.POST), o objeto Json a enviar e
     * implementa a função 'usar' para verificar a resposta
     * @param url O url do pedido
     * @param tipoPedido Tipo de pedido
     * @param jsonBody O corpo da mensagem
     * @param usar Interface de Resposta
     * @return mixed
     */
    public StringRequest enviarAPI(String url, Integer tipoPedido, JSONObject jsonBody, final APIStringResposta usar)
    {
        if (ligadoInternet())
        {
            final String requestBody = jsonBody.toString();

            StringRequest stringRequest = new StringRequest(tipoPedido, baseURL+url, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    usar.Sucesso(response);
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    usar.Erro(error);
                }
            }) {
                @Override
                public String getBodyContentType() {
                    return "application/json; charset=utf-8";
                }

                @Override
                public byte[] getBody() throws AuthFailureError {
                    try {
                        return requestBody == null ? null : requestBody.getBytes("utf-8");
                    } catch (UnsupportedEncodingException uee) {
                        VolleyLog.wtf("Unsupported Encoding while trying to get the bytes of %s using %s", requestBody, "utf-8");
                        return null;
                    }
                }

                @Override
                protected Response<String> parseNetworkResponse(NetworkResponse response) {
                    String responseString = "";
                    if (response != null) {
                        responseString = String.valueOf(response.statusCode);
                        // can get more details such as response.headers
                    }
                    return Response.success(responseString, HttpHeaderParser.parseCacheHeaders(response));
                }
            };

            return stringRequest;
        }

        return null;
    }
}
