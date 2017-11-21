package com.example.wedless.myintentservice.util;

import android.util.Log;

/**
 * Created by Wedless on 2017/11/3.
 */

public class MyLog {

    public static final String TAG = "MyLog";

    public static void show(String msg) {

        d(TAG, msg);

    }
    public static void d(String tag, String msg) {

        Log.d(TAG, "msg = " + msg);

    }
}
