package pt.ipleiria.estg.tesp.psi.projsi.sistematrocas.projetosi_android_1718.helpers;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;

import pt.ipleiria.estg.tesp.psi.projsi.sistematrocas.projetosi_android_1718.modelos.Proposta;

/**
 * Created by JAPorelo on 07-12-2017.
 * Project ProjetoSI_Android_1718 - pt.ipleiria.estg.tesp.psi.projsi.sistematrocas.projetosi_android_1718.helpers
 */

public class PropostaBDTable extends BDHelper<Proposta> {

    static final String TABLE_NAME = "propostas";

    private static final String ID_CAT_PROPOSTA_PROPOSTA = "catProposta";
    private static final String QUANT_PROPOSTA = "quant";
    private static final String ID_USER_PROPOSTA = "idUser";
    private static final String ID_ANUNCIO_PROPOSTA = "idAnuncio";
    private static final String ESTADO_PROPOSTA = "estado";
    private static final String DATA_PROPOSTA_PROPOSTA = "dataProposta";

    public PropostaBDTable(Context context) {
        super(context);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTablePropostas = "CREATE TABLE " + TABLE_NAME +
                "(id INTEGER PRIMARY KEY AUTOINCREMENT," +
                ID_CAT_PROPOSTA_PROPOSTA + " INTEGER NOT NULL," +
                QUANT_PROPOSTA + " INTEGER NOT NULL," +
                ID_USER_PROPOSTA + " INTEGER NOT NULL," +
                ID_ANUNCIO_PROPOSTA + " INTEGER NOT NULL," +
                ESTADO_PROPOSTA + " TEXT NOT NULL," +
                DATA_PROPOSTA_PROPOSTA + " DATE DEFAULT CURRENT_DATE," +
                "FOREIGN KEY(" + ID_CAT_PROPOSTA_PROPOSTA + ") REFERENCES " +
                CategoriaBDTable.TABLE_NAME + "(id)," +
                "FOREIGN KEY(" + ID_USER_PROPOSTA + ") REFERENCES " +
                UserBDTable.TABLE_NAME + "(id)," +
                "FOREIGN KEY(" + ID_ANUNCIO_PROPOSTA + ") REFERENCES " +
                AnuncioBDTable.TABLE_NAME + "(id));";

        db.execSQL(createTablePropostas);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        String dropTableAnuncios = "DROP TABLE IF EXISTS " + TABLE_NAME;
        db.execSQL(dropTableAnuncios);
        this.onCreate(db);
    }

    @Override
    public ArrayList<Proposta> select() {

        ArrayList<Proposta> propostas = new ArrayList<>();

        String selectPropostas = "SELECT * FROM " + TABLE_NAME;

        Cursor cursor = database.rawQuery(selectPropostas, null);

        if(cursor.moveToFirst()) {

            do
            {
                Proposta proposta = new Proposta(
                        cursor.getLong(1),
                        cursor.getInt(2),
                        cursor.getLong(3),
                        cursor.getLong(4),
                        cursor.getString(5),
                        cursor.getString(6)
                );

                proposta.setId(cursor.getLong(0));

                propostas.add(proposta);
            }
            while (cursor.moveToNext());

            cursor.close();
        }

        return propostas;
    }

    @Override
    public ArrayList<Proposta> select(String whereClause, String[] whereArgs) {

        ArrayList<Proposta> propostas = new ArrayList<>();

        String selectPropostas = "SELECT * FROM " + TABLE_NAME + whereClause;

        Cursor cursor = database.rawQuery(selectPropostas, whereArgs);

        if(cursor.moveToFirst()) {

            do
            {
                Proposta proposta = new Proposta(
                        cursor.getLong(1),
                        cursor.getInt(2),
                        cursor.getLong(3),
                        cursor.getLong(4),
                        cursor.getString(5),
                        cursor.getString(6)
                );

                proposta.setId(cursor.getLong(0));

                propostas.add(proposta);
            }
            while (cursor.moveToNext());

            cursor.close();
        }

        return propostas;
    }

    @Override
    public Proposta selectByID(Long id) {

        String selectPropostas = "SELECT * FROM " + TABLE_NAME + " WHERE id = ?";

        Cursor cursor = database.rawQuery(selectPropostas, new String[] {id.toString()});

        if(cursor.moveToFirst()) {

            Proposta proposta = new Proposta(
                    cursor.getLong(1),
                    cursor.getInt(2),
                    cursor.getLong(3),
                    cursor.getLong(4),
                    cursor.getString(5),
                    cursor.getString(6)
            );

            proposta.setId(cursor.getLong(0));

            cursor.close();

            return proposta;
        }

        cursor.close();

        return null;
    }

    @Override
    public Proposta insert(Proposta proposta) {

        ContentValues values = new ContentValues();

        values.put(ID_CAT_PROPOSTA_PROPOSTA, proposta.getCatProposta());
        values.put(QUANT_PROPOSTA, proposta.getQuant());
        values.put(ID_USER_PROPOSTA, proposta.getIdUser());
        values.put(ID_ANUNCIO_PROPOSTA, proposta.getIdAnuncio());
        values.put(ESTADO_PROPOSTA, proposta.getEstado());
        values.put(DATA_PROPOSTA_PROPOSTA, proposta.getDataProposta());

        Long id = database.insert(TABLE_NAME, null, values);

        if(id >= 0) {
            proposta.setId(id);
            return proposta;
        }

        return null;
    }

    @Override
    public boolean update(Proposta proposta) {

        ContentValues values = new ContentValues();

        values.put(ID_CAT_PROPOSTA_PROPOSTA, proposta.getCatProposta());
        values.put(QUANT_PROPOSTA, proposta.getQuant());
        values.put(ID_USER_PROPOSTA, proposta.getIdUser());
        values.put(ID_ANUNCIO_PROPOSTA, proposta.getIdAnuncio());
        values.put(ESTADO_PROPOSTA, proposta.getEstado());
        values.put(DATA_PROPOSTA_PROPOSTA, proposta.getDataProposta());

        return database.update(TABLE_NAME, values, "id = ?", new String[] {proposta.getId().toString()}) > 0;
    }

    @Override
    public boolean delete(Long id) {
        return database.delete(TABLE_NAME, "id = ?", new String[] {id.toString()}) > 0;
    }
}
