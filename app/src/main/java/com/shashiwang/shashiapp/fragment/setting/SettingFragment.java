package com.shashiwang.shashiapp.fragment.setting;


import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;

import com.example.util.SharedPreferencesHelper;
import com.shashiwang.shashiapp.R;
import com.shashiwang.shashiapp.base.BasePresenter;
import com.shashiwang.shashiapp.base.LazyLoadFragment;
import com.shashiwang.shashiapp.customizeview.SettingNormalLayout;

import androidx.navigation.Navigation;
import butterknife.BindView;

import static com.shashiwang.shashiapp.constant.Constant.RESULT_SUCCESS;
import static com.shashiwang.shashiapp.constant.Constant.TOKEN;

public class SettingFragment extends LazyLoadFragment {

    @BindView(R.id.item_broadcast)
    SettingNormalLayout broadcast;
    @BindView(R.id.item_about)
    SettingNormalLayout about;
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

        broadcast.setOnClickListener(view -> Navigation.findNavController(view).navigate(R.id.action_settingFragment_to_settingBroadcastFragment));
        about.setOnClickListener(view -> Navigation.findNavController(view).navigate(R.id.action_settingFragment_to_aboutFragment));

        if(!TextUtils.isEmpty((String) SharedPreferencesHelper.getSharedPreference(TOKEN,""))){
            btExit.setVisibility(View.VISIBLE);
            btExit.setOnClickListener(view -> {
                SharedPreferencesHelper.remove(TOKEN);
                getActivity().setResult(RESULT_SUCCESS);
                getActivity().finish();
            });
        }

    }


}
