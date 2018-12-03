package com.shashiwang.shashiapp.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;

import com.baidu.location.BDLocation;
import com.baidu.mapapi.cloud.CloudRgcResult;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BitmapDescriptor;
import com.baidu.mapapi.map.BitmapDescriptorFactory;
import com.baidu.mapapi.map.MapPoi;
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
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.shashiwang.shashiapp.R;
import com.shashiwang.shashiapp.adapter.LocationAdapter;
import com.shashiwang.shashiapp.base.IBasePresenter;
import com.shashiwang.shashiapp.base.TopBarBaseActivity;
import com.shashiwang.shashiapp.presenter.LocationPresenter;
import com.shashiwang.shashiapp.view.ILocationView;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import javax.microedition.khronos.opengles.GL10;
import javax.security.auth.login.LoginException;

import butterknife.BindView;

import static com.shashiwang.shashiapp.constant.Constant.RESULT_DATA;
import static com.shashiwang.shashiapp.constant.Constant.RESULT_SUCCESS;

public class LocationActivity extends TopBarBaseActivity<LocationPresenter> implements ILocationView{
    private static final String TAG = "LocationActivity";

    @BindView(R.id.mp_location)
    MapView mapView;
    @BindView(R.id.tv_location_edit)
    EditText tvSearch;
    @BindView(R.id.rv_location)
    RecyclerView recyclerView;

    private BaiduMap map;

    private LocationAdapter adapter;
    private List<LocationAdapter.PoiBean> data;

    private int lastSelect = -1;

    @Override
    protected LocationPresenter setPresenter() {
        return new LocationPresenter(this,this);
    }

    @Override
    protected int getFrameContentView() {
        return R.layout.activity_loction;
    }

    @Override
    protected void initFrame(Bundle savedInstanceState) {
        initConfig();
        initView();
        initEvent();
    }

    private void initView() {
        setTitle("选择地点");
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new LocationAdapter(null,this);
        recyclerView.setAdapter(adapter);
    }

    private void initConfig() {
        map = mapView.getMap();
        map.setMapType(BaiduMap.MAP_TYPE_NORMAL);
        map.setMyLocationEnabled(true);

        mapView.showZoomControls(false);// 不显示默认的缩放控件
        mapView.showScaleControl(false);// 不显示默认比例尺控件

        Log.i(TAG, "initConfig: getMapStatus  "+map.getMapStatus());
        Log.i(TAG, "initConfig: getMapType  "+map.getMapType());
    }

    private void initEvent(){
        setTopRightButton(R.drawable.icon_certain, () -> {
            if(lastSelect != -1){
                Intent intent = new Intent();
                intent.putExtra(RESULT_DATA, data.get(lastSelect).info.address);
                setResult(RESULT_SUCCESS, intent);
                finish();
            }else {
                Toast.makeText(LocationActivity.this,"请选择一个地点",Toast.LENGTH_SHORT).show();
            }
        });

        map.setOnMapClickListener(new BaiduMap.OnMapClickListener() {
            @Override
            public void onMapClick(LatLng latLng) {
                Log.i(TAG, "onMapClick");
                Log.i(TAG, "onMapClick latitude" + latLng.latitude);
                Log.i(TAG, "onMapClick longitude" + latLng.longitude);
                presenter.setLatLng(latLng);
            }

            @Override
            public boolean onMapPoiClick(MapPoi mapPoi) {
                Log.i(TAG, "onMapPoiClick");
                Log.i(TAG, "onMapClick getName" + mapPoi.getName());
                Log.i(TAG, "onMapClick getPosition" + mapPoi.getPosition());
                return false;
            }
        });

        tvSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                Log.i(TAG, "beforeTextChanged: "+charSequence);
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                Log.i(TAG, "onTextChanged: "+charSequence);
            }

            @Override
            public void afterTextChanged(Editable editable) {
                Log.i(TAG, "afterTextChanged: "+editable);
                String str = editable.toString();

                if(TextUtils.isEmpty(str)){
                    return;
                }

                PoiNearbySearchOption nearbySearchOption = new PoiNearbySearchOption()
                        .keyword(str)//检索关键字
                        .pageNum(0)//分页编号，默认是0页
                        .pageCapacity(20)//设置每页容量，默认10条
                        .radius(50000);//附近检索半径

                presenter.searchNear(nearbySearchOption);
            }
        });

        adapter.setOnItemClickListener((adapter, view, position) -> {
            final LatLng latLng = data.get(position).info.location;
            targetPoint(latLng);
            moveTo(latLng);

            if(lastSelect != -1){
                data.get(lastSelect).isSelect = false;
            }
            data.get(position).isSelect = true;
            lastSelect = position;
            adapter.notifyDataSetChanged();
        });

    }

    private void targetPoint( LatLng latLng){
        map.clear();
        BitmapDescriptor icon = BitmapDescriptorFactory .fromResource(R.drawable.icon_locate_tiny);
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
    public void loadDataSuccess(List<PoiInfo> data) {
        Log.i(TAG, "loadDataSuccess: ");
        List<LocationAdapter.PoiBean> list = new ArrayList<>(10);

        for( PoiInfo info:data){
            Log.i(TAG, "loadDataSuccess: info "+ info.name);
            list.add(new LocationAdapter.PoiBean(info));
        }
        this.data = list;
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
        LatLng latLng = new LatLng(location.getLatitude(),location.getLongitude());
        moveTo(latLng);

        MyLocationData locData = new MyLocationData.Builder()
                .accuracy(1000)
                .direction(100)
                .latitude(location.getLatitude())
                .longitude(location.getLongitude())
                .build();
        map.setMyLocationData(locData);

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
