package com.codetouch.bitcoinprice.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.codetouch.bitcoinprice.Constants;
import com.codetouch.bitcoinprice.R;
import com.codetouch.bitcoinprice.database.PriceDAO;
import com.codetouch.bitcoinprice.models.Price;
import com.codetouch.bitcoinprice.services.RequestController;

import org.json.JSONObject;

public class MainActivity extends AppCompatActivity {

    private ProgressBar pgbRequest;
    private TextView txvValue;

    private PriceDAO priceDAO = new PriceDAO();

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
        RequestController requestController = new RequestController();
        requestController.get(Constants.URL.api, null, new RequestController.OnRequestResult() {
            @Override
            public void onResponse(JSONObject jsonObject) {
                if (jsonObject == null) {
                    Price price = priceDAO.selectLast();
                    if (price == null)
                        txvValue.setText(R.string.request_failed);
                    else
                        txvValue.setText(String.valueOf(price.getCurrent()));
                } else {
                    Price price = Price.decodeJson(jsonObject);
                    if (price == null) {
                        txvValue.setText(R.string.request_failed);
                    } else {
                        txvValue.setText(String.valueOf(price.getCurrent()));
                        price.setId((int) priceDAO.insert(price));
                    }
                }
                pgbRequest.setVisibility(View.GONE);
            }
        });
    }
}
