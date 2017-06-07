package com.example.cryptocurrencyapp;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.cryptocurrencyapp.utils.CryptonatorUtils;

import java.text.SimpleDateFormat;
import java.util.ArrayList;


/**
 * Created by harderg on 5/25/17.
 */

public class CryptoCurrencyAdapter extends RecyclerView.Adapter<CryptoCurrencyAdapter.CryptoCurrencyItemViewHolder> {
    private ArrayList<CryptonatorUtils.CryptoCurrencyItem> mCryptoCurrencyItems;
    private OnCryptoItemClickListener mCryptoItemClickListener;

    public interface OnCryptoItemClickListener {
        void onCryptoItemClick(CryptonatorUtils.CryptoCurrencyItem cryptoCurrencyItem);
    }

    public CryptoCurrencyAdapter(OnCryptoItemClickListener clickListener) {
        mCryptoItemClickListener = clickListener;
    }

    public void updateCryptoCurrencyItems(ArrayList<CryptonatorUtils.CryptoCurrencyItem> cryptoCurrencyItem) {
        mCryptoCurrencyItems = cryptoCurrencyItem;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        if (mCryptoCurrencyItems != null) {
            return mCryptoCurrencyItems.size();
        } else {
            return 0;
        }
    }

    @Override
    public CryptoCurrencyItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View itemView = inflater.inflate(R.layout.crypto_currency_item, parent, false);
        return new CryptoCurrencyItemViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(CryptoCurrencyItemViewHolder holder, int position) {
        holder.bind(mCryptoCurrencyItems.get(position));
    }

    class CryptoCurrencyItemViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private TextView mCryptoCurrencyTV;

        public CryptoCurrencyItemViewHolder(View itemView) {
            super(itemView);
            mCryptoCurrencyTV = (TextView)itemView.findViewById(R.id.tv_crypto_currency_text);
            itemView.setOnClickListener(this);
        }

        public void bind(CryptonatorUtils.CryptoCurrencyItem cryptoCurrencyItem) {
            mCryptoCurrencyTV.setText(cryptoCurrencyItem.name.toString());
        }

        @Override
        public void onClick(View v) {
            CryptonatorUtils.CryptoCurrencyItem cryptoCurrencyItem = mCryptoCurrencyItems.get(getAdapterPosition());
            mCryptoItemClickListener.onCryptoItemClick(cryptoCurrencyItem);
        }
    }
}