package com.example.zch.imspeak.base;

import android.app.Application;

import com.example.zch.imspeak.utils.DealUnKnowException;
import com.example.zch.imspeak.utils.LogUtils;

/**
 * 基类的application
 * 
 * @author bamboo
 * 
 */
public abstract class BaseApplication extends Application implements DealUnKnowException.ExceptionCallBack {
	protected DealUnKnowException mUnKnowException;
	protected boolean mBisOpen = true;
	

	@Override
	public void onCreate() {
		super.onCreate();
		if (mBisOpen) {
			init();
			mBisOpen = false;
		}
		
	}
	
	protected void init() {
		// 1、是否开启程序异常处理
		if (isOpenUnKnowException()) {
			mUnKnowException = DealUnKnowException.getInstance();
			mUnKnowException.setSaveLogPath(getUnKnowExceptionPath());
			mUnKnowException.init(this, this);
			LogUtils.setDebug(true);
		}
	}

	
	/**
	 * 需要在末尾+/
	 * 
	 * @return
	 */
	public abstract String getUnKnowExceptionPath();

	public abstract boolean isOpenUnKnowException();
}
