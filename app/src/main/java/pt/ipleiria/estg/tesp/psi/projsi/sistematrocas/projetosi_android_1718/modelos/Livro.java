package pt.ipleiria.estg.tesp.psi.projsi.sistematrocas.projetosi_android_1718.modelos;

/**
 * Created by leona on 23/11/2017.
 */

public class Livro extends Categoria {

    private String titulo;
    private String editora;
    private String autor;
    private Integer isbn;

    public Livro(String nome, String titulo, String editora, String autor, Integer isbn) {

        super(nome);
        this.titulo = titulo;
        this.editora = editora;
        this.autor = autor;
        this.isbn = isbn;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getEditora() {
        return editora;
    }

    public void setEditora(String editora) {
        this.editora = editora;
    }

    public String getAutor() {
        return autor;
    }

    public void setAutor(String autor) {
        this.autor = autor;
    }

    public Integer getIsbn() {
        return isbn;
    }

    public void setIsbn(Integer isbn) {
        this.isbn = isbn;
    }
}
