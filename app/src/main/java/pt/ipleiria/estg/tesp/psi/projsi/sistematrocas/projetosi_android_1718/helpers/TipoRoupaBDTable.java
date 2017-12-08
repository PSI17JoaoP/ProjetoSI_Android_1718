package pt.ipleiria.estg.tesp.psi.projsi.sistematrocas.projetosi_android_1718.helpers;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

/**
 * Created by JAPorelo on 07-12-2017.
 */

public class TipoRoupaBDTable extends BDHelper {

    private SQLiteDatabase database;

    static final String TABLE_NAME = "tipo_roupa";

    private static final String NOME_TIPO_ROUPA = "nome";

    TipoRoupaBDTable(Context context) {
        super(context);
        this.database = this.getWritableDatabase();
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
