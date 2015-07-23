package com.example.zch.imspeak.service;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.preference.PreferenceManager;
import android.util.Log;

import org.jivesoftware.smack.ConnectionListener;
import org.jivesoftware.smack.SmackException;
import org.jivesoftware.smack.XMPPConnection;
import org.jivesoftware.smack.XMPPException;
import org.jivesoftware.smack.chat.Chat;
import org.jivesoftware.smack.chat.ChatManager;
import org.jivesoftware.smack.chat.ChatManagerListener;
import org.jivesoftware.smack.chat.ChatMessageListener;
import org.jivesoftware.smack.packet.Message;
import org.jivesoftware.smack.packet.Presence;
import org.jivesoftware.smack.roster.RosterListener;
import org.jivesoftware.smack.tcp.XMPPTCPConnection;
import org.jivesoftware.smack.tcp.XMPPTCPConnectionConfiguration;
import org.jivesoftware.smackx.ping.PingFailedListener;
import org.jivesoftware.smackx.ping.PingManager;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Created by ZCH on 15/7/17.
 */
public class SmackConnection implements ConnectionListener, ChatManagerListener, RosterListener, ChatMessageListener, PingFailedListener {

    public static enum ConnectionState {
        CONNECTED, CONNECTING, RECONNECTING, DISCONNECTED;
    }

    private static final String TAG = "ImSpeak";
    private final Context applicationContext;
    private final String password;
    private final String userName;
    private final String serviceName = "im.littledonkey.com";

    private XMPPTCPConnection xmpptcpConnection;
    private List<String> roster;
    private BroadcastReceiver broadcastReceiver;

    public SmackConnection(Context context) {
        applicationContext = context.getApplicationContext();
        password = PreferenceManager.getDefaultSharedPreferences(context).getString("xmppPassword", null);
        userName = PreferenceManager.getDefaultSharedPreferences(context).getString("xmppUserName", null);
    }

    public void connectServer() throws IOException, XMPPException, SmackException {
        Log.i(TAG, ">>>>>>>>>>>>>>>>Get connected()<<<<<<<<<<<<<<<");
        XMPPTCPConnectionConfiguration.Builder builder = XMPPTCPConnectionConfiguration.builder();
        builder.setServiceName(serviceName);
        builder.setHost(serviceName);
        builder.setPort(5222);
        builder.setUsernameAndPassword(userName, password);
        builder.setConnectTimeout(3000);

        xmpptcpConnection = new XMPPTCPConnection(builder.build());
        xmpptcpConnection.addConnectionListener(this);
        xmpptcpConnection.connect();
        xmpptcpConnection.login();

        PingManager.setDefaultPingInterval(600);
        PingManager pingManager = PingManager.getInstanceFor(xmpptcpConnection);
        pingManager.registerPingFailedListener(this);
        setupSendMessageReceiver();
        ChatManager.getInstanceFor(xmpptcpConnection).addChatListener(this);
    }

    //todo 逻辑需要在分析
    private void setupSendMessageReceiver() {
        broadcastReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                String action = intent.getAction();
                if (action.equals(SmackService.SEND_MESSAGE)) {
                    sendMessage(intent.getStringExtra(SmackService.BUNDLE_MESSAGE_BODY), intent.getStringExtra(SmackService.BUNDLE_TO));
                }
            }
        };
        IntentFilter filter = new IntentFilter();
        filter.addAction(SmackService.SEND_MESSAGE);
        applicationContext.registerReceiver(broadcastReceiver, filter);
    }

    private void sendMessage(String messageBody,String toJid){
        Log.i(TAG,"sendMessage()");
        Chat chat = ChatManager.getInstanceFor(xmpptcpConnection).createChat(toJid, this);
        try{
            chat.sendMessage(messageBody);
        }catch (SmackException.NotConnectedException e){
            e.printStackTrace();
        }
    }


    @Override
    public void chatCreated(Chat chat, boolean createdLocally) {
        Log.i(TAG, "chatCreated()");
        chat.addMessageListener(this);
    }

    @Override
    public void processMessage(Chat chat, Message message) {
        Log.i(TAG, "processMessage()");
        if (message.getType().equals(Message.Type.chat)
                || message.getType().equals(Message.Type.normal)){
            if (message.getBody() != null) {
                Intent intent = new Intent();
            }
        }

    }

    @Override
    public void connected(XMPPConnection connection) {
        SmackService.connectionState = ConnectionState.CONNECTED;
        Log.i(TAG, "connected()");
    }

    public void disconnect() throws SmackException.NotConnectedException {
        Log.i(TAG, "disconnect()");
        if (xmpptcpConnection.isConnected()) {
            xmpptcpConnection.disconnect();
        }
        if (broadcastReceiver != null) {
            applicationContext.unregisterReceiver(broadcastReceiver);
            broadcastReceiver = null;
        }
    }

    private void rebuildRoster() {
        roster = new ArrayList<>();
        String status;
        //for (RosterEntry entry :)
    }

    @Override
    public void authenticated(XMPPConnection connection, boolean resumed) {

    }

    @Override
    public void connectionClosed() {

    }

    @Override
    public void connectionClosedOnError(Exception e) {

    }

    @Override
    public void reconnectionSuccessful() {

    }

    @Override
    public void reconnectingIn(int seconds) {

    }

    @Override
    public void reconnectionFailed(Exception e) {

    }

    @Override
    public void pingFailed() {

    }

    @Override
    public void entriesAdded(Collection<String> addresses) {

    }

    @Override
    public void entriesUpdated(Collection<String> addresses) {

    }

    @Override
    public void entriesDeleted(Collection<String> addresses) {

    }

    @Override
    public void presenceChanged(Presence presence) {

    }
}
