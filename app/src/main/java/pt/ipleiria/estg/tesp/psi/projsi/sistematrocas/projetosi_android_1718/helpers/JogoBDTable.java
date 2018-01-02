package pt.ipleiria.estg.tesp.psi.projsi.sistematrocas.projetosi_android_1718.helpers;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;

import pt.ipleiria.estg.tesp.psi.projsi.sistematrocas.projetosi_android_1718.modelos.Brinquedo;
import pt.ipleiria.estg.tesp.psi.projsi.sistematrocas.projetosi_android_1718.modelos.Categoria;
import pt.ipleiria.estg.tesp.psi.projsi.sistematrocas.projetosi_android_1718.modelos.Jogo;

/**
 * Created by JAPorelo on 08-12-2017.
 * Project ProjetoSI_Android_1718 - pt.ipleiria.estg.tesp.psi.projsi.sistematrocas.projetosi_android_1718.helpers
 */

public class JogoBDTable extends BDHelper<Jogo> {

    private static final String TABLE_NAME = "cat_jogo";

    private static final String ID_BRINQUEDO_CAT_JOGO = "id_brinquedo";
    private static final String ID_GENERO_JOGO_CAT_JOGO = "id_genero";
    private static final String PRODUTORA_CAT_JOGO = "produtora";

