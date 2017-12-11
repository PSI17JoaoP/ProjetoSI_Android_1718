package pt.ipleiria.estg.tesp.psi.projsi.sistematrocas.projetosi_android_1718.modelos;

/**
 * Created by leona on 23/11/2017.
 */

public class Computador extends Eletronica {

    private String processador;
    private String ram;
    private String hdd;
    private String gpu;
    private String os;
    private Integer portatil;

    public Computador(Long id, String nome, String descricao, String marca, String processador,
                      String ram, String hdd, String gpu, String os, Integer portatil) {

        super(id, nome, descricao, marca);
        this.processador = processador;
        this.ram = ram;
        this.hdd = hdd;
        this.gpu = gpu;
        this.os = os;
        this.portatil = portatil;
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

    public String getGpu() {
        return gpu;
    }

    public void setGpu(String gpu) {
        this.gpu = gpu;
    }

    public String getOs() {
        return os;
    }

    public void setOs(String os) {
        this.os = os;
    }

    public Integer getPortatil() {
        return portatil;
    }

    public void setPortatil(Integer portatil) {
        this.portatil = portatil;
    }
}
