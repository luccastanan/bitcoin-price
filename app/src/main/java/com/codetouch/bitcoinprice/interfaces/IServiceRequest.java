package com.codetouch.bitcoinprice.interfaces;

import com.codetouch.bitcoinprice.APIController;

import org.json.JSONObject;

/**
 * Created by lucca on 2/7/2018.
 */

public interface IServiceRequest {

    void get(String path, JSONObject params, APIController.OnRequestResult onRequestResult);

}
