package com.shashiwang.shashiapp.base;

import android.app.Application;
import android.text.TextUtils;
import android.util.Log;

import com.baidu.mapapi.SDKInitializer;
import com.baidu.mapapi.cloud.CloudManager;
import com.example.config.Config;
import com.example.net.interceptors.LoggingInterceptor;
import com.example.util.SharedPreferencesHelper;
import com.liulishuo.filedownloader.FileDownloader;

import cn.jpush.android.api.JPushInterface;

import static com.shashiwang.shashiapp.constant.Constant.TOKEN;


public class BaseApplication extends Application {
    private static final String TAG = "BaseApplication";

    public static boolean isFirst = true;

    @Override
    public void onCreate() {
        super.onCreate();


        Config.init(this)
                .withApiHost("isFirst", true)
                .withApiHost("http://api.shashiwang/")
                .withInterceptor(new LoggingInterceptor())
                .configure();

        SDKInitializer.initialize(BaseApplication.this);
        initJPush();
        FileDownloader.setup(BaseApplication.this);

    }

    private void initJPush() {
        JPushInterface.setDebugMode(true);
        JPushInterface.init(this);
        if (TextUtils.isEmpty((String) SharedPreferencesHelper.getSharedPreference(TOKEN, ""))) {
            JPushInterface.stopPush(this);
        }
    }
}
