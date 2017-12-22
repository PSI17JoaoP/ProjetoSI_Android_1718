package pt.ipleiria.estg.tesp.psi.projsi.sistematrocas.projetosi_android_1718.helpers;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;

import pt.ipleiria.estg.tesp.psi.projsi.sistematrocas.projetosi_android_1718.modelos.Categoria;
import pt.ipleiria.estg.tesp.psi.projsi.sistematrocas.projetosi_android_1718.modelos.Livro;

/**
 * Created by JAPorelo on 07-12-2017.
 * Project ProjetoSI_Android_1718 - pt.ipleiria.estg.tesp.psi.projsi.sistematrocas.projetosi_android_1718.helpers
 */

public class LivroBDTable extends BDHelper<Livro> {

    private static final String TABLE_NAME = "cat_livros";

    private static final String ID_CATEGORIA_CAT_LIVROS = "id_categoria";
    private static final String TITULO_CAT_LIVROS = "titulo";
    private static final String EDITORA_CAT_LIVROS = "editora";
    private static final String AUTOR_CAT_LIVROS = "autor";
    private static final String ISBN_CAT_LIVROS = "isbn";

    public LivroBDTable(Context context) {
        super(context);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTableLivros = "CREATE TABLE " + TABLE_NAME +
                "(" + ID_CATEGORIA_CAT_LIVROS + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                TITULO_CAT_LIVROS + " TEXT NOT NULL," +
                EDITORA_CAT_LIVROS + " TEXT NOT NULL," +
                AUTOR_CAT_LIVROS + " TEXT NOT NULL," +
                ISBN_CAT_LIVROS + " INTEGER NOT NULL," +
                "FOREIGN KEY(" + ID_CATEGORIA_CAT_LIVROS + ") REFERENCES " +
                CategoriaBDTable.TABLE_NAME + "(id));";

        db.execSQL(createTableLivros);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        String dropTableLivros = "DROP TABLE IF EXISTS " + TABLE_NAME;
        db.execSQL(dropTableLivros);
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
    public ArrayList<Livro> select() {

        ArrayList<Livro> livros = new ArrayList<>();

        CategoriaBDTable categoriaBDTable = new CategoriaBDTable(super.context);

        String selectLivros = "SELECT * FROM " + TABLE_NAME;

        Cursor cursor = database.rawQuery(selectLivros, null);

        if(cursor.moveToFirst()) {

            do
            {
                Categoria categoria = categoriaBDTable.selectByID(cursor.getLong(0));

                Livro livro = new Livro(
                        categoria.getNome(),
                        cursor.getString(1),
                        cursor.getString(2),
                        cursor.getString(3),
                        cursor.getInt(4)
                );

                livro.setId(categoria.getId());

                livros.add(livro);
            }
            while (cursor.moveToNext());

            cursor.close();
        }

        return livros;
    }

    @Override
    public ArrayList<Livro> select(String whereClause, String[] whereArgs) {

        ArrayList<Livro> livros = new ArrayList<>();

        CategoriaBDTable categoriaBDTable = new CategoriaBDTable(super.context);

        String selectLivros = "SELECT * FROM " + TABLE_NAME + whereClause;

        Cursor cursor = database.rawQuery(selectLivros, whereArgs);

        if(cursor.moveToFirst()) {

            do
            {
                Categoria categoria = categoriaBDTable.selectByID(cursor.getLong(0));

                Livro livro = new Livro(
                        cursor.getString(1),
                        cursor.getString(2),
                        cursor.getString(3),
                        cursor.getString(4),
                        cursor.getInt(5)
                );

                livro.setId(categoria.getId());

                livros.add(livro);
            }
            while (cursor.moveToNext());

            cursor.close();
        }

        return livros;
    }

    @Override
    public Livro selectByID(Long id) {

        CategoriaBDTable categoriaBDTable = new CategoriaBDTable(super.context);

        String selectLivros = "SELECT * FROM " + TABLE_NAME + " WHERE " + ID_CATEGORIA_CAT_LIVROS + " = ?";

        Cursor cursor = database.rawQuery(selectLivros, new String[] {id.toString()});

        if(cursor.moveToFirst()) {

            Categoria categoria = categoriaBDTable.selectByID(cursor.getLong(0));

            Livro livro = new Livro(
                    cursor.getString(1),
                    cursor.getString(2),
                    cursor.getString(3),
                    cursor.getString(4),
                    cursor.getInt(5)
            );

            livro.setId(categoria.getId());

            cursor.close();

            return livro;
        }

        cursor.close();

        return null;
    }

    @Override
    public Livro insert(Livro livro) {

        CategoriaBDTable categoriaBDTable = new CategoriaBDTable(super.context);

        Categoria categoriaLivro = new Categoria(livro.getNome());

        Categoria categoriaInserida = categoriaBDTable.insert(categoriaLivro);

        if(categoriaInserida != null) {

            ContentValues values = new ContentValues();

            values.put(ID_CATEGORIA_CAT_LIVROS, categoriaInserida.getId());
            values.put(TITULO_CAT_LIVROS, livro.getTitulo());
            values.put(EDITORA_CAT_LIVROS, livro.getEditora());
            values.put(AUTOR_CAT_LIVROS, livro.getAutor());
            values.put(ISBN_CAT_LIVROS, livro.getIsbn());

            Long id = database.insert(TABLE_NAME, null, values);

            if (id >= 0) {
                livro.setId(id);
                return livro;
            }
        }

        return null;
    }

    @Override
    public boolean update(Livro livro) {

        CategoriaBDTable categoriaBDTable = new CategoriaBDTable(super.context);

        Categoria categoriaLivro = new Categoria(livro.getNome());
        categoriaLivro.setId(livro.getId());

        if (categoriaBDTable.update(categoriaLivro)) {

            ContentValues values = new ContentValues();

            values.put(ID_CATEGORIA_CAT_LIVROS, categoriaLivro.getId());
            values.put(TITULO_CAT_LIVROS, livro.getTitulo());
            values.put(EDITORA_CAT_LIVROS, livro.getEditora());
            values.put(AUTOR_CAT_LIVROS, livro.getAutor());
            values.put(ISBN_CAT_LIVROS, livro.getIsbn());

            return database.update(TABLE_NAME, values, ID_CATEGORIA_CAT_LIVROS + " = ?", new String[]{livro.getId().toString()}) > 0;
        } else {
            return false;
        }
    }

    @Override
    public boolean delete(Long id) {
        return database.delete(TABLE_NAME, ID_CATEGORIA_CAT_LIVROS + " = ?", new String[]{id.toString()}) > 0 && database.delete(CategoriaBDTable.TABLE_NAME, "id = ?", new String[]{id.toString()}) > 0;
    }
}
