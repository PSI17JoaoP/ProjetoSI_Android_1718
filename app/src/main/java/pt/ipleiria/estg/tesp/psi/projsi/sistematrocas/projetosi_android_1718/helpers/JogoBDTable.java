package pt.ipleiria.estg.tesp.psi.projsi.sistematrocas.projetosi_android_1718.helpers;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import pt.ipleiria.estg.tesp.psi.projsi.sistematrocas.projetosi_android_1718.modelos.Jogo;

/**
 * Created by JAPorelo on 08-12-2017.
 * Project ProjetoSI_Android_1718 - pt.ipleiria.estg.tesp.psi.projsi.sistematrocas.projetosi_android_1718.helpers
 */

public class JogoBDTable extends BDHelper<Jogo> {

    private static final String TABLE_NAME = "cat_jogo";

    private static final String ID_BRINQUEDO_CAT_JOGO = "id_brinquedo";
    private static final String ID_GENERO_JOGO_CAT_JOGO = "id_genero";
    private static final String PRODUTORA_CAT_JOGO = "produtora";

    public JogoBDTable(Context context) {
        super(context);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTableJogo = "CREATE TABLE " + TABLE_NAME +
                "(" + ID_BRINQUEDO_CAT_JOGO + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                ID_GENERO_JOGO_CAT_JOGO + " INTEGER NOT NULL," +
                PRODUTORA_CAT_JOGO + " TEXT NOT NULL," +
                "FOREIGN KEY(" + ID_GENERO_JOGO_CAT_JOGO + ") REFERENCES " +
                GeneroJogoBDTable.TABLE_NAME + "(id)," +
                "FOREIGN KEY(" + ID_BRINQUEDO_CAT_JOGO + ") REFERENCES " +
                BrinquedoBDTable.TABLE_NAME + "(" + BrinquedoBDTable.ID_CATEGORIA_CAT_BRINQUEDO + "));";

        db.execSQL(createTableJogo);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        String dropTableJogo = "DROP TABLE IF EXISTS " + TABLE_NAME;
        db.execSQL(dropTableJogo);
        this.onCreate(db);
    }
}
