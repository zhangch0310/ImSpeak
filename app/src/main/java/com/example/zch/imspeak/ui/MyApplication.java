package com.example.zch.imspeak.ui;

import com.example.zch.imspeak.base.BaseApplication;

/**
 * Created by Administrator on 2015/7/14.
 */
public class MyApplication extends BaseApplication {

    private MyApplication mApp;
    @Override
    public void onCreate() {
        super.onCreate();
        this.mApp = this;

    }

    @Override
    public String getUnKnowExceptionPath() {
        return null;
    }

    @Override
    public boolean isOpenUnKnowException() {
        return false;
    }

    @Override
    public void happenedException() {

    }
}
