package pt.ipleiria.estg.tesp.psi.projsi.sistematrocas.projetosi_android_1718.modelos;

/**
 * Created by leona on 23/11/2017.
 */

public class Jogo extends Brinquedo {
    private Integer idGenero;
    private String produtora;

    public Jogo(Integer id, String nome, String editora, Integer faixaEtaria, String descricao, Integer idGenero, String produtora) {
        super(id, nome, editora, faixaEtaria, descricao);
        this.idGenero = idGenero;
        this.produtora = produtora;
    }

    public Integer getIdGenero() {
        return idGenero;
    }

    public void setIdGenero(Integer idGenero) {
        this.idGenero = idGenero;
    }

    public String getProdutora() {
        return produtora;
    }

    public void setProdutora(String produtora) {
        this.produtora = produtora;
    }
}
