package pt.ipleiria.estg.tesp.psi.projsi.sistematrocas.projetosi_android_1718.helpers;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

/**
 * Created by JAPorelo on 07-12-2017.
 */

public class LivroBDTable extends BDHelper {

    private static final String TABLE_NAME = "cat_livros";

    private static final String ID_CATEGORIA_CAT_LIVROS = "id_categoria";
    private static final String TITULO_CAT_LIVROS = "titulo";
    private static final String EDITORA_CAT_LIVROS = "editora";
    private static final String AUTOR_CAT_LIVROS = "autor";
    private static final String ISBN_CAT_LIVROS = "isbn";

    LivroBDTable(Context context) {
        super(context);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTableLivros = "CREATE TABLE " + TABLE_NAME +
                "(" + ID_CATEGORIA_CAT_LIVROS + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                TITULO_CAT_LIVROS + " TEXT NOT NULL," +
                EDITORA_CAT_LIVROS + " TEXT NOT NULL," +
                AUTOR_CAT_LIVROS + " TEXT NOT NULL," +
                ISBN_CAT_LIVROS + " INTEGER NOT NULL," +
                "FOREIGN KEY(" + ID_CATEGORIA_CAT_LIVROS + ") REFERENCES " +
                CategoriaBDTable.TABLE_NAME + "(id));";

        db.execSQL(createTableLivros);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        String dropTableLivros = "DROP TABLE IF EXISTS " + TABLE_NAME;
        db.execSQL(dropTableLivros);
        this.onCreate(db);
    }
}
