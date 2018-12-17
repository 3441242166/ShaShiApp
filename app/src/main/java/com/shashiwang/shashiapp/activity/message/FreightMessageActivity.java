package com.shashiwang.shashiapp.activity.message;

import android.annotation.SuppressLint;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.baidu.mapapi.cloud.CloudManager;
import com.baidu.mapapi.cloud.CloudRgcInfo;
import com.baidu.mapapi.cloud.CloudRgcResult;
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
import com.shashiwang.shashiapp.customizeview.MessageLayout;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.Map;

import butterknife.BindView;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

import static com.shashiwang.shashiapp.constant.Constant.ID;

public class FreightMessageActivity extends BaseTopBarActivity {
    private static final String TAG = "FreightMessageActivity";

    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.tv_time)
    TextView tvTime;
    @BindView(R.id.tv_content)
    TextView tvContent;
    @BindView(R.id.tv_startpos)
    MessageLayout tvStart;
    @BindView(R.id.tv_endpos)
    MessageLayout tvEnd;
    @BindView(R.id.tv_price)
    MessageLayout tvPrice;
    @BindView(R.id.tv_distance)
    MessageLayout tvDistance;
    @BindView(R.id.tv_car)
    MessageLayout tvCar;
    @BindView(R.id.tv_name)
    MessageLayout tvName;
    @BindView(R.id.tv_phone)
    MessageLayout tvPhone;

    int id = -1;
    private FreightMessage message;

    @Override
    protected BasePresenter setPresenter() {
        return null;
    }

    @Override
    protected int getFrameContentView() {
        return R.layout.activity_freight_message;
    }

    @Override
    protected void initFrame(Bundle savedInstanceState) {
        setTitle("详情");
        id = getIntent().getIntExtra(ID,-1);
        Log.i(TAG, "initFrame: DataID = " + id);
        EventBus.getDefault().register(this);
        getMessage();
    }

    @SuppressLint("CheckResult")
    private void getMessage(){
        RxRetrofitClient.builder()
                .url("api/freight/"+id)
                .build()
                .get()
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(s -> {
                    Log.i(TAG, "getList: success " + s);
                    HttpResult<FreightMessage> result = new Gson().fromJson(s,new TypeToken<HttpResult<FreightMessage>>(){}.getType());

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

    private void loadDataSuccess(FreightMessage message){
        tvTitle.setText(""+message.getUser_id());
        tvTime.setText(message.getUpdated_at());
        tvContent.setText(message.getRemark());

        tvStart.setContantText(message.getStart_location());
        tvEnd.setContantText(message.getEnd_location());
        tvDistance.setContantText(String.valueOf(message.getDistance()));
        tvName.setContantText(message.getCargo_name());
        tvPrice.setContantText(String.valueOf(message.getPrice()));
        tvCar.setContantText(message.getCar_category());
        tvPhone.setContantText("123456789");

        CloudRgcInfo info = new CloudRgcInfo();
        info.location = String.valueOf(message.getStart_location_lat()) +"," + String.valueOf(message.getStart_location_lng());
        CloudManager.getInstance().rgcSearch(info);
        info.location = String.valueOf(message.getEnd_location_lat()) +"," + String.valueOf(message.getEnd_location_lng());
        CloudManager.getInstance().rgcSearch(info);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(CloudRgcResult data ) {

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }
}
