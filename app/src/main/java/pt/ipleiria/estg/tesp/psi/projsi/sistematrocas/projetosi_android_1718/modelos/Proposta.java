package pt.ipleiria.estg.tesp.psi.projsi.sistematrocas.projetosi_android_1718.modelos;

/**
 * Created by leona on 06/11/2017.
 */

public class Proposta {
    private Integer id;
    private Integer catProposta;
    private Integer quant;
    private Integer idUser;
    private Integer idAnuncio;
    private String estado;
    private String dataProposta;

    public Proposta(Integer id, Integer catProposta, Integer quant, Integer idUser, Integer idAnuncio, String estado, String dataProposta) {
        this.id = id;
        this.catProposta = catProposta;
        this.quant = quant;
        this.idUser = idUser;
        this.idAnuncio = idAnuncio;
        this.estado = estado;
        this.dataProposta = dataProposta;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getCatProposta() {
        return catProposta;
    }

    public void setCatProposta(Integer catProposta) {
        this.catProposta = catProposta;
    }

    public Integer getQuant() {
        return quant;
    }

    public void setQuant(Integer quant) {
        this.quant = quant;
    }

    public Integer getIdUser() {
        return idUser;
    }

    public void setIdUser(Integer idUser) {
        this.idUser = idUser;
    }

    public Integer getIdAnuncio() {
        return idAnuncio;
    }

    public void setIdAnuncio(Integer idAnuncio) {
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
