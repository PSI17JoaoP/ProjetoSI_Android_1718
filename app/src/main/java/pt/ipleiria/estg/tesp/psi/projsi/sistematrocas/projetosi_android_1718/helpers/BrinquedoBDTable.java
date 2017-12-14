package pt.ipleiria.estg.tesp.psi.projsi.sistematrocas.projetosi_android_1718.helpers;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;

import pt.ipleiria.estg.tesp.psi.projsi.sistematrocas.projetosi_android_1718.modelos.Brinquedo;
import pt.ipleiria.estg.tesp.psi.projsi.sistematrocas.projetosi_android_1718.modelos.Categoria;

/**
 * Created by JAPorelo on 08-12-2017.
 * Project ProjetoSI_Android_1718 - pt.ipleiria.estg.tesp.psi.projsi.sistematrocas.projetosi_android_1718.helpers
 */

public class BrinquedoBDTable extends BDHelper<Brinquedo> {

    static final String TABLE_NAME = "cat_brinquedo";

    static final String ID_CATEGORIA_CAT_BRINQUEDO = "id_categoria";
    private static final String EDITORA_CAT_BRINQUEDO = "editora";
    private static final String FAIXA_ETARIA_CAT_BRINQUEDO = "faixa_etaria";
    private static final String DESCRICAO_CAT_BRINQUEDO = "descricao";

    public BrinquedoBDTable(Context context) {
        super(context);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTableBrinquedo = "CREATE TABLE " + TABLE_NAME +
                "(" + ID_CATEGORIA_CAT_BRINQUEDO + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                EDITORA_CAT_BRINQUEDO + " TEXT NOT NULL," +
                FAIXA_ETARIA_CAT_BRINQUEDO + " INTEGER NOT NULL," +
                DESCRICAO_CAT_BRINQUEDO + " TEXT NOT NULL," +
                "FOREIGN KEY(" + ID_CATEGORIA_CAT_BRINQUEDO + ") REFERENCES " +
                CategoriaBDTable.TABLE_NAME + "(id));";

        db.execSQL(createTableBrinquedo);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        String dropTableBrinquedo = "DROP TABLE IF EXISTS " + TABLE_NAME;
        db.execSQL(dropTableBrinquedo);
        this.onCreate(db);
    }
    @Override
    public ArrayList<Brinquedo> select() {

        ArrayList<Brinquedo> brinquedos = new ArrayList<>();

        CategoriaBDTable categoriaBDTable = new CategoriaBDTable(super.context);

        String selectBrinquedos = "SELECT * FROM " + TABLE_NAME;

        Cursor cursor = database.rawQuery(selectBrinquedos, null);

        if(cursor.moveToFirst()) {

            do
            {
                Categoria categoria = categoriaBDTable.selectByID(cursor.getLong(0));

                Brinquedo brinquedo = new Brinquedo(
                        categoria.getNome(),
                        cursor.getString(2),
                        cursor.getInt(3),
                        cursor.getString(3)
                );

                brinquedo.setId(categoria.getId());

                brinquedos.add(brinquedo);
            }
            while (cursor.moveToNext());

            cursor.close();
        }

        return brinquedos;
    }

    @Override
    public ArrayList<Brinquedo> select(String whereClause, String[] whereArgs) {

        ArrayList<Brinquedo> brinquedos = new ArrayList<>();

        CategoriaBDTable categoriaBDTable = new CategoriaBDTable(super.context);

        String selectBrinquedos = "SELECT * FROM " + TABLE_NAME + whereClause;

        Cursor cursor = database.rawQuery(selectBrinquedos, whereArgs);

        if(cursor.moveToFirst()) {

            do
            {
                Categoria categoria = categoriaBDTable.selectByID(cursor.getLong(0));

                Brinquedo brinquedo = new Brinquedo(
                        categoria.getNome(),
                        cursor.getString(2),
                        cursor.getInt(3),
                        cursor.getString(3)
                );

                brinquedo.setId(categoria.getId());

                brinquedos.add(brinquedo);
            }
            while (cursor.moveToNext());

            cursor.close();
        }

        return brinquedos;
    }

    @Override
    public Brinquedo selectByID(Long id) {

        CategoriaBDTable categoriaBDTable = new CategoriaBDTable(super.context);

        String selectBrinquedos = "SELECT * FROM " + TABLE_NAME + " WHERE " + ID_CATEGORIA_CAT_BRINQUEDO + " = ?";

        Cursor cursor = database.rawQuery(selectBrinquedos, new String[] {id.toString()});

        if(cursor.moveToFirst()) {

            Categoria categoria = categoriaBDTable.selectByID(cursor.getLong(0));

            Brinquedo brinquedo = new Brinquedo(
                    categoria.getNome(),
                    cursor.getString(2),
                    cursor.getInt(3),
                    cursor.getString(3)
            );

            brinquedo.setId(categoria.getId());

            cursor.close();

            return brinquedo;
        }

        cursor.close();

        return null;
    }

    @Override
    public Brinquedo insert(Brinquedo brinquedo) {

        CategoriaBDTable categoriaBDTable = new CategoriaBDTable(super.context);

        Categoria categoriaBrinquedo = new Categoria(brinquedo.getNome());

        Categoria categoriaInserida = categoriaBDTable.insert(categoriaBrinquedo);

        if(categoriaInserida != null) {

            ContentValues values = new ContentValues();

            values.put(ID_CATEGORIA_CAT_BRINQUEDO, categoriaInserida.getId());
            values.put(EDITORA_CAT_BRINQUEDO, brinquedo.getEditora());
            values.put(FAIXA_ETARIA_CAT_BRINQUEDO, brinquedo.getFaixaEtaria());
            values.put(DESCRICAO_CAT_BRINQUEDO, brinquedo.getDescricao());

            Long id = database.insert(TABLE_NAME, null, values);

            if (id >= 0) {
                brinquedo.setId(id);
                return brinquedo;
            }
        }

        return null;
    }

    @Override
    public boolean update(Brinquedo brinquedo) {

        CategoriaBDTable categoriaBDTable = new CategoriaBDTable(super.context);

        Categoria categoriaBrinquedo = new Categoria(brinquedo.getNome());
        categoriaBrinquedo.setId(brinquedo.getId());

        if(categoriaBDTable.update(categoriaBrinquedo)) {

            ContentValues values = new ContentValues();

            values.put(ID_CATEGORIA_CAT_BRINQUEDO, categoriaBrinquedo.getId());
            values.put(EDITORA_CAT_BRINQUEDO, brinquedo.getEditora());
            values.put(FAIXA_ETARIA_CAT_BRINQUEDO, brinquedo.getFaixaEtaria());
            values.put(DESCRICAO_CAT_BRINQUEDO, brinquedo.getDescricao());

            return database.update(TABLE_NAME, values, ID_CATEGORIA_CAT_BRINQUEDO + " = ?", new String[] {brinquedo.getId().toString()}) > 0;
        } else {
            return false;
        }
    }

    @Override
    public boolean delete(Long id) {
        return database.delete(TABLE_NAME, ID_CATEGORIA_CAT_BRINQUEDO + " = ?", new String[]{id.toString()}) > 0 && database.delete(CategoriaBDTable.TABLE_NAME, "id = ?", new String[]{id.toString()}) > 0;
    }
}
