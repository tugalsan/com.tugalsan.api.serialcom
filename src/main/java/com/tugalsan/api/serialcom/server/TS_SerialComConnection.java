package com.tugalsan.api.serialcom.server;

import com.fazecast.jSerialComm.*;
import com.tugalsan.api.function.client.maythrow.uncheckedexceptions.TGS_FuncMTUCE_In1;
import com.tugalsan.api.function.client.maythrow.uncheckedexceptions.TGS_FuncMTUCE_In2;

import com.tugalsan.api.log.server.TS_Log;
import com.tugalsan.api.serialcom.server.utils.*;
import com.tugalsan.api.thread.server.sync.TS_ThreadSyncTrigger;
import com.tugalsan.api.function.client.TGS_FuncUtils;

public class TS_SerialComConnection implements AutoCloseable {

    final private static TS_Log d = TS_Log.of(true, TS_SerialComConnection.class);

    final public String namePort() {
        return port == null ? null : TS_SerialComUtils.namePort(port);
    }

    private boolean successfulPort() {
        return port != null;
    }

    private boolean successfulSetup() {
        return successfulSetup;
    }
    private boolean successfulSetup;

    private boolean successfulConnect() {
        return threadReply != null;
    }

    final public boolean isConnected() {
        return successfulPort() && successfulSetup() && successfulConnect() && port.isOpen();
    }

    private final TS_ThreadSyncTrigger killTrigger;

    private TS_SerialComConnection(TS_ThreadSyncTrigger killTrigger, TS_SerialComOnReply onReply) {
        this.killTrigger = killTrigger;
        //BIND MESSAGE BROKER
        if (onReply.onReply_customMessageBroker == null) {//use default broker
            this.messageBroker = TS_SerialComMessageBroker.of(onReply.defaultMessageBrokerMessageSize);
            this.messageBroker.setConnection(this);
            this.onReply = reply -> messageBroker.onReply(reply);
        } else {//if custom broker exists
            this.messageBroker = null;
            this.onReply = onReply.onReply_customMessageBroker;
        }
        //BIND PARAMETERS
        var parity = onReply.onConnectError.onSetupError.onPortError.parity.parity;
        this.parityName = parity == null ? null : parity.name();
        var stopBits = onReply.onConnectError.onSetupError.onPortError.parity.stopBits.stopBits;
        this.stopBitsName = stopBits == null ? null : stopBits.name();
        this.dataBits = onReply.onConnectError.onSetupError.onPortError.parity.stopBits.dataBits.dataBits;
        this.baudRate = onReply.onConnectError.onSetupError.onPortError.parity.stopBits.dataBits.baudRate.baudRate;
        //BIND PORT
        this.port = onReply.onConnectError.onSetupError.onPortError.parity.stopBits.dataBits.baudRate.port.serialPort;
        if (!successfulPort()) {
            d.ce("constructor", "error detected", "!successfulPort");
            onReply.onConnectError.onSetupError.onPortError.portError.run();
            return;
        }
        //BIND SETUP
        this.successfulSetup = TS_SerialComUtils.setup(port, baudRate, dataBits, stopBits, parity);
        if (!successfulSetup()) {
            d.ce("constructor", "error detected", "!successfulSetup");
            onReply.onConnectError.onSetupError.setupError.run();
            return;
        }
        //CONNECT AND BIND REPLY
        this.threadReply = TS_SerialComUtils.connect(killTrigger, port, this.onReply);
        if (!successfulConnect()) {
            d.ce("constructor", "error detected", "!successfulConnect");
            onReply.onConnectError.connectError.run();
            return;
        }
    }
    final private TS_SerialComMessageBroker messageBroker;
    private TS_SerialComUtilsThreadReply threadReply;
    final public TGS_FuncMTUCE_In1<String> onReply;
    final public String parityName;
    final public String stopBitsName;
    final public int dataBits;
    final public int baudRate;
    final public SerialPort port;

    public static TS_SerialComConnection of(TS_ThreadSyncTrigger killTrigger, TS_SerialComOnReply onReply) {
        return new TS_SerialComConnection(killTrigger, onReply);
    }

    @Override
    public /*boolean*/ void close() {
        if (!isConnected()) {
            d.ce("disconnect", "Error on not connected");
            return /*false*/;
        }
        /*return*/ TS_SerialComUtils.disconnect(killTrigger, port, threadReply);
    }

    public boolean send(String command) {
        if (!isConnected()) {
            d.ce("send", "Error on not connected");
            return false;
        }
        return TS_SerialComUtils.send(port, command);
    }

    public boolean useAndClose_WithDefaultMessageBroker(TGS_FuncMTUCE_In2<TS_SerialComConnection, TS_SerialComMessageBroker> con_mb) {
        //TODO: TGS_FuncMTCEUtils.run(exe, exception, finalExe);
        try {
            if (!isConnected()) {
                d.ce("useAndClose", "Error on not connected");
                return false;
            }
            con_mb.run(this, messageBroker);
        } catch (Exception e) {
            TGS_FuncUtils.throwIfInterruptedException(e);
            d.ct("useAndClose", e);
            return false;
        } finally {
            close();
        }
        return true;
    }

    public boolean useAndClose_WithCustomMessageBroker(TGS_FuncMTUCE_In1<TS_SerialComConnection> con) {
        //TODO: TGS_FuncMTCEUtils.call(cmp, exception, finalExe)
        try {
            if (!isConnected()) {
                d.ce("useAndClose", "Error on not connected");
                return false;
            }
            con.run(this);
        } catch (Exception e) {
            TGS_FuncUtils.throwIfInterruptedException(e);
            d.ct("useAndClose", e);
            return false;
        } finally {
            close();
        }
        return true;
    }
}
