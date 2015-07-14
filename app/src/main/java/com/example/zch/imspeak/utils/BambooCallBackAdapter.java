package com.example.zch.imspeak.utils;

import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.widget.Toast;

import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;

public abstract class BambooCallBackAdapter extends RequestCallBack<String> {

	private Context context;

	public BambooCallBackAdapter(Context context) {
		this.context = context;
	}

//	@Override
//	public void onSuccess(ResponseInfo<String> arg0) {
//		String result = arg0.result;
//		LogUtils.i("服务器数据"+ result);
//		try {
//			if(result!=null){
//				JSONObject object = new JSONObject(result);
//				int code = object.getInt("result");
//				if(code == 0){
//					object.isNull(name);
//					onSuccessResult(object.getString("data"));
//				}
//			}
//		} catch (JSONException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//	}
	@Override
	public void onFailure(HttpException arg0, String result) {
		Toast.makeText(context, "网络繁忙,请稍后再试...", 0).show();
		LogUtils.e("访问网络错误Log:"+result);
	}
	
//	public abstract void onSuccessResult(String result);
}
