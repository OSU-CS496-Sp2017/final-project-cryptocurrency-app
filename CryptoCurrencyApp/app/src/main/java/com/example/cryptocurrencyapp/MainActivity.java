package com.example.cryptocurrencyapp;

import com.example.cryptocurrencyapp.utils.*;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.io.IOException;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements CryptoCurrencyAdapter.OnCryptoItemClickListener,
        SharedPreferences.OnSharedPreferenceChangeListener {

    private RecyclerView mCryptoListRV;
    private CryptoCurrencyAdapter mcryptoCurrencyAdapter;

    private ProgressBar mLoadingIndicatorPB;
    private RecyclerView mSearchResultsRV;
    private TextView mLoadingErrorMessageTV;

    private static final String TAG = MainActivity.class.getSimpleName();

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


        doCryptonatorSearch(getCurrencies());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_settings:
                Log.d(TAG, "Hit action settings");
                Intent settingsIntent = new Intent(this, SettingsActivity.class);
                startActivity(settingsIntent);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }


    private ArrayList<String> getCurrencies(){
        SharedPreferences sharedPreferences =
                PreferenceManager.getDefaultSharedPreferences(this);

        ArrayList<String> currencies = new ArrayList<>();

        boolean prefBTC = sharedPreferences.getBoolean(
                getString(R.string.pref_btc_key), true);
        boolean prefETH = sharedPreferences.getBoolean(
                getString(R.string.pref_eth_key), false);
        boolean prefLTC = sharedPreferences.getBoolean(
                getString(R.string.pref_ltc_key), false);
        boolean prefXMR = sharedPreferences.getBoolean(
                getString(R.string.pref_xmr_key), false);
        boolean prefXRP = sharedPreferences.getBoolean(
                getString(R.string.pref_xrp_key), false);
        boolean prefDOGE = sharedPreferences.getBoolean(
                getString(R.string.pref_doge_key), true);

        if(prefBTC) currencies.add("btc-usd");
        if(prefETH) currencies.add("eth-usd");
        if(prefLTC) currencies.add("ltc-usd");
        if(prefXMR) currencies.add("xmr-usd");
        if(prefXRP) currencies.add("xrp-usd");
        if(prefDOGE) currencies.add("doge-usd");

        return currencies;

    }

    private void doCryptonatorSearch(ArrayList<String> currencies) {
        ArrayList<String> cryptonatorURLs = new ArrayList<>();
        for(String currency : currencies) {
            cryptonatorURLs.add(CryptonatorUtils.buildCryptonatorURL(currency));
        }
        Log.d("MainActivity", "got search url: " + cryptonatorURLs.toString());
        new CryptoCurrencySearchTask().execute(cryptonatorURLs);
    }

    @Override
    public void onCryptoItemClick(CryptonatorUtils.CryptoCurrencyItem cryptoCurrencyItem) {
        Intent intent = new Intent(this, CryptoCurrencyDetailActivity.class);
        intent.putExtra(CryptonatorUtils.CryptoCurrencyItem.EXTRA_CRYPTO_RESULT, cryptoCurrencyItem);
        startActivity(intent);
    }

    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String s) {
        doCryptonatorSearch(getCurrencies());
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
