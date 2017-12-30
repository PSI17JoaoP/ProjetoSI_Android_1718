package pt.ipleiria.estg.tesp.psi.projsi.sistematrocas.projetosi_android_1718.helpers;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;

import pt.ipleiria.estg.tesp.psi.projsi.sistematrocas.projetosi_android_1718.modelos.Categoria;
import pt.ipleiria.estg.tesp.psi.projsi.sistematrocas.projetosi_android_1718.modelos.Computador;
import pt.ipleiria.estg.tesp.psi.projsi.sistematrocas.projetosi_android_1718.modelos.Eletronica;

/**
 * Created by JAPorelo on 08-12-2017.
 * Project ProjetoSI_Android_1718 - pt.ipleiria.estg.tesp.psi.projsi.sistematrocas.projetosi_android_1718.helpers
 */

public class ComputadorBDTable extends BDHelper<Computador> {

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

    @Override
    public void onOpen(SQLiteDatabase db) {
        if(db.isOpen()) {
            Cursor cursor = db.rawQuery("SELECT DISTINCT tbl_name FROM sqlite_master WHERE tbl_name = '" + TABLE_NAME + "';", null);

            if (cursor != null) {

                if (cursor.getCount() == 0) {
                    cursor.close();
                    this.onCreate(db);
                }

                cursor.close();
            }
        }
    }

    @Override
    public ArrayList<Computador> select() {

        ArrayList<Computador> computadores = new ArrayList<>();

        CategoriaBDTable categoriaBDTable = new CategoriaBDTable(super.context);
        EletronicaBDTable eletronicaBDTable = new EletronicaBDTable(super.context);

        String selectComputadores = "SELECT * FROM " + TABLE_NAME;

        Cursor cursor = database.rawQuery(selectComputadores, null);

        if(cursor.moveToFirst()) {

            do
            {
                Eletronica eletronica = eletronicaBDTable.selectByID(cursor.getLong(0));
                Categoria categoria = categoriaBDTable.selectByID(eletronica.getId());

                Computador computador = new Computador(
                        categoria.getNome(),
                        eletronica.getDescricao(),
                        eletronica.getMarca(),
                        cursor.getString(1),
                        cursor.getString(2),
                        cursor.getString(3),
                        cursor.getString(4),
                        cursor.getString(5),
                        cursor.getInt(6)

                );

                computador.setId(categoria.getId());

                computadores.add(computador);
            }
            while (cursor.moveToNext());

            cursor.close();
        }

        return computadores;
    }

    @Override
    public ArrayList<Computador> select(String whereClause, String[] whereArgs) {

        ArrayList<Computador> computadores = new ArrayList<>();

        CategoriaBDTable categoriaBDTable = new CategoriaBDTable(super.context);
        EletronicaBDTable eletronicaBDTable = new EletronicaBDTable(super.context);

        String selectComputadores = "SELECT * FROM " + TABLE_NAME + whereClause;

        Cursor cursor = database.rawQuery(selectComputadores, whereArgs);

        if(cursor.moveToFirst()) {

            do
            {
                Eletronica eletronica = eletronicaBDTable.selectByID(cursor.getLong(0));
                Categoria categoria = categoriaBDTable.selectByID(eletronica.getId());

                Computador computador = new Computador(
                        categoria.getNome(),
                        eletronica.getDescricao(),
                        eletronica.getMarca(),
                        cursor.getString(1),
                        cursor.getString(2),
                        cursor.getString(3),
                        cursor.getString(4),
                        cursor.getString(5),
                        cursor.getInt(6)
                );

                computador.setId(categoria.getId());

                computadores.add(computador);
            }
            while (cursor.moveToNext());

            cursor.close();
        }

        return computadores;
    }

