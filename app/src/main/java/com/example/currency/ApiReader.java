package com.example.currency;

import static com.example.currency.Constants.FLOATAPIURL;
import static com.example.currency.Constants.METEOAPIURL;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;


public class ApiReader {
    public static String getValuesFromApi(String apiCode) throws IOException {
        InputStream apiContentStream = null;
        String result = "";
        try{
            switch (apiCode){
                case METEOAPIURL:
                    apiContentStream = downloadUrlContent(METEOAPIURL);
                    result = METEOAPI.getKaunasWeatherForecast(apiContentStream);
                    break;
                case FLOATAPIURL:
                    apiContentStream = downloadUrlContent(FLOATAPIURL);
                    result = FLOATAPI.getCurrencyRatesBaseUsd(apiContentStream);
                    break;
                default:
            }
        }
        finally{
            if(apiContentStream != null){
                apiContentStream.close();
            }
        }
        return result;
    }

    private static InputStream downloadUrlContent(String urlString) throws IOException {
        URL url = new URL(urlString);
        HttpURLConnection conn=(HttpURLConnection) url.openConnection();
        conn.setReadTimeout(10000);
        conn.setConnectTimeout(10000);
        conn.setRequestMethod("GET");
        conn.setDoInput(true);
        conn.connect();
        return conn.getInputStream();
    }


}
