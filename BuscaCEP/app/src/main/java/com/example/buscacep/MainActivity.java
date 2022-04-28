package com.example.buscacep;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.util.concurrent.ExecutionException;

public class MainActivity extends AppCompatActivity {

    public static HttpWithService conexao;

    EditText txtCep;

    TextView txtResultado;

    Button btnBuscarCep;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        bindActivityInService();  //todo: Service iniciado e bind feito ao startar a activity

        txtCep = findViewById(R.id.editTextNumber);
        txtResultado = findViewById(R.id.lblResposta);

        btnBuscarCep = findViewById(R.id.btnBuscaCep);

        btnBuscarCep.setOnClickListener(v -> {
                try {
                    CEP cep = new HttpService(txtCep.getText().toString().trim()).execute().get();

                    txtResultado.setText(cep.toString());

                } catch (ExecutionException | InterruptedException e) {
                    e.printStackTrace();
                }
            });
    }

    private void bindActivityInService(){
        Intent i = new Intent(this, HttpWithService.class);
        startService(i);
        bindService(
                i,
                serviceConnection,
                Context.BIND_AUTO_CREATE);
    }

    private final ServiceConnection serviceConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            HttpWithService.ConnectionBinder binder = (HttpWithService.ConnectionBinder) service;
            conexao = binder.pegarInstancia();

        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
        }
    };
}