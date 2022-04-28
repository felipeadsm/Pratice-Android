package com.example.cinapp;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.Nullable;

public class MyService1 extends Service {

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        String text = intent.getExtras().getString("Text");
        if (text == null) {
            Log.e("devlog", "Sem texto");
        } else if (!text.equals("")){
            Toast.makeText(this, text, Toast.LENGTH_SHORT).show();
        }

        return super.onStartCommand(intent, flags, startId);
    }
}
