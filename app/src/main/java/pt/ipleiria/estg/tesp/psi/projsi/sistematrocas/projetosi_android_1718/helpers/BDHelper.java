package pt.ipleiria.estg.tesp.psi.projsi.sistematrocas.projetosi_android_1718.helpers;

import android.content.Context;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by JAPorelo on 06-12-2017.
 */

abstract class BDHelper extends SQLiteOpenHelper {

    private static final String BD_NAME = "SisTrocasBD";
    private static int BD_VERSION = 1;

    BDHelper(Context context) {
        super(context, BD_NAME, null, BD_VERSION);
    }
}
