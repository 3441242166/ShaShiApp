package com.shashiwang.shashiapp.base;

import android.app.Application;
import android.text.TextUtils;
import android.util.Log;

import com.baidu.mapapi.SDKInitializer;
import com.baidu.mapapi.cloud.CloudManager;
import com.example.config.Config;
import com.example.net.interceptors.LoggingInterceptor;
import com.example.util.SharedPreferencesHelper;
import com.facebook.stetho.Stetho;
import com.shashiwang.shashiapp.activity.MainActivity;
import com.shashiwang.shashiapp.util.LatLngListener;

import cn.jpush.android.api.JPushInterface;

import static com.shashiwang.shashiapp.constant.Constant.TOKEN;


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
        String registrationId = JPushInterface.getRegistrationID(this);
        Log.i(TAG, "login: registrationId = "+registrationId);

        JPushInterface.setDebugMode(true);
        JPushInterface.init(this);

        if(TextUtils.isEmpty((String) SharedPreferencesHelper.getSharedPreference(TOKEN,""))){
            JPushInterface.stopPush(this);
        }

    }
}
