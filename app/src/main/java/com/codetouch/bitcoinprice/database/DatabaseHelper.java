package com.codetouch.bitcoinprice.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by luccasgaluppotanan on 2/8/18.
 */

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final int VERSION_DB = 1;
    private static final String NAME_DB = "bitcoin.db";
    private SQLiteDatabase db;
    public static DatabaseHelper mInstance;

    public static DatabaseHelper getInstance(Context context) {
        if (mInstance == null)
            mInstance = new DatabaseHelper(context);
        mInstance.open(true);
        return mInstance;
    }

    public static DatabaseHelper getInstance() {
        return mInstance;
    }

    public DatabaseHelper(Context context) {
        super(context, NAME_DB, null, VERSION_DB);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    private void open(boolean write) {
        db = write ? this.getWritableDatabase() : this.getReadableDatabase();
    }

    public SQLiteDatabase getDb() {
        return this.db;
    }
}
