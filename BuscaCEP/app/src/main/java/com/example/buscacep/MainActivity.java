package com.example.buscacep;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    public static HttpWithService service;
    EditText txtCep;
    TextView txtResultado;
    Button btnBuscarCep;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        bindActivityInService(); //TODO: Service iniciado e bind feito ao startar a activity

        txtCep = findViewById(R.id.editTextNumber);
        txtResultado = findViewById(R.id.lblResposta);

        btnBuscarCep = findViewById(R.id.btnBuscaCep);

        btnBuscarCep.setOnClickListener(
                v-> {
                    HttpWithService service = new HttpWithService();
                    try {
                        CEP saida = service.httpWithService(txtCep.getText().toString().trim());
                        txtResultado.setText(saida.toString());
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
        );
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
            MainActivity.service = binder.pegarInstancia();
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            service = null;
        }
    };
}