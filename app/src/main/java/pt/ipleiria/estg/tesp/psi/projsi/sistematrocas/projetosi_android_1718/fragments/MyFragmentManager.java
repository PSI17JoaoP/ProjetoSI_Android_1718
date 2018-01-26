package pt.ipleiria.estg.tesp.psi.projsi.sistematrocas.projetosi_android_1718.fragments;

import android.support.v4.app.Fragment;
import android.content.Context;
import android.util.Base64;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;

import pt.ipleiria.estg.tesp.psi.projsi.sistematrocas.projetosi_android_1718.modelos.Categoria;

/**
 * Created by JAPorelo on 22-12-2017.
 * Project ProjetoSI_Android_1718 - pt.ipleiria.estg.tesp.psi.projsi.sistematrocas.projetosi_android_1718.fragments
 */

public class MyFragmentManager {

    private static final String PACKAGE_PATH = "pt.ipleiria.estg.tesp.psi.projsi.sistematrocas.projetosi_android_1718.fragments.";

    public static Fragment getFormFragment(String categoriaFragment, String[] categorias, Context context) throws ClassNotFoundException {

        for (String categoriaNome : categorias) {
            if (categoriaFragment.equals(categoriaNome)) {
                if (Class.forName(PACKAGE_PATH + categoriaNome + "FormFragment") != null) {
                    return Fragment.instantiate(context, (PACKAGE_PATH + categoriaNome + "FormFragment"));
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

    public static String getImagemBase64(byte[] imagensBytes) {

        byte[] imagemBase64Bytes = Base64.encode(imagensBytes, Base64.DEFAULT);

        return new String(imagemBase64Bytes);
    }
}
