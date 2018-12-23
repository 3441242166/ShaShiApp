package com.shashiwang.shashiapp.activity.post;

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

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

import static com.shashiwang.shashiapp.constant.ApiConstant.URL_DRIVER;
import static com.shashiwang.shashiapp.constant.Constant.REQUEST_END_LOCATION;
import static com.shashiwang.shashiapp.constant.Constant.REQUEST_START_LOCATION;

public class PostDriverActivity extends BaseTopBarActivity{
    private static final String TAG = "PostDriverActivity";

    @BindView(R.id.ed_salary)
    PostEditLayout edSalary;
    @BindView(R.id.ed_location)
    PostEditLayout edLocation;
    @BindView(R.id.ed_people)
    PostEditLayout edPeople;
    @BindView(R.id.ed_phone)
    PostEditLayout edPhone;

    @BindView(R.id.ed_work_message)
    PostEditPlusLayout edMessage;

    @BindView(R.id.ch_year)
    PostChooseLayout chYear;

    @BindView(R.id.bt_send)
    Button btSend;



    @Override
    protected PostPresenter setPresenter() {
        return null;
    }

    @Override
    protected int getFrameContentView() {
        return R.layout.activity_post_driver;
    }

    @Override
    protected void initFrame(Bundle savedInstanceState) {
        setTitle("司机招聘");
        initEvent();
    }

    private void initEvent() {
        chYear.setOnClickListener(view -> {
            ChooseBottomDialog dialog = new ChooseBottomDialog(PostDriverActivity.this,"选择车辆类型",R.array.work_year);
            dialog.setOnChooseListener((str,i) -> {
                chYear.setContantText(str);

            });
            dialog.show();
        });

        btSend.setOnClickListener(view -> postData());
    }

    @SuppressLint("CheckResult")
    private void postData() {

        if(checkData()){
            RxRetrofitClient.builder()
                    .header(new TokenInterceptor())
                    .url(URL_DRIVER)
                    .params("salary",edSalary.getContantText())
                    .params("job_desc",edMessage.getContantText())
                    .params("work_year",chYear.getContantText())
                    .params("work_address",edLocation.getContantText())
                    .params("linkman",edPeople.getContantText())
                    .params("phone",edPhone.getContantText())
                    .build()
                    .post()
                    .subscribeOn(Schedulers.newThread())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(s -> {
                        Log.i(TAG, "getList: success " + s);
                        HttpResult<MessageResult<FreightMessage>> result = new Gson().fromJson(s,new TypeToken<HttpResult<MessageResult<FreightMessage>>>(){}.getType());

                        if(result.isSuccess()){
                            Toast.makeText(PostDriverActivity.this,"发布成功",Toast.LENGTH_SHORT).show();
                            finish();
                        }else {
                            Toast.makeText(PostDriverActivity.this,result.getMessage(),Toast.LENGTH_SHORT).show();
                        }

                    }, throwable -> {
                        Log.i(TAG, "getList: error = " + throwable);
                        Toast.makeText(PostDriverActivity.this,throwable.getMessage(),Toast.LENGTH_SHORT).show();
                    });
        }

    }

    private boolean checkData() {

        return true;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        // 获取地图返回坐标和地名
        if(resultCode == Constant.RESULT_SUCCESS && data!=null){
                edLocation.setContantText(data.getStringExtra(Constant.LOCATION_NAME));
        }
    }

}
