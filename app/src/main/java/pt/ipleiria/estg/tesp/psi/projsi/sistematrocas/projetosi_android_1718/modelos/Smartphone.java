package pt.ipleiria.estg.tesp.psi.projsi.sistematrocas.projetosi_android_1718.modelos;

/**
 * Created by leona on 23/11/2017.
 */

public class Smartphone extends Eletronica {

    private String processador;
    private String ram;
    private String hdd;
    private String os;
    private String tamanho;

    public Smartphone(String nome, String descricao, String marca, String processador, String ram, String hdd, String os, String tamanho) {

        super(nome, descricao, marca);
        this.processador = processador;
        this.ram = ram;
        this.hdd = hdd;
        this.os = os;
        this.tamanho = tamanho;
    }

    public String getProcessador() {
        return processador;
    }

    public void setProcessador(String processador) {
        this.processador = processador;
    }

    public String getRam() {
        return ram;
    }

    public void setRam(String ram) {
        this.ram = ram;
    }

    public String getHdd() {
        return hdd;
    }

    public void setHdd(String hdd) {
        this.hdd = hdd;
    }

    public String getOs() {
        return os;
    }

    public void setOs(String os) {
        this.os = os;
    }

    public String getTamanho() {
        return tamanho;
    }

    public void setTamanho(String tamanho) {
        this.tamanho = tamanho;
    }
}
