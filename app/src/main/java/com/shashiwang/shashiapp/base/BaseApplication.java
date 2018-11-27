package com.shashiwang.shashiapp.base;

import android.app.Application;

import com.example.config.Config;


public class BaseApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        Config.init(this)
                .withApiHost("http://toppest.ink:8086/")
                .configure();

    }
}
