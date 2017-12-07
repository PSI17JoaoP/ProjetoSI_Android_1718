package pt.ipleiria.estg.tesp.psi.projsi.sistematrocas.projetosi_android_1718;

import android.content.Intent;
import android.graphics.Color;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import pt.ipleiria.estg.tesp.psi.projsi.sistematrocas.projetosi_android_1718.singletons.SingletonAPIManager;

public class LoginActivity extends AppCompatActivity {

    private EditText editTextLoginPin;
    private TextView textViewMensagem;

    public static final String DADOS_USERNAME = "username";
    public static final String DADOS_EMAIL = "email";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        textViewMensagem = findViewById(R.id.textViewLoginMensagem);
        editTextLoginPin = findViewById(R.id.editTextLoginPin);
    }

    public void onClickLogin(View view) {
        String pinString = editTextLoginPin.getText().toString().trim();

        if(pinString.isEmpty()) {
            textViewMensagem.setText(R.string.mensagem_pin_vazio);
            textViewMensagem.setTextColor(Color.RED);
        } else {
            String url = "http://10.0.2.2:8888/clientes/pin/" + pinString;

            JsonObjectRequest user = SingletonAPIManager.getInstance(this).pedirAPI(url, new SingletonAPIManager.APIJsonResposta() {
                @Override
                public void Sucesso(JSONObject resultado) {
                    try {

                        JSONObject userResult = resultado.getJSONObject("User");

                        String username = userResult.getString("Username");
                        String email = userResult.getString("Email");

                        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                        intent.putExtra(DADOS_USERNAME, username);
                        intent.putExtra(DADOS_EMAIL, email);
                        startActivity(intent);

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }

                @Override
                public void Erro(VolleyError erro) {
                    //TODO: Switch para vários tipos de erros.
                    if(erro.networkResponse.statusCode == 404) {
                        textViewMensagem.setText(R.string.mensagem_pin_incorreto);
                        textViewMensagem.setTextColor(Color.RED);
                    } else {
                        Snackbar snackbar = Snackbar.make(findViewById(R.id.loginCoordinatorLayout), getString(R.string.mensagem_login_erro) + " (" + erro.networkResponse.statusCode + ").", Snackbar.LENGTH_LONG);
                        snackbar.show();
                    }
                }
            });

            SingletonAPIManager.getInstance(this).getRequestQueue().add(user);
        }
    }
}
