package pt.ipleiria.estg.tesp.psi.projsi.sistematrocas.projetosi_android_1718.helpers;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

/**
 * Created by JAPorelo on 08-12-2017.
 */

public class EletronicaBDTable extends BDHelper {

    static final String TABLE_NAME = "cat_eletronica";
    static final String ID_CATEGORIA_CAT_ELETRONICA = "id_categoria";
    private static final String MARCA_CAT_ELETRONICA = "marca";
    private static final String DESCRICAO_CAT_ELETRONICA = "descricao";

    EletronicaBDTable(Context context) {
        super(context);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTableEletronica = "CREATE TABLE " + TABLE_NAME +
                "(" + ID_CATEGORIA_CAT_ELETRONICA + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                MARCA_CAT_ELETRONICA + " TEXT NOT NULL," +
                DESCRICAO_CAT_ELETRONICA + " TEXT NOT NULL," +
                "FOREIGN KEY(" + ID_CATEGORIA_CAT_ELETRONICA + ") REFERENCES " +
                CategoriaBDTable.TABLE_NAME + "(id));";

        db.execSQL(createTableEletronica);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        String dropTableEletronica = "DROP TABLE IF EXISTS " + TABLE_NAME;
        db.execSQL(dropTableEletronica);
        this.onCreate(db);
    }
}
