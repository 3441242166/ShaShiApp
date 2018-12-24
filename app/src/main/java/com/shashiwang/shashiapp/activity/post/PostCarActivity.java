package com.shashiwang.shashiapp.activity.post;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.net.interceptors.TokenInterceptor;
import com.example.net.rx.RxRetrofitClient;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.shashiwang.shashiapp.R;
import com.shashiwang.shashiapp.activity.MainActivity;
import com.shashiwang.shashiapp.base.BaseTopBarActivity;
import com.shashiwang.shashiapp.bean.FreightMessage;
import com.shashiwang.shashiapp.bean.HttpResult;
import com.shashiwang.shashiapp.bean.MessageResult;
import com.shashiwang.shashiapp.customizeview.PostChooseLayout;
import com.shashiwang.shashiapp.customizeview.PostEditLayout;
import com.shashiwang.shashiapp.customizeview.PostEditPlusLayout;
import com.shashiwang.shashiapp.dialog.ChooseBottomDialog;
import com.shashiwang.shashiapp.presenter.PostPresenter;
import com.shashiwang.shashiapp.util.FileUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

import static com.shashiwang.shashiapp.constant.ApiConstant.URL_CAR;

public class PostCarActivity extends BaseTopBarActivity {
    private static final String TAG = "PostCarActivity";

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
    @BindView(R.id.ch_type)
    PostChooseLayout chType;
    @BindView(R.id.ed_create_year)
    PostEditLayout edCreateYear;
    @BindView(R.id.ed_message)
    PostEditPlusLayout edMessage;
    @BindView(R.id.bt_send)
    Button btSend;

    private Map<String,Integer> data;

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
        setTitle("出售车辆");
        data = FileUtil.getJsonFormAssets(this,"carType.json");
        initEvent();
    }

    private void initEvent() {
        List<String> list = new ArrayList<>();
        for( String key :data.keySet()){
            list.add(key);
        }

        chType.setOnClickListener(view -> {
            ChooseBottomDialog dialog = new ChooseBottomDialog(PostCarActivity.this,"选择车辆",list);
            dialog.setOnChooseListener((str,i) -> chType.setContantText(str));
            dialog.show();
        });

        btSend.setOnClickListener(view -> postData());
    }

    @SuppressLint("CheckResult")
    private void postData() {

        int carType = data.get(chType.getContantText());

        if(checkData()){
            RxRetrofitClient.builder()
                    .header(new TokenInterceptor())
                    .url(URL_CAR)
                    .params("brand",edBrand.getContantText())
                    .params("category",carType)
                    .params("factory_year",edCreateYear.getContantText())
                    .params("mileage",edMileage.getContantText())
                    .params("price",edPrice.getContantText())
                    .params("linkman",edPeople.getContantText())
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
                            Toast.makeText(PostCarActivity.this,"发布成功",Toast.LENGTH_SHORT).show();
                            finish();
                        }else {
                            Toast.makeText(PostCarActivity.this,result.getMessage(),Toast.LENGTH_SHORT).show();
                        }

                    }, throwable -> {
                        Log.i(TAG, "getList: error = " + throwable);
                        Toast.makeText(PostCarActivity.this,throwable.getMessage(),Toast.LENGTH_SHORT).show();
                    });
        }

    }

    private boolean checkData() {

        return true;
    }


}
