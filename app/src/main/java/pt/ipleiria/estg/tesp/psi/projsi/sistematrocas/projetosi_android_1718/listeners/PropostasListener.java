package pt.ipleiria.estg.tesp.psi.projsi.sistematrocas.projetosi_android_1718.listeners;

import java.util.ArrayList;

import pt.ipleiria.estg.tesp.psi.projsi.sistematrocas.projetosi_android_1718.modelos.Proposta;

/**
 * Created by leona on 22/12/2017.
 */

public interface PropostasListener {
    void onRefreshPropostas(ArrayList<Proposta> propostas);
    void onUpdatePropostas(Proposta proposta, int acao);
}
