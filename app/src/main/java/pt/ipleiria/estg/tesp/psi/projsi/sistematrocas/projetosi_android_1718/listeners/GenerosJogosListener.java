package pt.ipleiria.estg.tesp.psi.projsi.sistematrocas.projetosi_android_1718.listeners;

import android.content.Context;

import java.util.ArrayList;

import pt.ipleiria.estg.tesp.psi.projsi.sistematrocas.projetosi_android_1718.modelos.GeneroJogo;

/**
 * Created by JAPorelo on 31-12-2017.
 * Project ProjetoSI_Android_1718 - pt.ipleiria.estg.tesp.psi.projsi.sistematrocas.projetosi_android_1718.listeners
 */

public interface GenerosJogosListener {
    void onErrorGenerosJogosAPI(String message, Exception ex);
    void onRefreshGenerosJogos(ArrayList<GeneroJogo> generosJogos, Context context);
    void onUpdateGenerosJogos(GeneroJogo generoJogo, int acao);
}
