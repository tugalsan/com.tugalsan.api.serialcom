package com.tugalsan.api.serialcom.server;

import com.tugalsan.api.executable.client.*;

public class TS_SerialComOnConnectionError {

    private TS_SerialComOnConnectionError(TS_SerialComParity parity, TGS_ExecutableType3<Boolean, Boolean, Boolean> successfulPort_successfulSetup_successfulConnect) {
        this.parity = parity;
        this.successfulPort_successfulSetup_successfulConnect = successfulPort_successfulSetup_successfulConnect;
    }
    final protected TS_SerialComParity parity;
    final protected TGS_ExecutableType3<Boolean, Boolean, Boolean> successfulPort_successfulSetup_successfulConnect;

    public static TS_SerialComOnConnectionError of(TS_SerialComParity parity, TGS_ExecutableType3<Boolean, Boolean, Boolean> successfulPort_successfulSetup_successfulConnect) {
        return new TS_SerialComOnConnectionError(parity, successfulPort_successfulSetup_successfulConnect);
    }

    public TS_SerialComOnReply onReply(TGS_ExecutableType1<String> reply) {
        return TS_SerialComOnReply.of(this, reply);
    }
}
