package pt.ipleiria.estg.tesp.psi.projsi.sistematrocas.projetosi_android_1718.helpers;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;

import pt.ipleiria.estg.tesp.psi.projsi.sistematrocas.projetosi_android_1718.modelos.Categoria;
import pt.ipleiria.estg.tesp.psi.projsi.sistematrocas.projetosi_android_1718.modelos.Roupa;

/**
 * Created by JAPorelo on 07-12-2017.
 * Project ProjetoSI_Android_1718 - pt.ipleiria.estg.tesp.psi.projsi.sistematrocas.projetosi_android_1718.helpers
 */

public class RoupaBDTable extends BDHelper<Roupa> {

    private static final String TABLE_NAME = "cat_roupa";

    private static final String ID_CATEGORIA_CAT_ROUPA = "id_categoria";
    private static final String MARCA_CAT_ROUPA = "marca";
    private static final String TAMANHO_CAT_ROUPA = "tamanho";
    private static final String ID_TIPO_ROUPA_CAT_ROUPA = "id_tipo";

    public RoupaBDTable(Context context) {
        super(context);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTableRoupa = "CREATE TABLE " + TABLE_NAME +
                "(" + ID_CATEGORIA_CAT_ROUPA + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                MARCA_CAT_ROUPA + " TEXT NOT NULL," +
                TAMANHO_CAT_ROUPA + " TEXT NOT NULL," +
                ID_TIPO_ROUPA_CAT_ROUPA + " INTEGER NOT NULL" +
                "FOREIGN KEY(" + ID_TIPO_ROUPA_CAT_ROUPA + ") REFERENCES " +
                TipoRoupaBDTable.TABLE_NAME + "(id)," +
                "FOREIGN KEY(" + ID_CATEGORIA_CAT_ROUPA + ") REFERENCES " +
                CategoriaBDTable.TABLE_NAME + "(id));";

        db.execSQL(createTableRoupa);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        String dropTableRoupa = "DROP TABLE IF EXISTS " + TABLE_NAME;
        db.execSQL(dropTableRoupa);
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
    public ArrayList<Roupa> select() {

        ArrayList<Roupa> roupas = new ArrayList<>();

        CategoriaBDTable categoriaBDTable = new CategoriaBDTable(super.context);

        String selectRoupas = "SELECT * FROM " + TABLE_NAME;

        Cursor cursor = database.rawQuery(selectRoupas, null);

        if(cursor.moveToFirst()) {

            do
            {
                Categoria categoria = categoriaBDTable.selectByID(cursor.getLong(0));

                Roupa roupa = new Roupa(
                        categoria.getNome(),
                        cursor.getString(2),
                        cursor.getString(3),
                        cursor.getLong(4)
                );

                roupa.setId(categoria.getId());

                roupas.add(roupa);
            }
            while (cursor.moveToNext());

            cursor.close();
        }

        return roupas;
    }

    @Override
    public ArrayList<Roupa> select(String whereClause, String[] whereArgs) {

        ArrayList<Roupa> roupas = new ArrayList<>();

        CategoriaBDTable categoriaBDTable = new CategoriaBDTable(super.context);

        String selectRoupas = "SELECT * FROM " + TABLE_NAME + whereClause;

        Cursor cursor = database.rawQuery(selectRoupas, whereArgs);

        if(cursor.moveToFirst()) {

            do
            {
                Categoria categoria = categoriaBDTable.selectByID(cursor.getLong(0));

                Roupa roupa = new Roupa(
                        categoria.getNome(),
                        cursor.getString(2),
                        cursor.getString(3),
                        cursor.getLong(4)
                );

                roupa.setId(categoria.getId());

                roupas.add(roupa);
            }
            while (cursor.moveToNext());

            cursor.close();
        }

        return roupas;
    }

    @Override
    public Roupa selectByID(Long id) {

        CategoriaBDTable categoriaBDTable = new CategoriaBDTable(super.context);

        String selectRoupas = "SELECT * FROM " + TABLE_NAME + " WHERE " + ID_CATEGORIA_CAT_ROUPA + " = ?";

        Cursor cursor = database.rawQuery(selectRoupas, new String[] {id.toString()});

        if(cursor.moveToFirst()) {

            Categoria categoria = categoriaBDTable.selectByID(cursor.getLong(0));

            Roupa roupa = new Roupa(
                    categoria.getNome(),
                    cursor.getString(2),
                    cursor.getString(3),
                    cursor.getLong(4)
            );

            roupa.setId(categoria.getId());

            cursor.close();

            return roupa;
        }

        cursor.close();

        return null;
    }

    @Override
    public Roupa insert(Roupa roupa) {

        CategoriaBDTable categoriaBDTable = new CategoriaBDTable(super.context);

        Categoria categoriaRoupa = new Categoria(roupa.getNome());
        categoriaRoupa.setId(roupa.getId());

        Categoria categoriaInserida = categoriaBDTable.insert(categoriaRoupa);

        if(categoriaInserida != null) {

            ContentValues values = new ContentValues();

            values.put(ID_CATEGORIA_CAT_ROUPA, categoriaInserida.getId());
            values.put(MARCA_CAT_ROUPA, roupa.getMarca());
            values.put(TAMANHO_CAT_ROUPA, roupa.getTamanho());
            values.put(ID_TIPO_ROUPA_CAT_ROUPA, roupa.getIdTipo());

            if ((database.insert(TABLE_NAME, null, values)) >= 0) {
                return roupa;
            }
        }

        return null;
    }

    @Override
    public boolean update(Roupa roupa) {

        CategoriaBDTable categoriaBDTable = new CategoriaBDTable(super.context);

        Categoria categoriaRoupa = new Categoria(roupa.getNome());
        categoriaRoupa.setId(roupa.getId());

        if(categoriaBDTable.update(categoriaRoupa)) {

            ContentValues values = new ContentValues();

            values.put(MARCA_CAT_ROUPA, roupa.getMarca());
            values.put(TAMANHO_CAT_ROUPA, roupa.getTamanho());
            values.put(ID_TIPO_ROUPA_CAT_ROUPA, roupa.getIdTipo());

            return database.update(TABLE_NAME, values, ID_CATEGORIA_CAT_ROUPA + " = ?", new String[] {roupa.getId().toString()}) > 0;
        } else {
            return false;
        }
    }

    @Override
    public boolean delete(Long id) {
        return database.delete(TABLE_NAME, ID_CATEGORIA_CAT_ROUPA + " = ?", new String[]{id.toString()}) > 0 && database.delete(CategoriaBDTable.TABLE_NAME, "id = ?", new String[]{id.toString()}) > 0;
    }
}
