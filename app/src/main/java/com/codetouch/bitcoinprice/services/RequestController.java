package com.codetouch.bitcoinprice.services;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.codetouch.bitcoinprice.AppController;
import com.codetouch.bitcoinprice.Constants;
import com.codetouch.bitcoinprice.interfaces.IRequest;

import org.json.JSONObject;

/**
 * Created by lucca on 2/7/2018.
 */

public class RequestController implements IRequest {

    @Override
    public void get(String path, JSONObject params, final OnRequestResult onRequestResult) {
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, Constants.URL.api, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                onRequestResult.onResponse(response);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                onRequestResult.onResponse(null);
            }
        });
        AppController.getInstance().addToRequestQueue(request);
    }

    public interface OnRequestResult {
        void onResponse(JSONObject jsonObject);
    }
}
