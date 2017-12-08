package pt.ipleiria.estg.tesp.psi.projsi.sistematrocas.projetosi_android_1718.helpers;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

/**
 * Created by JAPorelo on 07-12-2017.
 */

public class RoupaBDTable extends BDHelper {

    private SQLiteDatabase database;

    private static final String TABLE_NAME = "cat_roupa";

    private static final String ID_CATEGORIA_CAT_ROUPA = "id_categoria";
    private static final String MARCA_CAT_ROUPA = "marca";
    private static final String TAMANHO_CAT_ROUPA = "tamanho";
    private static final String ID_TIPO_ROUPA_CAT_ROUPA = "idTipo";

    RoupaBDTable(Context context) {
        super(context);
        this.database = this.getWritableDatabase();
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTableRoupa = "CREATE TABLE " + TABLE_NAME +
                "(" + ID_CATEGORIA_CAT_ROUPA + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                MARCA_CAT_ROUPA + " TEXT NOT NULL," +
                TAMANHO_CAT_ROUPA + " TEXT NOT NULL," +
                "FOREIGN KEY(" + ID_TIPO_ROUPA_CAT_ROUPA + ") REFERENCES " +
                TipoRoupaBDTable.TABLE_NAME + "(id)," +
                "FOREIGN KEY(" + ID_CATEGORIA_CAT_ROUPA + ") REFERENCES " +
                CategoriaBDTable.TABLE_NAME + "(id));";

        db.execSQL(createTableRoupa);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        String dropTableRoupa = "DROP TABLE IF EXISTS " + TABLE_NAME;
        db.execSQL(dropTableRoupa);
        this.onCreate(db);
    }
}
