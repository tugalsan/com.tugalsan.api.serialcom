package com.tugalsan.api.serialcom.server.utils;

import com.fazecast.jSerialComm.SerialPort;
import com.tugalsan.api.function.client.maythrow.uncheckedexceptions.TGS_FuncMTUCE_In1;
import com.tugalsan.api.log.server.TS_Log;

import com.tugalsan.api.thread.server.sync.TS_ThreadSyncTrigger;
import com.tugalsan.api.thread.server.sync.TS_ThreadSyncWait;
import com.tugalsan.api.function.client.TGS_FuncUtils;
import com.tugalsan.api.function.client.maythrow.checkedexceptions.TGS_FuncMTCEUtils;
import java.time.Duration;

public class TS_SerialComUtilsThreadReply implements TGS_FuncMTUCE_In1<TS_ThreadSyncTrigger> {

    final private static TS_Log d = TS_Log.of(TS_SerialComUtilsThreadReply.class);

    final private TS_ThreadSyncTrigger killTrigger;
    final private SerialPort serialPort;
    final private TGS_FuncMTUCE_In1<String> onReply;

    private TS_SerialComUtilsThreadReply(TS_ThreadSyncTrigger killTrigger, SerialPort serialPort, TGS_FuncMTUCE_In1<String> onReply) {
        this.killTrigger = killTrigger;
        this.serialPort = serialPort;
        this.onReply = onReply;
    }

    public static TS_SerialComUtilsThreadReply of(TS_ThreadSyncTrigger killTrigger, SerialPort serialPort, TGS_FuncMTUCE_In1<String> onReply) {
        return new TS_SerialComUtilsThreadReply(killTrigger, serialPort, onReply);
    }

    public volatile boolean killMe = false;

    private void waitForNewData() {
        var dur = Duration.ofMillis(20);
        while (serialPort.bytesAvailable() == 0 || serialPort.bytesAvailable() == -1) {
            if (killMe) {
                return;
            }
            TS_ThreadSyncWait.of(d.className, killTrigger, dur);
        }
    }

    private void appendToBuffer() {
        if (killMe) {
            return;
        }
        var bytes = new byte[serialPort.bytesAvailable()];
        var length = serialPort.readBytes(bytes, bytes.length);
        if (length == -1) {
            return;
        }
        var string = new String(bytes);
        if (string.isEmpty()) {
            return;
        }
        buffer.append(string);
    }

    private void processBuffer() {
        if (killMe) {
            return;
        }
        //IS THERE ANY CMD?
        var idx = buffer.indexOf("\n");
        if (idx == -1) {
            return;
        }
        //PROCESS FIRST CMD
        var firstCommand = buffer.substring(0, idx).replace("\r", "");
        onReply.run(firstCommand);
        //REMOVE FIRST CMD FROM BUFFER
        var leftOver = buffer.length() == idx + 1
                ? ""
                : buffer.substring(idx + 1, buffer.length());
        buffer.setLength(0);
        buffer.append(leftOver);
        //PROCESS LEFT OVER CMDS
        processBuffer();
    }

    private void handleError(Exception e) {
        TGS_FuncUtils.throwIfInterruptedException(e);
        if (killMe) {
            return;
        }
//        e.printStackTrace();
    }

    @Override
    public void run(TS_ThreadSyncTrigger killTrigger) {
        while (!killMe) {
            TGS_FuncMTCEUtils.run(() -> {
                waitForNewData();
                appendToBuffer();
                processBuffer();
            }, e -> handleError(e));
            if (killTrigger.hasTriggered()) {
                killMe = true;
            }
        }
    }
    final private StringBuilder buffer = new StringBuilder();
}
