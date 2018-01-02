package pt.ipleiria.estg.tesp.psi.projsi.sistematrocas.projetosi_android_1718;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import pt.ipleiria.estg.tesp.psi.projsi.sistematrocas.projetosi_android_1718.modelos.Anuncio;
import pt.ipleiria.estg.tesp.psi.projsi.sistematrocas.projetosi_android_1718.singletons.SingletonAnuncios;

public class DetalhesAnuncioActivity extends NavDrawerActivity {

    private SharedPreferences preferences;
    public static final String ID_ANUNCIO = "ID";

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        View.inflate(this, R.layout.activity_detalhes_anuncio, (ViewGroup) findViewById(R.id.app_content));

        preferences = getSharedPreferences("APP_SETTINGS", Context.MODE_PRIVATE);

        TextView titulo = findViewById(R.id.txtDetalhesTitulo);
        TextView dataCriacao = findViewById(R.id.txtDetalhesDataCriacao);
        TextView dataConclusao = findViewById(R.id.txtDetalhesDataConclusao);

        Intent intent = getIntent();
        Long idAnuncioTemp = intent.getLongExtra(ID_ANUNCIO, 4L);

        Anuncio anuncio = SingletonAnuncios.getInstance(this).pesquisarAnuncioID(idAnuncioTemp);


        titulo.setText(anuncio.getTitulo());
        dataCriacao.setText(anuncio.getDataCriacao());
        if (anuncio.getDataConclusao().isEmpty())
        {
            dataConclusao.setText("ATIVO");
        }else
        {
            dataConclusao.setText(anuncio.getDataConclusao());
        }

    }
}
