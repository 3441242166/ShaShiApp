package com.shashiwang.shashiapp.base;


import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
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

public abstract class TopBarBaseActivity<T extends IBasePresenter> extends BaseMvpActivity<T> {

    private FrameLayout viewContent;
    private TextView tvTitle;
    private Button btLeft;
    private Button btRight;

    protected abstract int getContentView();

    protected abstract void init(Bundle savedInstanceState);

    public interface OnClickListener{
        void onClick();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_base_topbar);

        viewContent = findViewById(R.id.ac_base_content);
        tvTitle = findViewById(R.id.ac_base_title);
        btLeft = findViewById(R.id.ac_base_left);
        btRight = findViewById(R.id.ac_base_right);
        //将继承 TopBarBaseActivity 的布局解析到 FrameLayout 里面
        LayoutInflater.from(TopBarBaseActivity.this).inflate(getContentView(), viewContent);
        ButterKnife.bind(viewContent);

        init(savedInstanceState);
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

}
