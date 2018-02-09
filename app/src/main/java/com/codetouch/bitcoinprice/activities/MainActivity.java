package com.codetouch.bitcoinprice.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.codetouch.bitcoinprice.Constants;
import com.codetouch.bitcoinprice.R;
import com.codetouch.bitcoinprice.adapters.PriceAdapter;
import com.codetouch.bitcoinprice.database.PriceDAO;
import com.codetouch.bitcoinprice.models.Price;
import com.codetouch.bitcoinprice.services.RequestController;

import org.json.JSONObject;

import java.util.List;

import static com.codetouch.bitcoinprice.Utilities.formatReal;

public class MainActivity extends AppCompatActivity {

    private ProgressBar pgbRequest;
    private TextView txvMin, txvMax, txvValue;
    private RecyclerView rcvPrices;

    private PriceDAO priceDAO = new PriceDAO();

    private List<Price> priceList;
    private PriceAdapter priceAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        pgbRequest = findViewById(R.id.pgb_request);
        txvMin = findViewById(R.id.txv_min);
        txvMax = findViewById(R.id.txv_max);
        txvValue = findViewById(R.id.txv_value);
        rcvPrices = findViewById(R.id.rcv_prices);
        rcvPrices.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));

        findViewById(R.id.btn_refresh).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                requestValue();
            }
        });
        loadPrices();
        requestValue();
    }

    private void loadPrices() {
        priceList = priceDAO.selectAll();
        if (priceList.size() > 0) {
            showPrice(priceList.remove(priceList.size() - 1));
            priceAdapter = new PriceAdapter(priceList);
            rcvPrices.setAdapter(priceAdapter);
        }
    }

    private void requestValue() {
        pgbRequest.setVisibility(View.VISIBLE);
        RequestController requestController = new RequestController();
        requestController.get(Constants.URL.api, null, new RequestController.OnRequestResult() {
            @Override
            public void onResponse(JSONObject jsonObject) {
                if (jsonObject == null) {
                    Price price = priceDAO.selectLast();
                    newPrice(price);
                } else {
                    Price price = Price.decodeJson(jsonObject);
                    newPrice(price);
                }
                pgbRequest.setVisibility(View.GONE);
            }
        });
    }

    private void showPrice(Price price) {
        if (price == null) {
            txvValue.setText(R.string.request_failed);
        } else {
            txvMin.setText(formatReal(price.getMin()));
            txvMax.setText(formatReal(price.getMax()));
            txvValue.setText(String.valueOf(price.getCurrent()));
        }
    }

    private void newPrice(Price price) {
        if (price != null && !priceDAO.exist(price.getDate().getTime())) {
            price.setId((int) priceDAO.insert(price));
            priceList.add(price);
            loadPrices();
        }
    }
}
