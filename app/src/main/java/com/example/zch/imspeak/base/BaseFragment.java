package com.example.zch.imspeak.base;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.zch.imspeak.R;
import com.example.zch.imspeak.utils.BambooCallBackAdapter;
import com.example.zch.imspeak.utils.HttpTools;
import com.example.zch.imspeak.utils.NetworkUtils;
import com.lidroid.xutils.http.RequestParams;
import com.lidroid.xutils.http.client.HttpRequest.HttpMethod;
/**
 * 
 * 项目名称:shanshice<br/>
 * 类名称:BaseFragment<br/>
 * 描述:所有fragment的基类
 *
 * @author:bamboo
 * @Date:2015-3-23下午4:52:29
 */
public abstract  class BaseFragment extends Fragment {
	
	private  InputMethodManager manager;
	public  TextView tv_left,tv_title,tv_right;
	public  LinearLayout ll_headView;
	private HttpTools http;
	private Context context;
	
	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);
		this.context = activity;
	}
	/**
	 * 隐藏软键盘
	 */
	protected void hideKeyboard() {
		if (getActivity().getWindow().getAttributes().softInputMode != WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN) {
			if (getActivity().getCurrentFocus() != null)
				manager.hideSoftInputFromWindow(getActivity().getCurrentFocus()
						.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
		}
	}
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		
		return onInflaterView(inflater);
	}
	
	
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		manager = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
		findHeadView();
		http = HttpTools.getInstance();
		initHeadView(ll_headView,tv_left,tv_title,tv_right);
	}

	/**
	 * 
	 * 功能:初始化头部
	 * @param ll_headView
	 * @param tv_left
	 * @param tv_title
	 * @param tv_right 
	 * @author: huchao
	 * @date:2015-5-14下午1:50:11
	 */
	protected void initHeadView(LinearLayout ll_headView, TextView tv_left, TextView tv_title, TextView tv_right) {
		if(tv_left!=null){
			tv_left.setVisibility(View.INVISIBLE);
		}
	}
	private void findHeadView() {
		ll_headView = (LinearLayout) findViewById(R.id.base_headview);
		tv_left = (TextView) findViewById(R.id.head_tv_left);
		tv_title = (TextView) findViewById(R.id.head_tv_title);
		tv_right = (TextView) findViewById(R.id.head_tv_right);
	}
	/**
	 * 
	 * 功能:activity 公共方法    
	 * @param id
	 * @return 
	 * @author: huchao
	 * @date:2015-5-14上午9:09:58
	 */
	public View findViewById(int id) {
		return getView().findViewById(id);
	}
	public abstract View onInflaterView(LayoutInflater inflater);
	
	/**fragment To activity**/
	public void toActivity(Activity activity,Class<?> toClsActivity){
		Intent intent = new Intent(activity,toClsActivity);
		startActivity(intent);
	}
	
	public void show(Context context,String msg){
		Toast.makeText(context,msg, Toast.LENGTH_SHORT).show();
	}
	
	/**
	 * 
	 * 功能:统一发送网络请求
	 * @param url
	 * @param params
	 * @param callBack 
	 * @author: huchao
	 * @date:2015-6-9上午9:54:20
	 */
	public void sendPost(String url,RequestParams params,BambooCallBackAdapter callBack){
		if(NetworkUtils.checkNetState(context)){
			http.send(HttpMethod.POST, url, params,callBack);
		}else {
			Toast.makeText(context, "当前网络不可用", Toast.LENGTH_LONG).show();
		}
	}
}
