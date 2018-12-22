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
import com.shashiwang.shashiapp.bean.FactoryMessage;
import com.shashiwang.shashiapp.bean.HttpResult;
import com.shashiwang.shashiapp.customizeview.MessageLayout;
import com.shashiwang.shashiapp.util.DateUtil;
import com.shashiwang.shashiapp.util.StringUtil;

import butterknife.BindView;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

import static com.shashiwang.shashiapp.constant.ApiConstant.URL_FACTORY;
import static com.shashiwang.shashiapp.constant.ApiConstant.URL_STATION;
import static com.shashiwang.shashiapp.constant.Constant.ID;

public class StationMessageActivity extends BaseTopBarActivity {
    private static final String TAG = "StationMessageActivity";

    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.tv_time)
    TextView tvTime;
    @BindView(R.id.tv_content)
    TextView tvContent;

    @BindView(R.id.tv_type)
    MessageLayout tvType;
    @BindView(R.id.tv_price)
    MessageLayout tvPrice;
    @BindView(R.id.tv_phone)
    MessageLayout tvPhone;
    @BindView(R.id.tv_address)
    MessageLayout tvAddress;
    @BindView(R.id.bt_phone)
    Button btPhone;

    private int id = -1;
    private FactoryMessage message;

    @Override
    protected BasePresenter setPresenter() {
        return null;
    }

    @Override
    protected int getFrameContentView() {
        return R.layout.activity_station_message;
    }

    @Override
    protected void initFrame(Bundle savedInstanceState) {
        setTitle("详情");
        id = getIntent().getIntExtra(ID,-1);
        initEvent();
        getMessage();
    }

    public void initEvent(){
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
                .url(URL_STATION + id)
                .build()
                .get()
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(s -> {
                    Log.i(TAG, "getList: success " + s);
                    HttpResult<FactoryMessage> result = new Gson().fromJson(s,new TypeToken<HttpResult<FactoryMessage>>(){}.getType());

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

    private void loadDataSuccess(FactoryMessage message){
        tvTitle.setText(StringUtil.getFirstChinese(message.getLinkman())+"先生");
        tvTime.setText(DateUtil.getDifferentString(message.getUpdated_at()));
        tvContent.setText(message.getRemark());

        tvType.setContantText("null");
        tvPrice.setContantText(String.valueOf(message.getCategory_price()));
        tvPhone.setContantText(message.getPhone());
        tvAddress.setContantText("null");
    }
}
