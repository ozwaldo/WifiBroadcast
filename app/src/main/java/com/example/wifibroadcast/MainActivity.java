package com.example.wifibroadcast;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.wifi.WifiManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private Switch aSwitch;
    private WifiManager wifiManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        aSwitch = (Switch) findViewById(R.id.swWifi);

        wifiManager = (WifiManager) getApplicationContext().getSystemService(Context.WIFI_SERVICE);

        aSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean
                    isChecked) {

                if (isChecked) {
                    wifiManager.setWifiEnabled(true);
                    aSwitch.setText("WIFI ON ");
                    /*Toast.makeText(MainActivity.this, "Switch ON",
                            Toast.LENGTH_SHORT).show();*/
                } else  {
                    wifiManager.setWifiEnabled(false);
                    aSwitch.setText("WIFI OFF ");
                    /*Toast.makeText(MainActivity.this, "Switch OFF",
                            Toast.LENGTH_SHORT).show();*/
                }
            }
        });

        /*if (wifiManager.isWifiEnabled()) {
            aSwitch.setChecked(true);
            aSwitch.setText("WIFI ON ");
        } else {
            aSwitch.setChecked(false);
            aSwitch.setText("WIFI OFF ");
        }*/
    }

    private BroadcastReceiver wifiBR = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            int wifiStateEXtra = intent.getIntExtra(
                    wifiManager.EXTRA_WIFI_STATE,
                    wifiManager.WIFI_STATE_UNKNOWN);

            switch (wifiStateEXtra) {
                case WifiManager.WIFI_STATE_ENABLED:
                    aSwitch.setChecked(true);
                    aSwitch.setText("WIFI ON ");
                    break;
                case WifiManager.WIFI_STATE_DISABLED:
                    aSwitch.setChecked(false);
                    aSwitch.setText("WIFI OFF ");
                    break;
            }
        }
    };

    @Override
    protected void onStart() {
        super.onStart();
        IntentFilter intentFilter = new IntentFilter(WifiManager.WIFI_STATE_CHANGED_ACTION);
        registerReceiver(wifiBR,intentFilter);
    }

    @Override
    protected void onStop() {
        super.onStop();
        unregisterReceiver(wifiBR);
    }
}
