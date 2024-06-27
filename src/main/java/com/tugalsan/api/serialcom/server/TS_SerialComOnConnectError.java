package com.tugalsan.api.serialcom.server;

import com.tugalsan.api.function.client.TGS_Func;
import com.tugalsan.api.function.client.TGS_Func_In1;



public class TS_SerialComOnConnectError {

    private TS_SerialComOnConnectError(TS_SerialComOnSetupError onSetupError, TGS_Func connectError) {
        this.onSetupError = onSetupError;
        this.connectError = connectError;
    }
    final protected TS_SerialComOnSetupError onSetupError;
    final protected TGS_Func connectError;

    public static TS_SerialComOnConnectError of(TS_SerialComOnSetupError onSetupError, TGS_Func connectError) {
        return new TS_SerialComOnConnectError(onSetupError, connectError);
    }

    public TS_SerialComOnReply onReply_useCustomMessageBroker(TGS_Func_In1<String> reply) {
        return TS_SerialComOnReply.of(this, reply);
    }

    public TS_SerialComOnReply onReply_useDefaultMessageBroker_withMaxMessageCount(int defaultMessageBrokerMessageSize) {
        return TS_SerialComOnReply.of(this, defaultMessageBrokerMessageSize);
    }
}
