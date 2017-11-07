package pt.ipleiria.estg.tesp.psi.projsi.sistematrocas.projetosi_android_1718.modelos;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by leona on 06/11/2017.
 */

public class GestorAnuncios {
    private List<Anuncio> listaAnuncios;

    public GestorAnuncios() {
        listaAnuncios = new ArrayList<Anuncio>();
        gerarFakeData();
    }

    public void gerarFakeData(){
        String data = "05/11/2017";

        Anuncio fAnuncio1 = new Anuncio(1, "Anuncio1", 1, 1, 1,
                2, 1, "ativo", data , "");
        Anuncio fAnuncio2 = new Anuncio(2, "Anuncio2", 1, 1, 1,
                2, 1, "ativo", data , "");

        this.listaAnuncios.add(fAnuncio1);
        this.listaAnuncios.add(fAnuncio2);
        this.listaAnuncios.add(fAnuncio2);
        this.listaAnuncios.add(fAnuncio2);
        this.listaAnuncios.add(fAnuncio2);
    }

    public List<Anuncio> getListaAnuncios(){
        return this.listaAnuncios;
    }

    public Anuncio getAnuncioById(Integer idAnuncio){
        for (Anuncio anuncio: listaAnuncios) {
            if (anuncio.getId() == idAnuncio){
                return anuncio;
            }
        }

        return null;
    }
}
