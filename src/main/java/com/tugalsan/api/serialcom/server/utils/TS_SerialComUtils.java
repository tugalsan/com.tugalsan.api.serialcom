package com.tugalsan.api.serialcom.server.utils;

import com.fazecast.jSerialComm.*;
import com.tugalsan.api.executable.client.*;
import com.tugalsan.api.list.client.TGS_ListUtils;
import com.tugalsan.api.log.server.TS_Log;
import com.tugalsan.api.thread.server.*;
import com.tugalsan.api.unsafe.client.TGS_UnSafe;
import java.util.ArrayList;
import java.util.List;

public class TS_SerialComUtils {

    final private static TS_Log d = TS_Log.of(TS_SerialComUtils.class);

    public static boolean send(SerialPort serialPort, String command) {
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

    public static List<SerialPort> list() {
        var listArr = SerialPort.getCommPorts();
        if (listArr == null || listArr.length == 0) {
            return new ArrayList();
        }
        return TGS_ListUtils.of(listArr);
    }

    public static boolean disconnect(SerialPort serialPort) {
        return disconnect(serialPort, null);
    }

    public static boolean disconnect(SerialPort serialPort, TS_ThreadExecutable receiveThread) {
        if (receiveThread != null) {
            receiveThread.killMe = true;
        }
        serialPort.removeDataListener();
        TS_ThreadWait.seconds(null, 2);//FOR ARDUINO
        return serialPort.closePort();
    }

    public static TS_ThreadExecutable connect(SerialPort serialPort, TGS_ExecutableType1<String> receivedNextCommand) {
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
        var killableThread = new TS_ThreadExecutable() {

            private void waitForNewData() {
                while (serialPort.bytesAvailable() == 0 || serialPort.bytesAvailable() == -1) {
                    if (killMe) {
                        return;
                    }
                    TS_ThreadWait.milliseconds(20);
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
                receivedNextCommand.execute(firstCommand);
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
                if (killMe) {
                    return;
                }
                e.printStackTrace();
            }

            @Override
            public void execute() {
                while (!killMe) {
                    TGS_UnSafe.execute(() -> {
                        waitForNewData();
                        appendToBuffer();
                        processBuffer();
                    }, e -> handleError(e));
                }
            }
            final private StringBuilder buffer = new StringBuilder();
        };
        TS_ThreadRun.now(killableThread.setName(d.className + ".connect"));
        return killableThread;
    }

    public static String name(SerialPort serialPort) {
        return serialPort.getDescriptivePortName();
    }

    public static boolean setup(SerialPort serialPort, int newBaudRate, int newDataBits, int newStopBits, int newParity) {
        var result = serialPort.setBaudRate(newBaudRate);
        if (!result) {
            d.ce("setup", "Error on setBaudRate");
            return false;
        }
        result = serialPort.setNumDataBits(newDataBits);
        if (!result) {
            d.ce("setup", "Error on setNumDataBits");
            return false;
        }
        result = serialPort.setNumStopBits(newStopBits);
        if (!result) {
            d.ce("setup", "Error on setNumStopBits");
            return false;
        }
        result = serialPort.setParity(newParity);
        if (!result) {
            d.ce("setup", "Error on setParity");
            return false;
        }
        return true;
    }

}
