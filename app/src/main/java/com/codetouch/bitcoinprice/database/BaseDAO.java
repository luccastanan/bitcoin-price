package com.codetouch.bitcoinprice.database;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

/**
 * Created by lucca on 2/8/2018.
 */

public class BaseDAO {

    private SQLiteDatabase db;
    private String table;

    BaseDAO(String _table) {
        db = DatabaseHelper.getInstance().getDb();
        this.table = _table;
    }

    long save(ContentValues values) {
        return db.insert(table, null, values);
    }

    Cursor find(int id) {
        return db.rawQuery("SELECT * FROM " + table + " WHERE id = ?", new String[]{String.valueOf(id)});
    }

    int alter(int id, ContentValues contentValues) {
        return db.update(table, contentValues, "id = ?", new String[]{String.valueOf(id)});
    }

    int remove(int id) {
        return db.delete(table, "id = ?", new String[]{String.valueOf(id)});
    }

    public SQLiteDatabase getDb() {
        return db;
    }
}
