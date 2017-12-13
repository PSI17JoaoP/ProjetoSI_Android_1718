package pt.ipleiria.estg.tesp.psi.projsi.sistematrocas.projetosi_android_1718.singletons;

import java.util.ArrayList;
import java.util.List;

import pt.ipleiria.estg.tesp.psi.projsi.sistematrocas.projetosi_android_1718.modelos.Anuncio;

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

        Anuncio fAnuncio1 = new Anuncio("Anuncio1", 1L, 1L, 1,
                2L, 1, "ativo", data , "");
        Anuncio fAnuncio2 = new Anuncio("Anuncio2", 1L, 1L, 1,
                2L, 1, "ativo", data , "");
        Anuncio fAnuncio3 = new Anuncio("Anuncio3", 1L, 1L, 1,
                2L, 1, "ativo", data , "");
        Anuncio fAnuncio4 = new Anuncio("Anuncio4", 1L, 1L, 1,
                2L, 1, "ativo", data , "");
        Anuncio fAnuncio5 = new Anuncio("Anuncio5", 1L, 1L, 1,
                2L, 1, "ativo", data , "");

        fAnuncio1.setId(Long.valueOf("1"));
        fAnuncio2.setId(Long.valueOf("2"));
        fAnuncio3.setId(Long.valueOf("3"));
        fAnuncio4.setId(Long.valueOf("4"));
        fAnuncio5.setId(Long.valueOf("5"));

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
        Anuncio novoAnuncio = anuncios.set(anuncio.getId().intValue(), anuncio);

        return anuncios.contains(novoAnuncio);
    }

    public Anuncio pesquisarAnuncioID(Long id)
    {
        return anuncios.get(id.intValue());
    }
}
