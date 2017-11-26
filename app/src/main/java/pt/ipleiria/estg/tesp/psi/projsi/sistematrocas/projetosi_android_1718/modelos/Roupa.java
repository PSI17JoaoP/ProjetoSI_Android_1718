package pt.ipleiria.estg.tesp.psi.projsi.sistematrocas.projetosi_android_1718.modelos;

/**
 * Created by leona on 23/11/2017.
 */

public class Roupa extends Categoria {
    private String marca;
    private String tamanho;
    private Integer idTipo;

    public Roupa(Integer id, String nome, String marca, String tamanho, Integer idTipo) {
        super(id, nome);
        this.marca = marca;
        this.tamanho = tamanho;
        this.idTipo = idTipo;
    }

    public String getMarca() {
        return marca;
    }

    public void setMarca(String marca) {
        this.marca = marca;
    }

    public String getTamanho() {
        return tamanho;
    }

    public void setTamanho(String tamanho) {
        this.tamanho = tamanho;
    }

    public Integer getIdTipo() {
        return idTipo;
    }

    public void setIdTipo(Integer idTipo) {
        this.idTipo = idTipo;
    }
}
