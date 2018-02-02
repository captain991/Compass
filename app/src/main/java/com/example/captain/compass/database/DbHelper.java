package com.example.captain.compass.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Environment;

import com.example.captain.compass.Application;

/**
 * Created by captain on 2018/1/25.
 */

public class DbHelper extends SQLiteOpenHelper {
    private static final String DB_NAME = Environment.getExternalStorageDirectory() + "/compass.db";
    private static final int DB_VERSION = 1;
    private SQLiteDatabase db;
    private static DbHelper instance = null;

    String CREATE_TABLE_ORDER = "CREATE TABLE form (" +
            " form_id TEXT PRIMARY KEY," +
            " sender_tel TEXT," +
            " receiver_name TEXT," +
            " receiver_address TEXT," +
            " receiver_tel TEXT," +
            " mark TEXT," +
            " receiver_latitude NUMERIC," +
            " receiver_longitude NUMERIC," +
            " state INTEGER" +
            ")";

    public static DbHelper getInstance() {
        if (instance == null) {
            synchronized (DbHelper.class) {
                if (instance == null)
                    instance = new DbHelper(Application.getApplication(), DB_NAME, null, DB_VERSION);
            }
        }
        return instance;
    }

    public DbHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        this.db = db;
        db.execSQL(CREATE_TABLE_ORDER);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
