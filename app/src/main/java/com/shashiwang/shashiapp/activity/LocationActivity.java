package com.shashiwang.shashiapp.activity;

import android.content.Intent;

import com.baidu.location.BDLocation;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BitmapDescriptor;
import com.baidu.mapapi.map.BitmapDescriptorFactory;
import com.baidu.mapapi.map.MapPoi;
import com.baidu.mapapi.map.MapStatus;
import com.baidu.mapapi.map.MapStatusUpdate;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.MarkerOptions;
import com.baidu.mapapi.map.MyLocationData;
import com.baidu.mapapi.map.UiSettings;
import com.baidu.mapapi.model.LatLng;
import com.baidu.mapapi.search.core.PoiInfo;
import com.baidu.mapapi.search.poi.PoiCitySearchOption;
import com.baidu.mapapi.search.sug.SuggestionResult;
import com.baidu.mapapi.search.sug.SuggestionSearchOption;
import com.shashiwang.shashiapp.R;
import com.shashiwang.shashiapp.adapter.LocationAdapter;
import com.shashiwang.shashiapp.base.BaseTopBarActivity;
import com.shashiwang.shashiapp.bean.Count;
import com.shashiwang.shashiapp.presenter.LocationPresenter;
import com.shashiwang.shashiapp.util.DataUtil;
import com.shashiwang.shashiapp.util.DividerItemDecoration;
import com.shashiwang.shashiapp.view.ILocationView;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import javax.microedition.khronos.opengles.GL10;

import butterknife.BindView;

import static com.shashiwang.shashiapp.constant.Constant.*;

public class LocationActivity extends BaseTopBarActivity<LocationPresenter> implements ILocationView{
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
        recyclerView.addItemDecoration(new DividerItemDecoration());
        adapter = new LocationAdapter(null,this);
        recyclerView.setAdapter(adapter);
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

    private void initEvent(){
        setTopRightButton(R.drawable.icon_certain, () -> {
            if(lastSelect != -1){
                Intent intent = new Intent();
                LatLng latLng = data.get(lastSelect).info.location;
                Log.i(TAG, "initEvent: " +latLng.latitude + "   " + latLng.longitude);

                intent.putExtra(LAT, String.valueOf(latLng.latitude));
                intent.putExtra(LNG, String.valueOf(latLng.longitude));
                intent.putExtra(LOCATION_NAME, data.get(lastSelect).info.name);

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
                Log.i(TAG, "onMapClick latitude  " + latLng.latitude);
                Log.i(TAG, "onMapClick longitude  " + latLng.longitude);
                presenter.setLatLng(latLng);
                targetPoint(latLng);
            }

            @Override
            public boolean onMapPoiClick(MapPoi mapPoi) {
                Log.i(TAG, "onMapPoiClick");
                Log.i(TAG, "onMapClick getName" + mapPoi.getName());
                Log.i(TAG, "onMapClick getPosition" + mapPoi.getPosition());
                return false;
            }
        });

        map.setOnMapDrawFrameCallback(new BaiduMap.OnMapDrawFrameCallback() {

            @Override
            public void onMapDrawFrame(GL10 gl10, MapStatus mapStatus) {
                Log.i(TAG, "draw lag = " +mapStatus.target.latitude + " lug = "+mapStatus.target.longitude);
                //currentPt = mapStatus.target;
            }

            @Override
            public void onMapDrawFrame(MapStatus mapStatus) {
                Log.i("aab", "draw");
            }
        });

        tvSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                Log.i(TAG, "beforeTextChanged: ");
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                Log.i(TAG, "onTextChanged: ");
            }

            @Override
            public void afterTextChanged(Editable editable) {
                String str = editable.toString().trim();
                Log.i(TAG, "afterTextChanged: " + str);
                if(TextUtils.isEmpty(str)){
                    return;
                }

                SuggestionSearchOption suggestionSearch = new SuggestionSearchOption()
                        .city("北京")
                        .keyword(str);

                presenter.suggestionSearch(suggestionSearch);

//                PoiNearbySearchOption nearbySearchOption = new PoiNearbySearchOption()
//                        .keyword(str)//检索关键字
//                        .pageNum(0)//分页编号，默认是0页
//                        .pageCapacity(20)//设置每页容量，默认10条
//                        .radius(2147482888);//附近检索半径
//
//                presenter.searchNear(nearbySearchOption);
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
    }

    @Override
    public void errorMessage(String throwable) {

    }

    @Override
    public void setMapLocation(BDLocation location) {
        Log.i(TAG, "setMapLocation: ");
        // 构造定位数据
        LatLng latLng = new LatLng(location.getLatitude(),location.getLongitude());
        DataUtil.setNewLocation(location);
        moveTo(latLng);

        MyLocationData locData = new MyLocationData.Builder()
                .accuracy(1000)
                .direction(100)
                .latitude(location.getLatitude())
                .longitude(location.getLongitude())
                .build();
        map.setMyLocationData(locData);

        for(Count count:DataUtil.getTargetData()){
            targetPoint(count.getLocation());
        }
    }

    @Override
    public void getSuggestionCity(List<SuggestionResult.SuggestionInfo> data) {
        if(data.size()>0) {
            PoiCitySearchOption citySearchOption = new PoiCitySearchOption()
                    .city(data.get(0).getCity())
                    .keyword(tvSearch.getText().toString().trim())
                    .pageNum(0)
                    .pageCapacity(20);

            presenter.searchCity(citySearchOption);
        }
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
