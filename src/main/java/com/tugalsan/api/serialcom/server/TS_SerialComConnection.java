package com.tugalsan.api.serialcom.server;

import com.fazecast.jSerialComm.*;
import com.tugalsan.api.executable.client.*;
import com.tugalsan.api.log.server.TS_Log;
import com.tugalsan.api.serialcom.server.utils.*;
import com.tugalsan.api.thread.server.TS_ThreadExecutable;

public class TS_SerialComConnection {

    final private static TS_Log d = TS_Log.of(TS_SerialComConnection.class);

    final public String name() {
        return port == null ? null : TS_SerialComUtils.name(port);
    }

    final private boolean successfulPort() {
        return port != null;
    }

    final private boolean successfulSetup() {
        return successfulSetup;
    }
    private boolean successfulSetup;

    final private boolean successfulConnect() {
        return threadReply != null;
    }

    final public boolean isConnected() {
        return successfulPort() && successfulSetup() && successfulConnect() && port.isOpen();
    }

    private TS_SerialComConnection(TS_SerialComOnReply onReply) {
        this.onReply = onReply.onReply;
        var parity = onReply.onConnectError.onSetupError.onPortError.parity.parity;
        this.parityName = parity == null ? null : parity.name();
        var stopBits = onReply.onConnectError.onSetupError.onPortError.parity.stopBits.stopBits;
        this.stopBitsName = stopBits == null ? null : stopBits.name();
        this.dataBits = onReply.onConnectError.onSetupError.onPortError.parity.stopBits.dataBits.dataBits;
        this.baudRate = onReply.onConnectError.onSetupError.onPortError.parity.stopBits.dataBits.baudRate.baudRate;
        this.port = onReply.onConnectError.onSetupError.onPortError.parity.stopBits.dataBits.baudRate.port.serialPort;
        if (!successfulPort()) {
            return;
        }
        this.successfulSetup = TS_SerialComUtils.setup(port, baudRate, dataBits, stopBits, parity);
        if (!successfulSetup) {
            return;
        }
        this.threadReply = TS_SerialComUtils.connect(port, this.onReply);
        if (!successfulPort()) {
            onReply.onConnectError.onSetupError.onPortError.portError.execute();
            return;
        }
        if (!successfulSetup()) {
            onReply.onConnectError.onSetupError.setupError.execute();
            return;
        }
        if (!successfulConnect()) {
            onReply.onConnectError.connectError.execute();
            return;
        }
    }
    private TS_ThreadExecutable threadReply;
    final public TGS_ExecutableType1<String> onReply;
    final public String parityName;
    final public String stopBitsName;
    final public int dataBits;
    final public int baudRate;
    final public SerialPort port;

    public static TS_SerialComConnection of(TS_SerialComOnReply onReply) {
        return new TS_SerialComConnection(onReply);
    }

    public boolean disconnect() {
        if (!isConnected()) {
            d.ce("disconnect", "Error on not connected");
            return false;
        }
        return TS_SerialComUtils.disconnect(port, threadReply);
    }

    public boolean send(String command) {
        if (!isConnected()) {
            d.ce("send", "Error on not connected");
            return false;
        }
        return TS_SerialComUtils.send(port, command);
    }

    public boolean useAndDisconnect(TGS_ExecutableType1<TS_SerialComConnection> con) {
        if (!isConnected()) {
            d.ce("useAndDisconnect", "Error on not connected");
            return false;
        }
        con.execute(this);
        disconnect();
        return true;
    }
}
