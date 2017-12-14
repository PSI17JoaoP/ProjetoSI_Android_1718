package pt.ipleiria.estg.tesp.psi.projsi.sistematrocas.projetosi_android_1718.singletons;

import android.content.Context;

import java.util.ArrayList;
import java.util.List;

import pt.ipleiria.estg.tesp.psi.projsi.sistematrocas.projetosi_android_1718.helpers.PropostaBDTable;
import pt.ipleiria.estg.tesp.psi.projsi.sistematrocas.projetosi_android_1718.modelos.Proposta;

/**
 * Created by leona on 21/11/2017.
 */

public class SingletonPropostas {
    private static SingletonPropostas INSTANCE = null;
    private ArrayList<Proposta> propostas;
    private PropostaBDTable bdTable;


    public static SingletonPropostas getInstance(Context context) {
        if (INSTANCE == null)
            INSTANCE = new SingletonPropostas(context);

        return INSTANCE;
    }

    private SingletonPropostas(Context context) {
        propostas = new ArrayList<>();
        bdTable = new PropostaBDTable(context);
        propostas = bdTable.select();

        //gerarFakeData();
    }

    /**
     * Provisório. Eliminar no futuro
     */
    /*private void gerarFakeData(){
        String data = "05/11/2017";

        Proposta fProposta1 = new Proposta(Long.valueOf("1"), 2, Long.valueOf("1"), Long.valueOf("1"), "ativo", data);
        Proposta fProposta2 = new Proposta(Long.valueOf("1"), 2, Long.valueOf("1"), Long.valueOf("1"), "ativo", data);
        Proposta fProposta3 = new Proposta(Long.valueOf("1"), 2, Long.valueOf("1"), Long.valueOf("1"), "ativo", data);
        Proposta fProposta4 = new Proposta(Long.valueOf("1"), 2, Long.valueOf("1"), Long.valueOf("1"), "ativo", data);
        Proposta fProposta5 = new Proposta(Long.valueOf("1"), 2, Long.valueOf("1"), Long.valueOf("1"), "ativo", data);
        Proposta fProposta6 = new Proposta(Long.valueOf("1"), 2, Long.valueOf("1"), Long.valueOf("1"), "ativo", data);
        Proposta fProposta7 = new Proposta(Long.valueOf("1"), 2, Long.valueOf("1"), Long.valueOf("1"), "ativo", data);
        Proposta fProposta8 = new Proposta(Long.valueOf("1"), 2, Long.valueOf("1"), Long.valueOf("1"), "ativo", data);

        fProposta1.setId(Long.valueOf("1"));
        fProposta2.setId(Long.valueOf("2"));
        fProposta3.setId(Long.valueOf("3"));
        fProposta4.setId(Long.valueOf("4"));
        fProposta5.setId(Long.valueOf("5"));
        fProposta6.setId(Long.valueOf("6"));
        fProposta7.setId(Long.valueOf("7"));
        fProposta8.setId(Long.valueOf("8"));

        this.propostas.add(fProposta1);
        this.propostas.add(fProposta2);
        this.propostas.add(fProposta3);
        this.propostas.add(fProposta4);
        this.propostas.add(fProposta5);
        this.propostas.add(fProposta6);
        this.propostas.add(fProposta7);
        this.propostas.add(fProposta8);
    }*/

    public ArrayList<Proposta> getPropostas() {
        return propostas;
    }

    public Integer getPropostasCount(){
        return propostas.size();
    }

    public boolean adicionarProposta(Proposta proposta)
    {
        Proposta propostaInserida = bdTable.insert(proposta);

        return propostaInserida != null && propostas.add(propostaInserida);
    }

    public boolean removerProposta(Proposta proposta)
    {
        return bdTable.delete(proposta.getId()) && propostas.remove(proposta);
    }

    public boolean editarProposta(Proposta proposta)
    {
        if(bdTable.update(proposta)) {
            Proposta novaProposta = propostas.set(proposta.getId().intValue(), proposta);

            return propostas.contains(novaProposta);
        } else {
            return false;
        }
    }

    public Proposta pesquisarPropostaID(Long id)
    {
        return propostas.get(id.intValue());
    }
}
