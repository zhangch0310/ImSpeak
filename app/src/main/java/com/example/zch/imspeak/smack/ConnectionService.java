package com.example.zch.imspeak.smack;

import org.jivesoftware.smack.ConnectionConfiguration;
import org.jivesoftware.smack.SmackException;
import org.jivesoftware.smack.XMPPException;
import org.jivesoftware.smack.tcp.XMPPTCPConnection;
import org.jivesoftware.smack.tcp.XMPPTCPConnectionConfiguration;

import java.io.IOException;

/**
 * Created by ZCH on 15/7/16.
 * 获取链接服务
 */
public class ConnectionService {
    private static final String domian = "127.0.0.1";//im.littledonkey.com  127.0.0.1
    private static final int port = 5222;
    private static final int timeout = 3000;
    private static XMPPTCPConnection abstractXMPPConnection = null;

    /* 获取connection链接*/
    private static XMPPTCPConnection openConnection() throws SmackException, XMPPException, IOException {
        XMPPTCPConnectionConfiguration.Builder connConfig = XMPPTCPConnectionConfiguration.builder();
        connConfig.setSecurityMode(ConnectionConfiguration.SecurityMode.disabled);//不适用安全模式
        //connConfig.setUsernameAndPassword("usera","123456");
        connConfig.setServiceName(domian);
        connConfig.setHost(domian);
        connConfig.setPort(port);
        connConfig.setConnectTimeout(timeout);
        System.out.println("------一:ConnectionConfiguration配置完成------");
        XMPPTCPConnection connection = new XMPPTCPConnection(connConfig.build());
        connection.connect();
        System.out.println("------二:和服务器建立链接完成------:" + connection.isConnected());
        return connection;

    }

    /**
     * 获取XMPPConnection链接
     */
    public static XMPPTCPConnection getConnectionInstance() throws SmackException, XMPPException, IOException {
        if (abstractXMPPConnection == null) {
            synchronized (ConnectionService.class){
                abstractXMPPConnection = openConnection();
            }
        }
        return abstractXMPPConnection;
    }
}
