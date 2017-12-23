package pt.ipleiria.estg.tesp.psi.projsi.sistematrocas.projetosi_android_1718.helpers;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;

import pt.ipleiria.estg.tesp.psi.projsi.sistematrocas.projetosi_android_1718.modelos.Anuncio;

/**
 * Created by JAPorelo on 06-12-2017.
 * Project ProjetoSI_Android_1718 - pt.ipleiria.estg.tesp.psi.projsi.sistematrocas.projetosi_android_1718.helpers
 */

public class AnuncioBDTable extends BDHelper<Anuncio> {

    static final String TABLE_NAME = "anuncios";

    private static final String TITULO_ANUNCIO = "titulo";
    private static final String ID_USER_ANUNCIO = "idUser";
    private static final String ID_CAT_OFERECER_ANUNCIO = "idCatOferecer";
    private static final String QUANT_OFERECER_ANUNCIO = "quantOferecer";
    private static final String ID_CAT_RECEBER_ANUNCIO = "idCatReceber";
    private static final String QUANT_RECEBER_ANUNCIO = "quantReceber";
    private static final String ESTADO_ANUNCIO = "estado";
    private static final String COMENTARIOS_ANUNCIO = "comentarios";
    private static final String DATA_CRIACAO_ANUNCIO = "dataCriacao";
    private static final String DATA_CONCLUSAO_ANUNCIO = "dataConclusao";

