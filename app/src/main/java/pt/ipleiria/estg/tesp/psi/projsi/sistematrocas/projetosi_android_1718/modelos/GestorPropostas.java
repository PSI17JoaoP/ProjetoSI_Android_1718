package pt.ipleiria.estg.tesp.psi.projsi.sistematrocas.projetosi_android_1718.modelos;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by leona on 06/11/2017.
 */

public class GestorPropostas {
    private List<Proposta> listaPropostas;

    public GestorPropostas() {
        listaPropostas = new ArrayList<Proposta>();
        gerarFakeData();
    }

    public void gerarFakeData(){
        String data = "05/11/2017";

        Proposta fProposta1 = new Proposta(1, 1, 2, 1, 1, "ativo", data);

        listaPropostas.add(fProposta1);
        listaPropostas.add(fProposta1);
        listaPropostas.add(fProposta1);
        listaPropostas.add(fProposta1);
        listaPropostas.add(fProposta1);
    }

    public List<Proposta> getListaPropostas(){
        return this.listaPropostas;
    }
}
