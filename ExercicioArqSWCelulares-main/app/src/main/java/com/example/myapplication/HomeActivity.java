package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

public class HomeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        Button button = findViewById(R.id.button7);
        Button button_2 = findViewById(R.id.button8);

        //TODO: Monitorar a tela em primeiro plano
        Intent intent = new Intent(this,MyIntentService.class);
        startService(intent);

        button.setOnClickListener(v->{
            Intent screenOne = new Intent(this, FirstScreenActivity.class);
            startActivity(screenOne);
        });

        button_2.setOnClickListener(v->{
            Intent scrennFinal = new Intent(this, FinalActivity.class);
            startActivity(scrennFinal);
        });
    }
}