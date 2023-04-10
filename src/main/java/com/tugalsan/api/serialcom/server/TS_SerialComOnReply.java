package com.tugalsan.api.serialcom.server;

import com.tugalsan.api.executable.client.TGS_ExecutableType1;

public class TS_SerialComOnReply {

    private TS_SerialComOnReply(TS_SerialComOnConnectError onConnectError, TGS_ExecutableType1<String> reply) {
        this.onConnectError = onConnectError;
        this.onReply = reply;
    }
    final protected TS_SerialComOnConnectError onConnectError;
    final protected TGS_ExecutableType1<String> onReply;

    public static TS_SerialComOnReply of(TS_SerialComOnConnectError onConnectError, TGS_ExecutableType1<String> reply) {
        return new TS_SerialComOnReply(onConnectError, reply);
    }

    @Deprecated //DO NOT FORGET TO DISCONNECT!
    public TS_SerialComConnection connect() {
        return TS_SerialComConnection.of(this);
    }

    public boolean onSucess_useAndDisconnect_the_(TGS_ExecutableType1<TS_SerialComConnection> con) {
        var connection = connect();
        if (!connection.isConnected()) {
            return false;
        }
        return connection.useAndDisconnect(con);
    }
}
