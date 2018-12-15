package com.shashiwang.shashiapp.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.List;

/**
 * Created by wanhao on 2017/10/3.
 */

public class PagerAdapter<T extends Fragment>extends FragmentPagerAdapter {

    List<T> list;
    String[]ar ;

    public PagerAdapter(FragmentManager fm, List<T> list, String[]ar) {
        super(fm);
        this.list = list;
        this.ar = ar;
    }

    @Override
    public Fragment getItem(int position) {
        return list.get(position);
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return ar[position];
    }
}
