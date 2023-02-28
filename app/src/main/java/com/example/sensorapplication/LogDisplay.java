package com.example.sensorapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class LogDisplay extends AppCompatActivity{

    private ListView logList;
    private ArrayAdapter<String> logAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_display);
        logList = findViewById(R.id.logView);
        logAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, Helper.logs);
//      create an arraydapter of strings which will populate the listview with every log in the arraylist
        logAdapter.setDropDownViewResource(android.R.layout.simple_list_item_1);
        logList.setAdapter(logAdapter);
    }


    @Override
    protected void onResume() {
        super.onResume();
        logList.getAdapter();
        if (logAdapter.getCount() >10 ) {
            logAdapter.clear();
            logAdapter.notifyDataSetChanged();
        }
        logAdapter.notifyDataSetChanged();
        logAdapter.addAll(Helper.logs);
    }
}