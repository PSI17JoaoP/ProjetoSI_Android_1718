package pt.ipleiria.estg.tesp.psi.projsi.sistematrocas.projetosi_android_1718.modelos;

/**
 * Created by leona on 06/11/2017.
 */

public class Anuncio {

    private Long id;
    private String titulo;
    private Long idUser;
    private Long idCatOferecer;
    private Integer quantOferecer;
    private Long idCatReceber;
    private Integer quantReceber;
    private String estado;
    private String dataCriacao;
    private String dataConclusao;
    private String comentarios;

    public Anuncio(Long id, String titulo, Long idUser, Long idCatOferecer, Integer quantOferecer,
                   Long idCatReceber, Integer quantReceber, String estado, String dataCriacao, String comentarios) {

        this.id = id;
        this.titulo = titulo;
        this.idUser = idUser;
        this.idCatOferecer = idCatOferecer;
        this.quantOferecer = quantOferecer;
        this.idCatReceber = idCatReceber;
        this.quantReceber = quantReceber;
        this.estado = estado;
        this.dataCriacao = dataCriacao;
        this.dataConclusao = null;
        this.comentarios = comentarios;
    }

    public Anuncio(Long id, String titulo, Long idUser, Long idCatOferecer,
                   Integer quantOferecer, Long idCatReceber, Integer quantReceber,
                   String estado, String dataCriacao, String dataConclusao, String comentarios) {

        this.id = id;
        this.titulo = titulo;
        this.idUser = idUser;
        this.idCatOferecer = idCatOferecer;
        this.quantOferecer = quantOferecer;
        this.idCatReceber = idCatReceber;
        this.quantReceber = quantReceber;
        this.estado = estado;
        this.dataCriacao = dataCriacao;
        this.dataConclusao = dataConclusao;
        this.comentarios = comentarios;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public Long getIdUser() {
        return idUser;
    }

    public void setIdUser(Long idUser) {
        this.idUser = idUser;
    }

    public Long getIdCatOferecer() {
        return idCatOferecer;
    }

    public void setIdCatOferecer(Long idCatOferecer) {
        this.idCatOferecer = idCatOferecer;
    }

    public Integer getQuantOferecer() {
        return quantOferecer;
    }

    public void setQuantOferecer(Integer quantOferecer) {
        this.quantOferecer = quantOferecer;
    }

    public Long getIdCatReceber() {
        return idCatReceber;
    }

    public void setIdCatReceber(Long idCatReceber) {
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
