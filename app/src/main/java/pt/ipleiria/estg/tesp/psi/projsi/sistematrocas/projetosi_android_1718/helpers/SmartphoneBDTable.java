package pt.ipleiria.estg.tesp.psi.projsi.sistematrocas.projetosi_android_1718.helpers;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;

import pt.ipleiria.estg.tesp.psi.projsi.sistematrocas.projetosi_android_1718.modelos.Categoria;
import pt.ipleiria.estg.tesp.psi.projsi.sistematrocas.projetosi_android_1718.modelos.Eletronica;
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

    @Override
    public ArrayList<Smartphone> select() {

        ArrayList<Smartphone> smartphones = new ArrayList<>();

        CategoriaBDTable categoriaBDTable = new CategoriaBDTable(super.context);
        EletronicaBDTable eletronicaBDTable = new EletronicaBDTable(super.context);

        String selectSmartphones = "SELECT * FROM " + TABLE_NAME;

        Cursor cursor = database.rawQuery(selectSmartphones, null);

        if(cursor.moveToFirst()) {

            do
            {
                Eletronica eletronica = eletronicaBDTable.selectByID(cursor.getLong(0));
                Categoria categoria = categoriaBDTable.selectByID(eletronica.getId());

                Smartphone smartphone = new Smartphone(
                        categoria.getNome(),
                        eletronica.getDescricao(),
                        eletronica.getMarca(),
                        cursor.getString(1),
                        cursor.getString(2),
                        cursor.getString(3),
                        cursor.getString(4),
                        cursor.getString(5)
                );

                smartphone.setId(categoria.getId());

                smartphones.add(smartphone);
            }
            while (cursor.moveToNext());

            cursor.close();
        }

        return smartphones;
    }

    @Override
    public ArrayList<Smartphone> select(String whereClause, String[] whereArgs) {

        ArrayList<Smartphone> smartphones = new ArrayList<>();

        CategoriaBDTable categoriaBDTable = new CategoriaBDTable(super.context);
        EletronicaBDTable eletronicaBDTable = new EletronicaBDTable(super.context);

        String selectSmartphones = "SELECT * FROM " + TABLE_NAME + whereClause;

        Cursor cursor = database.rawQuery(selectSmartphones, whereArgs);

        if(cursor.moveToFirst()) {

            do
            {
                Eletronica eletronica = eletronicaBDTable.selectByID(cursor.getLong(0));
                Categoria categoria = categoriaBDTable.selectByID(eletronica.getId());

                Smartphone smartphone = new Smartphone(
                        categoria.getNome(),
                        eletronica.getDescricao(),
                        eletronica.getMarca(),
                        cursor.getString(1),
                        cursor.getString(2),
                        cursor.getString(3),
                        cursor.getString(4),
                        cursor.getString(5)
                );

                smartphone.setId(categoria.getId());

                smartphones.add(smartphone);
            }
            while (cursor.moveToNext());

            cursor.close();
        }

        return smartphones;
    }

    @Override
    public Smartphone selectByID(Long id) {

        CategoriaBDTable categoriaBDTable = new CategoriaBDTable(super.context);
        EletronicaBDTable eletronicaBDTable = new EletronicaBDTable(super.context);

        String selectSmartphones = "SELECT * FROM " + TABLE_NAME + " WHERE " + ID_ELETRONICA_CAT_SMARTPHONE + " = ?";

        Cursor cursor = database.rawQuery(selectSmartphones, new String[] {id.toString()});

        if(cursor.moveToFirst()) {

            Eletronica eletronica = eletronicaBDTable.selectByID(cursor.getLong(0));
            Categoria categoria = categoriaBDTable.selectByID(eletronica.getId());

            Smartphone smartphone = new Smartphone(
                    categoria.getNome(),
                    eletronica.getDescricao(),
                    eletronica.getMarca(),
                    cursor.getString(1),
                    cursor.getString(2),
                    cursor.getString(3),
                    cursor.getString(4),
                    cursor.getString(5)
            );

            smartphone.setId(categoria.getId());

            cursor.close();

            return smartphone;
        }

        cursor.close();

        return null;
    }

    @Override
    public Smartphone insert(Smartphone smartphone) {

        CategoriaBDTable categoriaBDTable = new CategoriaBDTable(super.context);
        EletronicaBDTable eletronicaBDTable = new EletronicaBDTable(super.context);

        Categoria categoriaSmartphone = new Categoria(smartphone.getNome());
        Eletronica eletronicaSmartphone = new Eletronica(smartphone.getNome(), smartphone.getDescricao(), smartphone.getMarca());

        Eletronica eletronicaInserida = eletronicaBDTable.insert(eletronicaSmartphone);

        if(eletronicaInserida != null) {

            Categoria categoriaInserida = categoriaBDTable.insert(categoriaSmartphone);

            if (categoriaInserida != null) {

                ContentValues values = new ContentValues();

                values.put(ID_ELETRONICA_CAT_SMARTPHONE, smartphone.getId());
                values.put(CPU_CAT_SMARTPHONE, smartphone.getProcessador());
                values.put(RAM_CAT_SMARTPHONE, smartphone.getRam());
                values.put(HDD_CAT_SMARTPHONE, smartphone.getHdd());
                values.put(OS_CAT_SMARTPHONE, smartphone.getOs());
                values.put(TAMANHO_CAT_SMARTPHONE, smartphone.getTamanho());

                Long id = database.insert(TABLE_NAME, null, values);

                if (id >= 0) {
                    smartphone.setId(id);
                    return smartphone;
                }
            }
        }

        return null;
    }

    @Override
    public boolean update(Smartphone smartphone) {

        CategoriaBDTable categoriaBDTable = new CategoriaBDTable(super.context);
        EletronicaBDTable eletronicaBDTable = new EletronicaBDTable(super.context);

        Categoria categoriaComputador = new Categoria(smartphone.getNome());
        categoriaComputador.setId(smartphone.getId());

        Eletronica eletronicaComputador = new Eletronica(smartphone.getNome(), smartphone.getDescricao(), smartphone.getMarca());
        eletronicaComputador.setId(smartphone.getId());

        if(eletronicaBDTable.update(eletronicaComputador) && categoriaBDTable.update(categoriaComputador)) {

            ContentValues values = new ContentValues();

            values.put(ID_ELETRONICA_CAT_SMARTPHONE, smartphone.getId());
            values.put(CPU_CAT_SMARTPHONE, smartphone.getProcessador());
            values.put(RAM_CAT_SMARTPHONE, smartphone.getRam());
            values.put(HDD_CAT_SMARTPHONE, smartphone.getHdd());
            values.put(OS_CAT_SMARTPHONE, smartphone.getOs());
            values.put(TAMANHO_CAT_SMARTPHONE, smartphone.getTamanho());

            return database.update(TABLE_NAME, values, ID_ELETRONICA_CAT_SMARTPHONE + " = ?", new String[] {smartphone.getId().toString()}) > 0;
        } else {
            return false;
        }
    }

    @Override
    public boolean delete(Long id) {
        return database.delete(TABLE_NAME, ID_ELETRONICA_CAT_SMARTPHONE + " = ?", new String[]{id.toString()}) > 0
                && database.delete(EletronicaBDTable.TABLE_NAME, EletronicaBDTable.ID_CATEGORIA_CAT_ELETRONICA + " = ?", new String[]{id.toString()}) > 0
                && database.delete(CategoriaBDTable.TABLE_NAME, "id = ?", new String[]{id.toString()}) > 0;
    }
}
