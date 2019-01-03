package com.shashiwang.shashiapp.service;

import android.annotation.SuppressLint;
import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Toast;

import com.baidu.location.BDAbstractLocationListener;
import com.baidu.location.BDLocation;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.mapapi.model.LatLng;
import com.example.net.interceptors.TokenInterceptor;
import com.example.net.rx.RxRetrofitClient;
import com.example.util.SharedPreferencesHelper;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.shashiwang.shashiapp.bean.HttpResult;
import com.shashiwang.shashiapp.presenter.LocationPresenter;
import com.shashiwang.shashiapp.presenter.LoginPresenter;

import cn.jpush.android.api.JPushInterface;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

import static com.shashiwang.shashiapp.constant.ApiConstant.URL_LOGIN;
import static com.shashiwang.shashiapp.constant.ApiConstant.URL_UPLOAD_LOCATION;
import static com.shashiwang.shashiapp.constant.Constant.REGISTRATION_ID;
import static com.shashiwang.shashiapp.constant.Constant.TOKEN;
import static com.shashiwang.shashiapp.constant.Constant.USER_NAME;

public class LocationService extends Service {
    private static final String TAG = "LocationService";

    private LocationClient mLocClient;

    public LocationService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        return null;
    }

    @Override
    public void onCreate() {
        mLocClient = new LocationClient(this);
        super.onCreate();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        if(!mLocClient.isStarted()) {

            LocationClientOption option = new LocationClientOption();
            option.setCoorType("bd09ll");
            option.setOpenGps(true);
            // 1 分钟一次
            option.setScanSpan(1*60*1000);

            mLocClient.setLocOption(option);
            mLocClient.registerLocationListener(new MyLocationListener());
            mLocClient.start();
        }
        return super.onStartCommand(intent, flags, startId);
    }

    public class MyLocationListener extends BDAbstractLocationListener {

        @SuppressLint("CheckResult")
        @Override
        public void onReceiveLocation(BDLocation location){
            Log.i(TAG, "onReceiveLocation");
            Log.i(TAG, "onReceiveLocation: lat = " + location.getLatitude() + "  lng = " + location.getLongitude());

            String token = (String) SharedPreferencesHelper.getSharedPreference(TOKEN,"");

            if(TextUtils.isEmpty(token)){
                return;
            }

            RxRetrofitClient.builder()
                    .url(URL_UPLOAD_LOCATION)
                    .header(new TokenInterceptor())
                    .params("location_lat",location.getLatitude())
                    .params("location_lng",location.getLongitude())
                    .build()
                    .post()
                    .subscribeOn(Schedulers.newThread())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(s -> {
                        Log.i(TAG, "login: success " + s);
                        HttpResult result = new Gson().fromJson(s,new TypeToken<HttpResult>(){}.getType());

                        if(result.isSuccess()){

                        }else {

                        }

                    }, throwable -> {
                        Log.i(TAG, "login: error = " + throwable);
                    });
        }

    }
}
