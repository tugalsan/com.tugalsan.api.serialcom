package com.tugalsan.api.serialcom.server;

import com.tugalsan.api.callable.client.TGS_CallableType0_Run;



public class TS_SerialComOnPortError {

    private TS_SerialComOnPortError(TS_SerialComParity parity, TGS_CallableType0_Run portError) {
        this.parity = parity;
        this.portError = portError;
    }
    final protected TS_SerialComParity parity;
    final protected TGS_CallableType0_Run portError;

    public static TS_SerialComOnPortError of(TS_SerialComParity parity, TGS_CallableType0_Run portError) {
        return new TS_SerialComOnPortError(parity, portError);
    }

    public TS_SerialComOnSetupError onSetupError(TGS_CallableType0_Run setupError) {
        return TS_SerialComOnSetupError.of(this, setupError);
    }
}
