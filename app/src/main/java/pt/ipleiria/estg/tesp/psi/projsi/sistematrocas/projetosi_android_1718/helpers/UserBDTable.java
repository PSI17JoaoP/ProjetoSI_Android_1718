package pt.ipleiria.estg.tesp.psi.projsi.sistematrocas.projetosi_android_1718.helpers;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;

import pt.ipleiria.estg.tesp.psi.projsi.sistematrocas.projetosi_android_1718.modelos.User;

/**
 * Created by JAPorelo on 06-12-2017.
 * Project ProjetoSI_Android_1718 - pt.ipleiria.estg.tesp.psi.projsi.sistematrocas.projetosi_android_1718.helpers
 */

public class UserBDTable extends BDHelper<User> {

    static final String TABLE_NAME = "users";

    private static final String USERNAME_USER = "username";
    private static final String PASSWORD_USER = "password_hash";
    private static final String EMAIL_USER = "email";
    private static final String STATUS_USER = "status";

    public UserBDTable(Context context) {
        super(context);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTableUsers = "CREATE TABLE " + TABLE_NAME +
                "(id INTEGER PRIMARY KEY AUTOINCREMENT," +
                USERNAME_USER + " TEXT NOT NULL," +
                PASSWORD_USER + " TEXT NOT NULL," +
                EMAIL_USER + " TEXT NOT NULL," +
                STATUS_USER + " INTEGER NOT NULL);";

        db.execSQL(createTableUsers);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        String dropTableUsers = "DROP TABLE IF EXISTS " + TABLE_NAME;
        db.execSQL(dropTableUsers);
        this.onCreate(db);
    }

    @Override
    public ArrayList<User> select() {

        ArrayList<User> users = new ArrayList<>();

        String selectUsers = "SELECT * FROM " + TABLE_NAME;

        Cursor cursor = database.rawQuery(selectUsers, null);

        if(cursor.moveToFirst()) {

            do
            {
                User user = new User(cursor.getLong(0), cursor.getString(1), cursor.getString(2),cursor.getString(3), cursor.getInt(4));
                users.add(user);
            }
            while (cursor.moveToNext());

            cursor.close();
        }

        return users;
    }

    @Override
    public ArrayList<User> select(String whereClause, String[] whereArgs) {

        ArrayList<User> users = new ArrayList<>();

        String selectUsers = "SELECT * FROM " + TABLE_NAME + whereClause;

        Cursor cursor = database.rawQuery(selectUsers, whereArgs);

        if(cursor.moveToFirst()) {

            do
            {
                User user = new User(cursor.getLong(0), cursor.getString(1), cursor.getString(2),cursor.getString(3), cursor.getInt(4));
                users.add(user);
            }
            while (cursor.moveToNext());

            cursor.close();
        }

        return users;
    }

    @Override
    public User selectByID(Long id) {

        String selectUsers = "SELECT * FROM " + TABLE_NAME + " WHERE id = ?";

        Cursor cursor = database.rawQuery(selectUsers, new String[] {id.toString()});

        if(cursor.moveToFirst()) {

            User user = new User(cursor.getLong(0), cursor.getString(1), cursor.getString(2),cursor.getString(3), cursor.getInt(4));

            cursor.close();

            return user;
        }

        cursor.close();

        return null;
    }

    @Override
    public User insert(User user) {
        ContentValues values = new ContentValues();

        values.put(USERNAME_USER, user.getUsername());
        values.put(PASSWORD_USER, user.getPasswordHash());
        values.put(EMAIL_USER, user.getEmail());
        values.put(STATUS_USER, user.getStatus());

        Long id = database.insert(TABLE_NAME, null, values);

        if(id >= 0) {
            user.setId(id);
            return user;
        }

        return null;
    }

    @Override
    public boolean update(User user) {
        ContentValues values = new ContentValues();

        values.put(USERNAME_USER, user.getUsername());
        values.put(PASSWORD_USER, user.getPasswordHash());
        values.put(EMAIL_USER, user.getEmail());
        values.put(STATUS_USER, user.getStatus());

        return database.update(TABLE_NAME, values, "id = ?", new String[] {user.getId().toString()}) > 0;
    }

    @Override
    public boolean delete(Long id) {
        return database.delete(TABLE_NAME, "id = ?", new String[] {id.toString()}) > 0;
    }
}
