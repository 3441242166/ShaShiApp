package com.shashiwang.shashiapp.customizeview;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;

public class NoScrollViewPager extends ViewPager {

    //是否禁止左右滑动，true为禁止，false为不禁止
    private boolean noScroll = true;

    public NoScrollViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public void setNoScroll(boolean noScroll) {
        this.noScroll = noScroll;
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent arg0) {
        if(noScroll){
            return false;
        }else {
            return super.onInterceptTouchEvent(arg0);
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent arg0) {
        if (noScroll)
            return false;
        else
            return super.onTouchEvent(arg0);
    }
}
