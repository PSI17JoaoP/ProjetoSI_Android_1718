package pt.ipleiria.estg.tesp.psi.projsi.sistematrocas.projetosi_android_1718.helpers;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

/**
 * Created by JAPorelo on 06-12-2017.
 */

public class UserBDTable extends BDHelper {

    private SQLiteDatabase database;

    static final String TABLE_NAME = "users";

    private static final String USERNAME_USER = "username";
    private static final String PASSWORD_USER = "password_hash";
    private static final String EMAIL_USER = "email";
    private static final String STATUS_USER = "status";

    public UserBDTable(Context context) {
        super(context);
        this.database = this.getWritableDatabase();

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
}
