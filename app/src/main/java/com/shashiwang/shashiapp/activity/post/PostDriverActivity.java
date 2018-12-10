package com.shashiwang.shashiapp.activity.post;

import com.shashiwang.shashiapp.R;
import com.shashiwang.shashiapp.base.BasePresenter;
import com.shashiwang.shashiapp.base.BaseTopBarActivity;
import com.shashiwang.shashiapp.customizeview.PostChooseLayout;
import com.shashiwang.shashiapp.customizeview.PostEditLayout;
import com.shashiwang.shashiapp.customizeview.PostEditPlusLayout;
import com.shashiwang.shashiapp.customizeview.PostLocationLayout;
import com.shashiwang.shashiapp.dialog.ChooseBottomDialog;
import com.shashiwang.shashiapp.presenter.PostPresenter;
import com.shashiwang.shashiapp.view.PostDataView;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;

public class PostDriverActivity extends BaseTopBarActivity<PostPresenter> implements PostDataView {
    @BindView(R.id.ed_salary)
    PostEditLayout edMileage;
    @BindView(R.id.ed_location)
    PostEditLayout edLocation;
    @BindView(R.id.ed_people)
    PostEditLayout edPeople;
    @BindView(R.id.ed_phone)
    PostEditLayout edPhone;

    @BindView(R.id.ed_work_message)
    PostEditPlusLayout edMessage;

    @BindView(R.id.ch_year)
    PostChooseLayout chYear;

    @BindView(R.id.bt_send)
    Button btSend;

    @Override
    protected PostPresenter setPresenter() {
        return new PostPresenter(this,this);
    }

    @Override
    protected int getFrameContentView() {
        return R.layout.activity_post_driver;
    }

    @Override
    protected void initFrame(Bundle savedInstanceState) {
        setTitle("发布车辆买卖信息");
        initEvent();
    }

    private void initEvent() {
        chYear.setOnClickListener(view -> {
            ChooseBottomDialog dialog = new ChooseBottomDialog(PostDriverActivity.this,"选择车辆类型",R.array.work_year);
            dialog.setOnChooseListener(str -> chYear.setContantText(str));
            dialog.show();
        });

        btSend.setOnClickListener(view -> postData());
    }

    private void postData() {
        Map<String,String> map = new HashMap<>();



        presenter.postData(map);
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
