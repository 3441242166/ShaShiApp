package com.shashiwang.shashiapp.activity.post;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;

import com.shashiwang.shashiapp.activity.LocationActivity;
import com.shashiwang.shashiapp.base.BaseTopBarActivity;
import com.shashiwang.shashiapp.constant.Constant;
import com.shashiwang.shashiapp.customizeview.PostEditLayout;
import com.shashiwang.shashiapp.customizeview.PostEditPlusLayout;
import com.shashiwang.shashiapp.customizeview.PostLocationLayout;
import com.shashiwang.shashiapp.presenter.PostPresenter;
import com.shashiwang.shashiapp.view.PostDataView;
import com.shashiwang.shashiapp.R;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;

public class PostStoneFactoryActivity extends BaseTopBarActivity<PostPresenter> implements PostDataView {
    private static final String TAG = "PostStoneFactoryActivity";

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
    Button btSend;

    @Override
    protected PostPresenter setPresenter() {
        return new PostPresenter(this,this);
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
            startActivityForResult(new Intent(PostStoneFactoryActivity.this, LocationActivity.class),1);
        });

        btSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                postData();
            }
        });
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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode == Constant.RESULT_SUCCESS && data!=null){
            location.setContantText(data.getStringExtra(Constant.RESULT_DATA));
        }
    }
}
