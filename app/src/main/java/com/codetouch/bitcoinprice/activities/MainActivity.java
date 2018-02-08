package com.codetouch.bitcoinprice.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.codetouch.bitcoinprice.APIController;
import com.codetouch.bitcoinprice.Constants;
import com.codetouch.bitcoinprice.R;

import org.json.JSONException;
import org.json.JSONObject;

public class MainActivity extends AppCompatActivity {

    private ProgressBar pgbRequest;
    private TextView txvValue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        pgbRequest = findViewById(R.id.pgb_request);
        txvValue = findViewById(R.id.txv_value);

        requestValue();
    }

    private void requestValue() {
        pgbRequest.setVisibility(View.VISIBLE);
        APIController apiController = new APIController();
        apiController.get(Constants.URL.api, null, new APIController.OnRequestResult() {
            @Override
            public void onResponse(JSONObject jsonObject) {
                if (jsonObject == null)
                    txvValue.setText(R.string.request_failed);
                else
                    txvValue.setText(String.valueOf(extractValue(jsonObject)));
                pgbRequest.setVisibility(View.GONE);
            }
        });
    }

    private double extractValue(JSONObject jsonObject) {
        try {
            return jsonObject.getJSONObject("ticker").getDouble("last");
        } catch (JSONException e) {
            e.printStackTrace();
            return -1;
        }
    }
}
