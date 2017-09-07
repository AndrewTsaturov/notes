package com.example.notes.utils;

import android.util.Log;

public class LogUtils {

    private static final String TAG = "SaturovLogs";

    public static void E(String message) {
        Log.e(TAG, message);
    }
    public static void D(String m) {
        Log.d(TAG, m);
    }
}
