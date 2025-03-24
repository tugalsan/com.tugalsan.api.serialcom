package com.tugalsan.api.serialcom.server;


import com.tugalsan.api.serialcom.server.utils.TS_SerialComUtils.PARITY;
import com.tugalsan.api.function.client.maythrowexceptions.unchecked.TGS_FuncMTU;

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

    public TS_SerialComOnPortError onPortError(TGS_FuncMTU portError) {
        return TS_SerialComOnPortError.of(this, portError);
    }
}
