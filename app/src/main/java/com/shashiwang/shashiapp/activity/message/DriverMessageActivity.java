package com.shashiwang.shashiapp.activity.message;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
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
import com.shashiwang.shashiapp.bean.DriverMessage;
import com.shashiwang.shashiapp.bean.FreightMessage;
import com.shashiwang.shashiapp.bean.HttpResult;
import com.shashiwang.shashiapp.customizeview.ImageButton;
import com.shashiwang.shashiapp.customizeview.MessageLayout;
import com.shashiwang.shashiapp.util.DateUtil;
import com.shashiwang.shashiapp.util.StringUtil;

import butterknife.BindView;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

import static com.shashiwang.shashiapp.constant.ApiConstant.URL_DRIVER;
import static com.shashiwang.shashiapp.constant.Constant.ID;
import static com.shashiwang.shashiapp.util.TypeUtil.getYearString;

public class DriverMessageActivity extends BaseTopBarActivity {
    private static final String TAG = "DriverMessageActivity";

    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.tv_time)
    TextView tvTime;
    @BindView(R.id.tv_remark)
    TextView tvContent;

    @BindView(R.id.tv_salary)
    MessageLayout tvSalary;
    @BindView(R.id.tv_years)
    MessageLayout tvYears;
    @BindView(R.id.tv_location)
    MessageLayout tvLocation;
    @BindView(R.id.tv_phone)
    MessageLayout tvPhone;

    @BindView(R.id.bt_phone)
    Button btPhone;

    private int id = -1;
    private DriverMessage message;

    @Override
    protected BasePresenter setPresenter() {
        return null;
    }

    @Override
    protected int getFrameContentView() {
        return R.layout.activity_driver_message;
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
                .url(URL_DRIVER + id)
                .build()
                .get()
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(s -> {
                    Log.i(TAG, "getList: success " + s);
                    HttpResult<DriverMessage> result = new Gson().fromJson(s,new TypeToken<HttpResult<DriverMessage>>(){}.getType());

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

    private void loadDataSuccess(DriverMessage message) {
        tvTitle.setText(StringUtil.getFirstChinese(message.getLinkman())+"先生");
        tvTime.setText(DateUtil.getDifferentString(message.getUpdated_at()));
        tvContent.setText(message.getJob_desc());

        tvSalary.setContantText(""+message.getSalary());
        tvYears.setContantText(getYearString(message.getWork_year()));
        tvLocation.setContantText(message.getWork_address());
        tvPhone.setContantText(message.getPhone());
    }
}
