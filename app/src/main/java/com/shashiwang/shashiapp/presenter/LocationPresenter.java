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
import com.baidu.mapapi.search.core.CityInfo;
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
import com.baidu.mapapi.search.sug.OnGetSuggestionResultListener;
import com.baidu.mapapi.search.sug.SuggestionResult;
import com.baidu.mapapi.search.sug.SuggestionSearch;
import com.baidu.mapapi.search.sug.SuggestionSearchOption;
import com.shashiwang.shashiapp.base.BasePresenter;
import com.shashiwang.shashiapp.view.ILocationView;

import java.util.List;

public class LocationPresenter extends BasePresenter<ILocationView> {
    private static final String TAG = "LocationPresenter";

    private LocationClient mLocClient;

    private PoiSearch mPoiSearch;
    private SuggestionSearch mSuggestionSearch;
    private PoiNearbySearchOption option = new PoiNearbySearchOption();
    private BDLocation curLocation;
    private MyLocationData locData;
    private LatLng latLng;

    public LocationPresenter(ILocationView view, Context context) {
        super(view, context);
    }

    @Override
    public void init(Bundle savedInstanceState) {
        mLocClient = new LocationClient(mContext);
        initLocation();
        initPoi();
        mLocClient.start();
    }

    private void initLocation(){
        mLocClient.registerLocationListener(new MyLocationListener());

        LocationClientOption option = new LocationClientOption();
        option.setCoorType("bd09ll");
        option.setOpenGps(true);

        mLocClient.setLocOption(option);

    }

    private void initPoi(){
        mPoiSearch = PoiSearch.newInstance();
        mSuggestionSearch = SuggestionSearch.newInstance();

        mSuggestionSearch.setOnGetSuggestionResultListener(suggestionResult -> {
            List<SuggestionResult.SuggestionInfo> data = suggestionResult.getAllSuggestions();
            mView.getSuggestionCity(data);

            if(data!=null){
                for(SuggestionResult.SuggestionInfo info : data){
                    Log.i(TAG, "onGetSuggestionResult: " + info.city);

                }
            }else {
                Log.i(TAG, "onGetSuggestionResult: data is null");
            }

        });

        mPoiSearch.setOnGetPoiSearchResultListener(new OnGetPoiSearchResultListener() {
            @Override
            public void onGetPoiResult(PoiResult poiResult) {
                mView.dismissProgress();
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

                List<PoiAddrInfo> poiAddrInfos =  poiResult.getAllAddr();
                if(poiAddrInfos !=null) {
                    for (PoiAddrInfo info : poiAddrInfos) {
                        Log.i(TAG, "onGetPoiResult: name = " + info.name);
                    }
                }else {
                    Log.i(TAG, "onGetPoiResult: poiAddrInfos is NULL");
                }


                List<CityInfo> cityInfos = poiResult.getSuggestCityList();
                if(cityInfos !=null) {
                    for (CityInfo info : cityInfos) {
                        Log.i(TAG, "onGetPoiResult: city = " + info.city);
                    }
                }else {
                    Log.i(TAG, "onGetPoiResult: cityInfos is NULL");
                }

            }

            @Override
            public void onGetPoiDetailResult(PoiDetailResult poiDetailResult) {
                Log.i("aab", "CURRENTLAT = " + poiDetailResult.getLocation().latitude + "");
                Log.i("aab", "CURRENTLNG = " + poiDetailResult.getLocation().longitude + "");
                Log.i("aab", "address = " + poiDetailResult.getAddress());
                Log.i("aab", "tag = " + poiDetailResult.getTag());
                Log.i("aab", "uid = " + poiDetailResult.getUid());
                Log.i("aab", "url = " + poiDetailResult.getDetailUrl());
            }

            @Override
            public void onGetPoiDetailResult(PoiDetailSearchResult poiDetailSearchResult) {
            }

            @Override
            public void onGetPoiIndoorResult(PoiIndoorResult poiIndoorResult) {

            }
        });

    }

    public void suggestionSearch(SuggestionSearchOption option){
        mSuggestionSearch.requestSuggestion(option);
    }

    public void searchNear(PoiNearbySearchOption option){
        Log.i(TAG, "suggestionSearch: ");
        option.location(latLng);
        mPoiSearch.searchNearby(option);
    }

    public void searchCity(PoiCitySearchOption option){
        mPoiSearch.searchInCity(option);
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
    public void destroy() {
        mPoiSearch.destroy();
        mLocClient.stop();
        super.destroy();
    }
}
