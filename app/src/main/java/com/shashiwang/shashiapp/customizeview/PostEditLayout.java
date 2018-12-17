package com.shashiwang.shashiapp.customizeview;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.constraint.ConstraintLayout;
import android.text.InputType;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.EditText;
import android.widget.TextView;

import com.shashiwang.shashiapp.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class PostEditLayout extends ConstraintLayout {

    @BindView(R.id.post_title)
    TextView txTitle;
    @BindView(R.id.post_input)
    EditText etContent;

    String title;
    String hintContent;
    String content;
    int inputType;

    public PostEditLayout(Context context, AttributeSet attrs) {
        super(context, attrs);

        TypedArray array=context.obtainStyledAttributes(attrs, R.styleable.PostEditLayout);
        title = array.getString(R.styleable.PostEditLayout_title);
        content = array.getString(R.styleable.PostEditLayout_item_content);
        hintContent = array.getString(R.styleable.PostEditLayout_hint_content);
        inputType = array.getInt(R.styleable.LoginEditText_input_type,1);
        array.recycle();

        initView();
    }

    private void initView() {
        LayoutInflater inflater = LayoutInflater.from(getContext());
        inflater.inflate(R.layout.view_post_edit,this);
        ButterKnife.bind(this);

        txTitle.setText(title);
        etContent.setText(content);
        etContent.setHint(hintContent);

        switch (inputType){
            case 1:
                inputType = InputType.TYPE_CLASS_TEXT;
                break;
            case 2:
                inputType = InputType.TYPE_CLASS_NUMBER;
                break;
            case 3:
                inputType = InputType.TYPE_NUMBER_VARIATION_PASSWORD;
                break;
            case 4:
                inputType = InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD;
                break;
        }

        etContent.setInputType(inputType);
    }

    public String getContantText(){
        return etContent.getText().toString();
    }

    public void setContantText(String str){
        etContent.setText(str);
    }


}
