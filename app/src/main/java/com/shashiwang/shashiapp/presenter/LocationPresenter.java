package com.shashiwang.shashiapp.presenter;

import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;

import com.baidu.location.BDAbstractLocationListener;
import com.baidu.location.BDLocation;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.location.Poi;
import com.baidu.mapapi.map.MapStatusUpdate;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MyLocationData;
import com.baidu.mapapi.model.LatLng;
import com.baidu.mapapi.search.core.PoiInfo;
import com.baidu.mapapi.search.poi.OnGetPoiSearchResultListener;
import com.baidu.mapapi.search.poi.PoiAddrInfo;
import com.baidu.mapapi.search.poi.PoiCitySearchOption;
import com.baidu.mapapi.search.poi.PoiDetailResult;
import com.baidu.mapapi.search.poi.PoiDetailSearchResult;
import com.baidu.mapapi.search.poi.PoiIndoorResult;
import com.baidu.mapapi.search.poi.PoiNearbySearchOption;
import com.baidu.mapapi.search.poi.PoiResult;
import com.baidu.mapapi.search.poi.PoiSearch;
import com.example.ui.dialog.Loader;
import com.shashiwang.shashiapp.base.IBasePresenter;
import com.shashiwang.shashiapp.view.ILocationView;

import java.util.List;

public class LocationPresenter extends IBasePresenter<ILocationView>{
    private static final String TAG = "LocationPresenter";

    private LocationClient mLocationClient;

    private PoiSearch mPoiSearch;
    private PoiNearbySearchOption option = new PoiNearbySearchOption();
    private BDLocation curLocation;
    private LatLng curPoint;
    private MyLocationData locData;

    public LocationPresenter(Context context, ILocationView view) {
        mContext = context;
        mView  = view;

        mLocationClient = new LocationClient(mContext);
        initLocation();
        initPoi();
        mLocationClient.start();
        mLocationClient.requestLocation();
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
                //Log.i(TAG, "onGetPoiResult: data.length = "+data.size());
                if(data != null) {
                    for (PoiInfo info : data) {
                        Log.i(TAG, "onGetPoiResult: poi.address = " + info.address);
                        Log.i(TAG, "onGetPoiResult: poi.name = " + info.name);
                        Log.i(TAG, "onGetPoiResult: poi.location = " + info.location);
                    }
                    mView.loadDataSuccess(data);
                }else {
                    Log.i(TAG, "onGetPoiResult: data  is null" );
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
        mPoiSearch.searchNearby(option);
    }


    public  class MyLocationListener extends BDAbstractLocationListener {

        @Override
        public void onReceiveLocation(BDLocation location){
            Log.i(TAG, "onReceiveLocation");

            String addr = location.getAddrStr();    //获取详细地址信息
            String country = location.getCountry();    //获取国家
            String province = location.getProvince();    //获取省份
            String city = location.getCity();    //获取城市
            String district = location.getDistrict();    //获取区县
            String street = location.getStreet();    //获取街道信息
            int errorCode = location.getLocType();

            double latitude = location.getLatitude();    //获取纬度信息
            double longitude = location.getLongitude();    //获取经度信息

            List<Poi> poiList = location.getPoiList();

            if(!poiList.isEmpty()){
                for(Poi poi:poiList){
                    Log.i(TAG, "onReceiveLocation: poi = "+poi.getName());
                }
            }

            Log.i(TAG, "onReceiveLocation: addr = "+addr);
            Log.i(TAG, "onReceiveLocation: country = "+country);
            Log.i(TAG, "onReceiveLocation: province = "+province);
            Log.i(TAG, "onReceiveLocation: city = "+city);
            Log.i(TAG, "onReceiveLocation: district = "+district);
            Log.i(TAG, "onReceiveLocation: street = "+street);

            Log.i(TAG, "onReceiveLocation: latitude = "+latitude);
            Log.i(TAG, "onReceiveLocation: longitude = "+longitude);

            Log.i(TAG, "onReceiveLocation: errorCode = "+errorCode);

            curLocation = location;
            curPoint = new LatLng(curLocation.getLatitude(),location.getLongitude());

            mView.setMapLocation(location);

            PoiNearbySearchOption nearbySearchOption = new PoiNearbySearchOption()
                    .keyword("餐厅")//检索关键字
                    .location(curPoint)//检索位置
                    .pageNum(0)//分页编号，默认是0页
                    .pageCapacity(20)//设置每页容量，默认10条
                    .radius(5000);//附近检索半径

            searchNear(nearbySearchOption);
        }

    }



    @Override
    public void destroy() {
        mPoiSearch.destroy();
        mLocationClient.stop();

        super.destroy();
    }
}
