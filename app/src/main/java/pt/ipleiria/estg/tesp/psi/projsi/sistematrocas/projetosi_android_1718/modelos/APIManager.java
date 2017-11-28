package pt.ipleiria.estg.tesp.psi.projsi.sistematrocas.projetosi_android_1718.modelos;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.HttpHeaderParser;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONObject;

import java.io.UnsupportedEncodingException;

import pt.ipleiria.estg.tesp.psi.projsi.sistematrocas.projetosi_android_1718.singletons.SingletonAPIManager;

public class APIManager
{
    private Context context;
    private static final String baseUrl = "http://localhost:8888/";

    public APIManager(Context contexto) {
        this.context = contexto;
    }

    /**
     * Verifica se está ou não ligado à internet
     * @return boolean
     */
    private boolean ligadoInternet()
    {
        ConnectivityManager connManager = (ConnectivityManager)context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo internet = null;

        if (connManager != null) {
            internet = connManager.getActiveNetworkInfo();
        }

        return (internet != null && internet.isConnectedOrConnecting());
    }



    public interface APIJsonResposta {
        void onSucess(JSONObject result);
    }

    public interface APIStringResposta {
        void onSucess(String response);
    }


    /**
     * Pede 1 objeto ao url, depois é trabalhado através da função 'usar'
     * Exemplo url: "post/1"
     * @param url O url dos dados
     * @param usar Interface de resposta de um objeto JSON da API
     */
    public void pedirObjetoAPI(String url, final APIJsonResposta usar)
    {
        if (ligadoInternet())
        {
            JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
                    Request.Method.GET, baseUrl + url,null,
                    new Response.Listener<JSONObject>()
                    {
                        @Override
                        public void onResponse(JSONObject response) {
                            usar.onSucess(response);
                        }
                    },
                    new Response.ErrorListener()
                    {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                        }
                    }
            );

            SingletonAPIManager.getInstance(context).getRequestQueue().add(jsonObjectRequest);
        }

    }

    /**
     * Envia um objeto para a API e devolve o código de resposta. Nota: Criar o objeto json 1º
     * Exemplo url: "post/1"
     * @param url O url dos dados
     * @param tipoPedido Tipo de pedido, ou verbo HTTP (GET, POST, PUT, DELETE, ...)
     * @param jsonBody O body do json a enviar
     * @param usar Interface de resposta de uma string da API
     */
    public void enviarObjetoAPI(String url, Integer tipoPedido, JSONObject jsonBody, final APIStringResposta usar)
    {
        if (ligadoInternet())
        {
            final String requestBody = jsonBody.toString();

            StringRequest stringRequest = new StringRequest(tipoPedido, baseUrl+url,

            new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    usar.onSucess(response);
                }
            },
            new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    //Log.e("VOLLEY", error.toString());
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

            SingletonAPIManager.getInstance(context).getRequestQueue().add(stringRequest);
        }
    }
}
