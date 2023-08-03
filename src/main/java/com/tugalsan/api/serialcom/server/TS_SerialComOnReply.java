package com.tugalsan.api.serialcom.server;

import com.tugalsan.api.runnable.client.TGS_RunnableType1;
import com.tugalsan.api.runnable.client.TGS_RunnableType2;
import com.tugalsan.api.thread.server.TS_ThreadKillTrigger;
import com.tugalsan.api.unsafe.client.TGS_UnSafe;

public class TS_SerialComOnReply {

    private TS_SerialComOnReply(TS_SerialComOnConnectError onConnectError, int defaultMessageBrokerMessageSize) {
        this.onConnectError = onConnectError;
        this.onReply_customMessageBroker = null;
        this.defaultMessageBrokerMessageSize = defaultMessageBrokerMessageSize;
    }

    private TS_SerialComOnReply(TS_SerialComOnConnectError onConnectError, TGS_RunnableType1<String> onReply_customMessageBroker) {
        this.onConnectError = onConnectError;
        this.onReply_customMessageBroker = onReply_customMessageBroker;
        this.defaultMessageBrokerMessageSize = -1;
    }
    final protected TS_SerialComOnConnectError onConnectError;
    final protected TGS_RunnableType1<String> onReply_customMessageBroker;
    final protected int defaultMessageBrokerMessageSize;

    public static TS_SerialComOnReply of(TS_SerialComOnConnectError onConnectError, TGS_RunnableType1<String> onReply_customMessageBroker) {
        return new TS_SerialComOnReply(onConnectError, onReply_customMessageBroker);
    }

    public static TS_SerialComOnReply of(TS_SerialComOnConnectError onConnectError, int defaultMessageBrokerMessageSize) {
        return new TS_SerialComOnReply(onConnectError, defaultMessageBrokerMessageSize);
    }

    @Deprecated //USE useFuntions
    public TS_SerialComConnection connect_AutoClosable(TS_ThreadKillTrigger killTrigger) {
        return TS_SerialComConnection.of(killTrigger, this);
    }

    public boolean onSuccess_useAndClose_connection(TS_ThreadKillTrigger killTrigger, TGS_RunnableType1<TS_SerialComConnection> con) {
        return TGS_UnSafe.call(() -> connect_AutoClosable(killTrigger).useAndClose_WithCustomMessageBroker(con), e -> false);
    }

    public boolean onSuccess_useAndClose_defaultMessageBroker(TS_ThreadKillTrigger killTrigger, TGS_RunnableType2<TS_SerialComConnection, TS_SerialComMessageBroker> con_mb) {
        return TGS_UnSafe.call(() -> connect_AutoClosable(killTrigger).useAndClose_WithDefaultMessageBroker(con_mb), e -> false);
    }
}
