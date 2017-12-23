package pt.ipleiria.estg.tesp.psi.projsi.sistematrocas.projetosi_android_1718.helpers;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;

import pt.ipleiria.estg.tesp.psi.projsi.sistematrocas.projetosi_android_1718.modelos.Categoria;
import pt.ipleiria.estg.tesp.psi.projsi.sistematrocas.projetosi_android_1718.modelos.Eletronica;

/**
 * Created by JAPorelo on 08-12-2017.
 * Project ProjetoSI_Android_1718 - ${PACKAGE_NAME}
 */

public class EletronicaBDTable extends BDHelper<Eletronica> {

    static final String TABLE_NAME = "cat_eletronica";
    static final String ID_CATEGORIA_CAT_ELETRONICA = "id_categoria";
    private static final String MARCA_CAT_ELETRONICA = "marca";
    private static final String DESCRICAO_CAT_ELETRONICA = "descricao";

    public EletronicaBDTable(Context context) {
        super(context);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTableEletronica = "CREATE TABLE " + TABLE_NAME +
                "(" + ID_CATEGORIA_CAT_ELETRONICA + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                MARCA_CAT_ELETRONICA + " TEXT NOT NULL," +
                DESCRICAO_CAT_ELETRONICA + " TEXT NOT NULL," +
                "FOREIGN KEY(" + ID_CATEGORIA_CAT_ELETRONICA + ") REFERENCES " +
                CategoriaBDTable.TABLE_NAME + "(id));";

        db.execSQL(createTableEletronica);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        String dropTableEletronica = "DROP TABLE IF EXISTS " + TABLE_NAME;
        db.execSQL(dropTableEletronica);
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
    public ArrayList<Eletronica> select() {

        ArrayList<Eletronica> eletronicas = new ArrayList<>();

        CategoriaBDTable categoriaBDTable = new CategoriaBDTable(super.context);

        String selectEletronicas = "SELECT * FROM " + TABLE_NAME;

        Cursor cursor = database.rawQuery(selectEletronicas, null);

        if(cursor.moveToFirst()) {

            do
            {
                Categoria categoria = categoriaBDTable.selectByID(cursor.getLong(0));

                Eletronica eletronica = new Eletronica(
                        categoria.getNome(),
                        cursor.getString(2),
                        cursor.getString(3)
                );

                eletronica.setId(categoria.getId());

                eletronicas.add(eletronica);
            }
            while (cursor.moveToNext());

            cursor.close();
        }

        return eletronicas;
    }

    @Override
    public ArrayList<Eletronica> select(String whereClause, String[] whereArgs) {

        ArrayList<Eletronica> eletronicas = new ArrayList<>();

        CategoriaBDTable categoriaBDTable = new CategoriaBDTable(super.context);

        String selectEletronicas = "SELECT * FROM " + TABLE_NAME + whereClause;

        Cursor cursor = database.rawQuery(selectEletronicas, whereArgs);

        if(cursor.moveToFirst()) {

            do
            {
                Categoria categoria = categoriaBDTable.selectByID(cursor.getLong(0));

                Eletronica eletronica = new Eletronica(
                        categoria.getNome(),
                        cursor.getString(2),
                        cursor.getString(3)
                );

                eletronica.setId(categoria.getId());

                eletronicas.add(eletronica);
            }
            while (cursor.moveToNext());

            cursor.close();
        }

        return eletronicas;
    }

    @Override
    public Eletronica selectByID(Long id) {

        CategoriaBDTable categoriaBDTable = new CategoriaBDTable(super.context);

        String selectEletronicas = "SELECT * FROM " + TABLE_NAME + " WHERE " + ID_CATEGORIA_CAT_ELETRONICA + " = ?";

        Cursor cursor = database.rawQuery(selectEletronicas, new String[] {id.toString()});

        if(cursor.moveToFirst()) {

            Categoria categoria = categoriaBDTable.selectByID(cursor.getLong(0));

            Eletronica eletronica = new Eletronica(
                    categoria.getNome(),
                    cursor.getString(2),
                    cursor.getString(3)
            );

            eletronica.setId(categoria.getId());

            cursor.close();

            return eletronica;
        }

        cursor.close();

        return null;
    }

    @Override
    public Eletronica insert(Eletronica eletronica) {

        CategoriaBDTable categoriaBDTable = new CategoriaBDTable(super.context);

        Categoria categoriaEletronica = new Categoria(eletronica.getNome());

        Categoria categoriaInserida = categoriaBDTable.insert(categoriaEletronica);

        if(categoriaInserida != null) {

            ContentValues values = new ContentValues();

            values.put(ID_CATEGORIA_CAT_ELETRONICA, categoriaInserida.getId());
            values.put(MARCA_CAT_ELETRONICA, eletronica.getMarca());
            values.put(DESCRICAO_CAT_ELETRONICA, eletronica.getDescricao());

            Long id = database.insert(TABLE_NAME, null, values);

            if (id >= 0) {
                eletronica.setId(id);
                return eletronica;
            }
        }

        return null;
    }

    @Override
    public boolean update(Eletronica eletronica) {

        CategoriaBDTable categoriaBDTable = new CategoriaBDTable(super.context);

        Categoria categoriaEletronica = new Categoria(eletronica.getNome());
        categoriaEletronica.setId(eletronica.getId());

        if(categoriaBDTable.update(categoriaEletronica)) {

            ContentValues values = new ContentValues();

            values.put(ID_CATEGORIA_CAT_ELETRONICA, categoriaEletronica.getId());
            values.put(MARCA_CAT_ELETRONICA, eletronica.getMarca());
            values.put(DESCRICAO_CAT_ELETRONICA, eletronica.getDescricao());

            return database.update(TABLE_NAME, values, ID_CATEGORIA_CAT_ELETRONICA + " = ?", new String[] {eletronica.getId().toString()}) > 0;
        } else {
            return false;
        }
    }

    @Override
    public boolean delete(Long id) {
        return database.delete(TABLE_NAME, ID_CATEGORIA_CAT_ELETRONICA + " = ?", new String[]{id.toString()}) > 0 && database.delete(CategoriaBDTable.TABLE_NAME, "id = ?", new String[]{id.toString()}) > 0;
    }
}
