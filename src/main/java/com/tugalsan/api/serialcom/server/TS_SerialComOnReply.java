package com.tugalsan.api.serialcom.server;

import com.tugalsan.api.executable.client.TGS_ExecutableType1;

public class TS_SerialComOnReply {

    private TS_SerialComOnReply(TS_SerialComParity parity, TGS_ExecutableType1<String> reply) {
        this.parity = parity;
        this.onReply = reply;
    }
    final protected TS_SerialComParity parity;
    final protected TGS_ExecutableType1<String> onReply;

    public static TS_SerialComOnReply of(TS_SerialComParity parity, TGS_ExecutableType1<String> reply) {
        return new TS_SerialComOnReply(parity, reply);
    }

    public TS_SerialComConnection connect() {
        return TS_SerialComConnection.of(this);
    }

    public boolean use(TGS_ExecutableType1<TS_SerialComConnection> con) {
        return connect().useAndDisconnect(con);
    }
}
