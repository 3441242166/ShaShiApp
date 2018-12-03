package com.shashiwang.shashiapp.customizeview;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.constraint.ConstraintLayout;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.EditText;
import android.widget.TextView;

import com.shashiwang.shashiapp.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class PostEditPlusLayout extends ConstraintLayout {

    @BindView(R.id.post_title)
    TextView txTitle;
    @BindView(R.id.post_input)
    EditText etContent;

    String title;
    String hintContent;
    String content;

    public PostEditPlusLayout(Context context, AttributeSet attrs) {
        super(context, attrs);

        TypedArray array=context.obtainStyledAttributes(attrs, R.styleable.PostEditPlusLayout);
        title = array.getString(R.styleable.PostEditPlusLayout_title);
        content = array.getString(R.styleable.PostEditPlusLayout_item_content);
        hintContent = array.getString(R.styleable.PostEditPlusLayout_hint_content);
        array.recycle();

        initView();
    }

    private void initView() {
        LayoutInflater inflater = LayoutInflater.from(getContext());
        inflater.inflate(R.layout.view_post_editplus,this);
        ButterKnife.bind(this);

        txTitle.setText(title);
        etContent.setText(content);
        etContent.setHint(hintContent);
    }

    public String getContantText(){
        return etContent.getText().toString();
    }

    public void setContantText(String str){
        etContent.setText(str);
    }


}
