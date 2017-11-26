package pt.ipleiria.estg.tesp.psi.projsi.sistematrocas.projetosi_android_1718.modelos;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by leona on 21/11/2017.
 */

public class SingletonAnuncios {
    private static SingletonAnuncios INSTANCE = null;
    private List<Anuncio> anuncios;

    public static synchronized SingletonAnuncios getInstance() {
        if (INSTANCE == null)
            INSTANCE = new SingletonAnuncios();

        return INSTANCE;
    }

    private SingletonAnuncios() {
        anuncios = new ArrayList<>();

        gerarFakeData();
    }

    /**
     * Provisório. Eliminar no futuro
     */
    private void gerarFakeData(){
        String data = "05/11/2017";

        Anuncio fAnuncio1 = new Anuncio(1, "Anuncio1", 1, 1, 1,
                2, 1, "ativo", data , "");
        Anuncio fAnuncio2 = new Anuncio(2, "Anuncio2", 1, 1, 1,
                2, 1, "ativo", data , "");
        Anuncio fAnuncio3 = new Anuncio(3, "Anuncio3", 1, 1, 1,
                2, 1, "ativo", data , "");
        Anuncio fAnuncio4 = new Anuncio(4, "Anuncio4", 1, 1, 1,
                2, 1, "ativo", data , "");
        Anuncio fAnuncio5 = new Anuncio(5, "Anuncio5", 1, 1, 1,
                2, 1, "ativo", data , "");

        this.anuncios.add(fAnuncio1);
        this.anuncios.add(fAnuncio2);
        this.anuncios.add(fAnuncio3);
        this.anuncios.add(fAnuncio4);
        this.anuncios.add(fAnuncio5);
    }


    public List<Anuncio> getAnuncios() {
        return anuncios;
    }

    public Integer getAnunciosCount() {
        return anuncios.size();
    }

    public boolean adicionarAnuncio(Anuncio anuncio)
    {
        return anuncios.add(anuncio);
    }

    public boolean removerAnuncio(Anuncio anuncio)
    {
        return anuncios.remove(anuncio);
    }

    public boolean editarAnuncio(Anuncio anuncio)
    {
        Anuncio novoAnuncio = anuncios.set(anuncio.getId(), anuncio);

        if (anuncios.contains(novoAnuncio))
            return true;
        else
            return false;
    }

    public Anuncio pesquisarAnuncioID(Integer id)
    {
        return anuncios.get(id);
    }
}
