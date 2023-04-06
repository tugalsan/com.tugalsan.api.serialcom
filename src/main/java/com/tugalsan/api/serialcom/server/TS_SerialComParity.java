package com.tugalsan.api.serialcom.server;

import com.tugalsan.api.executable.client.TGS_ExecutableType1;

public class TS_SerialComParity {

    public static enum PARITY {
        NO_PARITY, ODD_PARITY, EVEN_PARITY;
    }

    private TS_SerialComParity(TS_SerialComStopBits stopBits, PARITY parity) {
        this.stopBits = stopBits;
        this.parity = parity;
    }
    final public TS_SerialComStopBits stopBits;
    final public PARITY parity;

    public static TS_SerialComParity of(TS_SerialComStopBits stopBits, PARITY parity) {
        return new TS_SerialComParity(stopBits, parity);
    }

    public TS_SerialComOnReceived onReceived(TGS_ExecutableType1<String> command) {
        return TS_SerialComOnReceived.of(this, command);
    }
}
