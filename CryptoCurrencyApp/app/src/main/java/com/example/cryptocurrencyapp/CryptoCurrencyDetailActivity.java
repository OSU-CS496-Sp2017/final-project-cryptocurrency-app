package com.example.cryptocurrencyapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.example.cryptocurrencyapp.utils.CryptonatorUtils;

/**
 * Created by harderg on 5/25/17.
 */

public class CryptoCurrencyDetailActivity extends AppCompatActivity {

    private static final String TAG = CryptoCurrencyDetailActivity.class.getSimpleName();

    private TextView mCryptoCurrencyNameTv;
    private TextView mCryptoCurrencyPriceTv;
    private TextView mCryptoCurrencyChangeTv;
    private TextView mCryptoCurrencyVolumeTv;

    private CryptonatorUtils.CryptoCurrencyItem mCryptoItem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crypto_currency_detail);

        mCryptoCurrencyNameTv = (TextView)findViewById(R.id.tv_crypto_currency_name);
        mCryptoCurrencyPriceTv = (TextView)findViewById(R.id.tv_crypto_currency_price);
        mCryptoCurrencyChangeTv = (TextView)findViewById(R.id.tv_crypto_currency_change);
        mCryptoCurrencyVolumeTv = (TextView)findViewById(R.id.tv_crypto_currency_volume);


        Intent intent = getIntent();
        if (intent != null && intent.hasExtra(CryptonatorUtils.CryptoCurrencyItem.EXTRA_CRYPTO_RESULT)) {
            mCryptoItem = (CryptonatorUtils.CryptoCurrencyItem)intent.getSerializableExtra(
                                CryptonatorUtils.CryptoCurrencyItem.EXTRA_CRYPTO_RESULT);
            Log.d(TAG, "mCryptoItem: " + mCryptoItem.toString());
            fillInLayoutText(mCryptoItem);
        }
    }


    private void fillInLayoutText(CryptonatorUtils.CryptoCurrencyItem CryptoItem)
    {
        Log.d(TAG, "In fillInLayoutText " + CryptoItem.name);
        mCryptoCurrencyNameTv.setText(CryptoItem.name);
        mCryptoCurrencyPriceTv.setText(CryptoItem.price.toString());
        mCryptoCurrencyChangeTv.setText(CryptoItem.change.toString());
        mCryptoCurrencyVolumeTv.setText(CryptoItem.volume.toString());
    }
}
