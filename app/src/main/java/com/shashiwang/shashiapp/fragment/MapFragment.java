package com.shashiwang.shashiapp.fragment;

import android.annotation.TargetApi;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.util.Log;

import com.baidu.location.BDLocation;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BitmapDescriptor;
import com.baidu.mapapi.map.BitmapDescriptorFactory;
import com.baidu.mapapi.map.CircleOptions;
import com.baidu.mapapi.map.MapPoi;
import com.baidu.mapapi.map.MapStatus;
import com.baidu.mapapi.map.MapStatusUpdate;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.MarkerOptions;
import com.baidu.mapapi.map.MyLocationData;
import com.baidu.mapapi.map.Overlay;
import com.baidu.mapapi.map.PolygonOptions;
import com.baidu.mapapi.map.Stroke;
import com.baidu.mapapi.map.UiSettings;
import com.baidu.mapapi.model.LatLng;
import com.baidu.mapapi.search.sug.SuggestionResult;
import com.example.util.SharedPreferencesHelper;
import com.shashiwang.shashiapp.R;
import com.shashiwang.shashiapp.activity.MainActivity;
import com.shashiwang.shashiapp.base.BaseFragment;
import com.shashiwang.shashiapp.bean.Count;
import com.shashiwang.shashiapp.presenter.MapFragmentPresenter;
import com.shashiwang.shashiapp.util.DataUtil;
import com.shashiwang.shashiapp.util.LocationUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

import static android.content.Context.NOTIFICATION_SERVICE;

public class MapFragment extends BaseFragment<MapFragmentPresenter> implements IMapView{

    @BindView(R.id.mp_location)
    MapView mapView;

    private BaiduMap map;

    @Override
    protected MapFragmentPresenter setPresenter() {
        return new MapFragmentPresenter(this,getContext());
    }

    @Override
    protected int setContentView() {
        return R.layout.fragment_map;
    }

    @Override
    protected void init(Bundle savedInstanceState) {
        initConfig();
        initView();
        initEvent();


    }

    private void initView() {

    }

    private void initConfig() {
        map = mapView.getMap();
        map.setMapType(BaiduMap.MAP_TYPE_NORMAL);
        // 开启定位图层
        map.setMyLocationEnabled(true);
        UiSettings mUiSettings = map.getUiSettings();
        mUiSettings. setOverlookingGesturesEnabled(false);
        mUiSettings.setRotateGesturesEnabled(false);

        Log.i(TAG, "initConfig: getMapStatus  "+map.getMapStatus());
        Log.i(TAG, "initConfig: getMapType  "+map.getMapType());
    }

    LatLng myLoc = null;
    private void initEvent(){
        map.setOnMapClickListener(new BaiduMap.OnMapClickListener() {
            @Override
            public void onMapClick(LatLng latLng) {
                myLoc = latLng;
                Log.i(TAG, "onMapClick");
                Log.i(TAG, "onMapClick latitude  " + latLng.latitude);
                Log.i(TAG, "onMapClick longitude  " + latLng.longitude);


            }

            @Override
            public boolean onMapPoiClick(MapPoi mapPoi) {
                Log.i(TAG, "onMapPoiClick");
                Log.i(TAG, "onMapClick getName" + mapPoi.getName());
                Log.i(TAG, "onMapClick getPosition" + mapPoi.getPosition());
                return false;
            }
        });
    }

    private void targetPoint(LatLng latLng,int ic){
        BitmapDescriptor icon = BitmapDescriptorFactory.fromResource(ic);
        MarkerOptions ooA = new MarkerOptions().position(latLng).icon(icon);
        map.addOverlay(ooA);
    }

    private void targetPoint(LatLng latLng){
        BitmapDescriptor icon = BitmapDescriptorFactory.fromResource(R.drawable.icon_locate_tiny);
        MarkerOptions ooA = new MarkerOptions().position(latLng).icon(icon);
        map.addOverlay(ooA);
    }

    private void moveTo(LatLng latLng){
        MapStatus sta = new MapStatus.Builder()
                .target(latLng)
                .zoom(15)
                .build();
        MapStatusUpdate u = MapStatusUpdateFactory.newMapStatus(sta);
        map .animateMapStatus(u);
    }


    @Override
    public void showProgress() {

    }

    @Override
    public void dismissProgress() {

    }



    @Override
    public void errorMessage(String throwable) {

    }

    @Override
    public void getSuggestionCity(List<SuggestionResult.SuggestionInfo> data) {

    }

    private boolean isFirst = true;
    @Override
    public void setMapLocation(BDLocation location) {
        // 构造定位数据
        map.clear();

        LatLng latLng = new LatLng(location.getLatitude(),location.getLongitude());
        DataUtil.nowCount.lat = latLng.latitude;
        DataUtil.nowCount.lng = latLng.longitude;
        targetPoint(latLng,R.drawable.my_locat);

        if(isFirst){
            isFirst = false;
            moveTo(latLng);
        }

        if (myLoc != null){
            CircleOptions mCircleOptions = new CircleOptions().center(myLoc)
                    .radius(DataUtil.nowCount.distance)
                    .fillColor(0x5900AFFF) ; //边框宽和边框颜色

            map.addOverlay(mCircleOptions);

            targetPoint(myLoc,R.drawable.set_ic);
        }
    }

    @Override
    public void loadDataSuccess(List<Count> data) {

        for(Count count:data){
            LatLng latLng = new LatLng(count.lat,count.lng);
            targetPoint(latLng);

            if (myLoc != null){
                double distance = DataUtil.nowCount.distance;
                double far = LocationUtils.getDistance(count.lat,count.lng,
                        myLoc.latitude,myLoc.longitude);
                Log.i(TAG, "loadDataSuccess: far = " + far);
                if((distance - far) < distance/10){
                    Log.i(TAG, "loadDataSuccess: far = " + far);
                    if (Build.VERSION.SDK_INT >= 26){
                        startNotification(count.count);
                    }else {
                        start(count.count);
                    }

                }
            }

        }
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public void startNotification(String count){
        NotificationManager notificationManager= (NotificationManager) getContext().getSystemService(NOTIFICATION_SERVICE);
        NotificationChannel channel = new NotificationChannel("wanhao.com", "wanhao.com", NotificationManager.IMPORTANCE_HIGH);
        notificationManager.createNotificationChannel(channel);
        Notification.Builder builder = new Notification.Builder(getContext(),"wanhao.com");

        Intent intent = new Intent(getContext(), MainActivity.class);
        PendingIntent mainPendingIntent = PendingIntent.getActivity(getContext(), 996, intent, PendingIntent.FLAG_CANCEL_CURRENT);

        builder.setSmallIcon(R.mipmap.ic_launcher)
                .setContentTitle(count + "要走出规定范围了")
                .setAutoCancel(true)
                .setContentIntent(mainPendingIntent);

        notificationManager.notify(Integer.valueOf(count),builder.build());
    }


    public void start(String count){
        NotificationManager mNotifyMgr =
                (NotificationManager) getContext().getSystemService(NOTIFICATION_SERVICE);
        PendingIntent contentIntent = PendingIntent.getActivity(
                getContext(), 996, new Intent(getContext(), MainActivity.class), PendingIntent.FLAG_CANCEL_CURRENT);

        Notification notification = new Notification.Builder(getContext())
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentTitle(count + "要走出规定范围了")
                .setContentIntent(contentIntent)
                .build();// getNotification()

        mNotifyMgr.notify(Integer.valueOf(count), notification);
    }
}
