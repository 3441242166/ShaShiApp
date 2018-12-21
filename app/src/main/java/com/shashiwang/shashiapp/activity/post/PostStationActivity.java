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
import com.shashiwang.shashiapp.customizeview.PostEditLayout;
import com.shashiwang.shashiapp.customizeview.PostEditPlusLayout;
import com.shashiwang.shashiapp.customizeview.PostLocationLayout;
import com.shashiwang.shashiapp.presenter.PostPresenter;

import butterknife.BindView;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class PostStationActivity extends BaseTopBarActivity{
    private static final String TAG = "PostStationActivity";

    @BindView(R.id.ed_title)
    PostEditLayout title;
    @BindView(R.id.ed_price)
    PostEditPlusLayout price;
    @BindView(R.id.ed_location)
    PostLocationLayout location;
    @BindView(R.id.ed_people)
    PostEditLayout people;
    @BindView(R.id.ed_phone)
    PostEditLayout phone;
    @BindView(R.id.ed_message)
    PostEditPlusLayout message;
    @BindView(R.id.bt_send)
    Button btSend;

    private String startLat;
    private String startLng;

    @Override
    protected PostPresenter setPresenter() {
        return null;
    }

    @Override
    protected int getFrameContentView() {
        return R.layout.activity_post_max_factory;
    }

    @Override
    protected void initFrame(Bundle savedInstanceState) {
        setTitle("搅拌厂");
        initEvent();
    }

    private void initEvent() {
        location.setOnClickListener(view -> {
            startActivityForResult(new Intent(PostStationActivity.this, LocationActivity.class),1);
        });

        btSend.setOnClickListener(view -> postData());
    }

    @SuppressLint("CheckResult")
    private void postData() {

        if(checkData()){
            RxRetrofitClient.builder()
                    .header(new TokenInterceptor())
                    .url("api/recruit/driver")
                    .params("name","")
                    .params("category_price","")
                    .params("linkman","")
                    .params("phone","")
                    .params("location_lat","")
                    .params("location_lng","")
                    .params("remark","")
                    .build()
                    .post()
                    .subscribeOn(Schedulers.newThread())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(s -> {
                        Log.i(TAG, "getList: success " + s);
                        HttpResult<MessageResult<FreightMessage>> result = new Gson().fromJson(s,new TypeToken<HttpResult<MessageResult<FreightMessage>>>(){}.getType());

                        if(result.isSuccess()){
                            Toast.makeText(PostStationActivity.this,"发布成功",Toast.LENGTH_SHORT).show();
                            finish();
                        }else {
                            Toast.makeText(PostStationActivity.this,result.getMessage(),Toast.LENGTH_SHORT).show();
                        }

                    }, throwable -> {
                        Log.i(TAG, "getList: error = " + throwable);
                        Toast.makeText(PostStationActivity.this,throwable.getMessage(),Toast.LENGTH_SHORT).show();
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
            location.setContantText(data.getStringExtra(Constant.LOCATION_NAME));
            startLat = data.getStringExtra(Constant.LAT);
            startLng = data.getStringExtra(Constant.LNG);
        }
    }

}
