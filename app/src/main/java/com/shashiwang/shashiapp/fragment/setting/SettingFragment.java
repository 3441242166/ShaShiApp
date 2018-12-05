package com.shashiwang.shashiapp.fragment.setting;


import com.shashiwang.shashiapp.R;
import com.shashiwang.shashiapp.base.BasePresenter;
import com.shashiwang.shashiapp.base.LazyLoadFragment;
import com.shashiwang.shashiapp.customizeview.SettingNormalLayout;

import androidx.navigation.Navigation;
import butterknife.BindView;

public class SettingFragment extends LazyLoadFragment {

    @BindView(R.id.item_broadcast)
    SettingNormalLayout broadcast;
    @BindView(R.id.item_about)
    SettingNormalLayout about;

    @Override
    protected BasePresenter setPresenter() {
        return null;
    }

    @Override
    protected int setContentView() {
        return R.layout.fragment_setting;
    }

    @Override
    protected void init() {
        broadcast.setOnClickListener(view -> Navigation.findNavController(view).navigate(R.id.action_settingFragment_to_settingBroadcastFragment));
        about.setOnClickListener(view -> Navigation.findNavController(view).navigate(R.id.action_settingFragment_to_aboutFragment));
    }


}
