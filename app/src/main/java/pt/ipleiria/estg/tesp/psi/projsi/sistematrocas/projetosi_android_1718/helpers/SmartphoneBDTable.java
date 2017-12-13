package pt.ipleiria.estg.tesp.psi.projsi.sistematrocas.projetosi_android_1718.helpers;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import pt.ipleiria.estg.tesp.psi.projsi.sistematrocas.projetosi_android_1718.modelos.Smartphone;

/**
 * Created by JAPorelo on 08-12-2017.
 * Project ProjetoSI_Android_1718 - pt.ipleiria.estg.tesp.psi.projsi.sistematrocas.projetosi_android_1718.helpers
 */

public class SmartphoneBDTable extends BDHelper<Smartphone> {

    private static final String TABLE_NAME = "cat_smartphone";

    private static final String ID_ELETRONICA_CAT_SMARTPHONE = "id_eletronica";
    private static final String CPU_CAT_SMARTPHONE = "processador";
    private static final String RAM_CAT_SMARTPHONE = "ram";
    private static final String HDD_CAT_SMARTPHONE = "hdd";
    private static final String OS_CAT_SMARTPHONE = "os";
    private static final String TAMANHO_CAT_SMARTPHONE = "tamanho";

    public SmartphoneBDTable(Context context) {
        super(context);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTableSmartphone = "CREATE TABLE " + TABLE_NAME +
                "(" + ID_ELETRONICA_CAT_SMARTPHONE + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                CPU_CAT_SMARTPHONE+ " TEXT NOT NULL," +
                RAM_CAT_SMARTPHONE + " TEXT NOT NULL," +
                HDD_CAT_SMARTPHONE + " TEXT NOT NULL," +
                OS_CAT_SMARTPHONE + " TEXT NOT NULL," +
                TAMANHO_CAT_SMARTPHONE + " TEXT NOT NULL," +
                "FOREIGN KEY(" + ID_ELETRONICA_CAT_SMARTPHONE + ") REFERENCES " +
                EletronicaBDTable.TABLE_NAME + "(" + EletronicaBDTable.ID_CATEGORIA_CAT_ELETRONICA + "));";

        db.execSQL(createTableSmartphone);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        String dropTableSmartphone = "DROP TABLE IF EXISTS " + TABLE_NAME;
        db.execSQL(dropTableSmartphone);
        this.onCreate(db);
    }
}
