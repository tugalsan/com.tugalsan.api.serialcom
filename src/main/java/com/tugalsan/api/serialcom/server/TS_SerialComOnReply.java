package com.tugalsan.api.serialcom.server;

import com.tugalsan.api.executable.client.TGS_ExecutableType1;

public class TS_SerialComOnReply {

    private TS_SerialComOnReply(TS_SerialComOnError onError, TGS_ExecutableType1<String> reply) {
        this.onError = onError;
        this.onReply = reply;
    }
    final protected TS_SerialComOnError onError;
    final protected TGS_ExecutableType1<String> onReply;

    public static TS_SerialComOnReply of(TS_SerialComOnError onError, TGS_ExecutableType1<String> reply) {
        return new TS_SerialComOnReply(onError, reply);
    }

    @Deprecated //DO NOT FORGET TO DISCONNECT!
    public TS_SerialComConnection connect() {
        return TS_SerialComConnection.of(this);
    }

    public boolean onSucessUseAndDisconnect(TGS_ExecutableType1<TS_SerialComConnection> con) {
        var connection = connect();
        if (!connection.isConnected()) {
            return false;
        }
        return connection.useAndDisconnect(con);
    }
}
