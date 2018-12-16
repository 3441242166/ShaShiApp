package com.shashiwang.shashiapp.customizeview;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.support.constraint.ConstraintLayout;
import android.text.InputType;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.shashiwang.shashiapp.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class LoginEditText extends ConstraintLayout {

    @BindView(R.id.iv_left)
    ImageView ivLeft;
    @BindView(R.id.iv_right)
    ImageView ivRight;
    @BindView(R.id.ev_input)
    EditText evInput;

    int leftImage;
    int rightImage;
    String hintContent;
    int inputType = 1;
    private OnLeftClickListener onClickListener;


    public LoginEditText(Context context, AttributeSet attrs) {
        super(context, attrs);

        TypedArray array=context.obtainStyledAttributes(attrs, R.styleable.LoginEditText);
        leftImage = array.getResourceId(R.styleable.LoginEditText_left_icon,-1);
        rightImage = array.getResourceId(R.styleable.LoginEditText_right_icon,-1);
        inputType = array.getInt(R.styleable.LoginEditText_input_type,inputType);
        hintContent = array.getString(R.styleable.LoginEditText_hint_content);
        array.recycle();

        initView();
    }

    private void initView() {
        LayoutInflater inflater = LayoutInflater.from(getContext());
        inflater.inflate(R.layout.view_login_edit,this);
        ButterKnife.bind(this);

        if(leftImage != -1) {
            ivLeft.setImageResource(leftImage);
        }

        if(rightImage != -1) {
            ivRight.setImageResource(rightImage);
            ivRight.setOnClickListener(view -> {
                if (onClickListener != null) {
                    onClickListener.onClick();
                }
            });
        }else {
            ivRight.setVisibility(View.GONE);
        }

        evInput.setHint(hintContent);
        evInput.setInputType(inputType);

        switch (inputType){
            case 1:
                inputType = InputType.TYPE_CLASS_TEXT;
                break;
            case 2:
                inputType = InputType.TYPE_CLASS_NUMBER;
                break;
            case 3:
                inputType = InputType.TYPE_CLASS_PHONE;
                break;
            case 4:
                inputType = InputType.TYPE_TEXT_VARIATION_PASSWORD;
                break;
        }

        evInput.setInputType(inputType);
    }

    public String getContantText(){
        return evInput.getText().toString();
    }

    public void setConstantText(String str){
        evInput.setText(str);
    }

    public void setRightImage(int resID){
        ivRight.setVisibility(View.VISIBLE);
        ivRight.setImageResource(resID);
    }

    public void setRightImage(Bitmap resID){
        ivRight.setVisibility(View.VISIBLE);
        ivRight.setImageBitmap(resID);
    }

    public void setOnLeftClickListener(OnLeftClickListener onClickListener){
        this.onClickListener = onClickListener;
    }

    public void setInputType(int type){
        inputType = type;
        evInput.setInputType(inputType);
    }

    public interface OnLeftClickListener{
        void onClick();
    }
}
