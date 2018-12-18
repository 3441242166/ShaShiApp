package com.shashiwang.shashiapp.activity.post;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.widget.Button;
import android.widget.Toast;

import com.example.net.interceptors.TokenInterceptor;
import com.example.net.rx.RxRetrofitClient;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.shashiwang.shashiapp.R;
import com.shashiwang.shashiapp.activity.LocationActivity;
import com.shashiwang.shashiapp.base.BaseTopBarActivity;
import com.shashiwang.shashiapp.bean.FreightMessage;
import com.shashiwang.shashiapp.bean.HttpResult;
import com.shashiwang.shashiapp.bean.MessageResult;
import com.shashiwang.shashiapp.constant.Constant;
import com.shashiwang.shashiapp.customizeview.PostChooseLayout;
import com.shashiwang.shashiapp.customizeview.PostEditLayout;
import com.shashiwang.shashiapp.customizeview.PostEditPlusLayout;
import com.shashiwang.shashiapp.customizeview.PostLocationLayout;
import com.shashiwang.shashiapp.dialog.ChooseBottomDialog;
import com.shashiwang.shashiapp.presenter.PostPresenter;

import butterknife.BindView;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class PostFreightActivity extends BaseTopBarActivity{
    private static final String TAG = "PostFreightActivity";

    @BindView(R.id.ed_start_location)
    PostLocationLayout edStart;
    @BindView(R.id.ed_end_location)
    PostLocationLayout edEnd;

    @BindView(R.id.ed_mileage)
    PostEditLayout edMileage;
    @BindView(R.id.ed_name)
    PostEditLayout edName;
    @BindView(R.id.ed_price)
    PostEditLayout edPrice;
    @BindView(R.id.ed_phone)
    PostEditLayout edPhone;

    @BindView(R.id.ed_message)
    PostEditPlusLayout edMessage;

    @BindView(R.id.ch_car)
    PostChooseLayout chCar;
    @BindView(R.id.bt_send)
    Button btSend;

    private String startLat;
    private String startLng;
    private String endLat;
    private String endLng;

    @Override
    protected PostPresenter setPresenter() {
        return null;
    }


    @Override
    protected int getFrameContentView() {
        return R.layout.activity_post_freight;
    }

    @Override
    protected void initFrame(Bundle savedInstanceState) {
        setTitle("发布运费信息");
        initEvent();
    }

    private void initEvent() {
        edStart.setOnClickListener(view -> startActivityForResult(new Intent(PostFreightActivity.this, LocationActivity.class),1));
        edEnd.setOnClickListener(view -> startActivityForResult(new Intent(PostFreightActivity.this, LocationActivity.class),2));

        chCar.setOnClickListener(view -> {
            ChooseBottomDialog dialog = new ChooseBottomDialog(PostFreightActivity.this,"选择车辆类型",R.array.car_type);
            dialog.setOnChooseListener(str -> chCar.setContantText(str));
            dialog.show();
        });
        btSend.setOnClickListener(view -> postData());
    }

    @SuppressLint("CheckResult")
    private void postData() {

        if(checkData()){
            RxRetrofitClient.builder()
                    .header(new TokenInterceptor())
                    .url("api/freight/")
                    .params("start_location_lat",startLat)
                    .params("start_location_lng",startLng)
                    .params("end_location_lat",endLat)
                    .params("end_location_lng",endLng)
                    .params("start_location",edStart.getContantText())
                    .params("end_location",edEnd.getContantText())
                    .params("distance",edMileage.getContantText())
                    .params("cargo_name",edName.getContantText())
                    .params("price",edPrice.getContantText())
                    .params("car_category",chCar.getContantText())
                    .params("remark",edMessage.getContantText())
                    .params("phone",edPhone.getContantText())
                    .build()
                    .post()
                    .subscribeOn(Schedulers.newThread())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(s -> {
                        Log.i(TAG, "getList: success " + s);
                        HttpResult<MessageResult<FreightMessage>> result = new Gson().fromJson(s,new TypeToken<HttpResult<MessageResult<FreightMessage>>>(){}.getType());

                        if(result.isSuccess()){
                            Toast.makeText(PostFreightActivity.this,"发布成功",Toast.LENGTH_SHORT).show();
                            finish();
                        }else {
                            Toast.makeText(PostFreightActivity.this,result.getMessage(),Toast.LENGTH_SHORT).show();
                        }

                    }, throwable -> {
                        Log.i(TAG, "getList: error = " + throwable);
                        Toast.makeText(PostFreightActivity.this,throwable.getMessage(),Toast.LENGTH_SHORT).show();
                    });
        }

    }

    private boolean checkData() {

        return true;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode == Constant.RESULT_SUCCESS && data!=null){
            switch (requestCode){
                case 1:
                    edStart.setContantText(data.getStringExtra(Constant.LOCATION_NAME));
                    startLat = data.getStringExtra(Constant.LAT);
                    startLng = data.getStringExtra(Constant.LNG);

                    break;
                case 2:
                    edEnd.setContantText(data.getStringExtra(Constant.LOCATION_NAME));
                    endLat = data.getStringExtra(Constant.LAT);
                    endLng =  data.getStringExtra(Constant.LNG);
                    break;
            }
        }
        Log.i(TAG, "onActivityResult: Lat = " + startLat + " lng = " + startLng);
    }
}
