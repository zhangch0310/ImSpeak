package com.example.zch.imspeak.service;

import android.app.Service;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;
import android.os.Looper;

import org.jivesoftware.smack.SmackException;
import org.jivesoftware.smack.XMPPException;

import java.io.IOException;

/**
 * Created by ZCH on 15/7/17.
 */
public class SmackService extends Service {

    public static final String NEW_MESSAGE = "new message";
    public static final String SEND_MESSAGE = "send message";
    public static final String NEW_ROSTER = "new roster";

    public static final String BUNDLE_FROM_JID = "b_from";
    public static final String BUNDLE_MESSAGE_BODY = "b_body";
    public static final String BUNDLE_ROSTER = "b_body";
    public static final String BUNDLE_TO = "b_to";

    private boolean mActive;
    private Thread mThread;
    private Handler mTHandler;
    private SmackConnection smackConnection;

    public SmackService() {
    }

    public static SmackConnection.ConnectionState connectionState;

    public static SmackConnection.ConnectionState getState() {
        if (connectionState == null) {
            return SmackConnection.ConnectionState.DISCONNECTED;
        }
        return connectionState;
    }


    public void start() {
        if (!mActive) {
            mActive = true;
            if (mThread == null || !mThread.isAlive()) {
                mThread = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        Looper.prepare();
                        mTHandler = new Handler();
                        initConnection();
                        Looper.loop();
                    }
                });
                mThread.start();
            }
        }
    }

    public void stop() {
        mActive = false;
        mTHandler.post(new Runnable() {
            @Override
            public void run() {
                if (smackConnection != null) {
                    try {
                        smackConnection.disconnect();
                    } catch (SmackException.NotConnectedException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
    }

    /*初始化连接*/
    private void initConnection() {
        if (smackConnection == null) {
            smackConnection = new SmackConnection(this);
        }
        try {
            smackConnection.connectServer();
        } catch (IOException | XMPPException | SmackException e) {
            e.printStackTrace();
        }
    }


    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        this.start();
        return Service.START_STICKY;
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        this.stop();
    }
}
