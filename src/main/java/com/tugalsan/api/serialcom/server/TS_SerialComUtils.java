package com.tugalsan.api.serialcom.server;

import com.fazecast.jSerialComm.SerialPort;
import com.tugalsan.api.unsafe.client.TGS_UnSafe;

public class TS_SerialComUtils {

    public static void main(String... s) {
        var comPort = SerialPort.getCommPorts()[0];
        comPort.openPort();
        TGS_UnSafe.execute(() -> {
            while (true) {
                while (comPort.bytesAvailable() == 0) {
                    Thread.sleep(20);
                }
                var readBuffer = new byte[comPort.bytesAvailable()];
                var numRead = comPort.readBytes(readBuffer, readBuffer.length);
                System.out.println("Read " + numRead + " bytes.");
            }
        }, e -> {
            e.printStackTrace();
        });
        comPort.closePort();
    }
}
