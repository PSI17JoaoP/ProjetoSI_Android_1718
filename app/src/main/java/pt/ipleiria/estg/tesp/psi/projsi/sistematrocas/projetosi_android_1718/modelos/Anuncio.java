package pt.ipleiria.estg.tesp.psi.projsi.sistematrocas.projetosi_android_1718.modelos;

/**
 * Created by leona on 06/11/2017.
 */

public class Anuncio {

    private Long id;
    private String titulo;
    private Long idUser;
    private Long catOferecer;
    private Integer quantOferecer;
    private Long catReceber;
    private Integer quantReceber;
    private String estado;
    private String dataCriacao;
    private String dataConclusao;
    private String comentarios;

    public Anuncio(String titulo, Long idUser, Long catOferecer, Integer quantOferecer,
                   Long catReceber, Integer quantReceber, String estado, String dataCriacao, String comentarios) {

        this.titulo = titulo;
        this.idUser = idUser;
        this.catOferecer = catOferecer;
        this.quantOferecer = quantOferecer;
        this.catReceber = catReceber;
        this.quantReceber = quantReceber;
        this.estado = estado;
        this.dataCriacao = dataCriacao;
        this.dataConclusao = null;
        this.comentarios = comentarios;
    }

    public Anuncio(String titulo, Long idUser, Long catOferecer,
                   Integer quantOferecer, Long catReceber, Integer quantReceber,
                   String estado, String dataCriacao, String dataConclusao, String comentarios) {

        this.titulo = titulo;
        this.idUser = idUser;
        this.catOferecer = catOferecer;
        this.quantOferecer = quantOferecer;
        this.catReceber = catReceber;
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

    public Long getCatOferecer() {
        return catOferecer;
    }

    public void setCatOferecer(Long catOferecer) {
        this.catOferecer = catOferecer;
    }

    public Integer getQuantOferecer() {
        return quantOferecer;
    }

    public void setQuantOferecer(Integer quantOferecer) {
        this.quantOferecer = quantOferecer;
    }

    public Long getCatReceber() {
        return catReceber;
    }

    public void setCatReceber(Long catReceber) {
        this.catReceber = catReceber;
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
