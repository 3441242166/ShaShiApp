package com.shashiwang.shashiapp.activity;

import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Toast;

import com.shashiwang.shashiapp.R;
import com.shashiwang.shashiapp.base.BaseMvpActivity;
import com.shashiwang.shashiapp.base.IBasePresenter;
import com.shashiwang.shashiapp.fragment.MainFragment;
import com.shashiwang.shashiapp.fragment.MyFragment;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class MainActivity extends BaseMvpActivity {

    @BindView(R.id.ac_main_bottom)
    BottomNavigationView navigation;
    @BindView(R.id.ac_main_viewpager)
    ViewPager viewPager;

    private MainFragment mainFragment;
    private MyFragment myFragment;

    private List<Fragment> fragmentList;

    @Override
    protected IBasePresenter setPresenter() {
        return null;
    }

    @Override
    protected int getContentView() {
        return R.layout.activity_main;
    }

    @Override
    protected void init() {
        initView();
        initData();

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

}
