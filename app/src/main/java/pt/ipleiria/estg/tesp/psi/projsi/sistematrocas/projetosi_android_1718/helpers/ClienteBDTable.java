package pt.ipleiria.estg.tesp.psi.projsi.sistematrocas.projetosi_android_1718.helpers;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

/**
 * Created by JAPorelo on 07-12-2017.
 */

public class ClienteBDTable extends BDHelper {

    private static final String TABLE_NAME = "clientes";

    private static final String ID_USER_CLIENTE = "id_user";
    private static final String NOME_COMPLETO_CLIENTE = "nome_completo";
    private static final String DATA_NASCIMENTO_CLIENTE = "data_nascimento";
    private static final String TELEFONE_CLIENTE = "telefone";
    private static final String REGIAO_CLIENTE = "regiao";
    private static final String PIN_CLIENTE = "pin";

    public ClienteBDTable(Context context) {
        super(context);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTableClientes = "CREATE TABLE " + TABLE_NAME +
                "(" + ID_USER_CLIENTE + "INTEGER PRIMARY KEY AUTOINCREMENT," +
                NOME_COMPLETO_CLIENTE + " TEXT NOT NULL," +
                DATA_NASCIMENTO_CLIENTE+ " DATE NOT NULL," +
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
