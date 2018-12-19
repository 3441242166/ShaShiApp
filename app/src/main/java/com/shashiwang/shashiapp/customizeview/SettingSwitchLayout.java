package com.shashiwang.shashiapp.customizeview;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.constraint.ConstraintLayout;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TextView;

import com.shashiwang.shashiapp.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SettingSwitchLayout extends ConstraintLayout {

    @BindView(R.id.tv_title)
    TextView txTitle;
    @BindView(R.id.sw_switch)
    Switch sw;

    String title;

    OnSwitchListener onSwitchListener;

    public SettingSwitchLayout(Context context, AttributeSet attrs) {
        super(context, attrs);

        TypedArray array=context.obtainStyledAttributes(attrs, R.styleable.SettingSwitchLayout);
        title = array.getString(R.styleable.SettingSwitchLayout_title);
        array.recycle();

        initView();
    }

    private void initView() {
        LayoutInflater inflater = LayoutInflater.from(getContext());
        inflater.inflate(R.layout.view_setting_switch,this);
        ButterKnife.bind(this);

        sw.setOnCheckedChangeListener((compoundButton, b) -> {
            if(onSwitchListener != null){
                onSwitchListener.onSwitch(b);
            }
        });


        txTitle.setText(title);
    }

    public boolean isChecked(){
        return sw.isChecked();
    }

    public void setChecked(boolean is){
        sw.setChecked(is);
    }

    public void setCheckable(boolean is){
        sw.setClickable(is);
    }

    public void setOnSwitchListener(OnSwitchListener onSwitchListener){
        this.onSwitchListener = onSwitchListener;
    }

    public interface OnSwitchListener{
        void onSwitch(boolean is);
    }
}
