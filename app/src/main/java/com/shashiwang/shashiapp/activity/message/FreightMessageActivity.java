package com.shashiwang.shashiapp.activity.message;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.PopupWindow;
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
import com.shashiwang.shashiapp.util.DateUtil;
import com.shashiwang.shashiapp.util.StringUtil;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.Date;
import java.util.Map;

import butterknife.BindView;
import es.dmoral.toasty.Toasty;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

import static com.shashiwang.shashiapp.constant.Constant.ID;
import static com.shashiwang.shashiapp.util.MapUtil.isAvailable;

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
    @BindView(R.id.bt_phone)
    Button btPhone;

    private int id = -1;
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
        id = getIntent().getIntExtra(ID,-1);
        initView();
        initEvent();
        getMessage();
    }

    public void initView(){
        setTitle("详情");
    }

    public void initEvent(){
        tvStart.setOnClickListener(view -> {
            navigatorMap(message.getStart_location_lat(),message.getStart_location_lng());
        });


        tvEnd.setOnClickListener(view -> {
            navigatorMap(message.getEnd_location_lat(),message.getEnd_location_lng());
        });

        btPhone.setOnClickListener(view -> {
            Intent intent = new Intent(Intent.ACTION_DIAL);
            Uri data = Uri.parse("tel:" + message.getPhone());
            intent.setData(data);
            startActivity(intent);
        });
    }

    private void navigatorMap(String lat,String lng){
        boolean isBaiduUsed = isAvailable(FreightMessageActivity.this, "com.baidu.BaiduMap");
        boolean isGaodeUsed = isAvailable(FreightMessageActivity.this, "com.autonavi.minimap");
        Log.i(TAG, "initEvent: baidu = "+isBaiduUsed +"  gaode = "+isGaodeUsed);
        if(!isBaiduUsed && !isGaodeUsed){
            Toasty.info(FreightMessageActivity.this,"该手机未安装地图软件").show();
        }
        if(isBaiduUsed && isGaodeUsed){
            openSelectDialog(lat,lng);
            return;
        }
        if(isGaodeUsed){
            openGaodeMap(lat,lng);
            return;
        }
        if(isBaiduUsed){
            openBaiduMap(lat,lng);
            return;
        }

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
        tvTitle.setText(StringUtil.getFirstChinese(message.getLinkman())+"先生");
        tvTime.setText(DateUtil.getDifferentString(message.getUpdated_at()));
        tvContent.setText(message.getRemark());

        tvStart.setContantText(message.getStart_location());
        tvEnd.setContantText(message.getEnd_location());
        tvDistance.setContantText(String.valueOf(message.getDistance()));
        tvName.setContantText(message.getCargo_name());
        tvPrice.setContantText(String.valueOf(message.getPrice()));
        tvCar.setContantText(message.getCar_category());
        tvPhone.setContantText(message.getPhone());

        CloudRgcInfo info = new CloudRgcInfo();
        info.location = String.valueOf(message.getStart_location_lat()) +"," + String.valueOf(message.getStart_location_lng());
        CloudManager.getInstance().rgcSearch(info);
        info.location = String.valueOf(message.getEnd_location_lat()) +"," + String.valueOf(message.getEnd_location_lng());
        CloudManager.getInstance().rgcSearch(info);
    }

    private void openBaiduMap(String lat,String lng){
        Intent i1 = new Intent();
        String url = "baidumap://map/direction?destination="+lat+","+lng+"&src=com.shashiwang.shashiapp";
        Log.i(TAG, "openDialog: url = "+url);
        i1.setData(Uri.parse(url));
        startActivity(i1);
    }

    private void openGaodeMap(String lat,String lng){
        Intent intent = new Intent("android.intent.action.VIEW",
                android.net.Uri.parse("androidamap://navi?sourceApplication=amap&lat=" + lat + "&lon=" + lng));
        intent.setPackage("com.autonavi.minimap");
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }

    private void openSelectDialog(String lat,String lng){
        View view = LayoutInflater.from(this).inflate(R.layout.dialog_select, null);
        final PopupWindow popupWindow = getPopupWindow(view);
        //设置点击事件
        TextView tvBaidu = view.findViewById(R.id.tv_baidu);
        TextView tvGaode = view.findViewById(R.id.tv_gaode);

        tvBaidu.setOnClickListener(v -> {
            openBaiduMap(lat,lng);
            popupWindow.dismiss();
        });

        tvGaode.setOnClickListener(v -> {
            openGaodeMap(lat,lng);
            popupWindow.dismiss();
        });

        View parent = LayoutInflater.from(this).inflate(R.layout.activity_freight_message, null);
        //显示PopupWindow
        popupWindow.showAtLocation(parent, Gravity.BOTTOM, 0, 0);
    }

    private PopupWindow getPopupWindow(View view) {
        PopupWindow popupWindow = new PopupWindow(this);
        popupWindow.setContentView(view);
        popupWindow.setWidth(WindowManager.LayoutParams.MATCH_PARENT);
        popupWindow.setHeight(WindowManager.LayoutParams.WRAP_CONTENT);
        popupWindow.setBackgroundDrawable(new ColorDrawable(Color.WHITE));
        popupWindow.setFocusable(true);
        popupWindow.setOutsideTouchable(true);
        return popupWindow;
    }

}
