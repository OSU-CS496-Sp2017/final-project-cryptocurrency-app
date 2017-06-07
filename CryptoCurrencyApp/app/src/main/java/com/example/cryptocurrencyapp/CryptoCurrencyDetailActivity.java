package com.example.cryptocurrencyapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.example.cryptocurrencyapp.utils.CryptonatorUtils;

/**
 * Created by harderg on 5/25/17.
 */

public class CryptoCurrencyDetailActivity extends AppCompatActivity {
    private TextView mCryptoCurrencyNameTv;
    private TextView mCryptoCurrencyPriceTv;
    private TextView mCryptoCurrencyChangeTv;
    private TextView mCryptoCurrencyVolumeTv;

    private CryptonatorUtils.CryptoCurrencyItem mCryptoItem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.crypto_currency_item);

        mCryptoCurrencyNameTv = (TextView)findViewById(R.id.tv_crypto_currency_name);
        mCryptoCurrencyPriceTv = (TextView)findViewById(R.id.tv_crypto_currency_price);
        mCryptoCurrencyChangeTv = (TextView)findViewById(R.id.tv_crypto_currency_change);
        mCryptoCurrencyVolumeTv = (TextView)findViewById(R.id.tv_crypto_currency_volume);




        Intent intent = getIntent();
        if (intent != null) {
            mCryptoItem = (CryptonatorUtils.CryptoCurrencyItem) intent.getSerializableExtra(CryptonatorUtils.CryptoCurrencyItem.EXTRA_CRYPTO_RESULT);
        }

    }


    private void fillInLayoutText(CryptonatorUtils.CryptoCurrencyItem CryptoItem)
    {

        mCryptoCurrencyNameTv.setText("BitCoin");
        mCryptoCurrencyPriceTv.setText("10000");
        mCryptoCurrencyChangeTv.setText("12 Hours");
        mCryptoCurrencyVolumeTv.setText("A lot");
//
//        mCryptoCurrencyNameTv.setText(CryptoItem.toString());
//        mCryptoCurrencyPriceTv.setText(CryptoItem.toString());
//        mCryptoCurrencyChangeTv.setText(CryptoItem.toString());
//        mCryptoCurrencyVolumeTv.setText(CryptoItem.toString());
    }
}
