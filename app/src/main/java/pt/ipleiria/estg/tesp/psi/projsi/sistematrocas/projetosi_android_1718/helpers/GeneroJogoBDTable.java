package pt.ipleiria.estg.tesp.psi.projsi.sistematrocas.projetosi_android_1718.helpers;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

/**
 * Created by JAPorelo on 07-12-2017.
 * Project ProjetoSI_Android_1718 - pt.ipleiria.estg.tesp.psi.projsi.sistematrocas.projetosi_android_1718.helpers
 */

public class GeneroJogoBDTable extends BDHelper {

    static final String TABLE_NAME = "genero_jogo";

    private static final String NOME_GENERO_JOGO = "nome";

    public GeneroJogoBDTable(Context context) {
        super(context);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTableGeneroJogo = "CREATE TABLE " + TABLE_NAME +
                "(id INTEGER PRIMARY KEY AUTOINCREMENT," +
                NOME_GENERO_JOGO + " TEXT NOT NULL);";

        db.execSQL(createTableGeneroJogo);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        String dropTableGeneroJogo = "DROP TABLE IF EXISTS " + TABLE_NAME;
        db.execSQL(dropTableGeneroJogo);
        this.onCreate(db);
    }
}
