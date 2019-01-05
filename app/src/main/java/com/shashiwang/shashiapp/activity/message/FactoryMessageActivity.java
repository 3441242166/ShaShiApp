package com.shashiwang.shashiapp.activity.message;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
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
import com.example.net.rx.RxRetrofitClient;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.shashiwang.shashiapp.R;
import com.shashiwang.shashiapp.base.BasePresenter;
import com.shashiwang.shashiapp.base.BaseTopBarActivity;
import com.shashiwang.shashiapp.bean.FactoryMessage;
import com.shashiwang.shashiapp.bean.FreightMessage;
import com.shashiwang.shashiapp.bean.HttpResult;
import com.shashiwang.shashiapp.customizeview.ImageButton;
import com.shashiwang.shashiapp.customizeview.MessageLayout;
import com.shashiwang.shashiapp.util.DateUtil;
import com.shashiwang.shashiapp.util.StringUtil;

import butterknife.BindView;
import es.dmoral.toasty.Toasty;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

import static com.shashiwang.shashiapp.constant.ApiConstant.URL_FACTORY;
import static com.shashiwang.shashiapp.constant.Constant.ID;
import static com.shashiwang.shashiapp.util.MapUtil.isAvailable;

public class FactoryMessageActivity extends BaseTopBarActivity {
    private static final String TAG = "FactoryMessageActivity";

    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.tv_time)
    TextView tvTime;
    @BindView(R.id.tv_remark)
    TextView tvContent;

    @BindView(R.id.tv_price)
    MessageLayout tvPrice;
    @BindView(R.id.tv_phone)
    MessageLayout tvPhone;
    @BindView(R.id.tv_address)
    MessageLayout tvAddress;
    @BindView(R.id.bt_phone)
    Button btPhone;
    @BindView(R.id.bt_location)
    ImageButton btLocation;

    private int id = -1;
    private FactoryMessage message;

    @Override
    protected BasePresenter setPresenter() {
        return null;
    }

    @Override
    protected int getFrameContentView() {
        return R.layout.activity_factory_message;
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

        btLocation.setOnClickListener(view -> {
            navigatorMap(message.getLocation_lat(),message.getLocation_lng());
        });
    }

    private void navigatorMap(String lat,String lng){
        boolean isBaiduUsed = isAvailable(this, "com.baidu.BaiduMap");
        boolean isGaodeUsed = isAvailable(this, "com.autonavi.minimap");
        Log.i(TAG, "initEvent: baidu = "+isBaiduUsed +"  gaode = "+isGaodeUsed);
        if(!isBaiduUsed && !isGaodeUsed){
            Toasty.info(this,"该手机未安装地图软件").show();
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

    @SuppressLint("CheckResult")
    private void getMessage(){
        RxRetrofitClient.builder()
                .url(URL_FACTORY + id)
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

        tvPrice.setContantText(String.valueOf(message.getCategory_price()));
        tvPhone.setContantText(message.getPhone());
        tvAddress.setContantText(message.getLocation());
    }
}
