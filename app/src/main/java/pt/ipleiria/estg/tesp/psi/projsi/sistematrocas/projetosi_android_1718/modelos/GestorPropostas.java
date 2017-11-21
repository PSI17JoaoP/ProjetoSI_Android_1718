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
        Proposta fProposta2 = new Proposta(2, 1, 2, 1, 1, "ativo", data);
        Proposta fProposta3 = new Proposta(3, 1, 2, 1, 1, "ativo", data);
        Proposta fProposta4 = new Proposta(4, 1, 2, 1, 1, "ativo", data);
        Proposta fProposta5 = new Proposta(5, 1, 2, 1, 1, "ativo", data);
        Proposta fProposta6 = new Proposta(6, 1, 2, 1, 1, "ativo", data);
        Proposta fProposta7 = new Proposta(7, 1, 2, 1, 1, "ativo", data);
        Proposta fProposta8 = new Proposta(8, 1, 2, 1, 1, "ativo", data);

        listaPropostas.add(fProposta1);
        listaPropostas.add(fProposta2);
        listaPropostas.add(fProposta3);
        listaPropostas.add(fProposta4);
        listaPropostas.add(fProposta5);
        listaPropostas.add(fProposta6);
        listaPropostas.add(fProposta7);
        listaPropostas.add(fProposta8);
    }

    public List<Proposta> getListaPropostas(){
        return this.listaPropostas;
    }
}
