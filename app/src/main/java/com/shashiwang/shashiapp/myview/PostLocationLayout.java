package com.shashiwang.shashiapp.myview;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.constraint.ConstraintLayout;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.TextView;

import com.shashiwang.shashiapp.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class PostLocationLayout extends ConstraintLayout {

    @BindView(R.id.post_title)
    TextView txTitle;
    @BindView(R.id.post_content)
    TextView txContent;

    String title;
    String hintContent;
    String content;

    public PostLocationLayout(Context context, AttributeSet attrs) {
        super(context, attrs);

        TypedArray array=context.obtainStyledAttributes(attrs, R.styleable.PostLocationLayout);
        title = array.getString(R.styleable.PostLocationLayout_title);
        content = array.getString(R.styleable.PostLocationLayout_item_content);
        hintContent = array.getString(R.styleable.PostLocationLayout_hint_content);
        array.recycle();

        initView();
    }

    private void initView() {
        LayoutInflater inflater = LayoutInflater.from(getContext());
        inflater.inflate(R.layout.item_post_loction,this);
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
