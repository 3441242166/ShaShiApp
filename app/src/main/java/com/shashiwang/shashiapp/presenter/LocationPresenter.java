package com.shashiwang.shashiapp.presenter;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;

import com.baidu.location.BDAbstractLocationListener;
import com.baidu.location.BDLocation;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.mapapi.map.MyLocationData;
import com.baidu.mapapi.model.LatLng;
import com.baidu.mapapi.search.core.PoiInfo;
import com.baidu.mapapi.search.poi.OnGetPoiSearchResultListener;
import com.baidu.mapapi.search.poi.PoiDetailResult;
import com.baidu.mapapi.search.poi.PoiDetailSearchResult;
import com.baidu.mapapi.search.poi.PoiIndoorResult;
import com.baidu.mapapi.search.poi.PoiNearbySearchOption;
import com.baidu.mapapi.search.poi.PoiResult;
import com.baidu.mapapi.search.poi.PoiSearch;
import com.shashiwang.shashiapp.base.BasePresenter;
import com.shashiwang.shashiapp.view.ILocationView;

import java.util.List;

public class LocationPresenter extends BasePresenter<ILocationView> {
    private static final String TAG = "LocationPresenter";

    private LocationClient mLocationClient;

    private PoiSearch mPoiSearch;
    private PoiNearbySearchOption option = new PoiNearbySearchOption();
    private BDLocation curLocation;
    private MyLocationData locData;
    private LatLng latLng;

    public LocationPresenter(ILocationView view, Context context) {
        super(view, context);
    }

    private void initLocation(){
        LocationClientOption option = new LocationClientOption();

        option.setLocationMode(LocationClientOption.LocationMode.Hight_Accuracy);
        option.setCoorType("bd09ll");
        option.setOpenGps(true);
        option.setIsNeedAddress(true);
        option.setIsNeedLocationDescribe(true);
        option.setIsNeedLocationPoiList(true);

        mLocationClient.setLocOption(option);

        mLocationClient.registerLocationListener(new MyLocationListener());
    }

    private void initPoi(){
        mPoiSearch = PoiSearch.newInstance();

        mPoiSearch.setOnGetPoiSearchResultListener(new OnGetPoiSearchResultListener() {
            @Override
            public void onGetPoiResult(PoiResult poiResult) {
                Log.i(TAG, "onGetPoiResult");

                List<PoiInfo> data = poiResult.getAllPoi();
                if(data != null) {
                    for (PoiInfo info : data) {
                        Log.i(TAG, "onGetPoiResult: poi.address = " + info.address);
                        Log.i(TAG, "onGetPoiResult: poi.name = " + info.name);
                        Log.i(TAG, "onGetPoiResult: poi.location = " + info.location);
                    }
                    mView.loadDataSuccess(data);
                }else {
                    Log.i(TAG, "onGetPoiResult: DATA  is null" );
                }

            }

            @Override
            public void onGetPoiDetailResult(PoiDetailResult poiDetailResult) {

            }

            @Override
            public void onGetPoiDetailResult(PoiDetailSearchResult poiDetailSearchResult) {

            }

            @Override
            public void onGetPoiIndoorResult(PoiIndoorResult poiIndoorResult) {

            }
        });

    }

    public void searchNear(PoiNearbySearchOption option){
        Log.i(TAG, "searchNear: ");
        option.location(latLng);
        mPoiSearch.searchNearby(option);
    }

    public void setLatLng(LatLng latLng) {
        Log.i(TAG, "setLatLng: ");
        this.latLng = latLng;
    }


    public  class MyLocationListener extends BDAbstractLocationListener {

        @Override
        public void onReceiveLocation(BDLocation location){
            Log.i(TAG, "onReceiveLocation");
            latLng = new LatLng(location.getLatitude(),location.getLongitude());
            mView.setMapLocation(location);
        }

    }


    @Override
    public void init(Bundle savedInstanceState) {
        mLocationClient = new LocationClient(mContext);
        initLocation();
        initPoi();
        mLocationClient.start();
        mLocationClient.requestLocation();
    }

    @Override
    public void destroy() {
        mPoiSearch.destroy();
        mLocationClient.stop();
        super.destroy();
    }
}
