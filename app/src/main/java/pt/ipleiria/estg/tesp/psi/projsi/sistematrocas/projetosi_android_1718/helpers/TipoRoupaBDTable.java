package pt.ipleiria.estg.tesp.psi.projsi.sistematrocas.projetosi_android_1718.helpers;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;

import pt.ipleiria.estg.tesp.psi.projsi.sistematrocas.projetosi_android_1718.modelos.TipoRoupa;

/**
 * Created by JAPorelo on 07-12-2017.
 * Project ProjetoSI_Android_1718 - pt.ipleiria.estg.tesp.psi.projsi.sistematrocas.projetosi_android_1718.helpers
 */

public class TipoRoupaBDTable extends BDHelper<TipoRoupa> {

    static final String TABLE_NAME = "tipo_roupa";

    private static final String NOME_TIPO_ROUPA = "nome";

    public TipoRoupaBDTable(Context context) {
        super(context);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTableTipoRoupa = "CREATE TABLE " + TABLE_NAME +
                "(id INTEGER PRIMARY KEY AUTOINCREMENT," +
                NOME_TIPO_ROUPA + " TEXT NOT NULL);";

        db.execSQL(createTableTipoRoupa);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        String dropTableTipoRoupa = "DROP TABLE IF EXISTS " + TABLE_NAME;
        db.execSQL(dropTableTipoRoupa);
        this.onCreate(db);
    }

    @Override
    public ArrayList<TipoRoupa> select() {

        ArrayList<TipoRoupa> tipos = new ArrayList<>();

        String selectTiposRoupa = "SELECT * FROM " + TABLE_NAME;

        Cursor cursor = database.rawQuery(selectTiposRoupa, null);

        if(cursor.moveToFirst()) {

            do
            {
                TipoRoupa tipo = new TipoRoupa(cursor.getLong(0), cursor.getString(1));
                tipos.add(tipo);
            }
            while (cursor.moveToNext());

            cursor.close();
        }

        return tipos;
    }

    @Override
    public ArrayList<TipoRoupa> select(String whereClause, String[] whereArgs) {

        ArrayList<TipoRoupa> tipos = new ArrayList<>();

        String selectTiposRoupa = "SELECT * FROM " + TABLE_NAME + whereClause;

        Cursor cursor = database.rawQuery(selectTiposRoupa, whereArgs);

        if(cursor.moveToFirst()) {

            do
            {
                TipoRoupa tipo = new TipoRoupa(cursor.getLong(0), cursor.getString(1));
                tipos.add(tipo);
            }
            while (cursor.moveToNext());

            cursor.close();
        }

        return tipos;
    }

    @Override
    public TipoRoupa selectByID(Long id) {

        String selectTiposRoupa = "SELECT * FROM " + TABLE_NAME + " WHERE id = ?";

        Cursor cursor = database.rawQuery(selectTiposRoupa, new String[] {id.toString()});

        if(cursor.moveToFirst()) {

            TipoRoupa tipo = new TipoRoupa(cursor.getLong(0), cursor.getString(1));

            cursor.close();

            return tipo;
        }

        cursor.close();

        return null;
    }

    @Override
    public TipoRoupa insert(TipoRoupa tipo) {

        ContentValues values = new ContentValues();

        values.put(NOME_TIPO_ROUPA, tipo.getNome());

        Long id = database.insert(TABLE_NAME, null, values);

        if(id >= 0) {
            tipo.setId(id);
            return tipo;
        }

        return null;
    }

    @Override
    public boolean update(TipoRoupa tipo) {

        ContentValues values = new ContentValues();

        values.put(NOME_TIPO_ROUPA, tipo.getNome());

        return database.update(TABLE_NAME, values, "id = ?", new String[] {tipo.getId().toString()}) > 0;
    }

    @Override
    public boolean delete(Long id) {
        return database.delete(TABLE_NAME, "id = ?", new String[] {id.toString()}) > 0;
    }
}
