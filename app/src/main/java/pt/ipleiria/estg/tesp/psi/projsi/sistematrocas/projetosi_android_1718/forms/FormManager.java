package pt.ipleiria.estg.tesp.psi.projsi.sistematrocas.projetosi_android_1718.forms;

import android.support.v4.app.Fragment;
import android.content.Context;

import java.text.Normalizer;

/**
 * Created by JAPorelo on 22-12-2017.
 * Project ProjetoSI_Android_1718 - pt.ipleiria.estg.tesp.psi.projsi.sistematrocas.projetosi_android_1718.forms
 */

public class FormSelector {

    private static final String PACKAGE_PATH = "pt.ipleiria.estg.tesp.psi.projsi.sistematrocas.projetosi_android_1718.forms.";

    public Fragment selectForm(String categoriaForm, String[] categorias, Context context) throws ClassNotFoundException {

        for (String categoriaNome : categorias) {
            if (categoriaForm.equals(categoriaNome)) {
                if (Class.forName(PACKAGE_PATH + categoriaNome + "Fragment") != null) {
                    return Fragment.instantiate(context, (PACKAGE_PATH + categoriaNome + "Fragment"));
                }
            }
        }

        return null;
    }

    public 
}
