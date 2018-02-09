package com.codetouch.bitcoinprice.activities;

import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.codetouch.bitcoinprice.Constants;
import com.codetouch.bitcoinprice.R;
import com.codetouch.bitcoinprice.adapters.PriceAdapter;
import com.codetouch.bitcoinprice.database.PriceDAO;
import com.codetouch.bitcoinprice.models.Price;
import com.codetouch.bitcoinprice.services.RequestController;
import com.codetouch.bitcoinprice.util.NetworkUtil;

import org.json.JSONObject;

import java.util.List;

import static com.codetouch.bitcoinprice.util.Utilities.formatReal;

public class MainActivity extends AppCompatActivity {

    private CoordinatorLayout layout;
    private RelativeLayout ltProgress;
    private ProgressBar progress;
    private FloatingActionButton btnRefresh;
    private TextView txvMin, txvMax, txvValue, txvCent;
    private RecyclerView rcvPrices;

    private PriceDAO priceDAO = new PriceDAO();

    private List<Price> priceList;
    private PriceAdapter priceAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        layout = findViewById(R.id.layout);
        ltProgress = findViewById(R.id.lt_progress);
        progress = findViewById(R.id.progress);
        btnRefresh = findViewById(R.id.btn_refresh);
        txvMin = findViewById(R.id.txv_min);
        txvMax = findViewById(R.id.txv_max);
        txvValue = findViewById(R.id.txv_value);
        txvCent = findViewById(R.id.txv_cent);
        rcvPrices = findViewById(R.id.rcv_prices);
        rcvPrices.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));

        btnRefresh.setOnClickListener(new View.OnClickListener() {
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
        if (NetworkUtil.isConnected(this)) {
            requesting(true);
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
                    requesting(false);
                }
            });
        } else {
            Snackbar.make(layout, R.string.no_connection, Snackbar.LENGTH_SHORT).show();
        }
    }

    private void showPrice(Price price) {
        if (price == null) {
            txvValue.setText(R.string.request_failed);
        } else {
            txvMin.setText(formatReal(price.getMin()));
            txvMax.setText(formatReal(price.getMax()));
            String current[] = String.valueOf(price.getCurrent()).split("\\.");
            txvValue.setText(current[0]);
            txvCent.setText(current[1]);
        }
    }

    private void newPrice(Price price) {
        if (price != null) {
            if (priceDAO.exist(price.getDate())) {
                Snackbar.make(layout, R.string.exist_value, Snackbar.LENGTH_SHORT).show();
            } else {
                price.setId((int) priceDAO.insert(price));
                priceList.add(price);
                loadPrices();
            }
        }
    }

    private void requesting(boolean status) {
        ltProgress.setVisibility(status ? View.VISIBLE : View.GONE);
        btnRefresh.setVisibility(status ? View.GONE : View.VISIBLE);

    }
}
