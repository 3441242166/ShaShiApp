package com.shashiwang.shashiapp.fragment;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.baidu.tts.auth.AuthInfo;
import com.baidu.tts.chainofresponsibility.logger.LoggerProxy;
import com.baidu.tts.client.SpeechError;
import com.baidu.tts.client.SpeechSynthesizer;
import com.baidu.tts.client.SpeechSynthesizerListener;
import com.baidu.tts.client.TtsMode;
import com.example.util.SharedPreferencesHelper;
import com.shashiwang.shashiapp.R;
import com.shashiwang.shashiapp.activity.FeedbackActivity;
import com.shashiwang.shashiapp.activity.MainActivity;
import com.shashiwang.shashiapp.activity.SettingActivity;
import com.shashiwang.shashiapp.activity.UserMessageActivity;
import com.shashiwang.shashiapp.activity.post.PostListActivity;
import com.shashiwang.shashiapp.adapter.TextAdapter;
import com.shashiwang.shashiapp.activity.LoginActivity;
import com.shashiwang.shashiapp.base.LazyLoadFragment;
import com.shashiwang.shashiapp.constant.Constant;
import com.shashiwang.shashiapp.presenter.MyFragmentPresenter;
import com.shashiwang.shashiapp.util.DividerItemDecoration;
import com.shashiwang.shashiapp.view.IMyFragmentView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import de.hdodenhof.circleimageview.CircleImageView;
import es.dmoral.toasty.Toasty;

import static com.shashiwang.shashiapp.constant.Constant.REQUEST_LOGIN;
import static com.shashiwang.shashiapp.constant.Constant.REQUEST_SETTING;
import static com.shashiwang.shashiapp.constant.Constant.REQUEST_USER_MESSAGE;
import static com.shashiwang.shashiapp.constant.Constant.TOKEN;
import static com.shashiwang.shashiapp.constant.MessageType.POST;
import static com.shashiwang.shashiapp.constant.MessageType.POST_TITLE;

public class MyFragment extends LazyLoadFragment<MyFragmentPresenter> implements IMyFragmentView{
    private static final String TAG = "MyFragment";


    @BindView(R.id.iv_head)
    CircleImageView ivHead;
    @BindView(R.id.tv_name)
    TextView tvName;
    @BindView(R.id.bt_login)
    Button btLogin;
    @BindView(R.id.rv_list)
    RecyclerView recyclerView;
    @BindView(R.id.iv_setting)
    ImageView ivSetting;

    private TextAdapter adapter;

    private static final String[] TITLE = {"我的发布", "推荐有奖", "意见反馈", "发布信息"};
    private static final int[] IMG = {R.drawable.ic_my_1,R.drawable.ic_my_2,
            R.drawable.ic_my_3, R.drawable.ic_my_4};
    private static final Class[] CLASSES = {PostListActivity.class,MainActivity.class,FeedbackActivity.class, MainActivity.class};

    @Override
    protected MyFragmentPresenter setPresenter() {
        return new MyFragmentPresenter(this,getContext());
    }

    @Override
    protected int setContentView() {
        return R.layout.fragment_my;
    }

    @Override
    protected void init() {
        initView();
        initEvent();
    }

    private void initEvent() {
        adapter.setOnItemClickListener((adapter, view, position) -> {
            final Intent intent = new Intent(getContext(),CLASSES[position]);

            if(position == 0){
                intent.putExtra(Constant.TYPE,POST);
                intent.putExtra(Constant.TITLE,POST_TITLE);
                intent.putExtra(Constant.CLASS,PostListActivity.class);
            }

            startActivity(intent);
        });

        ivSetting.setOnClickListener(view -> {
            startActivityForResult(new Intent(getContext(), SettingActivity.class), REQUEST_SETTING);
        });

        ivHead.setOnClickListener(view -> {
//            if(!TextUtils.isEmpty((String)SharedPreferencesHelper.getSharedPreference(TOKEN,""))){
//                startActivityForResult(new Intent(getContext(), UserMessageActivity.class), REQUEST_USER_MESSAGE);
//            }else {
//                Toasty.info(getContext(),"请先登陆");
//            }
            test("你好啊 你叫什么名字?");
        });
    }

    private void test(String str){

        SpeechSynthesizer mSpeechSynthesizer = SpeechSynthesizer.getInstance();
        mSpeechSynthesizer.setParam(SpeechSynthesizer.PARAM_AUDIO_ENCODE, SpeechSynthesizer.AUDIO_ENCODE_PCM);
        mSpeechSynthesizer.setParam(SpeechSynthesizer.PARAM_AUDIO_RATE, SpeechSynthesizer.AUDIO_BITRATE_PCM);
        mSpeechSynthesizer.setContext(getContext());
        mSpeechSynthesizer.setSpeechSynthesizerListener(new SpeechSynthesizerListener() {
            @Override
            public void onSynthesizeStart(String s) {
                Log.i(TAG, "onSynthesizeStart: ");
            }

            @Override
            public void onSynthesizeDataArrived(String s, byte[] bytes, int i) {
                Log.i(TAG, "onSynthesizeDataArrived: ");
            }

            @Override
            public void onSynthesizeFinish(String s) {
                Log.i(TAG, "onSynthesizeFinish: ");
            }

            @Override
            public void onSpeechStart(String s) {
                Log.i(TAG, "onSpeechStart: ");
            }

            @Override
            public void onSpeechProgressChanged(String s, int i) {
                Log.i(TAG, "onSpeechProgressChanged: ");
            }

            @Override
            public void onSpeechFinish(String s) {
                Log.i(TAG, "onSpeechFinish: ");
            }

            @Override
            public void onError(String s, SpeechError speechError) {
                Log.i(TAG, "onError: "+ s +"\n"+speechError);
            }
        });

        //mSpeechSynthesizer.setAppId("15221121");
        mSpeechSynthesizer.setApiKey("vTLeIRab50P12ZP71vlK6GZp",
                "dPnoKK8jM7lgR1I3wf7K6ljrqn503guI");

        mSpeechSynthesizer.setParam(SpeechSynthesizer.PARAM_SPEAKER ,"3");
        mSpeechSynthesizer.initTts(TtsMode.ONLINE);

        mSpeechSynthesizer.speak(str);
    }

    private void initView() {
        List<TextAdapter.TextBean> list = new ArrayList<>(TITLE.length);
        for(int x=0;x<TITLE.length;x++){
            list.add(new TextAdapter.TextBean(IMG[x],TITLE[x],false));
        }
        adapter = new TextAdapter(list);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.addItemDecoration(new DividerItemDecoration());
        recyclerView.setAdapter(adapter);
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
    public void unLogin(boolean is) {
        if(is){
            btLogin.setVisibility(View.VISIBLE);
            tvName.setVisibility(View.GONE);
            btLogin.setOnClickListener(view -> startActivityForResult(new Intent(getContext(), LoginActivity.class),REQUEST_LOGIN));
        }else {
            btLogin.setVisibility(View.GONE);
            tvName.setVisibility(View.VISIBLE);
            presenter.getUserMessage();
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.i(TAG, "onActivityResult: requestCode = "+ requestCode + "  resultCode = "+ resultCode);
        presenter.checkLogin();
    }
}
