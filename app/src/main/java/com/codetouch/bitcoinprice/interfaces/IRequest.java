package com.codetouch.bitcoinprice.interfaces;

import com.codetouch.bitcoinprice.services.RequestController;

import org.json.JSONObject;

/**
 * Created by lucca on 2/7/2018.
 */

public interface IRequest {

    void get(String path, JSONObject params, RequestController.OnRequestResult onRequestResult);

}
