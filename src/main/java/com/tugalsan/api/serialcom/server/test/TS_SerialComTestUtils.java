package com.tugalsan.api.serialcom.server.test;

import com.fazecast.jSerialComm.*;
import com.tugalsan.api.log.server.*;
import com.tugalsan.api.serialcom.server.utils.*;

public class TS_SerialComTestUtils {

    final private static TS_Log d = TS_Log.of(TS_SerialComTestUtils.class);

    public static void test() {
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
                serialPort,
                115200,
                8,
                SerialPort.ONE_STOP_BIT,//ONE_POINT_FIVE_STOP_BITS / TWO_STOP_BITS
                SerialPort.NO_PARITY //ODD_PARITY EVEN_PARITY 
        ));
        d.cr("test", "connect.isSuccessfull = " + TS_SerialComUtils.connect(
                serialPort,
                receivedNextCommand -> {
                    d.cr("test", "receivedNextCommand", receivedNextCommand);
                }
        ));
        d.cr("test", "send.isSuccessfull = " + TS_SerialComUtils.send(
                serialPort,
                "hello"
        ));
        d.cr("test", "send.isSuccessfull = " + TS_SerialComUtils.send(
                serialPort,
                "dosomething"
        ));
        d.cr("test", "disconnect.isSuccessfull = " + TS_SerialComUtils.disconnect(
                serialPort
        ));
    }

}
