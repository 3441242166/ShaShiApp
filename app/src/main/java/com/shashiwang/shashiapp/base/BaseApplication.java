package com.shashiwang.shashiapp.base;

import android.app.Application;
import android.text.TextUtils;
import android.util.Log;

import com.baidu.mapapi.SDKInitializer;
import com.baidu.mapapi.cloud.CloudManager;
import com.example.config.Config;
import com.example.net.interceptors.LoggingInterceptor;
import com.example.util.SharedPreferencesHelper;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.liulishuo.filedownloader.FileDownloader;
import com.shashiwang.shashiapp.bean.Count;
import com.shashiwang.shashiapp.util.DataUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.jpush.android.api.JPushInterface;

import static com.shashiwang.shashiapp.constant.Constant.TOKEN;


public class BaseApplication extends Application {
    private static final String TAG = "BaseApplication";

    @Override
    public void onCreate() {
        super.onCreate();


        Config.init(this)
                .withApiHost("isFirst",true)
                .withApiHost("http://wanhao.ngrok.xiaomiqiu.cn/")
                .withInterceptor(new LoggingInterceptor())
                .configure();

        SDKInitializer.initialize(BaseApplication.this);
        initJPush();
        FileDownloader.setup(BaseApplication.this);

        DataUtil.init();
    }

    private void initJPush(){
        JPushInterface.setDebugMode(true);
        JPushInterface.init(this);
        if(TextUtils.isEmpty((String) SharedPreferencesHelper.getSharedPreference(TOKEN,""))){
            JPushInterface.stopPush(this);
        }
    }

}
