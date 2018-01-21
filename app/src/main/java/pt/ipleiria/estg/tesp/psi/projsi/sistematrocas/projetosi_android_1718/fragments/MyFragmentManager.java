package pt.ipleiria.estg.tesp.psi.projsi.sistematrocas.projetosi_android_1718.fragments;

import android.support.v4.app.Fragment;
import android.content.Context;

import java.lang.reflect.InvocationTargetException;

import pt.ipleiria.estg.tesp.psi.projsi.sistematrocas.projetosi_android_1718.modelos.Categoria;

/**
 * Created by JAPorelo on 22-12-2017.
 * Project ProjetoSI_Android_1718 - pt.ipleiria.estg.tesp.psi.projsi.sistematrocas.projetosi_android_1718.fragments
 */

public class MyFragmentManager {

    private static final String PACKAGE_PATH = "pt.ipleiria.estg.tesp.psi.projsi.sistematrocas.projetosi_android_1718.fragments.";

    public static Fragment getFragment(String categoriaFragment, String tipoFragment, String[] categorias, Context context) throws ClassNotFoundException {

        for (String categoriaNome : categorias) {
            if (categoriaFragment.equals(categoriaNome)) {
                if (Class.forName(PACKAGE_PATH + categoriaNome + tipoFragment + "Fragment") != null) {
                    return Fragment.instantiate(context, (PACKAGE_PATH + categoriaNome + "Fragment"));
                }
            }
        }

        return null;
    }

    public static Categoria getCategoria(Fragment fragmentForm, String[] categoriasForm, String categoriaKey, String nomeCategoria, Context context)
            throws ClassNotFoundException, NoSuchMethodException, InvocationTargetException, IllegalAccessException, NumberFormatException {

        for (String categoriaForm : categoriasForm) {

            if (categoriaKey.equals(categoriaForm)) {
                if (Class.forName(PACKAGE_PATH + categoriaForm + "FormFragment") != null) {
                    Fragment fragment = Fragment.instantiate(context, (PACKAGE_PATH + categoriaForm + "FormFragment"));
                    if (fragmentForm.getClass().equals(fragment.getClass())) {
                        return (Categoria) fragmentForm.getClass().getMethod("getCategoria", nomeCategoria.getClass()).invoke(fragmentForm, nomeCategoria);
                    }
                }
            }
        }

        return null;
    }
}
