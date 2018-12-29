package com.shashiwang.shashiapp.activity.post;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.Button;
import android.widget.Toast;

import com.afollestad.materialdialogs.MaterialDialog;
import com.shashiwang.shashiapp.R;
import com.shashiwang.shashiapp.adapter.PhotoAdapter;
import com.shashiwang.shashiapp.base.BaseTopBarActivity;
import com.shashiwang.shashiapp.constant.Constant;
import com.shashiwang.shashiapp.customizeview.PostChooseLayout;
import com.shashiwang.shashiapp.customizeview.PostEditLayout;
import com.shashiwang.shashiapp.customizeview.PostEditPlusLayout;
import com.shashiwang.shashiapp.dialog.ChooseBottomDialog;
import com.shashiwang.shashiapp.presenter.PostCarPresenter;
import com.shashiwang.shashiapp.util.FileUtil;
import com.shashiwang.shashiapp.view.IPostCarView;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import butterknife.BindView;

import static com.shashiwang.shashiapp.util.FileUtil.getRealPathFromURI;

public class PostCarActivity extends BaseTopBarActivity<PostCarPresenter> implements IPostCarView {
    private static final String TAG = "PostCarActivity";

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
    @BindView(R.id.ch_type)
    PostChooseLayout chType;
    @BindView(R.id.ed_create_year)
    PostEditLayout edCreateYear;
    @BindView(R.id.ed_message)
    PostEditPlusLayout edMessage;
    @BindView(R.id.rv_photos)
    RecyclerView rvPhotos;
    @BindView(R.id.bt_send)
    Button btSend;

    private Map<String,Integer> data;
    private PhotoAdapter adapter;
    private LinkedList<PhotoAdapter.PhotoBean> photoList;
    private MaterialDialog dialog;

    @Override
    protected PostCarPresenter setPresenter() {
        return new PostCarPresenter(this,this);
    }

    @Override
    protected int getFrameContentView() {
        return R.layout.activity_post_car_message;
    }

    @Override
    protected void initFrame(Bundle savedInstanceState) {
        setTitle("出售车辆");
        data = FileUtil.getJsonFormAssets(this,"carType.json");
        initView();
        initEvent();
    }

    private void initView() {
        adapter = new PhotoAdapter(null,true);
        rvPhotos.setLayoutManager(new GridLayoutManager(this,3));
        rvPhotos.setAdapter(adapter);
        photoList = new LinkedList<>();
        photoList.add(new PhotoAdapter.PhotoBean("",PhotoAdapter.PhotoBean.ADD_PHOTO));
        adapter.setNewData(photoList);

        dialog = new MaterialDialog.Builder(this)
                .title("Zzz...")
                .content("上传中...")
                .cancelable(false)
                .progress(true,100,false)
                .build();
    }

    private void initEvent() {
        List<String> list = new ArrayList<>();
        for( String key :data.keySet()){
            list.add(key);
        }

        chType.setOnClickListener(view -> {
            ChooseBottomDialog dialog = new ChooseBottomDialog(PostCarActivity.this,"选择车辆",list);
            dialog.setOnChooseListener((str,i) -> chType.setContantText(str));
            dialog.show();
        });

        adapter.setOnItemChildClickListener((adapter, view, position) -> {
            switch (view.getId()){
                case R.id.iv_delete:
                    if(photoList.getLast().getItemType() != PhotoAdapter.PhotoBean.ADD_PHOTO){
                        photoList.addLast(new PhotoAdapter.PhotoBean("",PhotoAdapter.PhotoBean.ADD_PHOTO));
                    }
                    photoList.remove(position);
                    adapter.notifyDataSetChanged();
                    break;
                case R.id.iv_add:
                    Intent albumIntent = new Intent(Intent.ACTION_PICK);
                    albumIntent.setData(MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                    startActivityForResult(albumIntent, Constant.REQUEST_GALLERY);
                    break;
            }
        });

        adapter.setOnItemClickListener((adapter, view, position) -> {

        });

        btSend.setOnClickListener(view -> postData());
    }

    private void postData() {
        int carType = data.get(chType.getContantText());
        List<String> list = new ArrayList<>(photoList.size()-1);
        for(int x=0;x<photoList.size()-1;x++){
            list.add(photoList.get(x).url);
        }
        presenter.postData(edBrand.getContantText(),carType,edCreateYear.getContantText(),edMileage.getContantText(),edPrice.getContantText()
                ,edPeople.getContantText(),edPhone.getContantText(),edMessage.getContantText(),list);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == Constant.REQUEST_GALLERY){
            if (data == null) {
                return;
            } else {
                //获取到用户所选图片的Uri
                Uri uri = data.getData();
                String path = getRealPathFromURI(this,uri);
                Log.i(TAG, "onActivityResult: path = " + path);
                photoList.addFirst(new PhotoAdapter.PhotoBean(path));

                if(photoList.size() == 7){
                    photoList.removeLast();
                }

                adapter.notifyDataSetChanged();
            }
        }
    }

    @Override
    public void showProgress() {
        dialog.show();
    }

    @Override
    public void dismissProgress() {
        dialog.dismiss();
    }

    @Override
    public void loadDataSuccess(Object data) {
        Toast.makeText(this,"发布成功",Toast.LENGTH_SHORT).show();
        finish();
    }

    @Override
    public void errorMessage(String throwable) {

    }
}
