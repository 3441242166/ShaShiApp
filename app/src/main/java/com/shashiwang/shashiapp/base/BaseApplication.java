package com.shashiwang.shashiapp.base;

import android.app.Application;
import android.util.Log;

import com.baidu.location.LocationClient;
import com.baidu.mapapi.SDKInitializer;
import com.baidu.mapapi.cloud.CloudManager;
import com.baidu.tts.client.SpeechSynthesizer;
import com.example.config.Config;
import com.example.net.interceptors.TokenInterceptor;
import com.shashiwang.shashiapp.util.LatLngListener;

import cn.jpush.android.api.JPushInterface;


public class BaseApplication extends Application {
    private static final String TAG = "BaseApplication";
    

    @Override
    public void onCreate() {
        super.onCreate();

        Config.init(this)
                .withApiHost("http://120.27.21.97/")
                .configure();

        SDKInitializer.initialize(this);
        CloudManager.getInstance().init();
        CloudManager.getInstance().registerListener(new LatLngListener());

        JPushInterface.setDebugMode(true);
        JPushInterface.init(this);
        //mLocationClient = new LocationClient(this);
        //声明LocationClient类
        //mLocationClient.registerLocationListener(myListener);

    }
}
