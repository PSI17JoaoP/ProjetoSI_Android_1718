package pt.ipleiria.estg.tesp.psi.projsi.sistematrocas.projetosi_android_1718.helpers;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;

import pt.ipleiria.estg.tesp.psi.projsi.sistematrocas.projetosi_android_1718.modelos.Cliente;

/**
 * Created by JAPorelo on 07-12-2017.
 * Project ProjetoSI_Android_1718 - pt.ipleiria.estg.tesp.psi.projsi.sistematrocas.projetosi_android_1718.helpers
 */

public class ClienteBDTable extends BDHelper<Cliente> {

    private static final String TABLE_NAME = "clientes";

    private static final String ID_USER_CLIENTE = "id_user";
    private static final String NOME_COMPLETO_CLIENTE = "nome_completo";
    private static final String DATA_NASCIMENTO_CLIENTE = "data_nascimento";
    private static final String TELEFONE_CLIENTE = "telefone";
    private static final String REGIAO_CLIENTE = "regiao";
    private static final String PIN_CLIENTE = "pin";

    public ClienteBDTable(Context context) {
        super(context);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTableClientes = "CREATE TABLE " + TABLE_NAME +
                "(" + ID_USER_CLIENTE + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                NOME_COMPLETO_CLIENTE + " TEXT NOT NULL," +
                DATA_NASCIMENTO_CLIENTE+ " DATE NOT NULL," +
                TELEFONE_CLIENTE + " INTEGER NOT NULL," +
                REGIAO_CLIENTE + " TEXT NOT NULL," +
                PIN_CLIENTE + " INTEGER NOT NULL," +
                "FOREIGN KEY(" + ID_USER_CLIENTE + ") REFERENCES " +
                UserBDTable.TABLE_NAME + "(id));";

        db.execSQL(createTableClientes);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        String dropTableClientes = "DROP TABLE IF EXISTS " + TABLE_NAME;
        db.execSQL(dropTableClientes);
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
    public ArrayList<Cliente> select() {

        ArrayList<Cliente> clientes = new ArrayList<>();

        String selectClientes = "SELECT * FROM " + TABLE_NAME;

        Cursor cursor = database.rawQuery(selectClientes, null);

        if(cursor.moveToFirst()) {

            do
            {
                Cliente cliente = new Cliente(
                        cursor.getLong(0),
                        cursor.getString(1),
                        cursor.getString(2),
                        cursor.getInt(3),
                        cursor.getString(4),
                        cursor.getString(5)
                );

                clientes.add(cliente);
            }
            while (cursor.moveToNext());

            cursor.close();
        }

        return clientes;
    }

    @Override
    public ArrayList<Cliente> select(String whereClause, String[] whereArgs) {

        ArrayList<Cliente> clientes = new ArrayList<>();

        String selectClientes = "SELECT * FROM " + TABLE_NAME + whereClause;

        Cursor cursor = database.rawQuery(selectClientes, whereArgs);

        if(cursor.moveToFirst()) {

            do
            {
                Cliente cliente = new Cliente(
                        cursor.getLong(0),
                        cursor.getString(1),
                        cursor.getString(2),
                        cursor.getInt(3),
                        cursor.getString(4),
                        cursor.getString(5)
                );

                clientes.add(cliente);
            }
            while (cursor.moveToNext());

            cursor.close();
        }

        return clientes;
    }

    @Override
    public Cliente selectByID(Long id) {

        String selectClientes = "SELECT * FROM " + TABLE_NAME + " WHERE " + ID_USER_CLIENTE + " = ?";

        Cursor cursor = database.rawQuery(selectClientes, new String[] {id.toString()});

        if(cursor.moveToFirst()) {

            Cliente cliente = new Cliente(
                    cursor.getLong(0),
                    cursor.getString(1),
                    cursor.getString(2),
                    cursor.getInt(3),
                    cursor.getString(4),
                    cursor.getString(5)
            );

            cursor.close();

            return cliente;
        }

        cursor.close();

        return null;
    }

    @Override
    public Cliente insert(Cliente cliente) {

        ContentValues values = new ContentValues();

        values.put(ID_USER_CLIENTE, cliente.getIdUser());
        values.put(NOME_COMPLETO_CLIENTE, cliente.getNomeCompleto());
        values.put(DATA_NASCIMENTO_CLIENTE, cliente.getDataNasc());
        values.put(TELEFONE_CLIENTE, cliente.getTelefone());
        values.put(REGIAO_CLIENTE, cliente.getRegiao());
        values.put(PIN_CLIENTE, cliente.getPin());

        if(database.insert(TABLE_NAME, null, values) >= 0) {
            return cliente;
        }

        return null;
    }

    @Override
    public boolean update(Cliente cliente) {

        ContentValues values = new ContentValues();

        values.put(ID_USER_CLIENTE, cliente.getIdUser());
        values.put(NOME_COMPLETO_CLIENTE, cliente.getNomeCompleto());
        values.put(DATA_NASCIMENTO_CLIENTE, cliente.getDataNasc());
        values.put(TELEFONE_CLIENTE, cliente.getTelefone());
        values.put(REGIAO_CLIENTE, cliente.getRegiao());
        values.put(PIN_CLIENTE, cliente.getPin());

        return database.update(TABLE_NAME, values, ID_USER_CLIENTE + " = ?", new String[] {cliente.getIdUser().toString()}) > 0;
    }

    @Override
    public boolean delete(Long id) {
        return database.delete(TABLE_NAME, ID_USER_CLIENTE + " = ?", new String[] {id.toString()}) > 0;
    }
}