    @Override
    public Computador selectByID(Long id) {

        CategoriaBDTable categoriaBDTable = new CategoriaBDTable(super.context);
        EletronicaBDTable eletronicaBDTable = new EletronicaBDTable(super.context);

        String selectComputadores = "SELECT * FROM " + TABLE_NAME + " WHERE " + ID_ELETRONICA_CAT_COMPUTADOR + " = ?";

        Cursor cursor = database.rawQuery(selectComputadores, new String[] {id.toString()});

        if(cursor.moveToFirst()) {

            Eletronica eletronica = eletronicaBDTable.selectByID(cursor.getLong(0));
            Categoria categoria = categoriaBDTable.selectByID(eletronica.getId());

            Computador computador = new Computador(
                    categoria.getNome(),
                    eletronica.getDescricao(),
                    eletronica.getMarca(),
                    cursor.getString(1),
                    cursor.getString(2),
                    cursor.getString(3),
                    cursor.getString(4),
                    cursor.getString(5),
                    cursor.getInt(6)
            );

            computador.setId(categoria.getId());

            cursor.close();

            return computador;
        }

        cursor.close();

        return null;
    }

    @Override
    public Computador insert(Computador computador) {

        CategoriaBDTable categoriaBDTable = new CategoriaBDTable(super.context);
        EletronicaBDTable eletronicaBDTable = new EletronicaBDTable(super.context);

        Categoria categoriaComputador = new Categoria(computador.getNome());
        categoriaComputador.setId(computador.getId());

        Eletronica eletronicaComputador = new Eletronica(computador.getNome(), computador.getDescricao(), computador.getMarca());
        eletronicaComputador.setId(computador.getId());


        Categoria categoriaInserida = categoriaBDTable.insert(categoriaComputador);

        if(categoriaInserida != null) {

            Eletronica eletronicaInserida = eletronicaBDTable.insert(eletronicaComputador);

            if (eletronicaInserida != null) {

                ContentValues values = new ContentValues();

                values.put(ID_ELETRONICA_CAT_COMPUTADOR, eletronicaInserida.getId());
                values.put(CPU_CAT_COMPUTADOR, computador.getProcessador());
                values.put(RAM_CAT_COMPUTADOR, computador.getRam());
                values.put(HDD_CAT_COMPUTADOR, computador.getHdd());
                values.put(GPU_CAT_COMPUTADOR, computador.getGpu());
                values.put(OS_CAT_COMPUTADOR, computador.getOs());
                values.put(PORTATIL_CAT_COMPUTADOR, computador.getPortatil());

                if (database.insert(TABLE_NAME, null, values) >= 0) {
                    return computador;
                }
            }
        }

        return null;
    }

    @Override
    public boolean update(Computador computador) {

        CategoriaBDTable categoriaBDTable = new CategoriaBDTable(super.context);
        EletronicaBDTable eletronicaBDTable = new EletronicaBDTable(super.context);

        Categoria categoriaComputador = new Categoria(computador.getNome());
        categoriaComputador.setId(computador.getId());

        Eletronica eletronicaComputador = new Eletronica(computador.getNome(), computador.getDescricao(), computador.getMarca());
        eletronicaComputador.setId(computador.getId());

        if(eletronicaBDTable.update(eletronicaComputador) && categoriaBDTable.update(categoriaComputador)) {

            ContentValues values = new ContentValues();

            values.put(CPU_CAT_COMPUTADOR, computador.getProcessador());
            values.put(RAM_CAT_COMPUTADOR, computador.getRam());
            values.put(HDD_CAT_COMPUTADOR, computador.getHdd());
            values.put(GPU_CAT_COMPUTADOR, computador.getGpu());
            values.put(OS_CAT_COMPUTADOR, computador.getOs());
            values.put(PORTATIL_CAT_COMPUTADOR, computador.getPortatil());

            return database.update(TABLE_NAME, values, ID_ELETRONICA_CAT_COMPUTADOR + " = ?", new String[] {computador.getId().toString()}) > 0;
        } else {
            return false;
        }
    }

    @Override
    public boolean delete(Long id) {
        return database.delete(TABLE_NAME, ID_ELETRONICA_CAT_COMPUTADOR + " = ?", new String[]{id.toString()}) > 0
                && database.delete(EletronicaBDTable.TABLE_NAME, EletronicaBDTable.ID_CATEGORIA_CAT_ELETRONICA + " = ?", new String[]{id.toString()}) > 0
                && database.delete(CategoriaBDTable.TABLE_NAME, "id = ?", new String[]{id.toString()}) > 0;
    }
}
