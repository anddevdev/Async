package com.example.currency;

import android.os.AsyncTask;

import java.io.IOException;

public class AsyncLoader  extends AsyncTask<String,Void,String> {

    protected String doInBackground(String... params) {

        try{
            return ApiReader.getValuesFromApi(params[0]);
        } catch (IOException ex) {
            return String.format("Error occurred => &$", ex.getMessage());
        }
    }
    @Override
    protected void onPostExecute(String result){ super.onPostExecute(result);}

}
