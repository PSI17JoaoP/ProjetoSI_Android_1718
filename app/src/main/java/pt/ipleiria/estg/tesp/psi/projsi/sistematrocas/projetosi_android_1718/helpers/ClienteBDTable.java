package pt.ipleiria.estg.tesp.psi.projsi.sistematrocas.projetosi_android_1718.helpers;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

/**
 * Created by JAPorelo on 07-12-2017.
 */

public class ClienteBDTable extends BDHelper {

    static final String TABLE_NAME = "clientes";
    private static final String ID_USER_CLIENTE = "idUser";
    private static final String NOME_COMPLETO_CLIENTE = "nomeCompleto";
    private static final String DATA_NASC_CLIENTE = "dataNasc";
    private static final String TELEFONE_CLIENTE = "telefone";
    private static final String REGIAO_CLIENTE = "regiao";
    private static final String PIN_CLIENTE = "pin";

    protected ClienteBDTable(Context context) {
        super(context);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTableClientes = "CREATE TABLE " + TABLE_NAME +
                "(" + ID_USER_CLIENTE + "INTEGER PRIMARY KEY AUTOINCREMENT," +
                NOME_COMPLETO_CLIENTE + " TEXT NOT NULL," +
                DATA_NASC_CLIENTE+ " DATE NOT NULL," +
                TELEFONE_CLIENTE + " INTEGER NOT NULL," +
                REGIAO_CLIENTE + " TEXT NOT NULL," +
                PIN_CLIENTE + " INTEGER NOT NULL," +
                "FOREIGN KEY(" + ID_USER_CLIENTE + ") REFERENCES " +
                UserBDTable.TABLE_NAME + "(id));";

        db.execSQL(createTableClientes);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        String dropTableClientes = "DROP TABLE IF EXISTS " + TABLE_NAME;
        db.execSQL(dropTableClientes);
        this.onCreate(db);
    }
}
