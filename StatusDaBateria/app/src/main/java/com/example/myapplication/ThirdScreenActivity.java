package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.List;

public class ThirdScreenActivity extends AppCompatActivity {

    private TextView mensagem;
    private static final String[] CONTATOS_PERMISSIONS = {
            Manifest.permission.READ_CONTACTS
    };
    private static final int CONTATOS_REQUEST = 1;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main3);

        Button botao3 = findViewById(R.id.button3);
        Button botao6 = findViewById(R.id.button6);

        mensagem = findViewById(R.id.textView6);

        //TODO: Monitorar a tela em primeiro plano
        Intent intent = new Intent(this,MyIntentService.class);
        startService(intent);

        botao3.setOnClickListener(
                v-> Snackbar.make(v,"Não existe tela 4", Snackbar.LENGTH_LONG).show()
        );

        botao6.setOnClickListener(v->{
            if(acceptPermission()){
                List<MyContact> contacts = ContactsHelper.getContacts(this);
                ArrayList<String> nomes = new ArrayList<>();
                if(contacts.size() >=3){
                    for (MyContact contact : contacts) {
                        nomes.add(contact.getName());
                    }
                    accessResources(nomes.get(2));
                } else {
                    mensagem.setText("Não tem três contatos salvos");
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
                if(contacts.size() >=3){
                    for (MyContact contact : contacts) {
                        nomes.add(contact.getName());
                    }
                    accessResources(nomes.get(2));
                } else {
                    mensagem.setText("O dispositivo não tem três contatos salvos!");
                }
            } else {
                mensagem.setText("Permissão para acessar os contatos negada!");
            }
        }
    }

    private boolean acceptPermission(){
        return checkSelfPermission(Manifest.permission.READ_CONTACTS
        ) == PackageManager.PERMISSION_GRANTED;
    }

    private void deniedPermission(){
        mensagem.setText(R.string.msg_sem_permissao);
    }

    private void accessResources(String resultadoID){
        mensagem.setText(resultadoID);
    }
}