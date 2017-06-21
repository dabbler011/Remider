package com.example.akshat.remider.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by akshat on 16/6/17.
 */

public class dbHelper extends SQLiteOpenHelper{

    public static final String DATABASE_NAME = "data.db";

    public static final int DATABASE_VERSION = 1;

    public dbHelper(Context context){
        super(context,DATABASE_NAME,null,DATABASE_VERSION);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS "+dbContract.dbEntry.TABLE_NAME);
        onCreate(db);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        final String SQL_CREATE_TABLE = "CREATE TABLE "+ dbContract.dbEntry.TABLE_NAME+ " ("+
                dbContract.dbEntry._ID +" INTEGER PRIMARY KEY AUTOINCREMENT, "+dbContract.dbEntry.COLUMN_NAME
                + " TEXT UNIQUE NOT NULL, "+dbContract.dbEntry.COLUMN_STATUS + " TEXT NOT NULL);";
        db.execSQL(SQL_CREATE_TABLE);
    }
}
