package com.codetouch.bitcoinprice.database;

import android.content.ContentValues;
import android.database.Cursor;

import com.codetouch.bitcoinprice.interfaces.IBaseDAO;
import com.codetouch.bitcoinprice.models.Price;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by luccasgaluppotanan on 2/8/18.
 */

public class PriceDAO extends BaseDAO implements IBaseDAO<Price> {

    public PriceDAO() {
        super("price");
    }

    @Override
    public long insert(Price price) {
        ContentValues values = new ContentValues();
        values.put("min_value", price.getMin());
        values.put("max_value", price.getMax());
        values.put("current_value", price.getCurrent());
        values.put("date", price.getDate().getTime());
        return save(values);
    }

    @Override
    public Price select(int id) {
        Cursor cursor = find(id);
        Price price = null;
        if (cursor.moveToFirst()) {
            price = new Price(cursor.getInt(cursor.getColumnIndex("id")),
                    cursor.getDouble(cursor.getColumnIndex("min_value")),
                    cursor.getDouble(cursor.getColumnIndex("max_value")),
                    cursor.getDouble(cursor.getColumnIndex("current_value")),
                    new Date(cursor.getLong(cursor.getColumnIndex("date"))));
        }
        cursor.close();
        return price;
    }

    @Override
    public int update(Price price) {
        ContentValues values = new ContentValues();
        values.put("min_value", price.getMin());
        values.put("max_value", price.getMax());
        values.put("current_value", price.getCurrent());
        return alter(price.getId(), values);
    }

    @Override
    public int delete(int id) {
        return remove(id);
    }

    public Price selectLast() {
        Cursor cursor = getDb().rawQuery("SELECT id FROM price ORDER BY id DESC LIMIT 1", null);
        Price price = null;
        if (cursor.moveToFirst()) {
            price = select(cursor.getInt(0));
        }
        return price;
    }

    public List<Price> selectAll() {
        Cursor cursor = getDb().rawQuery("SELECT id FROM price ORDER BY date", null);
        List<Price> priceList = new ArrayList<>();
        while (cursor.moveToNext()) {
            Price price = select(cursor.getInt(0));
            priceList.add(price);
        }
        return priceList;
    }
}
