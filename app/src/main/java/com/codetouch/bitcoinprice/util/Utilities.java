package com.codetouch.bitcoinprice.util;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * Created by lucca on 2/8/2018.
 */

public class Utilities {

    public static String timeToDate(long value) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH:mm:ss MM/dd", Locale.getDefault());
        return simpleDateFormat.format(new Date(value));
    }

    public static String formatReal(Double value) {
        return String.format("R$%s", value);
    }
}
