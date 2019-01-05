package com.shashiwang.shashiapp.activity.message;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.example.net.rx.RxRetrofitClient;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.shashiwang.shashiapp.R;
import com.shashiwang.shashiapp.adapter.PhotoAdapter;
import com.shashiwang.shashiapp.base.BasePresenter;
import com.shashiwang.shashiapp.base.BaseTopBarActivity;
import com.shashiwang.shashiapp.bean.CarMessage;
import com.shashiwang.shashiapp.bean.HttpResult;
import com.shashiwang.shashiapp.customizeview.MessageLayout;
import com.shashiwang.shashiapp.util.DateUtil;
import com.shashiwang.shashiapp.util.StringUtil;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import butterknife.BindView;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

import static com.shashiwang.shashiapp.constant.ApiConstant.URL_CAR;
import static com.shashiwang.shashiapp.constant.Constant.ID;
import static com.shashiwang.shashiapp.util.TypeUtil.getCarString;
import static com.shashiwang.shashiapp.util.TypeUtil.getYearString;

public class CarMessageActivity extends BaseTopBarActivity {
    private static final String TAG = "CarMessageActivity";

    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.tv_time)
    TextView tvTime;
    @BindView(R.id.tv_remark)
    TextView tvRemark;

    @BindView(R.id.tv_brand)
    MessageLayout tvBrand;
    @BindView(R.id.tv_mileage)
    MessageLayout tvMileage;
    @BindView(R.id.tv_price)
    MessageLayout tvPrice;
    @BindView(R.id.tv_phone)
    MessageLayout tvPhone;
    @BindView(R.id.tv_type)
    MessageLayout tvType;
    @BindView(R.id.tv_years)
    MessageLayout tvYears;
    @BindView(R.id.rv_car)
    RecyclerView rvCars;

    @BindView(R.id.bt_phone)
    Button btPhone;

    private PhotoAdapter adapter;
    private ArrayList<PhotoAdapter.PhotoBean> photoList = new ArrayList<>();

    private int id = -1;
    private CarMessage message;

    @Override
    protected BasePresenter setPresenter() {
        return null;
    }

    @Override
    protected int getFrameContentView() {
        return R.layout.activity_car_message;
    }

    @Override
    protected void initFrame(Bundle savedInstanceState) {
        id = getIntent().getIntExtra(ID,-1);

        initView();
        initEvent();
        getMessage();
    }

    private void initView() {
        setTitle("详情");
        adapter = new PhotoAdapter(null,false);
        rvCars.setLayoutManager(new GridLayoutManager(this,3));
        rvCars.setAdapter(adapter);
    }

    private void initEvent() {
        btPhone.setOnClickListener(view -> {
            Intent intent = new Intent(Intent.ACTION_DIAL);
            Uri data = Uri.parse("tel:" + message.getPhone());
            intent.setData(data);
            startActivity(intent);
        });

        adapter.setOnItemClickListener((adapter, view, position) -> {

        });
    }

    @SuppressLint("CheckResult")
    private void getMessage(){

        RxRetrofitClient.builder()
                .url(URL_CAR + id)
                .build()
                .get()
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(s -> {
                    Log.i(TAG, "getList: success " + s);
                    HttpResult<CarMessage> result = new Gson().fromJson(s,new TypeToken<HttpResult<CarMessage>>(){}.getType());

                    if(result.isSuccess()){
                        message = result.getData();
                        loadDataSuccess(message);
                    }else {
                        Log.i(TAG, "getMessage: fail");
                    }

                }, throwable -> {
                    Log.i(TAG, "getMessage: error " + throwable);
                });
    }

    private void loadDataSuccess(CarMessage message) {
        Log.i(TAG, "loadDataSuccess: getRemark = " + message.getRemark());
        Log.i(TAG, "loadDataSuccess: getBrand = " + message.getBrand());

        tvTitle.setText(StringUtil.getFirstChinese(message.getLinkman())+"先生");
        tvTime.setText(DateUtil.getDifferentString(message.getCreated_at()));

        tvBrand.setContantText(message.getBrand());
        tvMileage.setContantText(""+message.getMileage()+"公里");
        tvPrice.setContantText(""+message.getPrice());
        tvPhone.setContantText(message.getPhone());
        tvType.setContantText(getCarString(message.getCategory()));
        tvYears.setContantText(""+message.getFactory_year());

        tvRemark.setText(message.getRemark());

        for(String str:message.getImage()){
            photoList.add(new PhotoAdapter.PhotoBean(str));
        }

        adapter.setNewData(photoList);
    }

}
