package pt.ipleiria.estg.tesp.psi.projsi.sistematrocas.projetosi_android_1718.modelos;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by leona on 21/11/2017.
 */

public class SingletonPropostas {
    private static SingletonPropostas INSTANCE = null;
    private List<Proposta> propostas;


    public static SingletonPropostas getInstance() {
        if (INSTANCE == null)
            INSTANCE = new SingletonPropostas();

        return INSTANCE;
    }

    private SingletonPropostas() {
        propostas = new ArrayList<>();

        gerarFakeData();
    }

    /**
     * Provisório. Eliminar no futuro
     */
    private void gerarFakeData(){
        String data = "05/11/2017";

        Proposta fProposta1 = new Proposta(1, 1, 2, 1, 1, "ativo", data);
        Proposta fProposta2 = new Proposta(2, 1, 2, 1, 1, "ativo", data);
        Proposta fProposta3 = new Proposta(3, 1, 2, 1, 1, "ativo", data);
        Proposta fProposta4 = new Proposta(4, 1, 2, 1, 1, "ativo", data);
        Proposta fProposta5 = new Proposta(5, 1, 2, 1, 1, "ativo", data);
        Proposta fProposta6 = new Proposta(6, 1, 2, 1, 1, "ativo", data);
        Proposta fProposta7 = new Proposta(7, 1, 2, 1, 1, "ativo", data);
        Proposta fProposta8 = new Proposta(8, 1, 2, 1, 1, "ativo", data);

        this.propostas.add(fProposta1);
        this.propostas.add(fProposta2);
        this.propostas.add(fProposta3);
        this.propostas.add(fProposta4);
        this.propostas.add(fProposta5);
        this.propostas.add(fProposta6);
        this.propostas.add(fProposta7);
        this.propostas.add(fProposta8);
    }

    public List<Proposta> getPropostas() {
        return propostas;
    }


    public boolean adicionarProposta(Proposta proposta)
    {
        return propostas.add(proposta);
    }

    public boolean removerProposta(Proposta proposta)
    {
        return propostas.remove(proposta);
    }

    public boolean editarProposta(Proposta proposta)
    {
        Proposta novaProposta = propostas.set(proposta.getId(), proposta);

        if (propostas.contains(novaProposta))
            return true;
        else
            return false;
    }

    public Proposta pesquisarPropostaID(Integer id)
    {
        return propostas.get(id);
    }
}
