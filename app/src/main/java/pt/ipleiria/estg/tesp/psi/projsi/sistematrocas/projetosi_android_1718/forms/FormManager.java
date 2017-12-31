package pt.ipleiria.estg.tesp.psi.projsi.sistematrocas.projetosi_android_1718.forms;

import android.support.v4.app.Fragment;
import android.content.Context;
import android.support.v4.app.FragmentManager;

import java.lang.reflect.InvocationTargetException;

import pt.ipleiria.estg.tesp.psi.projsi.sistematrocas.projetosi_android_1718.modelos.Categoria;

/**
 * Created by JAPorelo on 22-12-2017.
 * Project ProjetoSI_Android_1718 - pt.ipleiria.estg.tesp.psi.projsi.sistematrocas.projetosi_android_1718.forms
 */

public class FormManager {

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

    public Categoria getCategoria(Fragment fragmentForm, String[] categoriasForm, String categoriaKey, String nomeCategoria, Context context)
            throws ClassNotFoundException, NoSuchMethodException, InvocationTargetException, IllegalAccessException, NumberFormatException {

        for (String categoriaForm : categoriasForm) {

            if (categoriaKey.equals(categoriaForm)) {
                if (Class.forName(PACKAGE_PATH + categoriaForm + "Fragment") != null) {
                    Fragment fragment = Fragment.instantiate(context, (PACKAGE_PATH + categoriaForm + "Fragment"));
                    if (fragmentForm.getClass().equals(fragment.getClass())) {
                        return (Categoria) fragmentForm.getClass().getMethod("getCategoria", nomeCategoria.getClass()).invoke(fragmentForm, nomeCategoria);
                    }
                }
            }
        }

        return null;
    }
}
