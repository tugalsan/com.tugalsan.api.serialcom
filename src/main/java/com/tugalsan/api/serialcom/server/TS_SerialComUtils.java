package com.tugalsan.api.serialcom.server;

import com.fazecast.jSerialComm.*;
import com.tugalsan.api.executable.client.*;
import com.tugalsan.api.log.server.TS_Log;
import com.tugalsan.api.thread.server.*;

public class TS_SerialComUtils {

    final private static TS_Log d = TS_Log.of(TS_SerialComUtils.class);

    private static boolean send(SerialPort serialPort, byte[] byteArray) {
        return serialPort.writeBytes(byteArray, byteArray.length) != -1;
    }

    public static boolean send(SerialPort serialPort, String string) {
        return send(serialPort, string.getBytes());
    }

    public static SerialPort[] list() {
        return SerialPort.getCommPorts();
    }

    private static SerialPort connect(SerialPort serialPort) {
        serialPort.setComPortParameters(9600, 8, SerialPort.ONE_STOP_BIT, SerialPort.NO_PARITY);
        serialPort.setComPortTimeouts(SerialPort.TIMEOUT_SCANNER, 0, 0);
        serialPort.openPort();
        return serialPort;
    }

    public static boolean disconnect(SerialPort serialPort) {
        serialPort.removeDataListener();
        return serialPort.closePort();
    }

    public static SerialPort on(SerialPort serialPort, TGS_ExecutableType1<String> receivedData_Len) {
        connect(serialPort);
        serialPort.addDataListener(new SerialPortDataListener() {
            @Override
            public int getListeningEvents() {
                return SerialPort.LISTENING_EVENT_DATA_AVAILABLE;
            }

            @Override
            public void serialEvent(SerialPortEvent event) {
                if (event.getEventType() != SerialPort.LISTENING_EVENT_DATA_AVAILABLE) {
                    return;
                }
                var receivedData = new byte[serialPort.bytesAvailable()];
                var receivedLen = serialPort.readBytes(receivedData, receivedData.length);
                if (receivedLen == -1) {
                    receivedData_Len.execute("");
                }
                receivedData_Len.execute(new String(receivedData));
            }
        });
        TS_ThreadWait.seconds(null, 2);//WAIT NEEDED
        return serialPort;
    }

    public static String name(SerialPort serialPort) {
        return serialPort.getDescriptivePortName();
    }

    public static void sendTest() {
        var serialPort = list()[0];
        d.cr("serialPort.name = " + name(serialPort));
        on(serialPort, receivedData -> d.cr("Read as '" + receivedData + "'"));
        d.cr("send.isSuccessfull = " + send(serialPort, "test me out"));
        d.cr("disconnect.isSuccessfull = " + disconnect(serialPort));
    }

    /* arduino
    int inByte = 0;         // incoming serial byte

void setup() {
  // start serial port at 9600 bps:
  Serial.begin(9600);
  while (!Serial) {
    ; // wait for serial port to connect. Needed for native USB port only
  }

  establishContact();  // send a byte to establish contact until receiver responds
}

void loop() {
  // if we get a valid byte, read analog ins:
  if (Serial.available() > 0) {
    // get incoming byte:
    inByte = Serial.read();
    Serial.print('B');
  }
}

void establishContact() {
  while (Serial.available() <= 0) {
    Serial.print('A');   // send a capital A
    delay(300);
  }
}
     */
}
