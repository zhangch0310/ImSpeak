
package com.example.zch.imspeak.ui.login;

import android.os.Bundle;
import android.util.Log;
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

import org.jivesoftware.smack.SmackException;
import org.jivesoftware.smack.XMPPException;
import org.jivesoftware.smack.tcp.XMPPTCPConnection;
import org.jivesoftware.smack.tcp.XMPPTCPConnectionConfiguration;

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

    private final String serviceName = "im.littledonkey.com";
    private static final String TAG = "ImSpeak";

    public int getlayoutResID() {
        return R.layout.activity_login;
    }

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ViewUtils.inject(this);
        init();

    }

    protected void initHeadView(LinearLayout ll_headview, TextView tv_left, TextView tv_title, TextView tv_right) {
        super.initHeadView(ll_headview, tv_left, tv_title, tv_right);
        tv_title.setText("登录");
    }

    private void init() {
        mBtnOk.setOnClickListener(this);
        mTvRegister.setOnClickListener(this);
        mTvForgetPwd.setOnClickListener(this);
    }

    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.login_btn_ok:
                new Thread(new Runnable() {
                    public void run() {
                        login(mEtName.getText().toString(), mEtPwd.getText().toString());
                    }
                });
                toActivity(MainActivity.class);
                break;
            case R.id.login_tv_register:
                toActivity(RegisterActivity.class);
                break;
        }
    }

    private void login(String userName, String password) {
        Log.i(TAG, ">>>>>>>>>>>>>>>>Get connected()<<<<<<<<<<<<<<<");
        XMPPTCPConnectionConfiguration.Builder builder = XMPPTCPConnectionConfiguration.builder();
        builder.setDebuggerEnabled(true);
        builder.setServiceName(serviceName);
        builder.setHost(serviceName);
        builder.setPort(5222);
        builder.setConnectTimeout(3000);
        XMPPTCPConnection xmpptcpConnection = new XMPPTCPConnection(builder.build());
        try {
            xmpptcpConnection.login(userName, password);
            xmpptcpConnection.connect();
        } catch (SmackException | IOException | XMPPException e) {
            e.printStackTrace();
        }

    }

}
