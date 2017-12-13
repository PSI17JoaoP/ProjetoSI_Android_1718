package pt.ipleiria.estg.tesp.psi.projsi.sistematrocas.projetosi_android_1718.modelos;

/**
 * Created by leona on 23/11/2017.
 */

public class Jogo extends Brinquedo {

    private Long idGenero;
    private String produtora;

    public Jogo(String nome, String editora, Integer faixaEtaria, String descricao, Long idGenero, String produtora) {

        super(nome, editora, faixaEtaria, descricao);
        this.idGenero = idGenero;
        this.produtora = produtora;
    }

    public Long getIdGenero() {
        return idGenero;
    }

    public void setIdGenero(Long idGenero) {
        this.idGenero = idGenero;
    }

    public String getProdutora() {
        return produtora;
    }

    public void setProdutora(String produtora) {
        this.produtora = produtora;
    }
}
