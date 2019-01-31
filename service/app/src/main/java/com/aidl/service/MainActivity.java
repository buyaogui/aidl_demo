package com.aidl.service;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
//
//        Intent intent = new Intent(getApplicationContext(), MyAidlService.class);
//        bindService(intent, mConnection, BIND_AUTO_CREATE);
    }

    public void start(View view) {
        Intent startService = new Intent(this, MyAidlService.class);
        startService(startService);
    }

    public void stop(View view) {
        Intent stopService = new Intent(this, MyAidlService.class);
        stopService(stopService);
    }
}
