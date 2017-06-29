package fpt.doan.tap.hcm.practic;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Thuans on 6/29/2017.
 */

public class DBUtils {
    static final String KEY_ROWID = "id";
    static final String KEY_NAME = "name";
    static final String KEY_DATE = "date";
    static final String DATABASE_NAME = "MyDB";
    static final String DATABASE_TABLE = "dates";
    static final int DATABASE_VERSION = 1;
    static final String DATABASE_CREATE =
            "create table dates ("
            +KEY_ROWID+" integer primary key autoincrement, "
                    + KEY_NAME +" text not null,"
                + KEY_DATE +  " text not null);";
    final Context context;
    DatabaseHelper DBHelper;
    SQLiteDatabase db;

    public DBUtils(Context ctx) {
        this.context = ctx;
        DBHelper = new DatabaseHelper(context);
    }

    private static class DatabaseHelper extends SQLiteOpenHelper {
        DatabaseHelper(Context context) {
            super(context, DATABASE_NAME, null, DATABASE_VERSION);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            try {
                db.execSQL(DATABASE_CREATE);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            db.execSQL("DROP TABLE IF EXISTS dates");
            onCreate(db);
        }
    }

    //---opens the database---
    public DBUtils open() throws SQLException {
        db = DBHelper.getWritableDatabase();
        return this;
    }

    //---closes the database---
    public void close() {
        DBHelper.close();
    }

    //---insert a date into the database---
    public long insertDate(String name, String date) {
        ContentValues initialValues = new ContentValues();
        initialValues.put(KEY_NAME, name);
        initialValues.put(KEY_DATE, date);
        return db.insert(DATABASE_TABLE, null, initialValues);
    }

    //---deletes a particular date---
    public boolean deleteDate(long rowId) {
        return db.delete(DATABASE_TABLE, KEY_ROWID + "=" + rowId, null) > 0;
    }

    //---retrieves all the dates---
    public Cursor getAllDates() {
        return db.query(DATABASE_TABLE, new String[]{KEY_ROWID, KEY_NAME,
                KEY_DATE}, null, null, null, null, null);
    }

    //---retrieves a particular date---
    public Cursor getDate(long rowId) throws SQLException {
        Cursor mCursor =
                db.query(true, DATABASE_TABLE, new String[]{KEY_ROWID,
                                KEY_NAME, KEY_DATE}, KEY_ROWID + "=" + rowId, null,
                        null, null, null, null);
        if (mCursor != null) {
            mCursor.moveToFirst();
        }
        return mCursor;
    }

    //---updates a date---
    public boolean updateDate(long rowId, String name, String date) {
        ContentValues args = new ContentValues();
        args.put(KEY_NAME, name);
        args.put(KEY_DATE, date);
        return db.update(DATABASE_TABLE, args, KEY_ROWID + "=" + rowId, null) > 0;
    }

}
