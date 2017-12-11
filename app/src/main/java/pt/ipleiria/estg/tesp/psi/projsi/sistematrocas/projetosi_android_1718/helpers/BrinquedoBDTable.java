package pt.ipleiria.estg.tesp.psi.projsi.sistematrocas.projetosi_android_1718.helpers;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

/**
 * Created by JAPorelo on 08-12-2017.
 * Project ProjetoSI_Android_1718 - pt.ipleiria.estg.tesp.psi.projsi.sistematrocas.projetosi_android_1718.helpers
 */

public class BrinquedoBDTable extends BDHelper {

    static final String TABLE_NAME = "cat_brinquedo";

    static final String ID_CATEGORIA_CAT_BRINQUEDO = "id_categoria";
    private static final String EDITORA_CAT_BRINQUEDO = "editora";
    private static final String FAIXA_ETARIA_CAT_BRINQUEDO = "faixa_etaria";
    private static final String DESCRICAO_CAT_BRINQUEDO = "descricao";

    public BrinquedoBDTable(Context context) {
        super(context);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTableBrinquedo = "CREATE TABLE " + TABLE_NAME +
                "(" + ID_CATEGORIA_CAT_BRINQUEDO + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                EDITORA_CAT_BRINQUEDO + " TEXT NOT NULL," +
                FAIXA_ETARIA_CAT_BRINQUEDO + " INTEGER NOT NULL," +
                DESCRICAO_CAT_BRINQUEDO + " TEXT NOT NULL," +
                "FOREIGN KEY(" + ID_CATEGORIA_CAT_BRINQUEDO + ") REFERENCES " +
                CategoriaBDTable.TABLE_NAME + "(id));";

        db.execSQL(createTableBrinquedo);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        String dropTableBrinquedo = "DROP TABLE IF EXISTS " + TABLE_NAME;
        db.execSQL(dropTableBrinquedo);
        this.onCreate(db);
    }
}
