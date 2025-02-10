package com.tugalsan.api.serialcom.server;

import com.tugalsan.api.function.client.maythrow.uncheckedexceptions.TGS_FuncMTUCE;



public class TS_SerialComOnSetupError {

    private TS_SerialComOnSetupError(TS_SerialComOnPortError onPortError, TGS_FuncMTUCE setupError) {
        this.onPortError = onPortError;
        this.setupError = setupError;
    }
    final protected TS_SerialComOnPortError onPortError;
    final protected TGS_FuncMTUCE setupError;

    public static TS_SerialComOnSetupError of(TS_SerialComOnPortError onPortError, TGS_FuncMTUCE setupError) {
        return new TS_SerialComOnSetupError(onPortError, setupError);
    }

    public TS_SerialComOnConnectError onConnectError(TGS_FuncMTUCE connectError) {
        return TS_SerialComOnConnectError.of(this, connectError);
    }
}
