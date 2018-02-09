package com.codetouch.bitcoinprice.models;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Date;

/**
 * Created by luccasgaluppotanan on 2/8/18.
 */

public class Price {

    private int id;
    private double min;
    private double max;
    private double current;
    private Date date;

    /**
     * @param _min     Menor valor
     * @param _max     Maior valor
     * @param _current Valor atual
     */
    public Price(int _id, double _min, double _max, double _current, Date _date) {
        this.id = _id;
        this.min = _min;
        this.max = _max;
        this.current = _current;
        this.date = _date;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public double getMin() {
        return min;
    }

    public void setMin(double min) {
        this.min = min;
    }

    public double getMax() {
        return max;
    }

    public void setMax(double max) {
        this.max = max;
    }

    public double getCurrent() {
        return current;
    }

    public void setCurrent(double current) {
        this.current = current;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public static Price decodeJson(JSONObject jsonObject) {
        try {
            JSONObject jsonTicker = jsonObject.getJSONObject("ticker");
            return new Price(-1,
                    jsonTicker.getDouble("low"),
                    jsonTicker.getDouble("high"),
                    jsonTicker.getDouble("last"),
                    new Date(jsonTicker.getLong("date")));
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
    }
}
