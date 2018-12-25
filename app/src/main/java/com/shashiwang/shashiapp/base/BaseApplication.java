package com.shashiwang.shashiapp.base;

import android.app.Application;

import com.baidu.mapapi.SDKInitializer;
import com.baidu.mapapi.cloud.CloudManager;
import com.example.config.Config;
import com.example.net.interceptors.LoggingInterceptor;
import com.facebook.stetho.Stetho;
import com.shashiwang.shashiapp.util.LatLngListener;

import cn.jpush.android.api.JPushInterface;


public class BaseApplication extends Application {
    private static final String TAG = "BaseApplication";
    

    @Override
    public void onCreate() {
        super.onCreate();

        Stetho.initializeWithDefaults(this);

        Config.init(this)
                .withApiHost("http://120.27.21.97/")
                .withInterceptor(new LoggingInterceptor())
                .configure();

        SDKInitializer.initialize(this);
        CloudManager.getInstance().init();
        CloudManager.getInstance().registerListener(new LatLngListener());

        JPushInterface.setDebugMode(true);
        JPushInterface.init(this);

    }
}
