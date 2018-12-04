package com.shashiwang.shashiapp.customizeview;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.constraint.ConstraintLayout;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.shashiwang.shashiapp.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SettingNormalLayout extends ConstraintLayout {

    @BindView(R.id.tv_title)
    TextView txTitle;
    @BindView(R.id.title_second_title)
    TextView tvSecond;
    @BindView(R.id.iv_icon)
    ImageView ivIcon;

    String title;
    String secondTitle;
    int icon;
    boolean isSecondTitleShow;
    boolean isIconShow;

    public SettingNormalLayout(Context context, AttributeSet attrs) {
        super(context, attrs);

        TypedArray array=context.obtainStyledAttributes(attrs, R.styleable.SettingNormalLayout);
        title = array.getString(R.styleable.SettingNormalLayout_title);
        secondTitle = array.getString(R.styleable.SettingNormalLayout_second_title);
        icon = array.getInt(R.styleable.SettingNormalLayout_icon,-1);

        isSecondTitleShow = array.getBoolean(R.styleable.SettingNormalLayout_second_title_show,false);
        isIconShow = array.getBoolean(R.styleable.SettingNormalLayout_icon_show,false);

        array.recycle();

        initView();
    }

    private void initView() {
        LayoutInflater inflater = LayoutInflater.from(getContext());
        inflater.inflate(R.layout.view_setting_normal,this);
        ButterKnife.bind(this);

        txTitle.setText(title);

        if(isSecondTitleShow){
            tvSecond.setText(secondTitle);
        }else {
            tvSecond.setVisibility(View.GONE);
        }

        if(isIconShow) {
            if (icon != -1) {
                ivIcon.setImageDrawable(getContext().getResources().getDrawable(icon, null));
            }
        }else {
            ivIcon.setVisibility(View.GONE);
        }

    }

}
