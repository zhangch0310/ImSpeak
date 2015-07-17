package com.example.zch.imspeak.smack;

import org.jivesoftware.smack.SmackException;
import org.jivesoftware.smack.XMPPException;
import org.jivesoftware.smack.iqrequest.IQRequestHandler;
import org.jivesoftware.smack.packet.IQ;
import org.jivesoftware.smack.tcp.XMPPTCPConnection;

import java.io.IOException;

/**
 * Created by ZCH on 15/7/16.
 * 用户服务
 */
public class UserService {
    private static XMPPTCPConnection connection;

   static {
        try {
            connection = ConnectionService.getConnectionInstance();
        } catch (SmackException | XMPPException | IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 用户登录
     *
     * @param userName 用户名
     * @param password 密码
     */
    public static void login(String userName, String password) throws IOException, XMPPException, SmackException {
        connection.login(userName, password);
        System.out.println("------三:" + userName + "成功登录------");
    }

    /**
     * 用户注销
     */
    public void logout() {
        if (connection != null) {
            connection.disconnect();
        }
        System.out.println("------用户注销------");
    }

    public static void main(String[] args) {
        try {
            login("usera", "123456");
        } catch (IOException e) {
            e.printStackTrace();
        } catch (XMPPException e) {
            e.printStackTrace();
        } catch (SmackException e) {
            e.printStackTrace();
        }
    }

    /**
     * 用户注册
     */
    public void register(String username, String password) {
        //todo 暂时未实现
        connection.registerIQRequestHandler(new IQRequestHandler() {
            @Override
            public IQ handleIQRequest(IQ iq) {
                return null;
            }

            @Override
            public Mode getMode() {
                return null;
            }

            @Override
            public IQ.Type getType() {
                return null;
            }

            @Override
            public String getElement() {
                return null;
            }

            @Override
            public String getNamespace() {
                return null;
            }
        });
    }
}
