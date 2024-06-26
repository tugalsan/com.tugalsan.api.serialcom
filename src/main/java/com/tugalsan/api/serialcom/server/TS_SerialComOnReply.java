package com.tugalsan.api.serialcom.server;



import com.tugalsan.api.callable.client.TGS_CallableType1_Run;
import com.tugalsan.api.callable.client.TGS_CallableType2_Run;
import com.tugalsan.api.thread.server.sync.TS_ThreadSyncTrigger;
import com.tugalsan.api.unsafe.client.TGS_UnSafe;

public class TS_SerialComOnReply {

    private TS_SerialComOnReply(TS_SerialComOnConnectError onConnectError, int defaultMessageBrokerMessageSize) {
        this.onConnectError = onConnectError;
        this.onReply_customMessageBroker = null;
        this.defaultMessageBrokerMessageSize = defaultMessageBrokerMessageSize;
    }

    private TS_SerialComOnReply(TS_SerialComOnConnectError onConnectError, TGS_CallableType1_Run<String> onReply_customMessageBroker) {
        this.onConnectError = onConnectError;
        this.onReply_customMessageBroker = onReply_customMessageBroker;
        this.defaultMessageBrokerMessageSize = -1;
    }
    final protected TS_SerialComOnConnectError onConnectError;
    final protected TGS_CallableType1_Run<String> onReply_customMessageBroker;
    final protected int defaultMessageBrokerMessageSize;

    public static TS_SerialComOnReply of(TS_SerialComOnConnectError onConnectError, TGS_CallableType1_Run<String> onReply_customMessageBroker) {
        return new TS_SerialComOnReply(onConnectError, onReply_customMessageBroker);
    }

    public static TS_SerialComOnReply of(TS_SerialComOnConnectError onConnectError, int defaultMessageBrokerMessageSize) {
        return new TS_SerialComOnReply(onConnectError, defaultMessageBrokerMessageSize);
    }

    @Deprecated //USE useFuntions
    public TS_SerialComConnection connect_AutoClosable(TS_ThreadSyncTrigger killTrigger) {
        return TS_SerialComConnection.of(killTrigger, this);
    }

    public boolean onSuccess_useAndClose_connection(TS_ThreadSyncTrigger killTrigger, TGS_CallableType1_Run<TS_SerialComConnection> con) {
        return TGS_UnSafe.call(() -> connect_AutoClosable(killTrigger).useAndClose_WithCustomMessageBroker(con), e -> false);
    }

    public boolean onSuccess_useAndClose_defaultMessageBroker(TS_ThreadSyncTrigger killTrigger, TGS_CallableType2_Run<TS_SerialComConnection, TS_SerialComMessageBroker> con_mb) {
        return TGS_UnSafe.call(() -> connect_AutoClosable(killTrigger).useAndClose_WithDefaultMessageBroker(con_mb), e -> false);
    }
}
