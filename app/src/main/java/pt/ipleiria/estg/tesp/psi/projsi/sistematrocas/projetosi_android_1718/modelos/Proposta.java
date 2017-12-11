package pt.ipleiria.estg.tesp.psi.projsi.sistematrocas.projetosi_android_1718.modelos;

/**
 * Created by leona on 06/11/2017.
 */

public class Proposta {

    private Long id;
    private Long catProposta;
    private Integer quant;
    private Long idUser;
    private Long idAnuncio;
    private String estado;
    private String dataProposta;

    public Proposta(Long id, Long catProposta, Integer quant, Long idUser,
                    Long idAnuncio, String estado, String dataProposta) {

        this.id = id;
        this.catProposta = catProposta;
        this.quant = quant;
        this.idUser = idUser;
        this.idAnuncio = idAnuncio;
        this.estado = estado;
        this.dataProposta = dataProposta;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getCatProposta() {
        return catProposta;
    }

    public void setCatProposta(Long catProposta) {
        this.catProposta = catProposta;
    }

    public Integer getQuant() {
        return quant;
    }

    public void setQuant(Integer quant) {
        this.quant = quant;
    }

    public Long getIdUser() {
        return idUser;
    }

    public void setIdUser(Long idUser) {
        this.idUser = idUser;
    }

    public Long getIdAnuncio() {
        return idAnuncio;
    }

    public void setIdAnuncio(Long idAnuncio) {
        this.idAnuncio = idAnuncio;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getDataProposta() {
        return dataProposta;
    }

    public void setDataProposta(String dataProposta) {
        this.dataProposta = dataProposta;
    }
}
