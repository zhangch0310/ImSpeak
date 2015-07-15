package com.example.zch.imspeak.ui.login;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
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

    private void init() {
        mBtnOk.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.login_btn_ok:
                //填写登陆的代码,登陆成功去主Activity
                toActivity(MainActivity.class);
                break;
        }
    }
}
