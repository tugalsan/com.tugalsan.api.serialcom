package com.tugalsan.api.serialcom.server;

import com.tugalsan.api.callable.client.TGS_CallableType0_Run;
import com.tugalsan.api.callable.client.TGS_CallableType1_Run;



public class TS_SerialComOnConnectError {

    private TS_SerialComOnConnectError(TS_SerialComOnSetupError onSetupError, TGS_CallableType0_Run connectError) {
        this.onSetupError = onSetupError;
        this.connectError = connectError;
    }
    final protected TS_SerialComOnSetupError onSetupError;
    final protected TGS_CallableType0_Run connectError;

    public static TS_SerialComOnConnectError of(TS_SerialComOnSetupError onSetupError, TGS_CallableType0_Run connectError) {
        return new TS_SerialComOnConnectError(onSetupError, connectError);
    }

    public TS_SerialComOnReply onReply_useCustomMessageBroker(TGS_CallableType1_Run<String> reply) {
        return TS_SerialComOnReply.of(this, reply);
    }

    public TS_SerialComOnReply onReply_useDefaultMessageBroker_withMaxMessageCount(int defaultMessageBrokerMessageSize) {
        return TS_SerialComOnReply.of(this, defaultMessageBrokerMessageSize);
    }
}
