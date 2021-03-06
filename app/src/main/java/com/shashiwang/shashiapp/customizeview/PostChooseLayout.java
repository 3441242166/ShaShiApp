package com.shashiwang.shashiapp.customizeview;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.constraint.ConstraintLayout;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.TextView;

import com.shashiwang.shashiapp.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class PostChooseLayout extends ConstraintLayout {

    @BindView(R.id.post_title)
    TextView txTitle;
    @BindView(R.id.post_content)
    TextView txContent;

    String title;
    String hintContent;
    String content;

    public PostChooseLayout(Context context, AttributeSet attrs) {
        super(context, attrs);

        TypedArray array=context.obtainStyledAttributes(attrs, R.styleable.PostChooseLayout);
        title = array.getString(R.styleable.PostChooseLayout_title);
        content = array.getString(R.styleable.PostChooseLayout_item_content);
        hintContent = array.getString(R.styleable.PostChooseLayout_hint_content);
        array.recycle();

        initView();
    }

    private void initView() {
        LayoutInflater inflater = LayoutInflater.from(getContext());
        inflater.inflate(R.layout.view_post_choose,this);
        ButterKnife.bind(this);

        txTitle.setText(title);
        txContent.setText(content);
        txContent.setHint(hintContent);
    }

    public String getContantText(){
        return txContent.getText().toString();
    }

    public void setContantText(String str){
        txContent.setText(str);
    }

}
