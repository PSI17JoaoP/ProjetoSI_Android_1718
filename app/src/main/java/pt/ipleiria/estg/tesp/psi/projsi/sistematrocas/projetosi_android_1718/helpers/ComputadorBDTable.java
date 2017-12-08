package pt.ipleiria.estg.tesp.psi.projsi.sistematrocas.projetosi_android_1718.helpers;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

/**
 * Created by JAPorelo on 08-12-2017.
 */

public class ComputadorBDTable extends BDHelper {

    private static final String TABLE_NAME = "cat_computador";

    private static final String ID_ELETRONICA_CAT_COMPUTADOR = "id_eletronica";
    private static final String CPU_CAT_COMPUTADOR = "processador";
    private static final String RAM_CAT_COMPUTADOR = "ram";
    private static final String HDD_CAT_COMPUTADOR = "hdd";
    private static final String GPU_CAT_COMPUTADOR = "gpu";
    private static final String OS_CAT_COMPUTADOR = "os";
    private static final String PORTATIL_CAT_COMPUTADOR = "portatil";

    public ComputadorBDTable(Context context) {
        super(context);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTableComputador = "CREATE TABLE " + TABLE_NAME +
                "(" + ID_ELETRONICA_CAT_COMPUTADOR + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                CPU_CAT_COMPUTADOR + " TEXT NOT NULL," +
                RAM_CAT_COMPUTADOR + " TEXT NOT NULL," +
                HDD_CAT_COMPUTADOR + " TEXT NOT NULL," +
                GPU_CAT_COMPUTADOR + " TEXT NOT NULL," +
                OS_CAT_COMPUTADOR + " TEXT NOT NULL," +
                PORTATIL_CAT_COMPUTADOR + " INTEGER NOT NULL," +
                "FOREIGN KEY(" + ID_ELETRONICA_CAT_COMPUTADOR + ") REFERENCES " +
                EletronicaBDTable.TABLE_NAME + "(" + EletronicaBDTable.ID_CATEGORIA_CAT_ELETRONICA + "));";

        db.execSQL(createTableComputador);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        String dropTableComputador = "DROP TABLE IF EXISTS " + TABLE_NAME;
        db.execSQL(dropTableComputador);
        this.onCreate(db);
    }
}
