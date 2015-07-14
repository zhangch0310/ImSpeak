package com.example.zch.imspeak.base;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.zch.imspeak.R;
import com.example.zch.imspeak.utils.AppManager;
import com.example.zch.imspeak.utils.BambooCallBackAdapter;
import com.example.zch.imspeak.utils.HttpTools;
import com.example.zch.imspeak.utils.NetworkUtils;
import com.lidroid.xutils.http.RequestParams;
import com.lidroid.xutils.http.client.HttpRequest.HttpMethod;

/**
 * 基类 activity
 * 
 * @author bamboo
 * 
 */
public abstract class BaseActivity extends FragmentActivity {
	protected AppManager mAppManager;
	private InputMethodManager manager;
	private TextView tv_left, tv_title, tv_right;
	private LinearLayout ll_headview;
	private Activity activity;
	private HttpTools http;

	/**
	 * 隐藏软键盘
	 */
	protected void hideKeyboard() {
		if (getWindow().getAttributes().softInputMode != WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN) {
			if (getCurrentFocus() != null)
				manager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
		}
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		mAppManager = AppManager.getInstance();
		mAppManager.addActivity(this);
		manager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
		setContentView(getlayoutResID());
		activity = this;
		http = HttpTools.getInstance();
		findHeadView();

		initHeadView(ll_headview, tv_left, tv_title, tv_right);
	}

	/**
	 * 
	 * 功能:初始化头布局
	 * 
	 * @param ll_headview
	 * @param tv_left
	 * @param tv_title
	 * @param tv_right
	 * @author: huchao
	 * @date:2015-5-13下午1:52:09
	 */
	protected void initHeadView(LinearLayout ll_headview, TextView tv_left, TextView tv_title, TextView tv_right) {
		if (tv_left != null) {
			tv_left.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					activity.finish();
				}
			});
		}
	}

	/**
	 * 
	 * 功能:获得资源的ID
	 * @return 
	 * @author: huchao
	 * @date:2015-5-15上午10:30:26
	 */
	public abstract int getlayoutResID();

	@Override
	protected void onDestroy() {
		super.onDestroy();
		mAppManager.finishActivity(this);
	}

	/**
	 * init views
	 */
	public void findHeadView() {
		tv_left = (TextView) findViewById(R.id.head_tv_left);
		tv_title = (TextView) findViewById(R.id.head_tv_title);
		tv_right = (TextView) findViewById(R.id.head_tv_right);
		ll_headview = (LinearLayout) findViewById(R.id.base_headview);
	}

	/**
	 * init data
	 */
	public void initData() {

	}

	public void toActivity(Class<?> toClsActivity) {
		toActivity(toClsActivity, null);
	}

	public void toActivity(Class<?> toClsActivity, Bundle bundle) {
		Intent intent = new Intent(this, toClsActivity);
		if (bundle != null) {
			intent.putExtras(bundle);
		}
		startActivity(intent);
	}

	public void show(String msg) {
		Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
	}

	public void show(int id) {
		Toast.makeText(this, id, Toast.LENGTH_SHORT).show();
	}

	public boolean isNetWork() {
		if (!NetworkUtils.checkNetState(this)) {
			Toast.makeText(this, R.string.str_check_network, Toast.LENGTH_SHORT).show();
			return false;
		}
		return true;
	}
	/**
	 * 
	 * 功能:发送网络求情
	 * @param url
	 * @param params
	 * @param callBack 
	 * @author: huchao
	 * @date:2015-6-9上午9:52:16
	 */
	public void sendPost(String url,RequestParams params,BambooCallBackAdapter callBack){
		if(isNetWork()){
			if(http==null){
				http = HttpTools.getInstance();
			}
			http.send(HttpMethod.POST, url, params,callBack);
		}
	}
}
