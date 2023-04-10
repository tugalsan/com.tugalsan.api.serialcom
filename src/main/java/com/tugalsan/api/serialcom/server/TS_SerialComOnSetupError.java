package com.tugalsan.api.serialcom.server;

import com.tugalsan.api.executable.client.*;

public class TS_SerialComOnSetupError {

    private TS_SerialComOnSetupError(TS_SerialComOnPortError onPortError, TGS_Executable setupError) {
        this.onPortError = onPortError;
        this.setupError = setupError;
    }
    final protected TS_SerialComOnPortError onPortError;
    final protected TGS_Executable setupError;

    public static TS_SerialComOnSetupError of(TS_SerialComOnPortError onPortError, TGS_Executable setupError) {
        return new TS_SerialComOnSetupError(onPortError, setupError);
    }

    public TS_SerialComOnConnectError onConnectError(TGS_Executable connectError) {
        return TS_SerialComOnConnectError.of(this, connectError);
    }
}
