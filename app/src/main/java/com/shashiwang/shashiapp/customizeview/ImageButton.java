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


public class ImageButton extends ConstraintLayout {

    @BindView(R.id.tv_content)
    TextView tvContent;
    @BindView(R.id.iv_icon)
    ImageView ivIcon;

    String content;
    int iconId;

    public ImageButton(Context context, AttributeSet attrs) {
        super(context, attrs);

        TypedArray array=context.obtainStyledAttributes(attrs, R.styleable.ImageButton);
        content = array.getString(R.styleable.ImageButton_item_content);
        iconId = array.getResourceId(R.styleable.ImageButton_item_icon,-1);
        array.recycle();

        initView();
    }

    private void initView() {
        LayoutInflater inflater = LayoutInflater.from(getContext());
        inflater.inflate(R.layout.view_imagebutton,this);
        ButterKnife.bind(this);

        if(iconId != -1){
            ivIcon.setVisibility(View.VISIBLE);
            ivIcon.setImageResource(iconId);
        }

        tvContent.setText(content);
    }

    public String getContentText(){
        return tvContent.getText().toString();
    }

    public void setContentText(String str){
        tvContent.setText(str);
    }

    public void setIcon(int str){
        ivIcon.setImageResource(str);
    }
}
