package com.example.sensorapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.BatteryManager;
import android.os.Bundle;
import android.widget.RadioGroup;
import android.widget.TextView;

import java.text.DecimalFormat;


public class InternalSensors extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_internal_sensors);
   }

    private BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            final RadioGroup unitChanges = findViewById(R.id.unitChange2);
            final TextView batteryTemp = findViewById(R.id.batTemp);
            final TextView batteryVolt = findViewById(R.id.batVolt);
            final TextView batteryLevel = findViewById(R.id.batPerc);
            float temperature = (float) intent.getIntExtra(BatteryManager.EXTRA_TEMPERATURE, 0) / 10;
            float voltage = (float) ((float) intent.getIntExtra(BatteryManager.EXTRA_VOLTAGE, 0 ) * 0.001);

//          Calling the function to set health and parsing the value in one line
            BatteryHealthSet((intent.getIntExtra(BatteryManager.EXTRA_HEALTH, 0)));
            BatteryChargingSet(intent);
            batteryLevel.setText((intent.getIntExtra(BatteryManager.EXTRA_LEVEL, 0)) + "%" + "\n" + "Battery");
            batteryVolt.setText("Voltage: " + voltage + "v");
            batteryTemp.setText("Battery Temperature: " + temperature + "°C");

//          Checking a default value on radio button to set temperature
            unitChanges.check(R.id.celsius);
            unitChanges.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(RadioGroup radioGroup, int i) {
                    int id = unitChanges.getCheckedRadioButtonId();
                    switch(id){
                        case R.id.celsius:
                            batteryTemp.setText("Battery Temperature: " + temperature + "°C");
                            break;
                        case R.id.fahr:
                            DecimalFormat df = new DecimalFormat("#.##");
                            df.setMaximumFractionDigits(2);
                            batteryTemp.setText("Battery Temperature = " + df.format((temperature * 1.8 + 32)) + " °F");
                            break;
                    }
                }
            });
        }
    };


    @Override
    protected void onResume() {
        super.onResume();
        IntentFilter intentFilter = new IntentFilter(Intent.ACTION_BATTERY_CHANGED);
        registerReceiver(broadcastReceiver, intentFilter);
    }

//  On pause unregisters the battery receiver which will save power when the user is not on the battery screen
    @Override
    protected void onPause() {
        super.onPause();
        unregisterReceiver(broadcastReceiver);
    }

    private void BatteryHealthSet(int health){
        final TextView batHealth = findViewById(R.id.batHealth);
//      The battery health action has a lot of statuses which are integers therefore I used a switch statement
        switch (health){
            case BatteryManager.BATTERY_HEALTH_UNKNOWN:
                batHealth.setText("Battery Health: Unknown");
                break;

            case BatteryManager.BATTERY_HEALTH_GOOD:
                batHealth.setText("Battery Health: Good");
                break;

            case BatteryManager.BATTERY_HEALTH_OVERHEAT:
                batHealth.setText("Battery Health: Overheating");
                break;
            case BatteryManager.BATTERY_HEALTH_DEAD:
                batHealth.setText("Battery Health: Dead");
                break;

            case BatteryManager.BATTERY_HEALTH_COLD:
                batHealth.setText("Battery Health: Cold");
                break;

            case BatteryManager.BATTERY_HEALTH_UNSPECIFIED_FAILURE:
                batHealth.setText("Battery Health: Unspecified Failure");
                break;

            case BatteryManager.BATTERY_HEALTH_OVER_VOLTAGE:
                batHealth.setText("Battery Health: Over Voltage");
                break;
        }
    }

    private void BatteryChargingSet(Intent intent){
        final TextView batStatus = findViewById(R.id.batStat);
        String status = "Status: ";
//      A big switch statement which will check which status is currently happening


        switch(intent.getIntExtra(BatteryManager.EXTRA_STATUS, 0)){
            case BatteryManager.BATTERY_STATUS_CHARGING:
                batStatus.setText(status + "Charging");
                break;

            case BatteryManager.BATTERY_STATUS_DISCHARGING:
                batStatus.setText(status + "Discharging");
                break;

            case BatteryManager.BATTERY_STATUS_FULL:
                batStatus.setText(status + "Full");
                break;

            case BatteryManager.BATTERY_STATUS_NOT_CHARGING:
                batStatus.setText(status + "Not Charging");
                break;

            case BatteryManager.BATTERY_STATUS_UNKNOWN:
                batStatus.setText(status + "Unknown");
                break;
        }
    }
}