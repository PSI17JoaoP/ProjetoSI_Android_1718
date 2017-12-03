package pt.ipleiria.estg.tesp.psi.projsi.sistematrocas.projetosi_android_1718;

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import pt.ipleiria.estg.tesp.psi.projsi.sistematrocas.projetosi_android_1718.modelos.APIManager;

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

            APIManager gestorAPI = new APIManager(getApplicationContext());
            gestorAPI.pedirObjetoAPI("/clientes/pin/" + pinString, new APIManager.APIJsonResposta() {

                @Override
                public void onSucess(JSONObject result) {
                    try {
                        JSONArray user = result.getJSONArray("User");

                        if(user != null) {
                            String username = user.getString(0);
                            String email = user.getString(1);

                            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                            intent.putExtra(DADOS_USERNAME, username);
                            intent.putExtra(DADOS_EMAIL, email);
                            startActivity(intent);

                        } else {
                            textViewMensagem.setText(R.string.mensagem_pin_incorreto);
                            textViewMensagem.setTextColor(Color.RED);
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            });
        }
    }
}
