package com.example.cryptocurrencyapp.utils;

import android.net.ParseException;
import android.net.Uri;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by harderg on 5/25/17.
 */

public class CryptonatorUtils {

    private final static String CRYPTO_BASE_URL = "https://api.cryptonator.com/api/ticker/";

    public static class CryptoCurrencyItem implements Serializable {
        public static final String EXTRA_CRYPTO_RESULT = "CryptonatorUtils.CryptoCurrencyItem";
        public String name;
        public Double price;
        public Double change;
        public Double volume;
    }

    public static String buildCryptonatorURL (String currencyName) {
        //TODO: Add necessary params
        return Uri.parse(CRYPTO_BASE_URL).buildUpon()
                .appendPath(currencyName)
                .build()
                .toString();
    }

    public static ArrayList<CryptoCurrencyItem> parseCryptocurrencyJSON(ArrayList<String> cryptocurrencyJSON) {
        try {
            ArrayList<CryptoCurrencyItem> cryptoItems = new ArrayList<>();
            for(int i = 0; i < cryptocurrencyJSON.size(); i++) {
                JSONObject cryptoCurrencyObj = new JSONObject(cryptocurrencyJSON.get(i));
                cryptoCurrencyObj = cryptoCurrencyObj.getJSONObject("ticker");

                //ArrayList<CryptoCurrencyItem> cryptoCurrencyItemsList = new ArrayList<>();
                CryptoCurrencyItem cryptoItem = new CryptoCurrencyItem();

                cryptoItem.name = cryptoCurrencyObj.getString("base");
                cryptoItem.price = cryptoCurrencyObj.getDouble("price");
                cryptoItem.change = cryptoCurrencyObj.getDouble("change");
                cryptoItem.volume = cryptoCurrencyObj.getDouble("volume");

                cryptoItems.add(cryptoItem);

            }
            return cryptoItems;

        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        } catch (ParseException e) {
            e.printStackTrace();
            return null;
        }
    }
}
