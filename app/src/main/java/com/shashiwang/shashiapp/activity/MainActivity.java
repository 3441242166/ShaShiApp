package com.shashiwang.shashiapp.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.Toast;

import com.shashiwang.shashiapp.R;
import com.shashiwang.shashiapp.base.BaseMvpActivity;
import com.shashiwang.shashiapp.customizeview.NoScrollViewPager;
import com.shashiwang.shashiapp.fragment.MainFragment;
import com.shashiwang.shashiapp.fragment.MyFragment;
import com.shashiwang.shashiapp.presenter.MainActivityPresenter;
import com.shashiwang.shashiapp.view.IMainActivityView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends BaseMvpActivity<MainActivityPresenter> implements IMainActivityView{
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

    @Override
    protected MainActivityPresenter setPresenter() {
        return new MainActivityPresenter(this,this);
    }

    @Override
    protected int getContentView() {
        return R.layout.activity_main;
    }

    public static String[] data = new String[] {android.Manifest.permission.ACCESS_FINE_LOCATION,
            android.Manifest.permission.ACCESS_COARSE_LOCATION,
            android.Manifest.permission.READ_PHONE_STATE,
            android.Manifest.permission.ACCESS_WIFI_STATE,
            android.Manifest.permission.ACCESS_NETWORK_STATE,
            android.Manifest.permission.WRITE_EXTERNAL_STORAGE,
            android.Manifest.permission.INTERNET,
            android.Manifest.permission.CHANGE_WIFI_STATE,};

    protected void init(Bundle savedInstanceState) {
        ButterKnife.bind(this);
        ActivityCompat.requestPermissions(this,data, 1);
        initView();
        initData();
        initEvent();
    }

    private void initView() {
        viewPager.setOverScrollMode(View.OVER_SCROLL_NEVER);
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
        imageView.setOnClickListener(view -> presenter.openMorePopupWindow());

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

    @Override
    public void openPopupWindow(PopupWindow popupWindow) {
        popupWindow.showAtLocation(navigation, Gravity.BOTTOM, 0, 0);
        //popupWindow.setOutsideTouchable(false);
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

    }

    private void showPopup(PopupWindow popupWindow) {
        popupWindow.showAtLocation(navigation, Gravity.BOTTOM, 0, 0);
        popupWindow.setOutsideTouchable(false);
        //Animation animation = AnimationUtils.loadAnimation(MainActivity.this, R.anim.turn_around_45);
        //animation.setFillAfter(true);
        //iv_close.startAnimation(animation);
    }


    private void dismissPopup(PopupWindow popupWindow) {
        if (popupWindow.isShowing()) {
            //Animation animation = AnimationUtils.loadAnimation(MainActivity.this, R.anim.turn_around_45_un);
            //animation.setFillAfter(true);
            //iv_close.startAnimation(animation);
            //ivMore.setVisibility(View.VISIBLE);
            //popupWindow.dismiss();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        for(Fragment fragment:fragmentList){
            fragment.onActivityResult(requestCode, resultCode, data);
        }

    }
}
