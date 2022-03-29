package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

public class FinalActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_final);

        Button button = findViewById(R.id.button9);
        Button button_2 = findViewById(R.id.button10);
        Button button_3 = findViewById(R.id.button11);


        //TODO: Monitorar a tela em primeiro plano
        Intent intent = new Intent(this,MyIntentService.class);
        startService(intent);

        button.setOnClickListener(v-> {
            Intent screenWiFi = new Intent(this, WiFiStatusActivity.class);
            startActivity(screenWiFi);
        });

        button_2.setOnClickListener(v-> {
            Intent screenAppList = new Intent(this, AppListActivity.class);
            startActivity(screenAppList);
        });

        button_3.setOnClickListener(v->{
            Intent screenBatery = new Intent(this, BatteryActivity.class);
            startActivity(screenBatery);
        });
    }
}