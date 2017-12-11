package pt.ipleiria.estg.tesp.psi.projsi.sistematrocas.projetosi_android_1718.modelos;

import java.util.List;

/**
 * Created by leona on 23/11/2017.
 */

public class User {

    private Long id;
    private String username;
    private String password_hash;
    private String email;
    private Integer status;
    private List<CategoriaPreferida> categoriasPreferidas;

    public User(Long id, String username, String password_hash, String email, Integer status) {

        this.id = id;
        this.username = username;
        this.password_hash = password_hash;
        this.email = email;
        this.status = status;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPasswordHash() {
        return password_hash;
    }

    public void setPasswordHash(String password_hash) {
        this.password_hash = password_hash;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public List<CategoriaPreferida> getCategoriasPreferidas() {
        return categoriasPreferidas;
    }

    public void setCategoriasPreferidas(List<CategoriaPreferida> categoriasPreferidas) {
        this.categoriasPreferidas = categoriasPreferidas;
    }

    public boolean addCategoriaPrerefida(CategoriaPreferida categoria)
    {
        return categoriasPreferidas.add(categoria);
    }

    public boolean removeCategoriaPreferida(CategoriaPreferida categoria)
    {
        return categoriasPreferidas.remove(categoria);
    }
}
