package com.shashiwang.shashiapp.fragment.setting;


import android.annotation.SuppressLint;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.example.net.interceptors.TokenInterceptor;
import com.example.net.rx.RxRetrofitClient;
import com.example.util.SharedPreferencesHelper;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.shashiwang.shashiapp.R;
import com.shashiwang.shashiapp.base.BasePresenter;
import com.shashiwang.shashiapp.base.BaseFragment;
import com.shashiwang.shashiapp.bean.HttpResult;
import com.shashiwang.shashiapp.bean.MessageResult;
import com.shashiwang.shashiapp.customizeview.SettingNormalLayout;

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
    @BindView(R.id.item_change)
    SettingNormalLayout change;
    @BindView(R.id.bt_exit)
    Button btExit;

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

    }

    @SuppressLint("CheckResult")
    private void logout() {
        RxRetrofitClient.builder()
                .header(new TokenInterceptor())
                .url(URL_LOGOUT)
                .build()
                .post()
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(s -> {
                    Log.i(TAG, "getList: success " + s);
                    HttpResult<MessageResult> result = new Gson().fromJson(s,new TypeToken<HttpResult<MessageResult>>(){}.getType());
                    if(!JPushInterface.isPushStopped(getContext())){
                        JPushInterface.stopPush(getContext());
                    }
                    SharedPreferencesHelper.remove(TOKEN);
                    getActivity().setResult(RESULT_SUCCESS);
                    getActivity().finish();
                    if(result.isSuccess()){

                    }else {

                    }

                }, throwable -> {
                    Log.i(TAG, "getList: error = " + throwable);
                    SharedPreferencesHelper.remove(TOKEN);
                    if(!JPushInterface.isPushStopped(getContext())){
                        JPushInterface.stopPush(getContext());
                    }
                    getActivity().setResult(RESULT_SUCCESS);
                    getActivity().finish();
                });

    }

    @Override
    public void onResume() {
        Log.i(TAG, "onResume: ");

        about.setOnClickListener(view -> Navigation.findNavController(view).navigate(R.id.action_settingFragment_to_aboutFragment));


        if(!TextUtils.isEmpty((String) SharedPreferencesHelper.getSharedPreference(TOKEN,""))){
            btExit.setVisibility(View.VISIBLE);
            change.setVisibility(View.VISIBLE);

            btExit.setOnClickListener(view -> {
                logout();
            });

            change.setOnClickListener(view -> Navigation.findNavController(view).navigate(R.id.action_settingFragment_to_changePasswordFragment));
        }
        super.onResume();
    }

}
