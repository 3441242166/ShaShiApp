package com.shashiwang.shashiapp.activity.post;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;

import com.shashiwang.shashiapp.R;
import com.shashiwang.shashiapp.activity.LocationTopBarActivity;
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

public class PostCostActivity extends BaseTopBarActivity<PostPresenter> implements PostDataView {

    @BindView(R.id.ed_start_location)
    PostLocationLayout edStart;
    @BindView(R.id.ed_end_location)
    PostLocationLayout edEnd;

    @BindView(R.id.ed_mileage)
    PostEditLayout edMileage;
    @BindView(R.id.ed_name)
    PostEditLayout edName;
    @BindView(R.id.ed_price)
    PostEditLayout edPrice;

    @BindView(R.id.ed_message)
    PostEditPlusLayout edMessage;

    @BindView(R.id.ch_car)
    PostChooseLayout chCar;
    @BindView(R.id.bt_send)
    Button btSend;


    @Override
    protected PostPresenter setPresenter() {
        return new PostPresenter(this,this);
    }


    @Override
    protected int getFrameContentView() {
        return R.layout.activity_post_cost;
    }

    @Override
    protected void initFrame(Bundle savedInstanceState) {
        setTitle("发布运费信息");
        initEvent();
    }

    private void initEvent() {
        edStart.setOnClickListener(view -> startActivityForResult(new Intent(PostCostActivity.this, LocationTopBarActivity.class),1));
        edEnd.setOnClickListener(view -> startActivityForResult(new Intent(PostCostActivity.this, LocationTopBarActivity.class),2));

        chCar.setOnClickListener(view -> {
            ChooseBottomDialog dialog = new ChooseBottomDialog(PostCostActivity.this,"选择车辆类型",R.array.car_type);
            dialog.setOnChooseListener(str -> chCar.setContantText(str));
            dialog.show();
        });
        btSend.setOnClickListener(view -> postData());
    }

    private void postData() {
        Map<String,String> map = new HashMap<>();



        presenter.postData(map);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode == Constant.RESULT_SUCCESS && data!=null){
            switch (requestCode){
                case 1:
                    edStart.setContantText(data.getStringExtra(Constant.RESULT_DATA));
                    break;
                case 2:
                    edEnd.setContantText(data.getStringExtra(Constant.RESULT_DATA));
                    break;
            }
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
