package com.shashiwang.shashiapp.activity.post;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.shashiwang.shashiapp.R;
import com.shashiwang.shashiapp.base.BasePresenter;
import com.shashiwang.shashiapp.base.BaseTopBarActivity;
import com.shashiwang.shashiapp.constant.Constant;
import com.shashiwang.shashiapp.customizeview.PostChooseLayout;
import com.shashiwang.shashiapp.customizeview.PostEditLayout;
import com.shashiwang.shashiapp.customizeview.PostEditPlusLayout;
import com.shashiwang.shashiapp.customizeview.PostLocationLayout;
import com.shashiwang.shashiapp.dialog.ChooseBottomDialog;
import com.shashiwang.shashiapp.presenter.PostPresenter;
import com.shashiwang.shashiapp.view.PostDataView;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;

public class PostCarMessageActivity extends BaseTopBarActivity<PostPresenter> implements PostDataView {

    @BindView(R.id.ed_brand)
    PostEditLayout edBrand;
    @BindView(R.id.ed_mileage)
    PostEditLayout edMileage;
    @BindView(R.id.ed_price)
    PostEditLayout edPrice;
    @BindView(R.id.ed_people)
    PostEditLayout edPeople;
    @BindView(R.id.ed_phone)
    PostEditLayout edPhone;
    @BindView(R.id.ed_message)
    PostEditPlusLayout message;
    @BindView(R.id.ch_type)
    PostChooseLayout chType;
    @BindView(R.id.ch_create_year)
    PostChooseLayout chCreateYear;
    @BindView(R.id.bt_send)
    Button btSend;

    @Override
    protected PostPresenter setPresenter() {
        return new PostPresenter(this,this);
    }

    @Override
    protected int getFrameContentView() {
        return R.layout.activity_post_car_message;
    }

    @Override
    protected void initFrame(Bundle savedInstanceState) {
        setTitle("石料厂");
        initEvent();
    }

    private void initEvent() {
        chType.setOnClickListener(view -> {
            ChooseBottomDialog dialog = new ChooseBottomDialog(PostCarMessageActivity.this,"选择车辆",R.array.car_type);
            dialog.setOnChooseListener(str -> chType.setContantText(str));
            dialog.show();
        });

        chCreateYear.setOnClickListener(view -> {
            ChooseBottomDialog dialog = new ChooseBottomDialog(PostCarMessageActivity.this,"选择年份",R.array.create_year);
            dialog.setOnChooseListener(str -> chCreateYear.setContantText(str));
            dialog.show();
        });

        btSend.setOnClickListener(view -> postData());
    }

    private void postData() {
        Map<String,String> map = new HashMap<>();



        presenter.postData(map);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (Constant.RESULT_SUCCESS == resultCode){

        }
    }

    @Override
    public void showProgress() {

    }

    @Override
    public void dismissProgress() {

    }

    @Override
    public void loadDataSuccess(Object data) {

    }

    @Override
    public void errorMessage(String throwable) {

    }
}
