package com.psx.customview;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    FuelGauge fuelGauge;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        fuelGauge = findViewById(R.id.fuel_gauge);
    }


    public void startMagic(View view) {
        fuelGauge.startMagic();
    }
}
