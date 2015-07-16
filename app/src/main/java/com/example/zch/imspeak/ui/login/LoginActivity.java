package com.example.zch.imspeak.ui.login;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.zch.imspeak.R;
import com.example.zch.imspeak.base.BaseActivity;
import com.example.zch.imspeak.ui.MainActivity;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;

public class LoginActivity extends BaseActivity implements View.OnClickListener{

    @ViewInject(R.id.login_et_name)
    private EditText mEtName;
    @ViewInject(R.id.login_et_pwd)
    private EditText mEtPwd;
    @ViewInject(R.id.login_btn_ok)
    private Button mBtnOk;
    @ViewInject(R.id.login_tv_register)
    private TextView mTvRegister;
    @ViewInject(R.id.login_tv_forgetPwd)
    private TextView mTvForgetPwd;

    @Override
    public int getlayoutResID() {
        return R.layout.activity_login;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ViewUtils.inject(this);

        init();

    }

    @Override
    protected void initHeadView(LinearLayout ll_headview, TextView tv_left, TextView tv_title, TextView tv_right) {
        super.initHeadView(ll_headview, tv_left, tv_title, tv_right);
        tv_title.setText("登录");
    }

    private void init() {
        mBtnOk.setOnClickListener(this);
        mTvRegister.setOnClickListener(this);
        mTvForgetPwd.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.login_btn_ok:
                toActivity(MainActivity.class);
                break;
            case R.id.login_tv_register:
                toActivity(RegisterActivity.class);
                break;
        }
    }
}
