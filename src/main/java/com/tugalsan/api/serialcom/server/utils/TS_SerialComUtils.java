package com.tugalsan.api.serialcom.server.utils;

import com.fazecast.jSerialComm.*;
import com.tugalsan.api.coronator.client.TGS_Coronator;
import com.tugalsan.api.runnable.client.*;
import com.tugalsan.api.list.client.TGS_ListUtils;
import com.tugalsan.api.log.server.TS_Log;
import com.tugalsan.api.stream.client.TGS_StreamUtils;
import com.tugalsan.api.thread.server.sync.TS_ThreadSyncTrigger;
import com.tugalsan.api.thread.server.async.TS_ThreadAsync;
import com.tugalsan.api.thread.server.TS_ThreadWait;
import java.util.ArrayList;
import java.util.List;

public class TS_SerialComUtils {

    final private static TS_Log d = TS_Log.of(TS_SerialComUtils.class);

    public static enum PARITY {
        NO_PARITY, ODD_PARITY, EVEN_PARITY;
    }

    public static enum STOP_BITS {
        ONE_STOP_BIT, ONE_POINT_FIVE_STOP_BITS, TWO_STOP_BITS;
    }

    public static boolean send(SerialPort serialPort, String command) {
        d.ci("send", command);
        var byteArray = command.getBytes();
        var result = serialPort.writeBytes(byteArray, byteArray.length) != -1;
        if (!result) {
            return false;
        }
        if (!command.endsWith("\n")) {
            return send(serialPort, "\n");
        }
        return result;
    }

    public static List<String> listNamesFull() {
        return TGS_StreamUtils.toLst(list().stream().map(p -> nameFull(p)));
    }

    public static List<String> listNamesPort() {
        return TGS_StreamUtils.toLst(list().stream().map(p -> namePort(p)));
    }

    public static List<SerialPort> list() {
        if (!dontDeleteMyTemporaryFilesDuringBootUp) {
            dontDeleteMyTemporaryFilesDuringBootUp = true;
            System.setProperty("fazecast.jSerialComm.appid", d.className);
        }
        d.ci("list", "");
        var listArr = SerialPort.getCommPorts();
        if (listArr == null || listArr.length == 0) {
            return new ArrayList();
        }
        return TGS_ListUtils.of(listArr);
    }
    private static boolean dontDeleteMyTemporaryFilesDuringBootUp = false;

    public static boolean disconnect(TS_ThreadSyncTrigger killTrigger, SerialPort serialPort) {
        d.ci("disconnect", "");
        return disconnect(killTrigger, serialPort, null);
    }

    public static boolean disconnect(TS_ThreadSyncTrigger killTrigger, SerialPort serialPort, TS_SerialComUtilsThreadReply threadReply) {
        d.ci("disconnect", "threadReply");
        if (threadReply != null) {
            threadReply.killMe = true;
        }
        serialPort.removeDataListener();
        TS_ThreadWait.seconds(d.className, killTrigger, 0);//FOR ARDUINO
        return serialPort.closePort();
    }

    public static TS_SerialComUtilsThreadReply connect(TS_ThreadSyncTrigger killTrigger, SerialPort serialPort, TGS_RunnableType1<String> onReply) {
        d.ci("connect", "onReply", onReply != null);
        var result = serialPort.setComPortTimeouts(SerialPort.TIMEOUT_NONBLOCKING, 0, 0);
        if (!result) {
            d.ce("connect", "Error on setComPortTimeouts");
            return null;
        }
        result = serialPort.openPort();
        if (!result) {
            d.ce("connect", "Error on openPort");
            return null;
        }
        var threadReply = TS_SerialComUtilsThreadReply.of(killTrigger, serialPort, onReply);
        TS_ThreadAsync.now(killTrigger, threadReply);
        return threadReply;
    }

    public static String nameFull(SerialPort serialPort) {
        d.ci("name", "");
        return serialPort.getDescriptivePortName();
    }

    public static String namePort(SerialPort serialPort) {
        d.ci("name", "");
//        d.cr("-", "serialPort.getSystemPortName()", serialPort.getDescriptivePortName());
//        d.cr("-", "serialPort.getSystemPortName()", serialPort.getPortDescription());
//        d.cr("-", "serialPort.getSystemPortName()", serialPort.getPortLocation());
//        d.cr("-", "serialPort.getSystemPortName()", serialPort.getSystemPortName());
//        d.cr("-", "serialPort.getSystemPortName()", serialPort.getSystemPortPath());
        return serialPort.getSystemPortName();
    }

    public static boolean setup(SerialPort serialPort, int baudRate, int dataBits, STOP_BITS stopBits, PARITY parity) {
        d.ci("setup", baudRate, dataBits, stopBits, parity);
        var result = serialPort.setBaudRate(baudRate);
        if (!result) {
            d.ce("setup", "Error on setBaudRate");
            return false;
        }
        result = serialPort.setNumDataBits(dataBits);
        if (!result) {
            d.ce("setup", "Error on setNumDataBits");
            return false;
        }
        result = serialPort.setNumStopBits(TGS_Coronator.ofInt()
                .anoint(val -> SerialPort.ONE_STOP_BIT)
                .anointAndCoronateIf(val -> stopBits == STOP_BITS.ONE_POINT_FIVE_STOP_BITS, val -> SerialPort.ONE_POINT_FIVE_STOP_BITS)
                .anointAndCoronateIf(val -> stopBits == STOP_BITS.TWO_STOP_BITS, val -> SerialPort.TWO_STOP_BITS)
                .coronate());
        if (!result) {
            d.ce("setup", "Error on setNumStopBits");
            return false;
        }
        result = serialPort.setParity(TGS_Coronator.ofInt()
                .anoint(val -> SerialPort.NO_PARITY)
                .anointAndCoronateIf(val -> parity == PARITY.EVEN_PARITY, val -> SerialPort.EVEN_PARITY)
                .anointAndCoronateIf(val -> parity == PARITY.ODD_PARITY, val -> SerialPort.ODD_PARITY)
                .coronate());
        if (!result) {
            d.ce("setup", "Error on setParity");
            return false;
        }
        return true;
    }

}
