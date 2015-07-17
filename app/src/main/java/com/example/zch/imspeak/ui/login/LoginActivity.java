
package com.example.zch.imspeak.ui.login;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.zch.imspeak.R;
import com.example.zch.imspeak.base.BaseActivity;
import com.example.zch.imspeak.service.SmackConnection;
import com.example.zch.imspeak.service.SmackService;
import com.example.zch.imspeak.smack.UserService;
import com.example.zch.imspeak.ui.MainActivity;
import com.example.zch.imspeak.utils.BambooCallBackAdapter;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.http.RequestParams;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.view.annotation.ViewInject;

import org.jivesoftware.smack.SmackException;
import org.jivesoftware.smack.XMPPException;

import java.io.IOException;

public class LoginActivity extends BaseActivity implements View.OnClickListener {

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

    private final String domain = "im.littledonkey.com";

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
        switch (v.getId()) {
            case R.id.login_btn_ok:
                this.login(mEtName.getText().toString()+domain, mEtPwd.getText().toString());
                toActivity(MainActivity.class);
                break;
            case R.id.login_tv_register:
                toActivity(RegisterActivity.class);
                break;
        }
    }

    private void login(String userName, String passwor) {
        if (!verifyJId(userName)) {
            return;
        }
        if (!SmackService.getState().equals(SmackConnection.ConnectionState.DISCONNECTED)) {
            SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
            preferences.edit()
                    .putString("xmppUserName", userName)
                    .putString("xmppPassword", passwor)
                    .commit();
            Intent intent = new Intent(this,SmackService.class);
            this.startService(intent);
        }else{
            Intent intent = new Intent(this,SmackService.class);
            this.stopService(intent);
        }

    }

    private static boolean verifyJId(String jid) {
        try {
            String parts[] = jid.split("@");
            if (parts.length != 2 || parts[0].length() == 0 || parts[1].length() == 0) {
                return false;
            }
        } catch (NullPointerException e) {
            return false;
        }
        return true;
    }

}
