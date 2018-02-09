package com.codetouch.bitcoinprice;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * Created by lucca on 2/8/2018.
 */

public class Utilities {

    public static String timeToDate(long value) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH:mm:ss dd/MM/yyyy", Locale.getDefault());
        return simpleDateFormat.format(new Date(value));
    }
}
