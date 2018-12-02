package com.shashiwang.shashiapp.activity;

import android.support.v7.app.AppCompatActivity;

import com.baidu.location.BDLocation;
import com.baidu.mapapi.cloud.CloudRgcResult;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BitmapDescriptor;
import com.baidu.mapapi.map.BitmapDescriptorFactory;
import com.baidu.mapapi.map.MapStatus;
import com.baidu.mapapi.map.MapStatusUpdate;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.MarkerOptions;
import com.baidu.mapapi.map.MyLocationConfiguration;
import com.baidu.mapapi.map.MyLocationData;
import com.baidu.mapapi.map.OverlayOptions;
import com.baidu.mapapi.model.LatLng;
import com.baidu.mapapi.search.core.PoiInfo;
import com.baidu.mapapi.search.poi.PoiNearbySearchOption;
import com.shashiwang.shashiapp.R;
import com.shashiwang.shashiapp.adapter.LocationAdapter;
import com.shashiwang.shashiapp.base.IBasePresenter;
import com.shashiwang.shashiapp.base.TopBarBaseActivity;
import com.shashiwang.shashiapp.presenter.LocationPresenter;
import com.shashiwang.shashiapp.view.ILocationView;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.MotionEvent;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import javax.security.auth.login.LoginException;

import butterknife.BindView;

public class LocationActivity extends TopBarBaseActivity<LocationPresenter> implements ILocationView{
    private static final String TAG = "LocationActivity";

    @BindView(R.id.mp_location)
    MapView mapView;
    @BindView(R.id.tv_location_search)
    TextView tvSearch;
    @BindView(R.id.rv_location)
    RecyclerView recyclerView;

    private BaiduMap map;

    private LocationAdapter adapter;

    @Override
    protected LocationPresenter setPresenter() {
        return new LocationPresenter(this,this);
    }

    @Override
    protected int getContentView() {
        return R.layout.activity_loction;
    }

    @Override
    protected void init(Bundle savedInstanceState) {
        initView();
        initConfig();
        initEvent();
    }

    private void initView() {
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new LocationAdapter(null,this);
        recyclerView.setAdapter(adapter);
    }

    private void initConfig() {
        map = mapView.getMap();
        map.setMyLocationEnabled(true);
        map.setMapType(BaiduMap.MAP_TYPE_NORMAL);
    }

    private void initEvent(){
        //map.seton
        map.setOnMapTouchListener(motionEvent -> {
            switch (motionEvent.getAction()) {
                case MotionEvent.ACTION_UP:
//                    option.location(currentPt);
//                    if (!TextUtils.isEmpty(currentSearch)) {
//                        option.keyword(currentSearch);
//                    } else {
//                        option.keyword(initSearch);
//                    }
//                    option.pageNum(currentIndex);
//                    option.radius(5000);
//                    option.pageCapacity(20);
//                    mPoiSearch.searchNearby(option);
//                    isChange = true;
//                    break;
            }
        });
    }

    @Override
    public void showProgress() {

    }

    @Override
    public void dismissProgress() {

    }

    @Override
    public void loadDataSuccess(List<PoiInfo> data) {
        Log.i(TAG, "loadDataSuccess: ");
        List<LocationAdapter.PoiBean> list = new ArrayList<>(10);

//        for( PoiInfo info:data){
//            list.add(new LocationAdapter.PoiBean(info));
//        }

        adapter.setNewData(list);
        //adapter.notifyDataSetChanged();
    }

    @Override
    public void errorMessage(String throwable) {

    }

    @Override
    public void setMapLocation(BDLocation location) {
        Log.i(TAG, "setMapLocation: ");
        // 构造定位数据
        BitmapDescriptor icon = BitmapDescriptorFactory .fromResource(R.drawable.icon_locate);
        LatLng latLng = new LatLng(location.getLatitude(),location.getLongitude());

        //创建marker
        MarkerOptions ooA = new MarkerOptions().position(latLng).icon(icon);

        map.addOverlay(ooA);

        MyLocationData locData = new MyLocationData.Builder()
                .accuracy(1000)
                .direction(100)
                .latitude(location.getLatitude())
                .longitude(location.getLongitude())
                .build();

        // 设置定位数据
        map.setMyLocationData(locData);

//        MyLocationConfiguration config = new MyLocationConfiguration(MyLocationConfiguration.LocationMode.NORMAL, true, icon);
//        map.setMyLocationConfiguration(config);
//        MapStatus.Builder builder = new MapStatus.Builder().target(latLng).zoom(18.0f);
//
//        map.animateMapStatus(MapStatusUpdateFactory.newMapStatus(builder.build()));

    }

    @Override
    protected void onResume() {
        super.onResume();
        mapView.onResume();
    }
    @Override
    protected void onPause() {
        super.onPause();
        mapView.onPause();
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        mapView.onDestroy();
    }

}
