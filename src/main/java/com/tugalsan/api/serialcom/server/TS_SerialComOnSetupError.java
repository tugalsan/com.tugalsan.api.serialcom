package com.tugalsan.api.serialcom.server;

import com.tugalsan.api.function.client.maythrowexceptions.unchecked.TGS_FuncMTU;



public class TS_SerialComOnSetupError {

    private TS_SerialComOnSetupError(TS_SerialComOnPortError onPortError, TGS_FuncMTU setupError) {
        this.onPortError = onPortError;
        this.setupError = setupError;
    }
    final protected TS_SerialComOnPortError onPortError;
    final protected TGS_FuncMTU setupError;

    public static TS_SerialComOnSetupError of(TS_SerialComOnPortError onPortError, TGS_FuncMTU setupError) {
        return new TS_SerialComOnSetupError(onPortError, setupError);
    }

    public TS_SerialComOnConnectError onConnectError(TGS_FuncMTU connectError) {
        return TS_SerialComOnConnectError.of(this, connectError);
    }
}
