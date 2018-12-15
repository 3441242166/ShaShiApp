package com.shashiwang.shashiapp.activity.post;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.net.interceptors.TokenInterceptor;
import com.example.net.rx.RxRetrofitClient;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.shashiwang.shashiapp.R;
import com.shashiwang.shashiapp.base.BasePresenter;
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
import com.shashiwang.shashiapp.view.PostDataView;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class PostCarMessageActivity extends BaseTopBarActivity {
    private static final String TAG = "PostCarMessageActivity";

    @BindView(R.id.ed_brand)
    PostEditLayout edBrand;
    @BindView(R.id.ed_mileage)
    PostEditLayout edMileage;
    @BindView(R.id.ed_price)
    PostEditLayout edPrice;
    @BindView(R.id.ed_people)
    PostEditLayout edPeople;
    @BindView(R.id.ed_phone)
    PostEditLayout edPhone;
    @BindView(R.id.ed_message)
    PostEditPlusLayout edMessage;
    @BindView(R.id.ch_type)
    PostChooseLayout chType;
    @BindView(R.id.ch_create_year)
    PostChooseLayout chCreateYear;
    @BindView(R.id.bt_send)
    Button btSend;

    @Override
    protected PostPresenter setPresenter() {
        return null;
    }

    @Override
    protected int getFrameContentView() {
        return R.layout.activity_post_car_message;
    }

    @Override
    protected void initFrame(Bundle savedInstanceState) {
        setTitle("石料厂");
        initEvent();
    }

    private void initEvent() {
        chType.setOnClickListener(view -> {
            ChooseBottomDialog dialog = new ChooseBottomDialog(PostCarMessageActivity.this,"选择车辆",R.array.car_type);
            dialog.setOnChooseListener(str -> chType.setContantText(str));
            dialog.show();
        });

        chCreateYear.setOnClickListener(view -> {
            ChooseBottomDialog dialog = new ChooseBottomDialog(PostCarMessageActivity.this,"选择年份",R.array.create_year);
            dialog.setOnChooseListener(str -> chCreateYear.setContantText(str));
            dialog.show();
        });

        btSend.setOnClickListener(view -> postData());
    }

    @SuppressLint("CheckResult")
    private void postData() {

        if(checkData()){
            RxRetrofitClient.builder()
                    .header(new TokenInterceptor())
                    .url("api/car/trade/")
                    .params("brand",edBrand.getContantText())
                    .params("category","")
                    .params("factory_year","")
                    .params("mileage",edMileage.getContantText())
                    .params("price",edPrice.getContantText())
                    .params("linkman","")
                    .params("phone",edPhone.getContantText())
                    .params("remark",edMessage.getContantText())
                    .build()
                    .post()
                    .subscribeOn(Schedulers.newThread())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(s -> {
                        Log.i(TAG, "getList: success " + s);
                        HttpResult<MessageResult<FreightMessage>> result = new Gson().fromJson(s,new TypeToken<HttpResult<MessageResult<FreightMessage>>>(){}.getType());

                        if(result.isSuccess()){
                            Toast.makeText(PostCarMessageActivity.this,"发布成功",Toast.LENGTH_SHORT).show();
                            finish();
                        }else {
                            Toast.makeText(PostCarMessageActivity.this,result.getMessage(),Toast.LENGTH_SHORT).show();
                        }

                    }, throwable -> {
                        Log.i(TAG, "getList: error = " + throwable);
                        Toast.makeText(PostCarMessageActivity.this,throwable.getMessage(),Toast.LENGTH_SHORT).show();
                    });
        }

    }

    private boolean checkData() {

        return true;
    }


}
