package com.example.currency;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.provider.SyncStateContract;
import android.view.View;
import android.widget.Adapter;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {


    ListView lvItems;
    TextView tvStatus;
    Switch swAsync;
    Adapter listAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        this.lvItems = findViewById(R.id.lvItems);
        this.tvStatus = findViewById(R.id.tvStatus);
        this.swAsync = findViewById(R.id.swAsync);

        this.listAdapter = new ArrayAdapter<>(getApplicationContext(), android.R.layout.simple_list_item_1, new ArrayList<>());
        this.lvItems.setAdapter((ListAdapter) this.listAdapter);
    }

    public void onBtnGetDataClick(View view) {
        this.tvStatus.setText("Loading data");
        if (this.swAsync.isChecked()) {
            getDataByAsyncTask();
            Toast.makeText(this, "Using Async task for MeteoLt", Toast.LENGTH_LONG).show();
        } else {
            getDataByThread();
            Toast.makeText(this, "Using Thread for FloatRates", Toast.LENGTH_LONG).show();
        }
    }

    public void getDataByAsyncTask() {
        new AsyncLoader() {
            @Override
            public void onPostExecute(String result) {
                tvStatus.setText("Data loaded: " + result);
            }
        }.execute(Constants.METEOAPIURL);
    }

    public void getDataByThread() {
    this.tvStatus.setText("Loading data");
    Runnable getDataAndDisplayRunnable = new Runnable(){
        @Override
        public void run(){
            try{
                final String result = ApiReader.getValuesFromApi(Constants.FLOATAPIURL);
                Runnable updateUIRunnable = new Runnable(){
                    @Override
                    public void run(){
                        tvStatus.setText("Data loaded:"+result);
                    }
                };
                runOnUiThread(updateUIRunnable);
            }catch(IOException e) {
                e.printStackTrace();
            }
        }
    };
    Thread thread = new Thread(getDataAndDisplayRunnable);
    thread.start();

    }
}