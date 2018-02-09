package com.codetouch.bitcoinprice.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.codetouch.bitcoinprice.R;
import com.codetouch.bitcoinprice.util.Utilities;
import com.codetouch.bitcoinprice.models.Price;

import java.util.List;

/**
 * Created by lucca on 2/9/2018.
 */

public class PriceAdapter extends RecyclerView.Adapter<PriceAdapter.MyViewHolder> {

    private List<Price> priceList;

    public PriceAdapter(List<Price> _priceList) {
        this.priceList = _priceList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_price, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        Price price = priceList.get((priceList.size() - 1) - position);
        holder.txvValue.setText(String.valueOf(price.getCurrent()));
        holder.txvMin.setText(Utilities.formatReal(price.getMin()));
        holder.txvMax.setText(Utilities.formatReal(price.getMax()));
        holder.txvDate.setText(Utilities.timeToDate(price.getDate()));
    }

    @Override
    public int getItemCount() {
        return priceList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView txvValue, txvMin, txvMax, txvDate;

        public MyViewHolder(View itemView) {
            super(itemView);
            txvValue = itemView.findViewById(R.id.txv_value);
            txvMin = itemView.findViewById(R.id.txv_min);
            txvMax = itemView.findViewById(R.id.txv_max);
            txvDate = itemView.findViewById(R.id.txv_date);
        }
    }
}
