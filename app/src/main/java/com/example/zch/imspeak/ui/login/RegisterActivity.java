package com.example.zch.imspeak.ui.login;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.zch.imspeak.R;
import com.example.zch.imspeak.base.BaseActivity;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;

public class RegisterActivity extends BaseActivity implements View.OnClickListener{

    @ViewInject(R.id.register_et_name)
    private EditText mEtName;
    @ViewInject(R.id.register_et_Pwd)
    private EditText mEtPwd;
    @ViewInject(R.id.register_et_againPwd)
    private EditText mEtAgainPwd;
    @ViewInject(R.id.register_btn_ok)
    private Button mBtnOk;

    @Override
    public int getlayoutResID() {
        return R.layout.activity_register;
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ViewUtils.inject(this);
        init();
    }

    private void init() {

        mBtnOk.setOnClickListener(this);
    }

    @Override
    protected void initHeadView(LinearLayout ll_headview, TextView tv_left, TextView tv_title, TextView tv_right) {
        super.initHeadView(ll_headview, tv_left, tv_title, tv_right);
        tv_title.setText("用户注册");
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.register_btn_ok:
                //注册成功,执行注册逻辑
                show("注册成功了");
                break;
        }
    }
}
