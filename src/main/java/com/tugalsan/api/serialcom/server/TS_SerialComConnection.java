package com.tugalsan.api.serialcom.server;

import com.fazecast.jSerialComm.*;
import com.tugalsan.api.coronator.client.TGS_Coronator;
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
        return threadExecutable != null;
    }

    final public boolean isConnected() {
        return success_portPresent() && success_portSetup() && success_portConnect() && port.isOpen();
    }

    private TS_SerialComConnection(TS_SerialComOnReceived onReceived) {
        this.onReceivedCommand = onReceived.command;
        this.parity = onReceived.parity.parity;
        this.stopBits = onReceived.parity.stopBits.stopBits;
        this.dataBits = onReceived.parity.stopBits.dataBits.dataBits;
        this.baudRate = onReceived.parity.stopBits.dataBits.baudRate.baudRate;
        this.port = onReceived.parity.stopBits.dataBits.baudRate.port.serialPort;
        if (!success_portPresent()) {
            return;
        }
        var stopBitsFlag = TGS_Coronator.ofInt()
                .anoint(val -> SerialPort.ONE_STOP_BIT)
                .anointAndCoronateIf(val -> stopBits == TS_SerialComStopBits.STOP_BITS.ONE_POINT_FIVE_STOP_BITS, val -> SerialPort.ONE_POINT_FIVE_STOP_BITS)
                .anointAndCoronateIf(val -> stopBits == TS_SerialComStopBits.STOP_BITS.TWO_STOP_BITS, val -> SerialPort.TWO_STOP_BITS)
                .coronate();
        var parityFlag = TGS_Coronator.ofInt()
                .anoint(val -> SerialPort.NO_PARITY)
                .anointAndCoronateIf(val -> parity == TS_SerialComParity.PARITY.EVEN_PARITY, val -> SerialPort.EVEN_PARITY)
                .anointAndCoronateIf(val -> parity == TS_SerialComParity.PARITY.ODD_PARITY, val -> SerialPort.ODD_PARITY)
                .coronate();

        this.setupSuccessfull = TS_SerialComUtils.setup(port, baudRate, dataBits, stopBitsFlag, parityFlag);
        if (!setupSuccessfull) {
            return;
        }
        this.threadExecutable = TS_SerialComUtils.connect(port, onReceivedCommand);
    }
    private TS_ThreadExecutable threadExecutable;
    final public TGS_ExecutableType1<String> onReceivedCommand;
    final public TS_SerialComParity.PARITY parity;
    final public TS_SerialComStopBits.STOP_BITS stopBits;
    final public int dataBits;
    final public int baudRate;
    final public SerialPort port;

    public static TS_SerialComConnection of(TS_SerialComOnReceived onReceived) {
        return new TS_SerialComConnection(onReceived);
    }

    public boolean disconnect() {
        if (isConnected()) {
            return TS_SerialComUtils.disconnect(port, threadExecutable);
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
        }
        d.ce("useAndDisconnect", "Error on not connected");
        return false;
    }
}
