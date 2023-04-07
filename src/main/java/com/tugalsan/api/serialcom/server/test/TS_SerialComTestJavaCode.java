package com.tugalsan.api.serialcom.server.test;

import com.tugalsan.api.log.server.*;
import com.tugalsan.api.serialcom.server.*;
import com.tugalsan.api.serialcom.server.utils.TS_SerialComUtils;
import com.tugalsan.api.thread.server.TS_ThreadWait;
import java.util.stream.IntStream;

public class TS_SerialComTestJavaCode {

    final private static TS_Log d = TS_Log.of(TS_SerialComTestJavaCode.class);

    public static void testBuilder() {
        d.cr("testBuilder", "------------------------------------------");
        TS_SerialComBuilder.portFirst()
                .baudRate_115200().dataBits_8().oneStopBit().parityNone()
                .onReceived(command -> d.cr("onReceived", command))
                .use(con -> {
                    d.cr("con.name()", con.name());
                    d.cr("con.success_portConnect()", con.success_portConnect());
                    d.cr("con.success_portPresent()", con.success_portPresent());
                    d.cr("con.success_portSetup()", con.success_portSetup());
                    d.cr("con.isConnected()", con.isConnected());
                    IntStream.range(0, 5).forEach(i -> {
                        d.cr("send.successful?", con.send("hello"));
                        d.cr("send.successful?", con.send("naber?"));
                        TS_ThreadWait.seconds(null, 1);
                    });
                });
    }

    public static void testUtils() {
        d.cr("testUtils", "------------------------------------------");
        var serialPortList = TS_SerialComUtils.list();
        if (serialPortList.isEmpty()) {
            d.cr("test", "No serial port detected");
            return;
        }
        var serialPort = serialPortList.get(0);
        d.cr("test", "serialPort.name = " + TS_SerialComUtils.name(
                serialPort
        ));
        d.cr("test", "setup.isSuccessfull = " + TS_SerialComUtils.setup(
                serialPort, 115200, 8,
                TS_SerialComUtils.STOP_BITS.ONE_STOP_BIT,
                TS_SerialComUtils.PARITY.NO_PARITY
        ));
        d.cr("test", "connect.isSuccessfull = " + TS_SerialComUtils.connect(
                serialPort,
                receivedNextCommand -> {
                    d.cr("test", "receivedNextCommand", receivedNextCommand);
                }
        ));
        IntStream.range(0, 5).forEach(i -> {
            d.cr("test", "send.isSuccessfull = " + TS_SerialComUtils.send(
                    serialPort,
                    "hello"
            ));
            d.cr("test", "send.isSuccessfull = " + TS_SerialComUtils.send(
                    serialPort,
                    "dosomething"
            ));
            TS_ThreadWait.seconds(null, 1);
        });
        d.cr("test", "disconnect.isSuccessfull = " + TS_SerialComUtils.disconnect(
                serialPort
        ));
    }
}