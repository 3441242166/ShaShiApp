package com.shashiwang.shashiapp.activity.post;

import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.shashiwang.shashiapp.R;
import com.shashiwang.shashiapp.activity.LoginActivity;
import com.shashiwang.shashiapp.adapter.PagerAdapter;
import com.shashiwang.shashiapp.base.BasePresenter;
import com.shashiwang.shashiapp.base.BaseTopBarActivity;
import com.shashiwang.shashiapp.fragment.MessageListFragment;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class PostListActivity extends BaseTopBarActivity {
    private static final String TAG = "PostListActivity";

    @BindView(R.id.tab_layout)
    TabLayout tabLayout ;
    @BindView(R.id.viewpager)
    ViewPager viewPager;

    private List<Fragment> fragmentList;

    @Override
    protected BasePresenter setPresenter() {
        return null;
    }

    @Override
    protected int getFrameContentView() {
        return R.layout.activity_post_list;
    }

    @Override
    protected void initFrame(Bundle savedInstanceState) {
        setTitle("我的发布");

        fragmentList = new ArrayList<>();
        fragmentList.add(MessageListFragment.newInstance(""));
        fragmentList.add(MessageListFragment.newInstance(""));
        fragmentList.add(MessageListFragment.newInstance(""));
        fragmentList.add(MessageListFragment.newInstance(""));
        fragmentList.add(MessageListFragment.newInstance(""));

        String []ar ={"石料厂","搅拌站","司机","运费","车辆"};
        viewPager.setAdapter(new PagerAdapter<>(getSupportFragmentManager(), fragmentList, ar));
        tabLayout.setupWithViewPager(viewPager);
        viewPager.setOverScrollMode(viewPager.OVER_SCROLL_NEVER);
        viewPager.setOffscreenPageLimit(3);
    }
}
