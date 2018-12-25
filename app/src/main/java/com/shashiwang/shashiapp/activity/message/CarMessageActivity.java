package com.shashiwang.shashiapp.activity.message;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;

import com.example.net.rx.RxRetrofitClient;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.shashiwang.shashiapp.R;
import com.shashiwang.shashiapp.base.BasePresenter;
import com.shashiwang.shashiapp.base.BaseTopBarActivity;
import com.shashiwang.shashiapp.bean.CarMessage;
import com.shashiwang.shashiapp.bean.HttpResult;
import com.shashiwang.shashiapp.customizeview.MessageLayout;
import com.shashiwang.shashiapp.util.DateUtil;
import com.shashiwang.shashiapp.util.StringUtil;

import butterknife.BindView;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

import static com.shashiwang.shashiapp.constant.ApiConstant.URL_CAR;
import static com.shashiwang.shashiapp.constant.Constant.ID;

public class CarMessageActivity extends BaseTopBarActivity {
    private static final String TAG = "CarMessageActivity";

    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.tv_time)
    TextView tvTime;
    @BindView(R.id.tv_content)
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

    @BindView(R.id.bt_phone)
    Button btPhone;


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
        setTitle("详情");
        id = getIntent().getIntExtra(ID,-1);
        initEvent();
        getMessage();
    }

    private void initEvent() {
        btPhone.setOnClickListener(view -> {
            Intent intent = new Intent(Intent.ACTION_DIAL);
            Uri data = Uri.parse("tel:" + message.getPhone());
            intent.setData(data);
            startActivity(intent);
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

        tvTitle.setText(StringUtil.getFirstChinese(message.getLinkman())+"先生");
        tvTime.setText(DateUtil.getDifferentString(message.getCreated_at()));

        tvBrand.setContantText(message.getBrand());
        tvMileage.setContantText(""+message.getMileage()+"公里");
        tvPrice.setContantText(""+message.getPrice());
        tvPhone.setContantText(message.getPhone());
        tvType.setContantText("null");
        tvYears.setContantText(message.getFactory_year());

        tvRemark.setText(message.getRemark());
    }

}
