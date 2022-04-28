package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.net.wifi.WifiManager;
import android.util.Log;
import android.widget.Button;

import android.os.Bundle;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class FirstScreenActivity extends AppCompatActivity {

    private MyBroadcastReceiver receiver;

    private TextView message;

    private static final String[] CONTATOS_PERMISSIONS = {
      Manifest.permission.READ_CONTACTS
    };

    private static final int CONTATOS_REQUEST = 1;


    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button botao = findViewById(R.id.button);
        Button botao2 = findViewById(R.id.button4);
        message = findViewById(R.id.textView4);

        //TODO: Monitorar a tela em primeiro plano
        Intent intent = new Intent(this,MyIntentService.class);
        startService(intent);

        botao.setOnClickListener(v->{
            Intent screenTwo = new Intent(this, SecondScreenActivity.class);
            startActivity(screenTwo);
        });


        botao2.setOnClickListener(v->{
            if(acceptPermission()){
                List<MyContact> contacts = ContactsHelper.getContacts(this);
                ArrayList<String> nomes = new ArrayList<>();
                if(contacts.size() >= 1){
                    for (MyContact contact : contacts) {
                        nomes.add(contact.getName());
                    }

                    accessResources(nomes.get(0));
                }else{
                    message.setText("Não tem contatos salvos");
                }

            } else {
                requestPermissions(CONTATOS_PERMISSIONS,CONTATOS_REQUEST);
                deniedPermission();
            }
        });
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onRequestPermissionsResult(
            int requestCode,
            @NonNull String[] permissions,
            @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if(requestCode == CONTATOS_REQUEST){
            if(grantResults[0] == PackageManager.PERMISSION_GRANTED){
                List<MyContact> contacts = ContactsHelper.getContacts(this);
                ArrayList<String> nomes = new ArrayList<>();
                if(contacts.size() >= 1){
                    for (MyContact contact : contacts) {
                        nomes.add(contact.getName());
                    }
                    accessResources(nomes.get(0));
                } else {
                    message.setText("Não tem contatos salvos");
                }
            } else {
                message.setText("Não deu Permissão para os contatos");
            }
        }
    }

    private boolean acceptPermission(){
        return checkSelfPermission(Manifest.permission.READ_CONTACTS)
                == PackageManager.PERMISSION_GRANTED;
    }

    private void deniedPermission(){
        message.setText(R.string.msg_sem_permissao);
    }

    private void accessResources(String resultadoID){
        message.setText(resultadoID);
    }

    @Override
    protected void onStart() {
        super.onStart();
        receiver = new MyBroadcastReceiver();
        IntentFilter filter = new IntentFilter(WifiManager.WIFI_STATE_CHANGED_ACTION);
        registerReceiver(receiver,filter);
    }

    @Override
    protected void onStop() {
        super.onStop();
        receiver = new MyBroadcastReceiver();
        IntentFilter filter = new IntentFilter(WifiManager.WIFI_STATE_CHANGED_ACTION);
        registerReceiver(receiver, filter);
    }

    class MyBroadcastReceiver extends BroadcastReceiver {
        @SuppressLint("SetTextI18n")
        @Override
        public void onReceive(Context context, Intent intent) {
            String status;
            if (checkWifiOnAndConnected()) {
                status = "Habilitado";
                Log.d("FASM", "O Status do Wi-Fi Mudou: " + status);
            } else {
                status = "Desabilitado";
                Log.d("FASM", "O Status do Wi-Fi Mudou: " + status);
            }
        }
    }

    public boolean checkWifiOnAndConnected() {
        WifiManager wifiMgr = (WifiManager) getApplicationContext().getSystemService(Context.WIFI_SERVICE);
        if (wifiMgr.isWifiEnabled()) {
            return true;
        } else {
            return false;
        }
    }

}