    public AnuncioBDTable(Context context) {
        super(context);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTableAnuncios = "CREATE TABLE " + TABLE_NAME +
                "(id INTEGER PRIMARY KEY AUTOINCREMENT," +
                TITULO_ANUNCIO + " TEXT NOT NULL," +
                ID_USER_ANUNCIO + " INTEGER NOT NULL," +
                ID_CAT_OFERECER_ANUNCIO + " INTEGER NOT NULL," +
                QUANT_OFERECER_ANUNCIO + " INTEGER NOT NULL," +
                ID_CAT_RECEBER_ANUNCIO + " INTEGER," +
                QUANT_RECEBER_ANUNCIO + " INTEGER," +
                ESTADO_ANUNCIO + " TEXT NOT NULL," +
                COMENTARIOS_ANUNCIO + " TEXT NOT NULL," +
                DATA_CRIACAO_ANUNCIO + " TEXT DEFAULT CURRENT_DATE," +
                DATA_CONCLUSAO_ANUNCIO + " TEXT," +
                "FOREIGN KEY(" + ID_USER_ANUNCIO + ") REFERENCES " +
                UserBDTable.TABLE_NAME + "(id)," +
                "FOREIGN KEY(" + ID_CAT_OFERECER_ANUNCIO + ") REFERENCES " +
                CategoriaBDTable.TABLE_NAME + "(id)," +
                "FOREIGN KEY(" + ID_CAT_RECEBER_ANUNCIO + ") REFERENCES " +
                CategoriaBDTable.TABLE_NAME + "(id));";

        db.execSQL(createTableAnuncios);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        String dropTableAnuncios = "DROP TABLE IF EXISTS " + TABLE_NAME;
        db.execSQL(dropTableAnuncios);
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
    public ArrayList<Anuncio> select() {

        ArrayList<Anuncio> anuncios = new ArrayList<>();

        String selectAnuncios = "SELECT * FROM " + TABLE_NAME;

        Cursor cursor = database.rawQuery(selectAnuncios, null);

        if(cursor.moveToFirst()) {

            do
            {
                Anuncio anuncio;

                if(cursor.getString(10) != null) {

                    anuncio = new Anuncio(
                            cursor.getString(1),
                            cursor.getLong(2),
                            cursor.getLong(3),
                            cursor.getInt(4),
                            cursor.getLong(5),
                            cursor.getInt(6),
                            cursor.getString(7),
                            cursor.getString(8),
                            cursor.getString(9),
                            cursor.getString(10)
                    );
                } else {

                    anuncio = new Anuncio(
                            cursor.getString(1),
                            cursor.getLong(2),
                            cursor.getLong(3),
                            cursor.getInt(4),
                            cursor.getLong(5),
                            cursor.getInt(6),
                            cursor.getString(7),
                            cursor.getString(8),
                            cursor.getString(9)
                    );
                }

                anuncio.setId(cursor.getLong(0));

                anuncios.add(anuncio);
            }
            while (cursor.moveToNext());

            cursor.close();
        }

        return anuncios;
    }

    @Override
    public ArrayList<Anuncio> select(String whereClause, String[] whereArgs) {

        ArrayList<Anuncio> anuncios = new ArrayList<>();

        String selectAnuncios = "SELECT * FROM " + TABLE_NAME + whereClause;

        Cursor cursor = database.rawQuery(selectAnuncios, whereArgs);

        if(cursor.moveToFirst()) {

            do
            {
                Anuncio anuncio;

                if(cursor.getString(10) != null) {

                    anuncio = new Anuncio(
                            cursor.getString(1),
                            cursor.getLong(2),
                            cursor.getLong(3),
                            cursor.getInt(4),
                            cursor.getLong(5),
                            cursor.getInt(6),
                            cursor.getString(7),
                            cursor.getString(8),
                            cursor.getString(9),
                            cursor.getString(10)
                    );
                } else {

                    anuncio = new Anuncio(
                            cursor.getString(1),
                            cursor.getLong(2),
                            cursor.getLong(3),
                            cursor.getInt(4),
                            cursor.getLong(5),
                            cursor.getInt(6),
                            cursor.getString(7),
                            cursor.getString(8),
                            cursor.getString(9)
                    );
                }

                anuncio.setId(cursor.getLong(0));

                anuncios.add(anuncio);
            }
            while (cursor.moveToNext());

            cursor.close();
        }

        return anuncios;
    }

    @Override
    public Anuncio selectByID(Long id) {

        String selectAnuncios = "SELECT * FROM " + TABLE_NAME + " WHERE id = ?";

        Cursor cursor = database.rawQuery(selectAnuncios, new String[] {id.toString()});

        if(cursor.moveToFirst()) {

            Anuncio anuncio;

            if(cursor.getString(10) != null) {

                anuncio = new Anuncio(
                        cursor.getString(1),
                        cursor.getLong(2),
                        cursor.getLong(3),
                        cursor.getInt(4),
                        cursor.getLong(5),
                        cursor.getInt(6),
                        cursor.getString(7),
                        cursor.getString(8),
                        cursor.getString(9),
                        cursor.getString(10)
                );
            } else {

                anuncio = new Anuncio(
                        cursor.getString(1),
                        cursor.getLong(2),
                        cursor.getLong(3),
                        cursor.getInt(4),
                        cursor.getLong(5),
                        cursor.getInt(6),
                        cursor.getString(7),
                        cursor.getString(8),
                        cursor.getString(9)
                );
            }

            anuncio.setId(cursor.getLong(0));

            cursor.close();

            return anuncio;
        }

        cursor.close();

        return null;
    }

    @Override
    public Anuncio insert(Anuncio anuncio) {

        ContentValues values = new ContentValues();

        values.put(TITULO_ANUNCIO, anuncio.getTitulo());
        values.put(ID_USER_ANUNCIO, anuncio.getIdUser());
        values.put(ID_CAT_OFERECER_ANUNCIO, anuncio.getCatOferecer());
        values.put(QUANT_OFERECER_ANUNCIO, anuncio.getQuantOferecer());
        values.put(ID_CAT_RECEBER_ANUNCIO, anuncio.getCatReceber());
        values.put(QUANT_OFERECER_ANUNCIO, anuncio.getQuantOferecer());
        values.put(ESTADO_ANUNCIO, anuncio.getEstado());
        values.put(COMENTARIOS_ANUNCIO, anuncio.getComentarios());
        values.put(DATA_CRIACAO_ANUNCIO, anuncio.getDataCriacao());
        values.put(DATA_CONCLUSAO_ANUNCIO, anuncio.getDataConclusao());

        Long id = database.insert(TABLE_NAME, null, values);

        if(id >= 0) {
            anuncio.setId(id);
            return anuncio;
        }

        return null;
    }

    @Override
    public boolean update(Anuncio anuncio) {

        ContentValues values = new ContentValues();

        values.put(TITULO_ANUNCIO, anuncio.getTitulo());
        values.put(ID_USER_ANUNCIO, anuncio.getIdUser());
        values.put(ID_CAT_OFERECER_ANUNCIO, anuncio.getCatOferecer());
        values.put(QUANT_OFERECER_ANUNCIO, anuncio.getQuantOferecer());
        values.put(ID_CAT_RECEBER_ANUNCIO, anuncio.getCatReceber());
        values.put(QUANT_RECEBER_ANUNCIO, anuncio.getQuantReceber());
        values.put(ESTADO_ANUNCIO, anuncio.getEstado());
        values.put(COMENTARIOS_ANUNCIO, anuncio.getComentarios());
        values.put(DATA_CRIACAO_ANUNCIO, anuncio.getDataCriacao());
        values.put(DATA_CONCLUSAO_ANUNCIO, anuncio.getDataConclusao());

        return database.update(TABLE_NAME, values, "id = ?", new String[] {anuncio.getId().toString()}) > 0;
    }

    @Override
    public boolean delete(Long id) {
        return database.delete(TABLE_NAME, "id = ?", new String[] {id.toString()}) > 0;
    }
}
