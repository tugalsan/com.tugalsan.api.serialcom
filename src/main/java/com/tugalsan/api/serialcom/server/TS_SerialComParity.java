package com.tugalsan.api.serialcom.server;

import com.tugalsan.api.executable.client.TGS_ExecutableType3;
import com.tugalsan.api.serialcom.server.utils.TS_SerialComUtils.PARITY;

public class TS_SerialComParity {

    private TS_SerialComParity(TS_SerialComStopBits stopBits, PARITY parity) {
        this.stopBits = stopBits;
        this.parity = parity;
    }
    final protected TS_SerialComStopBits stopBits;
    final protected PARITY parity;

    public static TS_SerialComParity of(TS_SerialComStopBits stopBits, PARITY parity) {
        return new TS_SerialComParity(stopBits, parity);
    }

    public TS_SerialComOnConnectionError onError(TGS_ExecutableType3<Boolean, Boolean, Boolean> successfulPort_successfulSetup_successfulConnect) {
        return TS_SerialComOnConnectionError.of(this, successfulPort_successfulSetup_successfulConnect);
    }
}
