package com.shashiwang.shashiapp.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;

import com.shashiwang.shashiapp.R;
import com.shashiwang.shashiapp.base.BasePresenter;
import com.shashiwang.shashiapp.base.LazyLoadFragment;

public class MessageListFragment extends LazyLoadFragment {


    public static MessageListFragment newInstance(String content) {
        Bundle args = new Bundle();
        args.putString("content", content);
        MessageListFragment fragment = new MessageListFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected BasePresenter setPresenter() {
        return null;
    }

    @Override
    protected int setContentView() {
        return R.layout.activity_message_list;
    }

    @Override
    protected void init() {

    }
}
