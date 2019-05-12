package com.shashiwang.shashiapp.presenter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.baidu.location.BDAbstractLocationListener;
import com.baidu.location.BDLocation;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.mapapi.map.MyLocationData;
import com.baidu.mapapi.model.LatLng;
import com.baidu.mapapi.search.poi.PoiSearch;
import com.baidu.mapapi.search.sug.SuggestionSearch;
import com.example.net.rx.RxRetrofitClient;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.shashiwang.shashiapp.base.BasePresenter;
import com.shashiwang.shashiapp.bean.Count;
import com.shashiwang.shashiapp.bean.HttpResult;
import com.shashiwang.shashiapp.fragment.IMapView;

import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

import static com.shashiwang.shashiapp.util.DataUtil.nowCount;


public class MapFragmentPresenter extends BasePresenter<IMapView> {
    private static final String TAG = "MapFragmentPresenter";

    private LocationClient mLocClient;

    public MapFragmentPresenter(IMapView view, Context context) {
        super(view, context);
    }

    @Override
    protected void init(Bundle savedInstanceState) {
        mLocClient = new LocationClient(mContext);
        initLocation();
        mLocClient.start();
    }

    private void initLocation() {
        Log.i(TAG, "initLocation: ");
        mLocClient.registerLocationListener(new BDAbstractLocationListener() {
            @Override
            public void onReceiveLocation(BDLocation location) {
                Log.i(TAG, "onReceiveLocation: " + location.getLocType());
//                Toast.makeText(mContext,""+ location.getLatitude()+"  " + location.getLongitude(),
//                        Toast.LENGTH_SHORT).show();
                postLocation(String.valueOf(location.getLatitude()),
                        String.valueOf(location.getLongitude()));
                getSonPosition(location);
            }
        });

        LocationClientOption locationOption = new LocationClientOption();
        locationOption.setCoorType("bd09ll");
        //可选，默认高精度，设置定位模式，高精度，低功耗，仅设备
        locationOption.setLocationMode(LocationClientOption.LocationMode.Hight_Accuracy);
        //可选，默认0，即仅定位一次，设置发起连续定位请求的间隔需要大于等于1000ms才是有效的
        locationOption.setScanSpan(3000);
        //可选，设置是否需要设备方向结果
        locationOption.setNeedDeviceDirect(true);
        //可选，默认false，设置是否当gps有效时按照1S1次频率输出GPS结果
        locationOption.setLocationNotify(true);
        //可选，默认false，设置是否开启Gps定位
        locationOption.setOpenGps(true);

        mLocClient.setLocOption(locationOption);
        mLocClient.start();
    }

    @SuppressLint("CheckResult")
    public void postLocation(String lat, String lng){
        RxRetrofitClient.builder()
                .url("postPosition")
                .params("lat",lat)
                .params("lng",lng)
                .params("count",nowCount.count)
                .build()
                .get()
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(s -> { }, throwable -> { });
    }

    @SuppressLint("CheckResult")
    public void getSonPosition(BDLocation location){
        RxRetrofitClient.builder()
                .url("getPosition")
                .params("count",nowCount.count)
                .build()
                .get()
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(s -> {
                            Log.i(TAG, "accept: "+s);

                            HttpResult<List<Count>> result = new Gson().fromJson(s,new TypeToken<HttpResult<List<Count>>>(){}.getType());

                            if(result.isSuccess()){
                                mView.setMapLocation(location);
                                mView.loadDataSuccess(result.getData());
                            }else {
                                mView.errorMessage(result.getMessage());
                            }

                        }
                        , throwable -> {
                            Log.i(TAG, "accept: "+throwable);
                            mView.errorMessage(throwable.toString());
                        });
    }

    @Override
    public void destroy() {
        mLocClient.stop();
        super.destroy();
    }
}
