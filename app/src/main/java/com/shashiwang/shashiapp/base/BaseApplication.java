package com.shashiwang.shashiapp.base;

import android.app.Application;

import com.baidu.location.LocationClient;
import com.baidu.mapapi.SDKInitializer;
import com.baidu.mapapi.cloud.CloudManager;
import com.example.config.Config;
import com.example.net.interceptors.TokenInterceptor;
import com.shashiwang.shashiapp.util.LatLngListener;


public class BaseApplication extends Application {

    public LocationClient mLocationClient = null;
    //private MyLocationListener myListener = new MyLocationListener();

    @Override
    public void onCreate() {
        super.onCreate();

        Config.init(this)
                .withApiHost("http://120.27.21.97/")
                .configure();

        SDKInitializer.initialize(this);
        CloudManager.getInstance().init();
        CloudManager.getInstance().registerListener(new LatLngListener());

        //mLocationClient = new LocationClient(this);
        //声明LocationClient类
        //mLocationClient.registerLocationListener(myListener);
    }
}
