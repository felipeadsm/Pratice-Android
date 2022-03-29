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

import java.util.ArrayList;
import java.util.List;

public class SecondScreenActivity extends AppCompatActivity {

    private TextView mensagem;
    private static final String[] CONTATOS_PERMISSIONS = {
            Manifest.permission.READ_CONTACTS
    };
    private static final int CONTATOS_REQUEST = 1;


    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        Button botao2 = findViewById(R.id.button2);
        Button botao5 = findViewById(R.id.button5);
        mensagem = findViewById(R.id.textView5);

        //TODO: Monitorar a tela em primeiro plano
        Intent intent = new Intent(this,MyIntentService.class);
        startService(intent);

        botao2.setOnClickListener(v->{
            Intent screenThree = new Intent(this, ThirdScreenActivity.class);
            startActivity(screenThree);
        });

        botao5.setOnClickListener(v->{
            if(acceptPermission()){
                List<MyContact> contacts = ContactsHelper.getContacts(this);
                ArrayList<String> nomes = new ArrayList<>();
                if(contacts.size() >=2){
                    for (MyContact contact : contacts) {
                        nomes.add(contact.getName());
                    }

                    accessResources(nomes.get(1));
                }else{
                    mensagem.setText("Não tem dois contatos salvos");
                }

            } else {
                requestPermissions(CONTATOS_PERMISSIONS,CONTATOS_REQUEST);
                deniedPermission();
            }
        });
    }

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
                if(contacts.size() >=2){
                    for (MyContact contact : contacts) {
                        nomes.add(contact.getName());
                    }
                    accessResources(nomes.get(1));
                }else{
                    mensagem.setText("Não tem dois contatos salvos");
                }
            } else {
                mensagem.setText("Não deu Permissão para os contatos");
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