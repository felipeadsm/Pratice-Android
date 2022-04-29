package com.example.buscacep;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.util.Log;

import com.google.gson.Gson;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Scanner;

public class HttpWithService extends Service {

    public CEP httpWithService(String cepinserido) throws InterruptedException {
        StringBuilder resposta = new StringBuilder();

        if (cepinserido != null && cepinserido.length() == 8) {
            Thread thread = new Thread(()-> { //TODO: é aconselhável rodar o service em uma thread axiliar
                try {
                    URL url = new URL("https://viacep.com.br/ws/" + cepinserido +"/json");

                    HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                    connection.setRequestMethod("GET");
                    connection.setRequestProperty("Content-type", "application/json");
                    connection.setRequestProperty("Accept", "application/json");
                    connection.setDoOutput(true);
                    connection.setConnectTimeout(5000);
                    connection.connect();

                    Scanner scanner = new Scanner(url.openStream());
                    while (scanner.hasNext()) {
                        resposta.append(scanner.next());
                        Log.d("resposta: ", String.valueOf(resposta));
                    }
                } catch (IOException e){
                    e.printStackTrace();
                }});
            thread.start();
            thread.join();
        }
        return new Gson().fromJson(resposta.toString(), CEP.class);
    }

    @Override
    public IBinder onBind(Intent intent) {
        return new HttpWithService.ConnectionBinder();
    }

    public class ConnectionBinder extends Binder {
        public HttpWithService pegarInstancia() {

            return HttpWithService.this;
        }
    }
}