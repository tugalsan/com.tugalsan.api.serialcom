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

    final public boolean success_portPresent() {
        return port != null;
    }

    final public boolean success_portSetup() {
        return setupSuccessfull;
    }
    private boolean setupSuccessfull;

    final public boolean success_portConnect() {
        return threadReply != null;
    }

    final public boolean isConnected() {
        return success_portPresent() && success_portSetup() && success_portConnect() && port.isOpen();
    }

    private TS_SerialComConnection(TS_SerialComOnReply onReply) {
        this.onReply = onReply.onReply;
        var parity = onReply.parity.parity;
        this.parityName = parity == null ? null : parity.name();
        var stopBits = onReply.parity.stopBits.stopBits;
        this.stopBitsName = stopBits == null ? null : stopBits.name();
        this.dataBits = onReply.parity.stopBits.dataBits.dataBits;
        this.baudRate = onReply.parity.stopBits.dataBits.baudRate.baudRate;
        this.port = onReply.parity.stopBits.dataBits.baudRate.port.serialPort;
        if (!success_portPresent()) {
            return;
        }
        this.setupSuccessfull = TS_SerialComUtils.setup(port, baudRate, dataBits, stopBits, parity);
        if (!setupSuccessfull) {
            return;
        }
        this.threadReply = TS_SerialComUtils.connect(port, this.onReply);
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
        if (isConnected()) {
            return TS_SerialComUtils.disconnect(port, threadReply);
        }
        d.ce("disconnect", "Error on not connected");
        return false;
    }

    public boolean send(String command) {
        if (isConnected()) {
            return TS_SerialComUtils.send(port, command);
        }
        d.ce("send", "Error on not connected");
        return false;
    }

    public boolean useAndDisconnect(TGS_ExecutableType1<TS_SerialComConnection> con) {
        if (isConnected()) {
            con.execute(this);
            disconnect();
            return true;
        }
        d.ce("useAndDisconnect", "Error on not connected");
        return false;
    }
}
