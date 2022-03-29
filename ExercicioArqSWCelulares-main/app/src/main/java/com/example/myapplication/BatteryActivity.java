package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

public class BatteryActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_batery);

        //TODO: Setar as variáveis nesses TextViews
        TextView full_charge = findViewById(R.id.textView16);
        TextView charge_time = findViewById(R.id.textView17);
        TextView battery_health = findViewById(R.id.textView18);

        //TODO: Monitorar a tela em primeiro plano
        Intent intent = new Intent(this,MyIntentService.class);
        startService(intent);


    }
}