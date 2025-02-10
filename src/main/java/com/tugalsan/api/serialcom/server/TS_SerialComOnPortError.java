package com.tugalsan.api.serialcom.server;

import com.tugalsan.api.function.client.maythrow.uncheckedexceptions.TGS_FuncMTUCE;



public class TS_SerialComOnPortError {

    private TS_SerialComOnPortError(TS_SerialComParity parity, TGS_FuncMTUCE portError) {
        this.parity = parity;
        this.portError = portError;
    }
    final protected TS_SerialComParity parity;
    final protected TGS_FuncMTUCE portError;

    public static TS_SerialComOnPortError of(TS_SerialComParity parity, TGS_FuncMTUCE portError) {
        return new TS_SerialComOnPortError(parity, portError);
    }

    public TS_SerialComOnSetupError onSetupError(TGS_FuncMTUCE setupError) {
        return TS_SerialComOnSetupError.of(this, setupError);
    }
}
