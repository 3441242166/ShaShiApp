package com.shashiwang.shashiapp.customizeview;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.constraint.ConstraintLayout;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.shashiwang.shashiapp.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MessageLayout extends ConstraintLayout {

    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.tv_content)
    TextView tvContent;
    @BindView(R.id.iv_icon)
    ImageView ivIcon;

    String title;
    String content;
    int iconId;

    public MessageLayout(Context context, AttributeSet attrs) {
        super(context, attrs);

        TypedArray array=context.obtainStyledAttributes(attrs, R.styleable.MessageLayout);
        title = array.getString(R.styleable.MessageLayout_title);
        content = array.getString(R.styleable.MessageLayout_item_content);
        iconId = array.getResourceId(R.styleable.MessageLayout_item_icon,-1);
        array.recycle();

        initView();
    }

    private void initView() {
        LayoutInflater inflater = LayoutInflater.from(getContext());
        inflater.inflate(R.layout.view_message,this);
        ButterKnife.bind(this);

        if(iconId != -1){
            ivIcon.setVisibility(View.VISIBLE);
            ivIcon.setImageResource(iconId);
        }

        tvTitle.setText(title);
        tvContent.setText(content);
    }

    public String getContantText(){
        return tvContent.getText().toString();
    }

    public void setContantText(String str){
        tvContent.setText(str);
    }

    public String getTitleText(){
        return tvTitle.getText().toString();
    }

    public void setTitleText(String str){
        tvTitle.setText(str);
    }
}

