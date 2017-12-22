package pt.ipleiria.estg.tesp.psi.projsi.sistematrocas.projetosi_android_1718.helpers;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;

import pt.ipleiria.estg.tesp.psi.projsi.sistematrocas.projetosi_android_1718.modelos.CategoriaPreferida;

/**
 * Created by JAPorelo on 10-12-2017.
 * Project ProjetoSI_Android_1718 - pt.ipleiria.estg.tesp.psi.projsi.sistematrocas.projetosi_android_1718.helpers
 */

public class CategoriaPreferidaBDTable extends BDHelper<CategoriaPreferida> {

    private static final String TABLE_NAME = "categorias_preferidas";
    private static final String ID_USER_CAT_PREFERIDAS = "id_user";
    private static final String CATEGORIA_CAT_PREFERIDAS = "categoria";

    public CategoriaPreferidaBDTable(Context context) {
        super(context);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTableCategoriasPreferidas = "CREATE TABLE " + TABLE_NAME +
                "(" + ID_USER_CAT_PREFERIDAS + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                CATEGORIA_CAT_PREFERIDAS + " TEXT NOT NULL," +
                "FOREIGN KEY(" + ID_USER_CAT_PREFERIDAS + ") REFERENCES " +
                UserBDTable.TABLE_NAME + "(id),";

        db.execSQL(createTableCategoriasPreferidas);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        String dropTableCategoriasPreferidas = "DROP TABLE IF EXISTS " + TABLE_NAME;
        db.execSQL(dropTableCategoriasPreferidas);
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
    public ArrayList<CategoriaPreferida> select() {

        ArrayList<CategoriaPreferida> categoriasPreferidas = new ArrayList<>();

        String selectCategoriasPreferidas = "SELECT * FROM " + TABLE_NAME;

        Cursor cursor = database.rawQuery(selectCategoriasPreferidas, null);

        if(cursor.moveToFirst()) {

            do
            {
                CategoriaPreferida categoriaPreferida = new CategoriaPreferida(cursor.getLong(0), cursor.getString(1));
                categoriasPreferidas.add(categoriaPreferida);
            }
            while (cursor.moveToNext());

            cursor.close();
        }

        return categoriasPreferidas;
    }

    @Override
    public ArrayList<CategoriaPreferida> select(String whereClause, String[] whereArgs) {

        ArrayList<CategoriaPreferida> categoriasPreferidas = new ArrayList<>();

        String selectCategoriasPreferidas = "SELECT * FROM " + TABLE_NAME + whereClause;

        Cursor cursor = database.rawQuery(selectCategoriasPreferidas, whereArgs);

        if(cursor.moveToFirst()) {

            do
            {
                CategoriaPreferida categoriaPreferida = new CategoriaPreferida(cursor.getLong(0), cursor.getString(1));
                categoriasPreferidas.add(categoriaPreferida);
            }
            while (cursor.moveToNext());

            cursor.close();
        }

        return categoriasPreferidas;
    }

    @Override
    public CategoriaPreferida selectByID(Long id) {

        String selectCategoriasPreferidas = "SELECT * FROM " + TABLE_NAME + " WHERE " + ID_USER_CAT_PREFERIDAS + " = ?";

        Cursor cursor = database.rawQuery(selectCategoriasPreferidas, new String[] {id.toString()});

        if(cursor.moveToFirst()) {

            CategoriaPreferida categoriaPreferida = new CategoriaPreferida(cursor.getLong(0), cursor.getString(1));

            cursor.close();

            return categoriaPreferida;
        }

        cursor.close();

        return null;
    }

    @Override
    public CategoriaPreferida insert(CategoriaPreferida categoria) {

        ContentValues values = new ContentValues();

        values.put(ID_USER_CAT_PREFERIDAS, categoria.getIdUser());
        values.put(CATEGORIA_CAT_PREFERIDAS, categoria.getCategoria());

        if(database.insert(TABLE_NAME, null, values) >= 0) {
            return categoria;
        }

        return null;
    }

    @Override
    public boolean update(CategoriaPreferida categoria) {

        ContentValues values = new ContentValues();

        values.put(ID_USER_CAT_PREFERIDAS, categoria.getIdUser());
        values.put(CATEGORIA_CAT_PREFERIDAS, categoria.getCategoria());

        return database.update(TABLE_NAME, values, ID_USER_CAT_PREFERIDAS + " = ?", new String[] {categoria.getIdUser().toString()}) > 0;
    }

    @Override
    public boolean delete(Long id) {
        return database.delete(TABLE_NAME, ID_USER_CAT_PREFERIDAS + " = ?", new String[] {id.toString()}) > 0;
    }
}
