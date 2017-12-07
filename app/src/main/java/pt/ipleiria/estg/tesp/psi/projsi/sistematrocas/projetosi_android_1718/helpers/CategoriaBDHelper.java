package pt.ipleiria.estg.tesp.psi.projsi.sistematrocas.projetosi_android_1718.helpers;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

/**
 * Created by JAPorelo on 07-12-2017.
 */

public class CategoriaBDHelper extends BDHelper {

    private SQLiteDatabase database;

    static final String TABLE_NAME = "categorias";

    private static final String NOME_CATEGORIA = "nome";

    protected CategoriaBDHelper(Context context) {
        super(context);
        this.database = this.getWritableDatabase();
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTableCategorias = "CREATE TABLE " + TABLE_NAME +
                "(id INTEGER PRIMARY KEY AUTOINCREMENT," +
                NOME_CATEGORIA + " TEXT NOT NULL);";

        db.execSQL(createTableCategorias);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        String dropTableCategorias = "DROP TABLE IF EXISTS " + TABLE_NAME;
        db.execSQL(dropTableCategorias);
        this.onCreate(db);
    }
}
