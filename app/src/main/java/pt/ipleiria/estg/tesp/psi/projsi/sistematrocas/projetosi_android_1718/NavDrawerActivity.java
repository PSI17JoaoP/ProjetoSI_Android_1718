package pt.ipleiria.estg.tesp.psi.projsi.sistematrocas.projetosi_android_1718;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.eclipse.paho.client.mqttv3.MqttSecurityException;

public class NavDrawerActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);

        SharedPreferences preferences;
        preferences = getSharedPreferences("APP_SETTINGS", Context.MODE_PRIVATE);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = findViewById(R.id.drawer_layout);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = findViewById(R.id.nav_view);
        View h = navigationView.getHeaderView(0);

        TextView txtUsername = h.findViewById(R.id.txtNavUsername);
        TextView txtEmail = h.findViewById(R.id.txtNavEmail);

        txtUsername.setText(preferences.getString("username", "Utilizador"));
        txtEmail.setText(preferences.getString("email", "email@email.com"));

        navigationView.setNavigationItemSelectedListener(this);

        /*MqttClient myClient;
        try{
            String clientID= preferences.getString("username","Utilizador");

            myClient = new MqttClient("tcp://10.0.2.2:1883", clientID, null);

            myClient.setCallback(new MqttCallback() {

                @Override
                public void messageArrived(String topic, MqttMessage message) throws Exception {
                    String messageBody = new String(message.getPayload());
                    Toast.makeText(getApplicationContext(), messageBody, Toast.LENGTH_SHORT).show();
                }

                @Override
                public void deliveryComplete(IMqttDeliveryToken token) {

                }

                @Override
                public void connectionLost(Throwable exception) {

                }
            });

            //--------------------------------------
            MqttConnectOptions connOp = new MqttConnectOptions();
            connOp.setCleanSession(true);
            connOp.setUserName(clientID);
            //connOp.setPassword("password");
            myClient.connect(connOp);

        } catch (MqttException e) {
            e.printStackTrace();
        }*/
    }


    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        switch (id)
        {
            case R.id.nav_home:

                Intent mainIntent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(mainIntent);

                break;

            case R.id.nav_meusAnuncios:

                Intent intentAnuncios = new Intent(getApplicationContext(), MeusAnunciosActivity.class);
                startActivity(intentAnuncios);

                break;

            case R.id.nav_pesquisar:

                Intent intentPesquisa = new Intent(getApplicationContext(), PesquisaActivity.class);
                startActivity(intentPesquisa);

                break;

            case R.id.nav_historico:

                Intent intentHistorico = new Intent(getApplicationContext(), HistoricoActivity.class);
                startActivity(intentHistorico);

                break;

            case R.id.nav_logout:

                SharedPreferences preferences = getApplicationContext().getSharedPreferences("APP_SETTINGS", Context.MODE_PRIVATE);

                if(preferences.edit().clear().commit()) {
                    Intent intentLogin = new Intent(getApplicationContext(), LoginActivity.class);
                    intentLogin.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(intentLogin);
                }
        }

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
