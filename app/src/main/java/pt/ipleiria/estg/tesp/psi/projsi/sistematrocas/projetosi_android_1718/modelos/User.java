package pt.ipleiria.estg.tesp.psi.projsi.sistematrocas.projetosi_android_1718.modelos;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by leona on 23/11/2017.
 */

public class User {
    private String username;
    private String password_hash;
    private String email;
    private Integer telefone;
    private Integer status;
    private List<Categoria> categoriasPreferidas;

    public User(String username, String password_hash, String email, Integer telefone, Integer status) {
        this.username = username;
        this.password_hash = password_hash;
        this.email = email;
        this.telefone = telefone;
        this.status = status;

        categoriasPreferidas = new ArrayList<>();
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword_hash() {
        return password_hash;
    }

    public void setPassword_hash(String password_hash) {
        this.password_hash = password_hash;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Integer getTelefone() {
        return telefone;
    }

    public void setTelefone(Integer telefone) {
        this.telefone = telefone;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public List<Categoria> getCategoriasPreferidas() {
        return categoriasPreferidas;
    }

    public void setCategoriasPreferidas(List<Categoria> categoriasPreferidas) {
        this.categoriasPreferidas = categoriasPreferidas;
    }

    public boolean addCategoriaPrerefida(Categoria categoria)
    {
        return categoriasPreferidas.add(categoria);
    }

    public boolean removeCategoriaPreferida(Categoria categoria)
    {
        return categoriasPreferidas.remove(categoria);
    }
}
