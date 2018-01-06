package com.android.logcat.crashlogger;

import android.app.Application;

import org.acra.ACRA;
import org.acra.annotation.AcraHttpSender;
import org.acra.sender.HttpSender;

/**
 * Created by Null on 2018-01-03.
 */

@AcraHttpSender(uri = "http://192.168.0.7:8080/crash",
        httpMethod = HttpSender.Method.POST)
public class CreateAcra extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        ACRA.init(this);
    }
}