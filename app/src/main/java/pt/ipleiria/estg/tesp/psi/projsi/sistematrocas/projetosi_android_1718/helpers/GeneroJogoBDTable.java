package pt.ipleiria.estg.tesp.psi.projsi.sistematrocas.projetosi_android_1718.helpers;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;

import pt.ipleiria.estg.tesp.psi.projsi.sistematrocas.projetosi_android_1718.modelos.GeneroJogo;

/**
 * Created by JAPorelo on 07-12-2017.
 * Project ProjetoSI_Android_1718 - pt.ipleiria.estg.tesp.psi.projsi.sistematrocas.projetosi_android_1718.helpers
 */

public class GeneroJogoBDTable extends BDHelper<GeneroJogo> {

    static final String TABLE_NAME = "genero_jogo";

    private static final String NOME_GENERO_JOGO = "nome";

    public GeneroJogoBDTable(Context context) {
        super(context);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTableGeneroJogo = "CREATE TABLE " + TABLE_NAME +
                "(id INTEGER PRIMARY KEY AUTOINCREMENT," +
                NOME_GENERO_JOGO + " TEXT NOT NULL);";

        db.execSQL(createTableGeneroJogo);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        String dropTableGeneroJogo = "DROP TABLE IF EXISTS " + TABLE_NAME;
        db.execSQL(dropTableGeneroJogo);
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
    public ArrayList<GeneroJogo> select() {

        ArrayList<GeneroJogo> generos = new ArrayList<>();

        String selectGenerosJogo = "SELECT * FROM " + TABLE_NAME;

        Cursor cursor = database.rawQuery(selectGenerosJogo, null);

        if(cursor.moveToFirst()) {

            do
            {
                GeneroJogo genero = new GeneroJogo(cursor.getLong(0), cursor.getString(1));
                generos.add(genero);
            }
            while (cursor.moveToNext());

            cursor.close();
        }

        return generos;
    }

    @Override
    public ArrayList<GeneroJogo> select(String whereClause, String[] whereArgs) {

        ArrayList<GeneroJogo> generos = new ArrayList<>();

        String selectGenerosJogo = "SELECT * FROM " + TABLE_NAME + whereClause;

        Cursor cursor = database.rawQuery(selectGenerosJogo, whereArgs);

        if(cursor.moveToFirst()) {

            do
            {
                GeneroJogo genero = new GeneroJogo(cursor.getLong(0), cursor.getString(1));
                generos.add(genero);
            }
            while (cursor.moveToNext());

            cursor.close();
        }

        return generos;
    }

    @Override
    public GeneroJogo selectByID(Long id) {

        String selectGenerosJogo = "SELECT * FROM " + TABLE_NAME + " WHERE id = ?";

        Cursor cursor = database.rawQuery(selectGenerosJogo, new String[] {id.toString()});

        if(cursor.moveToFirst()) {

            GeneroJogo genero = new GeneroJogo(cursor.getLong(0), cursor.getString(1));

            cursor.close();

            return genero;
        }

        cursor.close();

        return null;
    }

    @Override
    public GeneroJogo insert(GeneroJogo genero) {

        ContentValues values = new ContentValues();

        values.put("id", genero.getId());
        values.put(NOME_GENERO_JOGO, genero.getNome());

        if((database.insert(TABLE_NAME, null, values)) >= 0) {
            return genero;
        }

        return null;
    }

    @Override
    public boolean update(GeneroJogo genero) {

        ContentValues values = new ContentValues();

        values.put(NOME_GENERO_JOGO, genero.getNome());

        return database.update(TABLE_NAME, values, "id = ?", new String[] {genero.getId().toString()}) > 0;
    }

    @Override
    public boolean delete(Long id) {
        return database.delete(TABLE_NAME, "id = ?", new String[] {id.toString()}) > 0;
    }
}
