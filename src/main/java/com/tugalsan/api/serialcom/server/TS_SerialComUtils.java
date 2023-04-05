package com.tugalsan.api.serialcom.server;

import com.fazecast.jSerialComm.*;
import com.tugalsan.api.executable.client.*;
import com.tugalsan.api.thread.server.*;

public class TS_SerialComUtils {

    private static boolean send(SerialPort serialPort, byte[] byteArray) {
        return serialPort.writeBytes(byteArray, byteArray.length) != -1;
    }

    public static boolean send(SerialPort serialPort, String string) {
        return send(serialPort, string.getBytes());
    }

    public static SerialPort[] list() {
        return SerialPort.getCommPorts();
    }

    public static SerialPort connect(SerialPort serialPort) {
        serialPort.setComPortParameters(9600, 8, SerialPort.ONE_STOP_BIT, SerialPort.NO_PARITY);
        serialPort.setComPortTimeouts(SerialPort.TIMEOUT_SCANNER, 0, 0);
        serialPort.openPort();
        return serialPort;
    }

    public static SerialPort on(SerialPort serialPort, TGS_ExecutableType2<String, Integer> receivedData_Len) {
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
                receivedData_Len.execute(new String(receivedData), receivedLen);
            }
        });
        TS_ThreadWait.seconds(null, 2);//WAIT NEEDED
        return serialPort;
    }

    public static void sendTest() {
        send(on(connect(list()[0]), (receivedData, Len) -> {
            System.out.println("Read " + Len + " bytes as '" + receivedData + "'");
        }), "test me out");
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
