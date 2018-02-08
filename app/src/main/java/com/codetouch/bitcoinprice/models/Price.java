package com.codetouch.bitcoinprice.models;

/**
 * Created by luccasgaluppotanan on 2/8/18.
 */

public class Price {

    private double min;
    private double max;
    private double current;

    /**
     *
     * @param min Menor valor
     * @param max Maior valor
     * @param current Valor atual
     */
    public Price(double min, double max, double current) {
        this.min = min;
        this.max = max;
        this.current = current;
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
}
