package pt.ipleiria.estg.tesp.psi.projsi.sistematrocas.projetosi_android_1718;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONException;
import org.json.JSONObject;

import pt.ipleiria.estg.tesp.psi.projsi.sistematrocas.projetosi_android_1718.singletons.SingletonAPIManager;

public class LoginActivity extends AppCompatActivity {

    private EditText editTextLoginPin;
    private TextView textViewMensagem;

    private SharedPreferences.Editor prefEditor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        SharedPreferences preferences = getSharedPreferences("APP_SETTINGS", Context.MODE_PRIVATE);

        String pin = preferences.getString("pin", "");

        if (!pin.isEmpty()) {
            SingletonAPIManager.getInstance(getApplicationContext()).setAuth(pin);

            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
            startActivity(intent);

            finish();
        }

        else {
            setContentView(R.layout.activity_login);

            textViewMensagem = findViewById(R.id.textViewLoginMensagem);
            editTextLoginPin = findViewById(R.id.editTextLoginPin);

            prefEditor = preferences.edit();
            prefEditor.apply();
        }
    }

    public void onClickLogin(View view) {
        final String pinString = editTextLoginPin.getText().toString().trim();

        if(pinString.isEmpty()) {
            textViewMensagem.setText(R.string.mensagem_pin_vazio);
            textViewMensagem.setTextColor(Color.RED);
        } else {

            if(SingletonAPIManager.getInstance(this).ligadoInternet(this)) {

                SingletonAPIManager.getInstance(getApplicationContext()).setAuth(pinString);

                JsonObjectRequest user = SingletonAPIManager.getInstance(getApplicationContext()).pedirAPI("clientes/pin/" + pinString, getApplicationContext(), new SingletonAPIManager.APIJsonResposta() {
                    @Override
                    public void Sucesso(JSONObject resultado) {
                        try {

                            JSONObject userResult = resultado.getJSONObject("User");

                            Long id = userResult.getLong("ID");
                            String username = userResult.getString("Username");
                            String email = userResult.getString("Email");

                            prefEditor.putLong("id", id);
                            prefEditor.putString("username", username);
                            prefEditor.putString("email", email);
                            prefEditor.putString("pin", pinString);
                            prefEditor.apply();

                            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                            startActivity(intent);

                        } catch (JSONException e) {
                            textViewMensagem.setText(R.string.mensagem_erro_login);
                            textViewMensagem.setTextColor(Color.RED);
                        }
                    }

                    @Override
                    public void Erro(VolleyError erro) {
                        textViewMensagem.setText(R.string.mensagem_pin_incorreto);
                        textViewMensagem.setTextColor(Color.RED);
                    }
                });

                SingletonAPIManager.getInstance(this).getRequestQueue(this).add(user);
            } else {
                textViewMensagem.setText(R.string.mensagem_ligacao_internet);
                textViewMensagem.setTextColor(Color.RED);
            }
        }
    }
}
