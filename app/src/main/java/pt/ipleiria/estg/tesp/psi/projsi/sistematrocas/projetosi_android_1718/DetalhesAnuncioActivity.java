package pt.ipleiria.estg.tesp.psi.projsi.sistematrocas.projetosi_android_1718;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

import pt.ipleiria.estg.tesp.psi.projsi.sistematrocas.projetosi_android_1718.adaptadores.CategoriasAdapter;
import pt.ipleiria.estg.tesp.psi.projsi.sistematrocas.projetosi_android_1718.listeners.CategoriasListener;
import pt.ipleiria.estg.tesp.psi.projsi.sistematrocas.projetosi_android_1718.modelos.Anuncio;
import pt.ipleiria.estg.tesp.psi.projsi.sistematrocas.projetosi_android_1718.modelos.Brinquedo;
import pt.ipleiria.estg.tesp.psi.projsi.sistematrocas.projetosi_android_1718.modelos.Categoria;
import pt.ipleiria.estg.tesp.psi.projsi.sistematrocas.projetosi_android_1718.modelos.Computador;
import pt.ipleiria.estg.tesp.psi.projsi.sistematrocas.projetosi_android_1718.modelos.Eletronica;
import pt.ipleiria.estg.tesp.psi.projsi.sistematrocas.projetosi_android_1718.modelos.GeneroJogo;
import pt.ipleiria.estg.tesp.psi.projsi.sistematrocas.projetosi_android_1718.modelos.Jogo;
import pt.ipleiria.estg.tesp.psi.projsi.sistematrocas.projetosi_android_1718.modelos.Livro;
import pt.ipleiria.estg.tesp.psi.projsi.sistematrocas.projetosi_android_1718.modelos.Roupa;
import pt.ipleiria.estg.tesp.psi.projsi.sistematrocas.projetosi_android_1718.modelos.Smartphone;
import pt.ipleiria.estg.tesp.psi.projsi.sistematrocas.projetosi_android_1718.modelos.TipoRoupa;
import pt.ipleiria.estg.tesp.psi.projsi.sistematrocas.projetosi_android_1718.singletons.SingletonAnuncios;
import pt.ipleiria.estg.tesp.psi.projsi.sistematrocas.projetosi_android_1718.singletons.SingletonGenerosJogo;
import pt.ipleiria.estg.tesp.psi.projsi.sistematrocas.projetosi_android_1718.singletons.SingletonTiposRoupa;

public class DetalhesAnuncioActivity extends NavDrawerActivity implements CategoriasListener{

    private Anuncio anuncio;

    public static final String ID_ANUNCIO = "ID";
    public static final String TITULO_ANUNCIO = "TITULO";

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        View.inflate(this, R.layout.activity_detalhes_anuncio, (ViewGroup) findViewById(R.id.app_content));

        TextView titulo = findViewById(R.id.textViewDetalhesAnuncioTituloAnuncio);
        TextView dataCriacao = findViewById(R.id.textViewDetalhesAnuncioDataCriacao);

        TextView textViewConcluido = findViewById(R.id.textViewDetalhesAnuncioConcluido);
        TextView dataConclusao = findViewById(R.id.textViewDetalhesAnuncioDataConclusao);

