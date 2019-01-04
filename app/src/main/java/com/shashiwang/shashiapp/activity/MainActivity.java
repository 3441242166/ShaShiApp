package com.shashiwang.shashiapp.activity;

import android.Manifest;
import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.NotificationManagerCompat;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.Toast;

import com.afollestad.materialdialogs.MaterialDialog;
import com.shashiwang.shashiapp.R;
import com.shashiwang.shashiapp.base.BaseMvpActivity;
import com.shashiwang.shashiapp.customizeview.NoScrollViewPager;
import com.shashiwang.shashiapp.fragment.MainFragment;
import com.shashiwang.shashiapp.fragment.MyFragment;
import com.shashiwang.shashiapp.presenter.MainActivityPresenter;
import com.shashiwang.shashiapp.service.LocationService;
import com.shashiwang.shashiapp.view.IMainActivityView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import es.dmoral.toasty.Toasty;
import pub.devrel.easypermissions.AppSettingsDialog;
import pub.devrel.easypermissions.EasyPermissions;

import static android.provider.Settings.EXTRA_APP_PACKAGE;
import static android.provider.Settings.EXTRA_CHANNEL_ID;
import static com.shashiwang.shashiapp.constant.Constant.REQUEST_PERMISSION;

public class MainActivity extends BaseMvpActivity<MainActivityPresenter> implements IMainActivityView {
    private static final String TAG = "MainActivity";

    @BindView(R.id.bottom_main)
    BottomNavigationView navigation;
    @BindView(R.id.vp_main)
    NoScrollViewPager viewPager;
    @BindView(R.id.iv_bottom_more)
    ImageView imageView;

    private MainFragment mainFragment;
    private MyFragment myFragment;

    private List<Fragment> fragmentList;

    private MaterialDialog dialog;

    @Override
    protected MainActivityPresenter setPresenter() {
        return new MainActivityPresenter(this,this);
    }

    @Override
    protected int getContentView() {
        return R.layout.activity_main;
    }



    protected void init(Bundle savedInstanceState) {
        ButterKnife.bind(this);
        initView();
        initData();
        initEvent();
        startService(new Intent(this,LocationService.class));

        boolean is = NotificationManagerCompat.from(this).areNotificationsEnabled();
        Log.i(TAG, "init:  isOpen Notification " + is);
        if(!is){
            dialog.show();
        }
    }

    private void initView() {
        viewPager.setOverScrollMode(View.OVER_SCROLL_NEVER);

        dialog = new MaterialDialog.Builder(this)
                .title("警告")
                .content("为了您的使用体验,请打开通知权限")
                .cancelable(false)
                .positiveText("开启")
                .negativeText("忽略")
                .onPositive((dialog, which) -> {
                    openSetting();
                })
                .onNegative((dialog, which) -> {
                    dialog.cancel();
                })
                .build();
    }

    private void initData() {
        fragmentList = new ArrayList<>();
        mainFragment = new MainFragment();
        myFragment = new MyFragment();
        fragmentList.add(mainFragment);
        fragmentList.add(myFragment);

        FragmentPagerAdapter adapter = new FragmentPagerAdapter(getSupportFragmentManager()) {
            @Override
            public Fragment getItem(int position) {
                return fragmentList.get(position);
            }

            @Override
            public int getCount() {
                return fragmentList.size();
            }
        };
        viewPager.setAdapter(adapter);
        viewPager.setOffscreenPageLimit(3);
        viewPager.setNoScroll(true);
    }

    private void initEvent(){
        imageView.setOnClickListener(view -> openMorePopupWindow());

        navigation.setOnNavigationItemSelectedListener(item -> {
            invalidateOptionsMenu();
            switch (item.getItemId()) {
                case R.id.main_menu_home:
                    viewPager.setCurrentItem(0);
                    return true;
                case R.id.main_menu_my:
                    viewPager.setCurrentItem(1);
                    return true;
            }
            return false;
        });
    }

    private long exitTime = 0;
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if(keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_DOWN){
            if((System.currentTimeMillis()-exitTime) > 2000){
                Toast.makeText(this,"再按一次退出",Toast.LENGTH_SHORT).show();
                exitTime = System.currentTimeMillis();
            } else {
                finish();
                System.exit(0);
            }
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    public void openSetting(){
        // 根据isOpened结果，判断是否需要提醒用户跳转AppInfo页面，去打开App通知权限
        Intent intent = new Intent();
        intent.setAction(Settings.ACTION_APP_NOTIFICATION_SETTINGS);
        //这种方案适用于 API 26, 即8.0（含8.0）以上可以用
        intent.putExtra(EXTRA_APP_PACKAGE, getPackageName());
        intent.putExtra(EXTRA_CHANNEL_ID, getApplicationInfo().uid);

        //这种方案适用于 API21——25，即 5.0——7.1 之间的版本可以使用
        intent.putExtra("app_package", getPackageName());
        intent.putExtra("app_uid", getApplicationInfo().uid);

        // 小米6 -MIUI9.6-8.0.0系统，是个特例，通知设置界面只能控制"允许使用通知圆点"——然而这个玩意并没有卵用，我想对雷布斯说：I'm not ok!!!
        //  if ("MI 6".equals(Build.MODEL)) {
        //      intent.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
        //      Uri uri = Uri.fromParts("package", getPackageName(), null);
        //      intent.setData(uri);
        //      // intent.setAction("com.android.settings/.SubSettings");
        //  }
        startActivity(intent);
    }

    public void openMorePopupWindow(){
        presenter.openMorePopupWindow();
    }

    @Override
    public void openPopupWindow(PopupWindow popupWindow) {
        popupWindow.showAtLocation(navigation, Gravity.BOTTOM, 0, 0);
    }

    @Override
    public void showProgress() {

    }

    @Override
    public void dismissProgress() {

    }

    @Override
    public void loadDataSuccess(Object data) {

    }

    @Override
    public void errorMessage(String throwable) {
        Toasty.normal(this,throwable).show();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.i(TAG, "onActivityResult: requestCode = "+requestCode + "  resultCode = "+resultCode);

        for(Fragment fragment:fragmentList){
            fragment.onActivityResult(requestCode, resultCode, data);
        }

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        // Forward results to EasyPermissions
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this);
    }



}
