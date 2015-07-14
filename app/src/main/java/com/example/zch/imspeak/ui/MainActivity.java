package com.example.zch.imspeak.ui;

import android.os.Bundle;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.zch.imspeak.R;
import com.example.zch.imspeak.base.BaseActivity;


public class MainActivity extends BaseActivity {

    @Override
    public int getlayoutResID() {
        return R.layout.activity_main;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    protected void initHeadView(LinearLayout ll_headview, TextView tv_left, TextView tv_title, TextView tv_right) {
        super.initHeadView(ll_headview, tv_left, tv_title, tv_right);
        tv_title.setText("主界面");
    }
}
