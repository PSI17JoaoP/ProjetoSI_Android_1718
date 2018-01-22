package pt.ipleiria.estg.tesp.psi.projsi.sistematrocas.projetosi_android_1718;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.HashMap;

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

    private static final int NUMERO_COLUNAS = 2;

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
                    dataConclusao.setVisibility(View.GONE);
                    dataConclusao.setEnabled(false);
                    textViewConcluido.setVisibility(View.GONE);
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

        RecyclerView recyclerView;
        GridLayoutManager gridLayoutManager;

        HashMap<String, String> categoriaAtributos = new HashMap<>();

        switch (tipo)
        {
            case "Brinquedos":

                Brinquedo brinquedo = (Brinquedo) categoria;

                categoriaAtributos.put("Editora", brinquedo.getEditora());
                categoriaAtributos.put("Faixa Etária", brinquedo.getFaixaEtaria().toString());
                categoriaAtributos.put("Descrição", brinquedo.getDescricao());

                break;

            case "Jogos":

                Jogo jogo = (Jogo) categoria;

                categoriaAtributos.put("Editora", jogo.getEditora());
                categoriaAtributos.put("Faixa Etária", jogo.getFaixaEtaria().toString());
                categoriaAtributos.put("Descrição", jogo.getDescricao());

                GeneroJogo generoJogo = SingletonGenerosJogo.getInstance(this).pesquisarGeneroJogosID(jogo.getIdGenero());

                if(generoJogo != null) {
                    categoriaAtributos.put("Género", jogo.getDescricao());
                }

                categoriaAtributos.put("Produtora", jogo.getProdutora());

                break;

            case "Eletrónica":

                Eletronica eletronica = (Eletronica) categoria;

                categoriaAtributos.put("Descrição", eletronica.getDescricao());
                categoriaAtributos.put("Marca", eletronica.getMarca());

                break;

            case "Computadores":

                Computador computador = (Computador) categoria;

                categoriaAtributos.put("Descrição", computador.getDescricao());
                categoriaAtributos.put("Marca", computador.getMarca());
                categoriaAtributos.put("Processador", computador.getProcessador());
                categoriaAtributos.put("Memória RAM", computador.getRam());
                categoriaAtributos.put("Disco Rígido", computador.getHdd());
                categoriaAtributos.put("Placa Gráfica", computador.getGpu());
                categoriaAtributos.put("Sistema Operativo", computador.getOs());

                break;

            case "Smartphones":

                Smartphone smartphone = (Smartphone) categoria;

                categoriaAtributos.put("Descrição", smartphone.getDescricao());
                categoriaAtributos.put("Marca", smartphone.getMarca());
                categoriaAtributos.put("Processador", smartphone.getProcessador());
                categoriaAtributos.put("Memória RAM", smartphone.getRam());
                categoriaAtributos.put("Disco Rígido", smartphone.getHdd());
                categoriaAtributos.put("Sistema Operativo", smartphone.getOs());
                categoriaAtributos.put("Tamanho", smartphone.getTamanho());

                break;

            case "Livros":

                Livro livro = (Livro) categoria;

                categoriaAtributos.put("Título", livro.getTitulo());
                categoriaAtributos.put("Editora", livro.getEditora());
                categoriaAtributos.put("Autor", livro.getAutor());
                categoriaAtributos.put("ISBN", livro.getIsbn().toString());

                break;

            case "Roupa":

                Roupa roupa = (Roupa) categoria;

                categoriaAtributos.put("Marca", roupa.getMarca());
                categoriaAtributos.put("Tamanho", roupa.getTamanho());

                TipoRoupa tipoRoupa = SingletonTiposRoupa.getInstance(this).pesquisarTipoRoupaID(roupa.getId());

                if(tipoRoupa != null) {
                    categoriaAtributos.put("Tipo", tipoRoupa.getNome());
                }

                break;
        }

        CategoriasAdapter categoriasAdapter = new CategoriasAdapter(categoriaAtributos);

        if (tipoBem.equals("Oferecer")) {

            TextView textViewNomeTroco = findViewById(R.id.textViewDetalhesAnuncioTrocoCategoria);
            textViewNomeTroco.setText(categoria.getNome());

            recyclerView = findViewById(R.id.fragmentDetalhesAnuncioTroco);
            gridLayoutManager = new GridLayoutManager(this, NUMERO_COLUNAS, GridLayoutManager.VERTICAL, false);

            recyclerView.setLayoutManager(gridLayoutManager);
            recyclerView.setAdapter(categoriasAdapter);
        }

        else if(tipoBem.equals("Receber")) {

            TextView textViewNomePor = findViewById(R.id.textViewDetalhesAnuncioPorCategoria);
            textViewNomePor.setText(categoria.getNome());

            recyclerView = findViewById(R.id.fragmentDetalhesAnuncioPor);
            gridLayoutManager = new GridLayoutManager(this, NUMERO_COLUNAS, GridLayoutManager.VERTICAL, false);

            recyclerView.setLayoutManager(gridLayoutManager);
            recyclerView.setAdapter(categoriasAdapter);
        }
    }

    @Override
    public void onErroObterCategoria(String message, Exception ex) {

    }
}
