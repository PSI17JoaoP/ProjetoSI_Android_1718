package pt.ipleiria.estg.tesp.psi.projsi.sistematrocas.projetosi_android_1718;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.HashMap;

import pt.ipleiria.estg.tesp.psi.projsi.sistematrocas.projetosi_android_1718.adaptadores.CategoriasAdapter;
import pt.ipleiria.estg.tesp.psi.projsi.sistematrocas.projetosi_android_1718.listeners.AnunciosListener;
import pt.ipleiria.estg.tesp.psi.projsi.sistematrocas.projetosi_android_1718.listeners.CategoriasListener;
import pt.ipleiria.estg.tesp.psi.projsi.sistematrocas.projetosi_android_1718.listeners.ClientesListener;
import pt.ipleiria.estg.tesp.psi.projsi.sistematrocas.projetosi_android_1718.listeners.ImagesListener;
import pt.ipleiria.estg.tesp.psi.projsi.sistematrocas.projetosi_android_1718.listeners.PropostasListener;
import pt.ipleiria.estg.tesp.psi.projsi.sistematrocas.projetosi_android_1718.modelos.Anuncio;
import pt.ipleiria.estg.tesp.psi.projsi.sistematrocas.projetosi_android_1718.modelos.Brinquedo;
import pt.ipleiria.estg.tesp.psi.projsi.sistematrocas.projetosi_android_1718.modelos.Categoria;
import pt.ipleiria.estg.tesp.psi.projsi.sistematrocas.projetosi_android_1718.modelos.Cliente;
import pt.ipleiria.estg.tesp.psi.projsi.sistematrocas.projetosi_android_1718.modelos.Computador;
import pt.ipleiria.estg.tesp.psi.projsi.sistematrocas.projetosi_android_1718.modelos.Eletronica;
import pt.ipleiria.estg.tesp.psi.projsi.sistematrocas.projetosi_android_1718.modelos.GeneroJogo;
import pt.ipleiria.estg.tesp.psi.projsi.sistematrocas.projetosi_android_1718.modelos.Jogo;
import pt.ipleiria.estg.tesp.psi.projsi.sistematrocas.projetosi_android_1718.modelos.Livro;
import pt.ipleiria.estg.tesp.psi.projsi.sistematrocas.projetosi_android_1718.modelos.Proposta;
import pt.ipleiria.estg.tesp.psi.projsi.sistematrocas.projetosi_android_1718.modelos.Roupa;
import pt.ipleiria.estg.tesp.psi.projsi.sistematrocas.projetosi_android_1718.modelos.Smartphone;
import pt.ipleiria.estg.tesp.psi.projsi.sistematrocas.projetosi_android_1718.modelos.TipoRoupa;
import pt.ipleiria.estg.tesp.psi.projsi.sistematrocas.projetosi_android_1718.singletons.SingletonAnuncios;
import pt.ipleiria.estg.tesp.psi.projsi.sistematrocas.projetosi_android_1718.singletons.SingletonCategorias;
import pt.ipleiria.estg.tesp.psi.projsi.sistematrocas.projetosi_android_1718.singletons.SingletonClientes;
import pt.ipleiria.estg.tesp.psi.projsi.sistematrocas.projetosi_android_1718.singletons.SingletonGenerosJogo;
import pt.ipleiria.estg.tesp.psi.projsi.sistematrocas.projetosi_android_1718.singletons.SingletonPropostas;
import pt.ipleiria.estg.tesp.psi.projsi.sistematrocas.projetosi_android_1718.singletons.SingletonTiposRoupa;

public class DetalhesPropostaActivity extends NavDrawerActivity implements AnunciosListener, PropostasListener, CategoriasListener, ClientesListener, ImagesListener{

    private Anuncio anuncioProposta;
    private Proposta proposta;

    public static final String ID_PROPOSTA = "ID";

