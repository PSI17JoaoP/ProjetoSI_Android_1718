package pt.ipleiria.estg.tesp.psi.projsi.sistematrocas.projetosi_android_1718.helpers;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

/**
 * Created by JAPorelo on 06-12-2017.
 */

public class AnuncioBDTable extends BDHelper {

    private SQLiteDatabase database;

    static final String TABLE_NAME = "anuncios";

    private static final String TITULO_ANUNCIO = "titulo";
    private static final String ID_USER_ANUNCIO = "idUser";
    private static final String ID_CAT_OFERECER_ANUNCIO = "idCatOferecer";
    private static final String QUANT_OFERECER_ANUNCIO = "quantOferecer";
    private static final String ID_CAT_RECEBER_ANUNCIO = "idCatReceber";
    private static final String QUANT_RECEBER_ANUNCIO = "quantReceber";
    private static final String ESTADO_ANUNCIO = "estado";
    private static final String COMENTARIOS_ANUNCIO = "comentarios";
    private static final String DATA_CRIACAO_ANUNCIO = "dataCriacao";
    private static final String DATA_CONCLUSAO_ANUNCIO = "dataConclusao";

    protected AnuncioBDTable(Context context) {
        super(context);
        this.database = this.getWritableDatabase();
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTableAnuncios = "CREATE TABLE " + TABLE_NAME +
                "(id INTEGER PRIMARY KEY AUTOINCREMENT," +
                TITULO_ANUNCIO + " TEXT NOT NULL," +
                ID_USER_ANUNCIO + " INTEGER NOT NULL," +
                ID_CAT_OFERECER_ANUNCIO + " INTEGER NOT NULL," +
                QUANT_OFERECER_ANUNCIO + " INTEGER NOT NULL," +
                ID_CAT_RECEBER_ANUNCIO + " INTEGER NOT NULL," +
                QUANT_RECEBER_ANUNCIO + " INTEGER NOT NULL," +
                ESTADO_ANUNCIO + " TEXT NOT NULL," +
                COMENTARIOS_ANUNCIO + " TEXT NOT NULL," +
                DATA_CRIACAO_ANUNCIO + " DATE DEFAULT CURRENT_DATE," +
                DATA_CONCLUSAO_ANUNCIO + " DATE," +
                "FOREIGN KEY(" + ID_USER_ANUNCIO + ") REFERENCES " +
                UserBDTable.TABLE_NAME + "(id)," +
                "FOREIGN KEY(" + ID_CAT_OFERECER_ANUNCIO + ") REFERENCES " +
                CategoriaBDHelper.TABLE_NAME + "(id)," +
                "FOREIGN KEY(" + ID_CAT_RECEBER_ANUNCIO + ") REFERENCES " +
                CategoriaBDHelper.TABLE_NAME + "(id));";

        db.execSQL(createTableAnuncios);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        String dropTableAnuncios = "DROP TABLE IF EXISTS " + TABLE_NAME;
        db.execSQL(dropTableAnuncios);
        this.onCreate(db);
    }
}
