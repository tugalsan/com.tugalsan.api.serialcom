package com.tugalsan.api.serialcom.server;

import com.tugalsan.api.callable.client.TGS_CallableType0Void;



public class TS_SerialComOnSetupError {

    private TS_SerialComOnSetupError(TS_SerialComOnPortError onPortError, TGS_CallableType0Void setupError) {
        this.onPortError = onPortError;
        this.setupError = setupError;
    }
    final protected TS_SerialComOnPortError onPortError;
    final protected TGS_CallableType0Void setupError;

    public static TS_SerialComOnSetupError of(TS_SerialComOnPortError onPortError, TGS_CallableType0Void setupError) {
        return new TS_SerialComOnSetupError(onPortError, setupError);
    }

    public TS_SerialComOnConnectError onConnectError(TGS_CallableType0Void connectError) {
        return TS_SerialComOnConnectError.of(this, connectError);
    }
}
