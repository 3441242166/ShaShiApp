package com.shashiwang.shashiapp.customizeview;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.constraint.ConstraintLayout;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;

import com.shashiwang.shashiapp.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SettingChooseLayout extends ConstraintLayout {

    @BindView(R.id.tv_title)
    TextView txTitle;
    @BindView(R.id.sw_switch)
    ImageView iv;

    String title;
    boolean checkable = true;
    boolean choose = false;

    OnClickListener onSwitchListener;

    public SettingChooseLayout(Context context, AttributeSet attrs) {
        super(context, attrs);

        TypedArray array=context.obtainStyledAttributes(attrs, R.styleable.SettingChooseLayout);
        title = array.getString(R.styleable.SettingSwitchLayout_title);
        array.recycle();

        initView();
    }

    private void initView() {
        LayoutInflater inflater = LayoutInflater.from(getContext());
        inflater.inflate(R.layout.view_setting_choose,this);
        ButterKnife.bind(this);

        iv.setOnClickListener(v -> {
            if(checkable){
                choose = !choose;
                if(choose){
                    iv.setImageResource(R.drawable.ic_choose_open);
                }else {
                    iv.setImageResource(R.drawable.ic_choose_close);
                }
            }
        });

        txTitle.setText(title);
    }

    public boolean isChoose(){
        return choose;
    }

    public void setChecked(boolean is){
        choose = is;
        if(is){
            if(checkable){
                iv.setImageResource(R.drawable.ic_choose_unuse);
            }else {
                iv.setImageResource(R.drawable.ic_choose_open);
            }
        }else {
            iv.setImageResource(R.drawable.ic_choose_close);
        }
    }

    public void setCheckable(boolean is){
        checkable = is;
        if(is){
            if(choose){
                iv.setImageResource(R.drawable.ic_choose_open);
            }else {
                iv.setImageResource(R.drawable.ic_choose_close);
            }
        }else {
            if(choose){
                iv.setImageResource(R.drawable.ic_choose_unuse);
            }else {
                iv.setImageResource(R.drawable.ic_choose_close);
            }
        }
    }

    public void setOnClickListener(OnClickListener onSwitchListener){
        this.onSwitchListener = onSwitchListener;
    }

    public interface OnClickListener{
        void onClick(boolean is);
    }
}
