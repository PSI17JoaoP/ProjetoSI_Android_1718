package pt.ipleiria.estg.tesp.psi.projsi.sistematrocas.projetosi_android_1718.helpers;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

/**
 * Created by JAPorelo on 07-12-2017.
 * Project ProjetoSI_Android_1718 - pt.ipleiria.estg.tesp.psi.projsi.sistematrocas.projetosi_android_1718.helpers
 */

public class TipoRoupaBDTable extends BDHelper {

    static final String TABLE_NAME = "tipo_roupa";

    private static final String NOME_TIPO_ROUPA = "nome";

    public TipoRoupaBDTable(Context context) {
        super(context);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTableTipoRoupa = "CREATE TABLE " + TABLE_NAME +
                "(id INTEGER PRIMARY KEY AUTOINCREMENT," +
                NOME_TIPO_ROUPA + " TEXT NOT NULL);";

        db.execSQL(createTableTipoRoupa);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        String dropTableTipoRoupa = "DROP TABLE IF EXISTS " + TABLE_NAME;
        db.execSQL(dropTableTipoRoupa);
        this.onCreate(db);
    }
}