        if(getIntent().hasExtra(ID_ANUNCIO)) {

            Long anuncioId = getIntent().getLongExtra(ID_ANUNCIO, 0L);
            anuncio = SingletonAnuncios.getInstance(getApplicationContext()).pesquisarAnuncioID(anuncioId);

            if (anuncio != null) {

                SharedPreferences preferences = getSharedPreferences("APP_SETTINGS", Context.MODE_PRIVATE);
                Long userId = preferences.getLong("id", 0);

                titulo.setText(anuncio.getTitulo());
                dataCriacao.setText(anuncio.getDataCriacao());

                if (anuncio.getDataConclusao() == null) {
                    dataConclusao.setEnabled(false);
                    textViewConcluido.setEnabled(false);
                } else {
                    dataConclusao.setText(anuncio.getDataConclusao());
                }

                FloatingActionButton fabDetalhesAnuncio = findViewById(R.id.fabDetalhesAnuncio);

                if (anuncio.getIdUser().toString().equals(userId.toString())) {
                    fabDetalhesAnuncio.setBackgroundResource(android.R.color.holo_red_dark);
                    fabDetalhesAnuncio.setImageResource(android.R.drawable.ic_menu_delete);

                    fabDetalhesAnuncio.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            //TODO: Botão de Eliminar Anúncio.
                        }
                    });
                }

                else {
                    fabDetalhesAnuncio.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            Intent intent = new Intent(getApplicationContext(), EnviarPropostaActivity.class);
                            intent.putExtra(ID_ANUNCIO, anuncio.getId());
                            intent.putExtra(TITULO_ANUNCIO, anuncio.getTitulo());
                            startActivity(intent);
                        }
                    });
                }

                SingletonAnuncios.getInstance(getApplicationContext()).setCategoriasListener(this);
                SingletonAnuncios.getInstance(getApplicationContext()).getCategoriasAnuncio(anuncioId, "Oferecer", getApplicationContext());

                if(anuncio.getCatReceber() != null) {
                    SingletonAnuncios.getInstance(getApplicationContext()).getCategoriasAnuncio(anuncioId, "Receber", getApplicationContext());
                }
            }
        }
    }

    @Override
    public void onObterCategoria(Categoria categoria, String tipo, String tipoBem) {

        ArrayList<String> listLabels = new ArrayList<>();
        ArrayList<String> listValores = new ArrayList<>();

        switch (tipo)
        {
            case "Brinquedos":

                Brinquedo brinquedo = (Brinquedo) categoria;

                listLabels.add("Nome");
                listLabels.add("Editora");
                listLabels.add("Faixa Etária");
                listLabels.add("Descrição");

                listValores.add(brinquedo.getNome());
                listValores.add(brinquedo.getEditora());
                listValores.add(brinquedo.getFaixaEtaria().toString());
                listValores.add(brinquedo.getDescricao());

                break;

            case "Jogos":

                Jogo jogo = (Jogo) categoria;

                listLabels.add("Nome");
                listLabels.add("Editora");
                listLabels.add("Faixa Etária");
                listLabels.add("Descrição");
                listLabels.add("Género");
                listLabels.add("Produtora");

                listValores.add(jogo.getNome());
                listValores.add(jogo.getEditora());
                listValores.add(jogo.getFaixaEtaria().toString());
                listValores.add(jogo.getDescricao());

                GeneroJogo generoJogo = SingletonGenerosJogo.getInstance(this).pesquisarGeneroJogosID(jogo.getIdGenero());

                if(generoJogo != null) {
                    listValores.add(generoJogo.getNome());
                }

                listValores.add(jogo.getProdutora());

                break;

            case "Eletrónica":

                Eletronica eletronica = (Eletronica) categoria;

                listLabels.add("Nome");
                listLabels.add("Descrição");
                listLabels.add("Marca");

                listValores.add(eletronica.getNome());
                listValores.add(eletronica.getDescricao());
                listValores.add(eletronica.getMarca());

                break;

            case "Computadores":

                Computador computador = (Computador) categoria;

                listLabels.add("Nome");
                listLabels.add("Descrição");
                listLabels.add("Marca");
                listLabels.add("Processador");
                listLabels.add("Memória Ram");
                listLabels.add("Disco Rígido");
                listLabels.add("Placa Gráfica");
                listLabels.add("Sistema Operativo");
                listLabels.add("Portátil?");

                listValores.add(computador.getNome());
                listValores.add(computador.getDescricao());
                listValores.add(computador.getMarca());
                listValores.add(computador.getProcessador());
                listValores.add(computador.getRam());
                listValores.add(computador.getHdd());
                listValores.add(computador.getGpu());
                listValores.add(computador.getOs());

                if (computador.getPortatil() == 0) {
                    listValores.add("Não");
                } else {
                    listValores.add("Sim");
                }

                break;

            case "Smartphones":

                Smartphone smartphone = (Smartphone) categoria;

                listLabels.add("Nome");
                listLabels.add("Descrição");
                listLabels.add("Marca");
                listLabels.add("Processador");
                listLabels.add("Memória Ram");
                listLabels.add("Disco Rígido");
                listLabels.add("Sistema Operativo");
                listLabels.add("Tamanho");

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

                listLabels.add("Nome");
                listLabels.add("Título");
                listLabels.add("Editora");
                listLabels.add("Autor");
                listLabels.add("ISBN");

                listValores.add(livro.getNome());
                listValores.add(livro.getTitulo());
                listValores.add(livro.getEditora());
                listValores.add(livro.getAutor());
                listValores.add(livro.getIsbn().toString());

                break;

            case "Roupa":

                Roupa roupa = (Roupa) categoria;

                listLabels.add("Nome");
                listLabels.add("Marca");
                listLabels.add("Tamanho");
                listLabels.add("Tipo");

                listValores.add(roupa.getNome());
                listValores.add(roupa.getMarca());
                listValores.add(roupa.getTamanho());
                listValores.add(roupa.getTamanho());

                TipoRoupa tipoRoupa = SingletonTiposRoupa.getInstance(this).pesquisarTipoRoupaID(roupa.getId());

                if(tipoRoupa != null) {
                    listValores.add(tipoRoupa.getNome());
                }

                break;
        }

        //CategoriasAdapter adapter = new CategoriasAdapter(listLabels, listValores, this);

        if (tipoBem.equals("Oferecer")) {

        }

        else if(tipoBem.equals("Receber")) {

        }
    }
}