    public JogoBDTable(Context context) {
        super(context);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTableJogo = "CREATE TABLE " + TABLE_NAME +
                "(" + ID_BRINQUEDO_CAT_JOGO + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                ID_GENERO_JOGO_CAT_JOGO + " INTEGER NOT NULL," +
                PRODUTORA_CAT_JOGO + " TEXT NOT NULL," +
                "FOREIGN KEY(" + ID_GENERO_JOGO_CAT_JOGO + ") REFERENCES " +
                GeneroJogoBDTable.TABLE_NAME + "(id)," +
                "FOREIGN KEY(" + ID_BRINQUEDO_CAT_JOGO + ") REFERENCES " +
                BrinquedoBDTable.TABLE_NAME + "(" + BrinquedoBDTable.ID_CATEGORIA_CAT_BRINQUEDO + "));";

        db.execSQL(createTableJogo);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        String dropTableJogo = "DROP TABLE IF EXISTS " + TABLE_NAME;
        db.execSQL(dropTableJogo);
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
    public ArrayList<Jogo> select() {

        ArrayList<Jogo> jogos = new ArrayList<>();

        CategoriaBDTable categoriaBDTable = new CategoriaBDTable(super.context);
        BrinquedoBDTable brinquedoBDTable = new BrinquedoBDTable(super.context);

        String selectJogos = "SELECT * FROM " + TABLE_NAME;

        Cursor cursor = database.rawQuery(selectJogos, null);

        if(cursor.moveToFirst()) {

            do
            {
                Brinquedo brinquedo = brinquedoBDTable.selectByID(cursor.getLong(0));
                Categoria categoria = categoriaBDTable.selectByID(brinquedo.getId());

                Jogo jogo = new Jogo(
                        categoria.getNome(),
                        brinquedo.getEditora(),
                        brinquedo.getFaixaEtaria(),
                        brinquedo.getDescricao(),
                        cursor.getLong(2),
                        cursor.getString(3)
                );

                jogo.setId(categoria.getId());

                jogos.add(jogo);
            }
            while (cursor.moveToNext());

            cursor.close();
        }

        return jogos;
    }

    @Override
    public ArrayList<Jogo> select(String whereClause, String[] whereArgs) {

        ArrayList<Jogo> jogos = new ArrayList<>();

        CategoriaBDTable categoriaBDTable = new CategoriaBDTable(super.context);
        BrinquedoBDTable brinquedoBDTable = new BrinquedoBDTable(super.context);

        String selectJogos = "SELECT * FROM " + TABLE_NAME + whereClause;

        Cursor cursor = database.rawQuery(selectJogos, whereArgs);

        if(cursor.moveToFirst()) {

            do
            {
                Brinquedo brinquedo = brinquedoBDTable.selectByID(cursor.getLong(0));
                Categoria categoria = categoriaBDTable.selectByID(brinquedo.getId());

                Jogo jogo = new Jogo(
                        categoria.getNome(),
                        brinquedo.getEditora(),
                        brinquedo.getFaixaEtaria(),
                        brinquedo.getDescricao(),
                        cursor.getLong(2),
                        cursor.getString(3)
                );

                jogo.setId(categoria.getId());

                jogos.add(jogo);
            }
            while (cursor.moveToNext());

            cursor.close();
        }

        return jogos;
    }

    @Override
    public Jogo selectByID(Long id) {

        CategoriaBDTable categoriaBDTable = new CategoriaBDTable(super.context);
        BrinquedoBDTable brinquedoBDTable = new BrinquedoBDTable(super.context);

        String selectJogos = "SELECT * FROM " + TABLE_NAME + " WHERE " + ID_BRINQUEDO_CAT_JOGO + " = ?";

        Cursor cursor = database.rawQuery(selectJogos, new String[] {id.toString()});

        if(cursor.moveToFirst()) {

            Brinquedo brinquedo = brinquedoBDTable.selectByID(cursor.getLong(0));
            Categoria categoria = categoriaBDTable.selectByID(brinquedo.getId());

            Jogo jogo = new Jogo(
                    categoria.getNome(),
                    brinquedo.getEditora(),
                    brinquedo.getFaixaEtaria(),
                    brinquedo.getDescricao(),
                    cursor.getLong(2),
                    cursor.getString(3)
            );

            jogo.setId(categoria.getId());

            cursor.close();

            return jogo;
        }

        cursor.close();

        return null;
    }

    @Override
    public Jogo insert(Jogo jogo) {

        BrinquedoBDTable brinquedoBDTable = new BrinquedoBDTable(super.context);

        Brinquedo brinquedoJogo = new Brinquedo(jogo.getNome(), jogo.getEditora(), jogo.getFaixaEtaria(), jogo.getDescricao());
        brinquedoJogo.setId(jogo.getId());

        Brinquedo brinquedoInserida = brinquedoBDTable.insert(brinquedoJogo);

        if (brinquedoInserida != null) {

            ContentValues values = new ContentValues();

            values.put(ID_BRINQUEDO_CAT_JOGO, brinquedoInserida.getId());
            values.put(ID_GENERO_JOGO_CAT_JOGO, jogo.getIdGenero());
            values.put(PRODUTORA_CAT_JOGO, jogo.getProdutora());

            if ((database.insert(TABLE_NAME, null, values)) >= 0) {
                return jogo;
            }
        }

        return null;
    }

    @Override
    public boolean update(Jogo jogo) {

        CategoriaBDTable categoriaBDTable = new CategoriaBDTable(super.context);
        BrinquedoBDTable brinquedoBDTable = new BrinquedoBDTable(super.context);

        Categoria categoriaComputador = new Categoria(jogo.getNome());
        categoriaComputador.setId(jogo.getId());

        Brinquedo brinquedoComputador = new Brinquedo(jogo.getNome(), jogo.getEditora(), jogo.getFaixaEtaria(), jogo.getDescricao());
        brinquedoComputador.setId(jogo.getId());

        if(brinquedoBDTable.update(brinquedoComputador) && categoriaBDTable.update(categoriaComputador)) {

            ContentValues values = new ContentValues();

            values.put(ID_GENERO_JOGO_CAT_JOGO, jogo.getIdGenero());
            values.put(PRODUTORA_CAT_JOGO, jogo.getProdutora());

            return database.update(TABLE_NAME, values, ID_BRINQUEDO_CAT_JOGO + " = ?", new String[] {jogo.getId().toString()}) > 0;
        } else {
            return false;
        }
    }

    @Override
    public boolean delete(Long id) {
        return database.delete(TABLE_NAME, ID_BRINQUEDO_CAT_JOGO + " = ?", new String[]{id.toString()}) > 0
                && database.delete(EletronicaBDTable.TABLE_NAME, EletronicaBDTable.ID_CATEGORIA_CAT_ELETRONICA + " = ?", new String[]{id.toString()}) > 0
                && database.delete(CategoriaBDTable.TABLE_NAME, "id = ?", new String[]{id.toString()}) > 0;
    }
}
