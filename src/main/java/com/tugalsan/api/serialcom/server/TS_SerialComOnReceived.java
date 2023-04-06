package com.tugalsan.api.serialcom.server;

import com.tugalsan.api.executable.client.TGS_ExecutableType1;

public class TS_SerialComOnReceived {

    private TS_SerialComOnReceived(TS_SerialComParity parity, TGS_ExecutableType1<String> command) {
        this.parity = parity;
        this.command = command;
    }
    final protected TS_SerialComParity parity;
    final protected TGS_ExecutableType1<String> command;

    public static TS_SerialComOnReceived of(TS_SerialComParity parity, TGS_ExecutableType1<String> command) {
        return new TS_SerialComOnReceived(parity, command);
    }

    public TS_SerialComConnection connect() {
        return TS_SerialComConnection.of(this);
    }

    public boolean use(TGS_ExecutableType1<TS_SerialComConnection> con) {
        return connect().useAndDisconnect(con);
    }
}
