package com.tugalsan.api.serialcom.server;

import com.tugalsan.api.function.client.TGS_Func;



public class TS_SerialComOnSetupError {

    private TS_SerialComOnSetupError(TS_SerialComOnPortError onPortError, TGS_Func setupError) {
        this.onPortError = onPortError;
        this.setupError = setupError;
    }
    final protected TS_SerialComOnPortError onPortError;
    final protected TGS_Func setupError;

    public static TS_SerialComOnSetupError of(TS_SerialComOnPortError onPortError, TGS_Func setupError) {
        return new TS_SerialComOnSetupError(onPortError, setupError);
    }

    public TS_SerialComOnConnectError onConnectError(TGS_Func connectError) {
        return TS_SerialComOnConnectError.of(this, connectError);
    }
}
