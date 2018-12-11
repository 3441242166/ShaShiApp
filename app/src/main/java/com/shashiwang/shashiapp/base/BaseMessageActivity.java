package com.shashiwang.shashiapp.base;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.shashiwang.shashiapp.R;

public abstract class BaseMessageActivity<T extends BasePresenter> extends BaseTopBarActivity<T> {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_base_message);
    }
}
