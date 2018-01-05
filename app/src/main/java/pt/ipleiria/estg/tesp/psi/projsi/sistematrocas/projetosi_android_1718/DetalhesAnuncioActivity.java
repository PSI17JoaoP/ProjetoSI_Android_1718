package pt.ipleiria.estg.tesp.psi.projsi.sistematrocas.projetosi_android_1718;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import pt.ipleiria.estg.tesp.psi.projsi.sistematrocas.projetosi_android_1718.adaptadores.CategoriasAdapter;
import pt.ipleiria.estg.tesp.psi.projsi.sistematrocas.projetosi_android_1718.listeners.AnunciosListener;
import pt.ipleiria.estg.tesp.psi.projsi.sistematrocas.projetosi_android_1718.listeners.CategoriasListener;
import pt.ipleiria.estg.tesp.psi.projsi.sistematrocas.projetosi_android_1718.modelos.Anuncio;
import pt.ipleiria.estg.tesp.psi.projsi.sistematrocas.projetosi_android_1718.modelos.Brinquedo;
import pt.ipleiria.estg.tesp.psi.projsi.sistematrocas.projetosi_android_1718.modelos.Categoria;
import pt.ipleiria.estg.tesp.psi.projsi.sistematrocas.projetosi_android_1718.modelos.Computador;
import pt.ipleiria.estg.tesp.psi.projsi.sistematrocas.projetosi_android_1718.modelos.Eletronica;
import pt.ipleiria.estg.tesp.psi.projsi.sistematrocas.projetosi_android_1718.modelos.Jogo;
import pt.ipleiria.estg.tesp.psi.projsi.sistematrocas.projetosi_android_1718.modelos.Livro;
import pt.ipleiria.estg.tesp.psi.projsi.sistematrocas.projetosi_android_1718.modelos.Roupa;
import pt.ipleiria.estg.tesp.psi.projsi.sistematrocas.projetosi_android_1718.modelos.Smartphone;
import pt.ipleiria.estg.tesp.psi.projsi.sistematrocas.projetosi_android_1718.singletons.SingletonAnuncios;
import pt.ipleiria.estg.tesp.psi.projsi.sistematrocas.projetosi_android_1718.singletons.SingletonGenerosJogo;
import pt.ipleiria.estg.tesp.psi.projsi.sistematrocas.projetosi_android_1718.singletons.SingletonTiposRoupa;

public class DetalhesAnuncioActivity extends NavDrawerActivity implements AnunciosListener,CategoriasListener{

    private ListView lvTroco;
    private ListView lvPor;
    private TextView titulo;
    private TextView dataCriacao;
    private TextView dataConclusao;

    public static final String ID_ANUNCIO = "ID";

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        View.inflate(this, R.layout.activity_detalhes_anuncio, (ViewGroup) findViewById(R.id.app_content));

        SingletonAnuncios.getInstance(this).setAnunciosListener(this);
        SingletonAnuncios.getInstance(this).setCategoriasListener(this);

        titulo = findViewById(R.id.txtDetalhesTitulo);
        dataCriacao = findViewById(R.id.txtDetalhesDataCriacao);
        dataConclusao = findViewById(R.id.txtDetalhesDataConclusao);

        lvTroco = findViewById(R.id.lvDetalhesTroco);
        lvPor = findViewById(R.id.lvDetalhesPor);

        Intent intent = getIntent();
        Long idAnuncioTemp = intent.getLongExtra(ID_ANUNCIO, 4L);

