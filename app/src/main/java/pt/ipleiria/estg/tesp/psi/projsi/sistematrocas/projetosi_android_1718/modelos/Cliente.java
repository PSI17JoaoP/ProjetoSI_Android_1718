package pt.ipleiria.estg.tesp.psi.projsi.sistematrocas.projetosi_android_1718.modelos;

/**
 * Created by leona on 23/11/2017.
 */

public class Cliente {
    private Integer idUser;
    private String nomeCompleto;
    private String dataNasc;
    private Integer telefone;
    private String regiao;
    private Integer pin;

    public Cliente(Integer idUser, String nomeCompleto, String dataNasc, Integer telefone, String regiao, Integer pin) {
        this.idUser = idUser;
        this.nomeCompleto = nomeCompleto;
        this.dataNasc = dataNasc;
        this.telefone = telefone;
        this.regiao = regiao;
        this.pin = pin;
    }

    public Integer getIdUser() {
        return idUser;
    }

    public void setIdUser(Integer idUser) {
        this.idUser = idUser;
    }

    public String getNomeCompleto() {
        return nomeCompleto;
    }

    public void setNomeCompleto(String nomeCompleto) {
        this.nomeCompleto = nomeCompleto;
    }

    public String getDataNasc() {
        return dataNasc;
    }

    public void setDataNasc(String dataNasc) {
        this.dataNasc = dataNasc;
    }

    public Integer getTelefone() {
        return telefone;
    }

    public void setTelefone(Integer telefone) {
        this.telefone = telefone;
    }

    public String getRegiao() {
        return regiao;
    }

    public void setRegiao(String regiao) {
        this.regiao = regiao;
    }

    public Integer getPin() {
        return pin;
    }

    public void setPin(Integer pin) {
        this.pin = pin;
    }
}
