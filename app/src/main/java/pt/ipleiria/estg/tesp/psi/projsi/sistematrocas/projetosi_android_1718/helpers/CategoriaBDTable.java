package pt.ipleiria.estg.tesp.psi.projsi.sistematrocas.projetosi_android_1718.helpers;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;

import pt.ipleiria.estg.tesp.psi.projsi.sistematrocas.projetosi_android_1718.modelos.Categoria;

/**
 * Created by JAPorelo on 07-12-2017.
 */

public class CategoriaBDTable extends BDHelper<Categoria> {

    static final String TABLE_NAME = "categorias";

    private static final String NOME_CATEGORIA = "nome";

    CategoriaBDTable(Context context) {
        super(context);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTableCategorias = "CREATE TABLE " + TABLE_NAME +
                "(id INTEGER PRIMARY KEY AUTOINCREMENT," +
                NOME_CATEGORIA + " TEXT NOT NULL);";

        db.execSQL(createTableCategorias);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        String dropTableCategorias = "DROP TABLE IF EXISTS " + TABLE_NAME;
        db.execSQL(dropTableCategorias);
        this.onCreate(db);
    }

    @Override
    public ArrayList<Categoria> select() {

        ArrayList<Categoria> categorias = new ArrayList<>();

        String selectCategorias = "SELECT * FROM " + TABLE_NAME;

        Cursor cursor = database.rawQuery(selectCategorias, null);

        if(cursor.moveToFirst()) {

            do
            {
                Categoria categoria = new Categoria(
                        cursor.getString(1)
                );

                categoria.setId(cursor.getLong(0));

                categorias.add(categoria);
            }
            while (cursor.moveToNext());

            cursor.close();
        }

        return categorias;
    }

    @Override
    public Categoria insert(Categoria categoria) {
        ContentValues values = new ContentValues();

        values.put(NOME_CATEGORIA, categoria.getNome());

        Long id = database.insert(TABLE_NAME, null, values);

        if(id >= 0) {
            categoria.setId(id);
            return categoria;
        }

        return null;
    }

    @Override
    public boolean update(Categoria categoria) {

        ContentValues values = new ContentValues();

        values.put(NOME_CATEGORIA, categoria.getNome());

        return database.update(TABLE_NAME, values, "id = ?", new String[] {categoria.getId().toString()}) > 0;
    }

    @Override
    public boolean delete(Long id) {
        return database.delete(TABLE_NAME, "id = ?", new String[] {id.toString()}) > 0;
    }
}
