package sg.edu.np.mad.mad_practical2;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;

public class SQLiteDB extends SQLiteOpenHelper {
    private static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "userDB.db";

    public static final String TABLE = "User";
    public static final String NAME = "Name";
    public static final String DESCRIPTION = "Description";
    public static final String ID = "Id";
    public static final String FOLLOWED = "Followed";

    public SQLiteDB(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, DATABASE_NAME, factory, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String CREATE_USER_TABLE = "CREATE TABLE " +
                TABLE +
                "(" + NAME + " TEXT," +
                DESCRIPTION + " TEXT," +
                ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                FOLLOWED + " INTEGER);";
        Log.d("SQLiteDB", CREATE_USER_TABLE);

        sqLiteDatabase.execSQL(CREATE_USER_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE);
        onCreate(sqLiteDatabase);
    }

    public void addUser(User user) {
        ContentValues values = new ContentValues();
        values.put(NAME, user.name);
        values.put(DESCRIPTION, user.description);
        values.put(FOLLOWED, user.followed? 1:0);

        SQLiteDatabase db = this.getWritableDatabase();
        db.insert(TABLE, null, values);
        db.close();
    }

    public ArrayList<User> getUsers() {
        String dbQuery = "SELECT * FROM " + TABLE;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(dbQuery, null);

        ArrayList<User> users = new ArrayList<>();
        if (cursor.moveToNext()) {
            User user = new User();
            user.name = cursor.getString(0);
            user.description = cursor.getString(1);
            user.id = cursor.getInt(2);
            user.followed = 1==cursor.getInt(3);
            cursor.close();
        }
        db.close();
        return users;
    }

    public void updateUser(User user) {
        String query = "SELECT " + FOLLOWED + " FROM " + TABLE +
                " WHERE " + ID + " = " + Integer.toString(user.id);
        ContentValues values = new ContentValues();
        values.put(FOLLOWED, user.followed);
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(query, null);

        if (cursor.moveToFirst()) {
            db.update(TABLE, values, Integer.toString(user.id), null);
        }
        db.close();
    }
}
