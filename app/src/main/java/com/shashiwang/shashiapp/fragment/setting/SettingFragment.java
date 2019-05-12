package com.shashiwang.shashiapp.fragment.setting;


import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.net.interceptors.TokenInterceptor;
import com.example.net.rx.RxRetrofitClient;
import com.example.util.SharedPreferencesHelper;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.shashiwang.shashiapp.R;
import com.shashiwang.shashiapp.activity.LoginActivity;
import com.shashiwang.shashiapp.activity.MainActivity;
import com.shashiwang.shashiapp.base.BasePresenter;
import com.shashiwang.shashiapp.base.BaseFragment;
import com.shashiwang.shashiapp.bean.HttpResult;
import com.shashiwang.shashiapp.bean.MessageResult;
import com.shashiwang.shashiapp.customizeview.SettingNormalLayout;
import com.shashiwang.shashiapp.util.ActivityCollector;
import com.shashiwang.shashiapp.util.DataUtil;

import androidx.navigation.Navigation;
import butterknife.BindView;
import cn.jpush.android.api.JPushInterface;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

import static com.shashiwang.shashiapp.constant.ApiConstant.URL_LOGOUT;
import static com.shashiwang.shashiapp.constant.Constant.RESULT_SUCCESS;
import static com.shashiwang.shashiapp.constant.Constant.TOKEN;

public class SettingFragment extends BaseFragment {
    private static final String TAG = "SettingFragment";

    @BindView(R.id.item_about)
    SettingNormalLayout about;
    @BindView(R.id.item_area)
    SettingNormalLayout area;
    @BindView(R.id.bt_exit)
    Button btExit;

    boolean shape = (boolean) SharedPreferencesHelper.getSharedPreference("shape",true);


    @Override
    protected BasePresenter setPresenter() {
        return null;
    }

    @Override
    protected int setContentView() {
        return R.layout.fragment_setting;
    }

    @Override
    protected void init(Bundle savedInstanceState) {
        about.setSecondTitle((String) SharedPreferencesHelper.getSharedPreference("distance","1000"));
        if(shape){
            area.setSecondTitle("园形");

        }else {
            area.setSecondTitle("方形");
        }
        about.setSecondTitle(String.valueOf(DataUtil.nowCount.distance));
    }

    @SuppressLint("CheckResult")
    @Override
    public void onResume() {
        Log.i(TAG, "onResume: ");

        about.setOnClickListener(view -> {
            final EditText et = new EditText(getContext());
            et.setInputType( InputType.TYPE_CLASS_NUMBER);

            new AlertDialog.Builder(getContext()).setTitle("输入报警距离")
                    .setView(et)
                    .setPositiveButton("确定", (dialog, which) -> {
                        String distance = et.getText().toString();
                        RxRetrofitClient.builder()
                                .url("distance")
                                .params("count", DataUtil.nowCount.count)
                                .params("distance", distance)
                                .build()
                                .get()
                                .subscribeOn(Schedulers.newThread())
                                .observeOn(AndroidSchedulers.mainThread())
                                .subscribe(s -> {
                                            Log.i(TAG, "accept: "+s);

                                            HttpResult<Object> result = new Gson().fromJson(s,new TypeToken<HttpResult<Object>>(){}.getType());

                                            if(result.isSuccess()){
                                                Toast.makeText(getContext(),"设置成功",Toast.LENGTH_SHORT).show();
                                                DataUtil.nowCount.distance = Integer.valueOf(et.getText().toString());
                                                about.setSecondTitle(et.getText().toString());
                                                dialog.dismiss();
                                            }
                                        });
                    })
                    .setNegativeButton("取消", null)
                    .show();
        });

        area.setOnClickListener(v -> {
            if(shape){
                SharedPreferencesHelper.put("shape",false);
                area.setSecondTitle("方形");
            }else {
                SharedPreferencesHelper.put("shape",true);
                area.setSecondTitle("园形");
            }
            shape = !shape;
        });

        btExit.setOnClickListener(v -> {
            startActivity(new Intent(getContext(), LoginActivity.class));
            ActivityCollector.finishAll();
        });

        super.onResume();
    }

}