        Anuncio anuncio = SingletonAnuncios.getInstance(this).pesquisarAnuncioID(idAnuncioTemp);
/*
        titulo.setText(anuncio.getTitulo());
        dataCriacao.setText(anuncio.getDataCriacao());
        if (anuncio.getDataConclusao().isEmpty())
        {
            dataConclusao.setText("ATIVO");
        }else
        {
            dataConclusao.setText(anuncio.getDataConclusao());
        }
*/
        SingletonAnuncios.getInstance(this).getCategoriasAnuncio(idAnuncioTemp, "Oferecer");
        SingletonAnuncios.getInstance(this).getCategoriasAnuncio(idAnuncioTemp, "Receber");
    }

    @Override
    public void onObterCategoria(Categoria categoria, String tipo, String lista)
    {
        ArrayList<String> listLabels = new ArrayList<>();
        ArrayList<String> listValores = new ArrayList<>();

        switch (tipo)
        {
            case "Brinquedos":
                Brinquedo brinquedo = (Brinquedo) categoria;
                //labels
                listLabels.add("Nome");
                listLabels.add("Editora");
                listLabels.add("Faixa Etária");
                listLabels.add("Descrição");

                //Valores
                listValores.add(brinquedo.getNome());
                listValores.add(brinquedo.getEditora());
                listValores.add(brinquedo.getFaixaEtaria().toString());
                listValores.add(brinquedo.getDescricao());
                break;
            case "Jogos":
                Jogo jogo = (Jogo) categoria;

                //labels
                listLabels.add("Nome");
                listLabels.add("Editora");
                listLabels.add("Faixa Etária");
                listLabels.add("Descrição");
                listLabels.add("Género");
                listLabels.add("Produtora");
                //Valores
                listValores.add(jogo.getNome());
                listValores.add(jogo.getEditora());
                listValores.add(jogo.getFaixaEtaria().toString());
                listValores.add(jogo.getDescricao());
                listValores.add(SingletonGenerosJogo.getInstance(this).pesquisarGeneroJogosID(jogo.getIdGenero()).getNome());
                listValores.add(jogo.getProdutora());
                break;
            case "Eletrónica":
                Eletronica eletronica = (Eletronica) categoria;

                //labels
                listLabels.add("Nome");
                listLabels.add("Descrição");
                listLabels.add("Marca");
                //Valores
                listValores.add(eletronica.getNome());
                listValores.add(eletronica.getDescricao());
                listValores.add(eletronica.getMarca());
                break;
            case "Computadores":
                Computador computador = (Computador) categoria;

                //labels
                listLabels.add("Nome");
                listLabels.add("Descrição");
                listLabels.add("Marca");
                listLabels.add("Processador");
                listLabels.add("Memória Ram");
                listLabels.add("Disco Rígido");
                listLabels.add("Placa Gráfica");
                listLabels.add("Sistema Operativo");
                listLabels.add("Portátil?");
                //Valores
                listValores.add(computador.getNome());
                listValores.add(computador.getDescricao());
                listValores.add(computador.getMarca());
                listValores.add(computador.getProcessador());
                listValores.add(computador.getRam());
                listValores.add(computador.getHdd());
                listValores.add(computador.getGpu());
                listValores.add(computador.getOs());

                if (computador.getPortatil() == 0)
                {
                    listValores.add("Não");
                }else{
                    listValores.add("Sim");
                }
                break;
            case "Smartphones":
                Smartphone smartphone = (Smartphone) categoria;

                //labels
                listLabels.add("Nome");
                listLabels.add("Descrição");
                listLabels.add("Marca");
                listLabels.add("Processador");
                listLabels.add("Memória Ram");
                listLabels.add("Disco Rígido");
                listLabels.add("Sistema Operativo");
                listLabels.add("Tamanho");
                //Valores
                listValores.add(smartphone.getNome());
                listValores.add(smartphone.getDescricao());
                listValores.add(smartphone.getMarca());
                listValores.add(smartphone.getProcessador());
                listValores.add(smartphone.getRam());
                listValores.add(smartphone.getHdd());
                listValores.add(smartphone.getOs());
                listValores.add(smartphone.getTamanho());
                break;
            case "Livros":
                Livro livro = (Livro) categoria;

                //labels
                listLabels.add("Nome");
                listLabels.add("Título");
                listLabels.add("Editora");
                listLabels.add("Autor");
                listLabels.add("ISBN");
                //Valores
                listValores.add(livro.getNome());
                listValores.add(livro.getTitulo());
                listValores.add(livro.getEditora());
                listValores.add(livro.getAutor());
                listValores.add(livro.getIsbn().toString());
                break;
            case "Roupa":
                Roupa roupa = (Roupa) categoria;

                //labels
                listLabels.add("Nome");
                listLabels.add("Marca");
                listLabels.add("Tamanho");
                listLabels.add("Tipo");
                //Valores
                listValores.add(roupa.getNome());
                listValores.add(roupa.getMarca());
                listValores.add(roupa.getTamanho());
                listValores.add(roupa.getTamanho());
                listValores.add(SingletonTiposRoupa.getInstance(this).pesquisarTipoRoupaID(roupa.getId()).getNome());
                break;
        }

        CategoriasAdapter adapter = new CategoriasAdapter(listLabels, listValores, this);

        if (lista.equals("Oferecer"))
        {
            lvTroco.setAdapter(adapter);
        }else{
            lvPor.setAdapter(adapter);
        }
    }

    @Override
    public void onSuccessAnunciosAPI(Anuncio anuncio)
    {
        if (anuncio != null)
        {
            titulo.setText(anuncio.getTitulo());
            dataCriacao.setText(anuncio.getDataCriacao());
            if (anuncio.getDataConclusao() == null) {
                dataConclusao.setText("ATIVO");
            } else {
                dataConclusao.setText(anuncio.getDataConclusao());
            }
        }

    }

    @Override
    public void onErrorAnunciosAPI(String message, Exception ex) {

    }

    @Override
    public void onRefreshAnuncios(ArrayList<Anuncio> anuncios) {

    }
}
