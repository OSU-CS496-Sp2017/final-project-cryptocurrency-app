package com.example.cryptocurrencyapp;

import com.example.cryptocurrencyapp.utils.*;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.io.IOException;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements CryptoCurrencyAdapter.OnCryptoItemClickListener {

    private RecyclerView mCryptoListRV;
    private CryptoCurrencyAdapter mcryptoCurrencyAdapter;

    private ProgressBar mLoadingIndicatorPB;
    private RecyclerView mSearchResultsRV;
    private TextView mLoadingErrorMessageTV;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mCryptoListRV = (RecyclerView)findViewById(R.id.rv_crypto_currency_list);

        mLoadingIndicatorPB = (ProgressBar)findViewById(R.id.pb_loading_indicator);
        mLoadingErrorMessageTV = (TextView)findViewById(R.id.tv_loading_error_message);
        mSearchResultsRV = (RecyclerView)findViewById(R.id.rv_search_results);

        mCryptoListRV.setLayoutManager(new LinearLayoutManager(this));
        mCryptoListRV.setHasFixedSize(true);

        mcryptoCurrencyAdapter = new CryptoCurrencyAdapter(this);
        mCryptoListRV.setAdapter(mcryptoCurrencyAdapter);

        doCryptonatorSearch();
    }

    private void doCryptonatorSearch() {
        ArrayList<String> cryptonatorURLs = new ArrayList<>();
        cryptonatorURLs.add(CryptonatorUtils.buildCryptonatorURL("btc-usd"));
        cryptonatorURLs.add(CryptonatorUtils.buildCryptonatorURL("eth-usd"));
        cryptonatorURLs.add(CryptonatorUtils.buildCryptonatorURL("ltc-usd"));
        cryptonatorURLs.add(CryptonatorUtils.buildCryptonatorURL("xmr-usd"));
        cryptonatorURLs.add(CryptonatorUtils.buildCryptonatorURL("xrp-usd"));
        cryptonatorURLs.add(CryptonatorUtils.buildCryptonatorURL("doge-usd"));
        Log.d("MainActivity", "got search url: " + cryptonatorURLs.toString());
        new CryptoCurrencySearchTask().execute(cryptonatorURLs);
    }

    @Override
    public void onCryptoItemClick(CryptonatorUtils.CryptoCurrencyItem cryptoCurrencyItem) {
        Intent intent = new Intent(this, CryptoCurrencyDetailActivity.class);
        intent.putExtra(CryptonatorUtils.CryptoCurrencyItem.EXTRA_CRYPTO_RESULT, cryptoCurrencyItem);
        startActivity(intent);
    }


    public class CryptoCurrencySearchTask extends AsyncTask<ArrayList<String>, Void, ArrayList<String>> {
        @Override
        protected ArrayList<String> doInBackground(ArrayList<String>... params) {
            ArrayList<String> searchResults = new ArrayList<>();
            for(int i = 0; i < params[0].size();i++) {
                String cryptoSearchURL = params[0].get(i);
                try {
                    searchResults.add(NetworkUtils.doHTTPGet(cryptoSearchURL));
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            return searchResults;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            mLoadingIndicatorPB.setVisibility(View.VISIBLE);
        }

        @Override
        protected void onPostExecute(ArrayList<String> s) {
            Log.d("PostExecute", "got to the post execute" + s);

            mLoadingIndicatorPB.setVisibility(View.INVISIBLE);
            if (s != null) {
                mLoadingErrorMessageTV.setVisibility(View.INVISIBLE);
                mSearchResultsRV.setVisibility(View.VISIBLE);
                ArrayList<CryptonatorUtils.CryptoCurrencyItem> searchResult = CryptonatorUtils.parseCryptocurrencyJSON(s);
                mcryptoCurrencyAdapter.updateCryptoCurrencyItems(searchResult);
            } else {
                mSearchResultsRV.setVisibility(View.INVISIBLE);
                mLoadingErrorMessageTV.setVisibility(View.VISIBLE);
            }
        }
    }
}
