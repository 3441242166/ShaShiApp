package com.shashiwang.shashiapp.base;


import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.shashiwang.shashiapp.R;

import butterknife.ButterKnife;

/**
 * Created by wanhao on 2017/10/3.
 */

public abstract class BaseTopBarActivity<T extends BasePresenter> extends BaseMvpActivity<T> {

    private FrameLayout viewContent;
    private TextView tvTitle;
    private Button btLeft;
    private Button btRight;

    protected abstract int getFrameContentView();

    protected abstract void initFrame(Bundle savedInstanceState);

    @Override
    protected void init(Bundle savedInstanceState) {
        viewContent = findViewById(R.id.ac_base_content);
        tvTitle = findViewById(R.id.ac_base_title);
        btLeft = findViewById(R.id.ac_base_left);
        btRight = findViewById(R.id.ac_base_right);
        //将继承 BaseTopBarActivity 的布局解析到 FrameLayout 里面
        LayoutInflater.from(BaseTopBarActivity.this).inflate(getFrameContentView(), viewContent);
        ButterKnife.bind(this);

        initFrame(savedInstanceState);

        btLeft.setOnClickListener(view -> finish());
    }

    @Override
    protected int getContentView() {
        return R.layout.activity_base_topbar;
    }

    protected void setTitle(String title){
        if (!TextUtils.isEmpty(title)){
            tvTitle.setText(title);
        }
    }

    protected void setTopLeftButton(OnClickListener onClickListener){
        setTopLeftButton(R.drawable.icon_back_black, onClickListener);
    }

    protected void setTopLeftButton(int iconResId, OnClickListener onClickListener){
        btLeft.setVisibility(View.VISIBLE);
        btLeft.setBackgroundResource(iconResId);
        btLeft.setOnClickListener(v -> onClickListener.onClick());
    }

    protected void setTopRightButton(int iconResId, OnClickListener onClickListener){
        btRight.setVisibility(View.VISIBLE);
        btRight.setBackgroundResource(iconResId);
        btRight.setOnClickListener(v -> onClickListener.onClick());
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    public interface OnClickListener{
        void onClick();
    }
}