    private static final int NUMERO_COLUNAS = 2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_detalhes_proposta);
        View.inflate(this, R.layout.activity_detalhes_proposta, (ViewGroup) findViewById(R.id.app_content));

        FloatingActionButton fabAceitar = findViewById(R.id.fabAceitar);
        FloatingActionButton fabRecusar = findViewById(R.id.fabRecusar);

        TextView textViewDataProposto = findViewById(R.id.textViewDetalhesPropostaDataProposto);
        TextView textViewTitulo = findViewById(R.id.textViewDetalhesPropostaTituloAnuncio);

        if(getIntent().hasExtra(ID_PROPOSTA)) {

            Long propostaId = getIntent().getLongExtra(ID_PROPOSTA, 0);
            proposta = SingletonPropostas.getInstance(getApplicationContext()).pesquisarPropostaPorID(propostaId);

            if(proposta != null) {
                anuncioProposta = SingletonAnuncios.getInstance(getApplicationContext()).pesquisarAnuncioPorID(proposta.getIdAnuncio());

                textViewTitulo.setText(anuncioProposta.getTitulo());
                textViewDataProposto.setText(proposta.getDataProposta());

                SingletonPropostas.getInstance(getApplicationContext()).setPropostasListener(this);
                SingletonAnuncios.getInstance(getApplicationContext()).setAnunciosListener(this);

                fabAceitar.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        proposta.setEstado("ACEITE");
                        anuncioProposta.setEstado("FECHADO");

                        SingletonPropostas.getInstance(getApplicationContext()).alterarProposta(proposta, getApplicationContext());
                    }
                });

                fabRecusar.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        proposta.setEstado("RECUSADA");
                        anuncioProposta.setEstado("FECHADO");

                        SingletonPropostas.getInstance(getApplicationContext()).alterarProposta(proposta, getApplicationContext());
                    }
                });

                SingletonPropostas.getInstance(getApplicationContext()).setImagesListener(this);
                SingletonPropostas.getInstance(getApplicationContext()).getImagensProposta(proposta.getId(), getApplicationContext());

                SingletonClientes.getInstance(getApplicationContext()).setClientesListener(this);
                SingletonClientes.getInstance(getApplicationContext()).getClienteAPI(proposta.getIdUser(), getApplicationContext());

                SingletonCategorias.getInstance().setCategoriasListener(this);
                SingletonCategorias.getInstance().getCategoriasProposta(proposta.getId(), getApplicationContext());
            }
        }
    }

    private void showNotification(String message) {
        Snackbar snackbar = Snackbar
                .make(findViewById(R.id.coordinatorLayoutDetalhesProposta), message, Snackbar.LENGTH_LONG);

        snackbar.show();
    }

    @Override
    public void onSuccessAnunciosAPI(Anuncio anuncio) {
        showNotification("A proposta ao anúncio " + anuncio.getTitulo() + " foi " + proposta.getEstado().toLowerCase() + ".");
    }

    @Override
    public void onErrorAnunciosAPI(String message, Exception ex) {
        ex.printStackTrace();
        showNotification(message);
    }

    @Override
    public void onRefreshAnuncios(ArrayList<Anuncio> anuncios) {

    }

    @Override
    public void onSuccessPropostasAPI(Proposta proposta) {
        if(proposta != null) {
            SingletonAnuncios.getInstance(getApplicationContext()).alterarAnuncio(anuncioProposta, getApplicationContext());
        }
    }

    @Override
    public void onErrorPropostasAPI(String message, Exception ex) {
        ex.printStackTrace();
        showNotification(message);
    }

    @Override
    public void onRefreshPropostas(ArrayList<Proposta> propostas) {

    }

    @Override
    public void onObterCategoria(Categoria categoria, String tipoCategoria, String tipoBem) {

        RecyclerView recyclerView;
        GridLayoutManager gridLayoutManager;

        HashMap<String, String> categoriaAtributos = new HashMap<>();

        switch (tipoCategoria)
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

                GeneroJogo generoJogo = SingletonGenerosJogo.getInstance(this).pesquisarGeneroJogosPorID(jogo.getIdGenero());

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

                TipoRoupa tipoRoupa = SingletonTiposRoupa.getInstance(this).pesquisarTipoRoupaPorID(roupa.getId());

                if(tipoRoupa != null) {
                    categoriaAtributos.put("Tipo", tipoRoupa.getNome());
                }

                break;
        }

        CategoriasAdapter categoriasAdapter = new CategoriasAdapter(categoriaAtributos);

        TextView textViewNome = findViewById(R.id.textViewDetalhesPropostaProporNome);
        textViewNome.setText(categoria.getNome());

        recyclerView = findViewById(R.id.fragmentDetalhesProposta);
        gridLayoutManager = new GridLayoutManager(this, NUMERO_COLUNAS, GridLayoutManager.VERTICAL, false);

        recyclerView.setLayoutManager(gridLayoutManager);
        recyclerView.setAdapter(categoriasAdapter);
    }

    @Override
    public void onErroObterCategoria(String message, Exception ex) {
        ex.printStackTrace();
        showNotification(message);
    }

    @Override
    public void OnSucessoObterCliente(Cliente cliente) {
        TextView textViewCliente = findViewById(R.id.textViewDetalhesPropostaClienteProposta);
        textViewCliente.setText(cliente.getNomeCompleto());
    }

    @Override
    public void OnErroObterCliente(String message, Exception ex) {
        ex.printStackTrace();
        showNotification(message);
    }

    @Override
    public void OnSucessoImagensAPI(ArrayList<byte[]> imagensBytes) {

        ImageView imageViewProposta = findViewById(R.id.imageViewImagensProposta);

        byte[] imagem = imagensBytes.get(0);

        Glide.with(this)
                .fromBytes()
                .asBitmap()
                .fitCenter()
                .load(imagem)
                .into(imageViewProposta);
    }

    @Override
    public void OnErrorImagensAPI(String message, Exception ex) {
        ex.printStackTrace();
        showNotification(message);
    }
}
