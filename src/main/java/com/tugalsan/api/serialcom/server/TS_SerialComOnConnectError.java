package com.tugalsan.api.serialcom.server;

import com.tugalsan.api.callable.client.TGS_CallableType0Void;
import com.tugalsan.api.callable.client.TGS_CallableType1Void;



public class TS_SerialComOnConnectError {

    private TS_SerialComOnConnectError(TS_SerialComOnSetupError onSetupError, TGS_CallableType0Void connectError) {
        this.onSetupError = onSetupError;
        this.connectError = connectError;
    }
    final protected TS_SerialComOnSetupError onSetupError;
    final protected TGS_CallableType0Void connectError;

    public static TS_SerialComOnConnectError of(TS_SerialComOnSetupError onSetupError, TGS_CallableType0Void connectError) {
        return new TS_SerialComOnConnectError(onSetupError, connectError);
    }

    public TS_SerialComOnReply onReply_useCustomMessageBroker(TGS_CallableType1Void<String> reply) {
        return TS_SerialComOnReply.of(this, reply);
    }

    public TS_SerialComOnReply onReply_useDefaultMessageBroker_withMaxMessageCount(int defaultMessageBrokerMessageSize) {
        return TS_SerialComOnReply.of(this, defaultMessageBrokerMessageSize);
    }
}
