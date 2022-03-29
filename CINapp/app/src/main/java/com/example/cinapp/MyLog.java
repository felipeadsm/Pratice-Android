package com.example.cinapp;

import android.util.Log;

public class MyLog {
    private static final boolean DEBUG = BuildConfig.DEBUG;

    public static void debug(String tag, String text) {
        if (DEBUG) {
            Log.d(tag, text);
        }
    }

    public static void info(String tag, String text) {
        Log.i(tag, text);
    }
}
