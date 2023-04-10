package com.tugalsan.api.serialcom.server;

import com.tugalsan.api.executable.client.*;

public class TS_SerialComOnPortError {

    private TS_SerialComOnPortError(TS_SerialComParity parity, TGS_Executable portError) {
        this.parity = parity;
        this.portError = portError;
    }
    final protected TS_SerialComParity parity;
    final protected TGS_Executable portError;

    public static TS_SerialComOnPortError of(TS_SerialComParity parity, TGS_Executable portError) {
        return new TS_SerialComOnPortError(parity, portError);
    }

    public TS_SerialComOnSetupError onSetupError(TGS_Executable setupError) {
        return TS_SerialComOnSetupError.of(this, setupError);
    }
}
