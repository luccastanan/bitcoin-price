package com.codetouch.bitcoinprice.interfaces;

/**
 * Created by lucca on 2/8/2018.
 */

public interface IBaseDAO<T> {
    long insert(T object);

    T select(int id);

    int update(T object);

    int delete(int id);
}
