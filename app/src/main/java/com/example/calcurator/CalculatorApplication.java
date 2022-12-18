package com.example.calcurator;

import android.app.Application;
import android.widget.Button;

import io.realm.Realm;

public class CalculatorApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        Realm.init(this);
    }
}
