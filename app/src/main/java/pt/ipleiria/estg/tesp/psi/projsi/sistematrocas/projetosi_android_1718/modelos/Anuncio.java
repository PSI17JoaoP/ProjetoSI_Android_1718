package pt.ipleiria.estg.tesp.psi.projsi.sistematrocas.projetosi_android_1718.modelos;

/**
 * Created by leona on 06/11/2017.
 */

public class Anuncio {

    private Integer id;
    private String titulo;
    private Integer idUser;
    private Integer idCatOferecer;
    private Integer quantOferecer;
    private Integer idCatReceber;
    private Integer quantReceber;
    private String estado;
    private String dataCriacao;
    private String dataConclusao;
    private String comentarios;

    public Anuncio(Integer id, String titulo, Integer idUser, Integer idCatOferecer, Integer quantOferecer,
                   Integer idCatReceber, Integer quantReceber, String estado, String dataCriacao, String comentarios) {
        this.id = id;
        this.titulo = titulo;
        this.idUser = idUser;
        this.idCatOferecer = idCatOferecer;
        this.quantOferecer = quantOferecer;
        this.idCatReceber = idCatReceber;
        this.quantReceber = quantReceber;
        this.estado = estado;
        this.dataCriacao = dataCriacao;
        this.dataConclusao = "";
        this.comentarios = comentarios;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public Integer getIdUser() {
        return idUser;
    }

    public void setIdUser(Integer idUser) {
        this.idUser = idUser;
    }

    public Integer getIdCatOferecer() {
        return idCatOferecer;
    }

    public void setIdCatOferecer(Integer idCatOferecer) {
        this.idCatOferecer = idCatOferecer;
    }

    public Integer getQuantOferecer() {
        return quantOferecer;
    }

    public void setQuantOferecer(Integer quantOferecer) {
        this.quantOferecer = quantOferecer;
    }

    public Integer getIdCatReceber() {
        return idCatReceber;
    }

    public void setIdCatReceber(Integer idCatReceber) {
        this.idCatReceber = idCatReceber;
    }

    public Integer getQuantReceber() {
        return quantReceber;
    }

    public void setQuantReceber(Integer quantReceber) {
        this.quantReceber = quantReceber;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getDataCriacao() {
        return dataCriacao;
    }

    public void setDataCriacao(String dataCriacao) {
        this.dataCriacao = dataCriacao;
    }

    public String getDataConclusao() {
        return dataConclusao;
    }

    public void setDataConclusao(String dataConclusao) {
        this.dataConclusao = dataConclusao;
    }

    public String getComentarios() {
        return comentarios;
    }

    public void setComentarios(String comentarios) {
        this.comentarios = comentarios;
    }
}
