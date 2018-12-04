package com.shashiwang.shashiapp.activity.post;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.Button;

import com.shashiwang.shashiapp.activity.LocationTopBarActivity;
import com.shashiwang.shashiapp.base.BaseTopBarActivity;
import com.shashiwang.shashiapp.constant.Constant;
import com.shashiwang.shashiapp.dialog.ChooseBottomDialog;
import com.shashiwang.shashiapp.customizeview.PostEditLayout;
import com.shashiwang.shashiapp.customizeview.PostEditPlusLayout;
import com.shashiwang.shashiapp.customizeview.PostLocationLayout;
import com.shashiwang.shashiapp.presenter.IssueActivityPresenter;
import com.shashiwang.shashiapp.view.IIssueActivityView;
import com.shashiwang.shashiapp.R;

import butterknife.BindView;

public class PostStoneFactoryTopBarActivity extends BaseTopBarActivity<IssueActivityPresenter> implements IIssueActivityView {
    private static final String TAG = "PostStoneFactoryTopBarActivity";

    @BindView(R.id.ed_title)
    PostEditLayout title;
    @BindView(R.id.ed_price)
    PostEditPlusLayout price;
    @BindView(R.id.ed_location)
    PostLocationLayout location;
    @BindView(R.id.ed_people)
    PostEditLayout people;
    @BindView(R.id.ed_phone)
    PostEditLayout phone;
    @BindView(R.id.ed_message)
    PostEditPlusLayout message;
    @BindView(R.id.bt_send)
    Button send;

    @Override
    protected IssueActivityPresenter setPresenter() {
        return new IssueActivityPresenter(this,this);
    }

    @Override
    protected int getFrameContentView() {
        return R.layout.activity_post_stone_factory;
    }

    @Override
    protected void initFrame(Bundle savedInstanceState) {
        setTitle("石料厂");
        initEvent();
    }

    private void initEvent() {
        location.setOnClickListener(view -> {
            startActivityForResult(new Intent(PostStoneFactoryTopBarActivity.this, LocationTopBarActivity.class),1);
        });
        send.setOnClickListener(view -> new ChooseBottomDialog(PostStoneFactoryTopBarActivity.this,"xxx",R.array.car_type)
                .show());
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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == Constant.RESULT_SUCCESS && data!=null){
            location.setContantText(data.getStringExtra(Constant.RESULT_DATA));
        }
    }
}
