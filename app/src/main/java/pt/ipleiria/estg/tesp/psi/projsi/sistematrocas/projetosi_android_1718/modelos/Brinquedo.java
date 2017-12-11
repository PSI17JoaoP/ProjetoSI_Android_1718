package pt.ipleiria.estg.tesp.psi.projsi.sistematrocas.projetosi_android_1718.modelos;

/**
 * Created by leona on 23/11/2017.
 */

public class Brinquedo extends Categoria {

    private String editora;
    private Integer faixaEtaria;
    private String descricao;

    public Brinquedo(Long id, String nome, String editora, Integer faixaEtaria, String descricao) {

        super(id, nome);
        this.editora = editora;
        this.faixaEtaria = faixaEtaria;
        this.descricao = descricao;
    }

    public String getEditora() {
        return editora;
    }

    public void setEditora(String editora) {
        this.editora = editora;
    }

    public Integer getFaixaEtaria() {
        return faixaEtaria;
    }

    public void setFaixaEtaria(Integer faixaEtaria) {
        this.faixaEtaria = faixaEtaria;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }
}
