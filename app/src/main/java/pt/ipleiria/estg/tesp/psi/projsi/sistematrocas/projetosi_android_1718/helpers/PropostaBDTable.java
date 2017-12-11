package pt.ipleiria.estg.tesp.psi.projsi.sistematrocas.projetosi_android_1718.helpers;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

/**
 * Created by JAPorelo on 07-12-2017.
 * Project ProjetoSI_Android_1718 - pt.ipleiria.estg.tesp.psi.projsi.sistematrocas.projetosi_android_1718.helpers
 */

public class PropostaBDTable extends BDHelper {

    static final String TABLE_NAME = "propostas";

    private static final String ID_CAT_PROPOSTA_PROPOSTA = "catProposta";
    private static final String QUANT_PROPOSTA = "quant";
    private static final String ID_USER_PROPOSTA = "idUser";
    private static final String ID_ANUNCIO_PROPOSTA = "idAnuncio";
    private static final String ESTADO_PROPOSTA = "estado";
    private static final String DATA_PROPOSTA_PROPOSTA = "dataProposta";

    public PropostaBDTable(Context context) {
        super(context);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTablePropostas = "CREATE TABLE " + TABLE_NAME +
                "(id INTEGER PRIMARY KEY AUTOINCREMENT," +
                ID_CAT_PROPOSTA_PROPOSTA + " INTEGER NOT NULL," +
                QUANT_PROPOSTA + " INTEGER NOT NULL," +
                ID_USER_PROPOSTA + " INTEGER NOT NULL," +
                ID_ANUNCIO_PROPOSTA + " INTEGER NOT NULL," +
                ESTADO_PROPOSTA + " TEXT NOT NULL," +
                DATA_PROPOSTA_PROPOSTA + " DATE DEFAULT CURRENT_DATE," +
                "FOREIGN KEY(" + ID_CAT_PROPOSTA_PROPOSTA + ") REFERENCES " +
                CategoriaBDTable.TABLE_NAME + "(id)," +
                "FOREIGN KEY(" + ID_USER_PROPOSTA + ") REFERENCES " +
                UserBDTable.TABLE_NAME + "(id)," +
                "FOREIGN KEY(" + ID_ANUNCIO_PROPOSTA + ") REFERENCES " +
                AnuncioBDTable.TABLE_NAME + "(id));";

        db.execSQL(createTablePropostas);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        String dropTableAnuncios = "DROP TABLE IF EXISTS " + TABLE_NAME;
        db.execSQL(dropTableAnuncios);
        this.onCreate(db);
    }
}
