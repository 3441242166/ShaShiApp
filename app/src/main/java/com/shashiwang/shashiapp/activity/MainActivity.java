package com.shashiwang.shashiapp.activity;

import android.graphics.Color;
import android.media.Image;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.zhouwei.library.CustomPopWindow;
import com.shashiwang.shashiapp.R;
import com.shashiwang.shashiapp.base.BaseMvpActivity;
import com.shashiwang.shashiapp.base.IBasePresenter;
import com.shashiwang.shashiapp.fragment.MainFragment;
import com.shashiwang.shashiapp.fragment.MyFragment;
import com.shashiwang.shashiapp.presenter.MainActivityPresenter;
import com.shashiwang.shashiapp.view.IMainActivityView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import qiu.niorgai.StatusBarCompat;

import static android.widget.RelativeLayout.ALIGN_LEFT;

public class MainActivity extends BaseMvpActivity<MainActivityPresenter> implements IMainActivityView{
    private static final String TAG = "MainActivity";

    @BindView(R.id.ac_main_bottom)
    BottomNavigationView navigation;
    @BindView(R.id.ac_main_viewpager)
    ViewPager viewPager;
    @BindView(R.id.ac_main_add)
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

    @Override
    protected void init() {
        initView();
        initData();
        initEvent();
    }

    private void initView() {
        viewPager.setOverScrollMode(View.OVER_SCROLL_NEVER);

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
    }

    private void initEvent(){
        imageView.setOnClickListener(view -> presenter.openMorePopupWindow());
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
    public void openPopupWindow(CustomPopWindow customPopWindow) {
        Log.i(TAG, "openPopupWindowShow" + customPopWindow.getHeight());
        customPopWindow.showAsDropDown(imageView,0,-imageView.getHeight() - customPopWindow.getHeight());
        //customPopWindow.showAsDropDown(imageView,0,-100);
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
}
