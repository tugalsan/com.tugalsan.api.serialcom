package com.tugalsan.api.serialcom.server;

import com.fazecast.jSerialComm.*;
import com.tugalsan.api.executable.client.*;
import com.tugalsan.api.log.server.TS_Log;
import com.tugalsan.api.thread.server.*;

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

    public static SerialPort[] list() {
        return SerialPort.getCommPorts();
    }

    public static boolean disconnect(SerialPort serialPort) {
        serialPort.removeDataListener();
        TS_ThreadWait.seconds(null, 2);//FOR ARDUINO
        return serialPort.closePort();
    }

    public static boolean connect(SerialPort serialPort, TGS_ExecutableType1<String> receivedData_Len) {
        boolean result;
        result = serialPort.setComPortTimeouts(SerialPort.TIMEOUT_SCANNER, 0, 0);
        if (!result) {
            d.ce("connect", "Error on setComPortTimeouts");
            return false;
        }
        result = serialPort.openPort();
        if (!result) {
            d.ce("connect", "Error on openPort");
            return false;
        }
        result = serialPort.addDataListener(new SerialPortDataListener() {
            @Override
            public int getListeningEvents() {
                return SerialPort.LISTENING_EVENT_DATA_AVAILABLE;
            }

            @Override
            public void serialEvent(SerialPortEvent event) {
                if (event.getEventType() != SerialPort.LISTENING_EVENT_DATA_AVAILABLE) {
                    d.ce("connect", "SerialPortDataListener", "serialEvent", "skip.event.getEventType", event.getEventType());
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
        if (!result) {
            d.ce("connect", "Error on addDataListener");
            return false;
        }
        TS_ThreadWait.seconds(null, 2);//WAIT NEEDED
        return true;
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

    //TODO WRITE A BUILDER
    public static void sendTest() {
        var serialPort = list()[0];
        d.cr("sendTest", "serialPort.name = " + name(serialPort));
        d.cr("sendTest", "setup.isSuccessfull = " + setup(serialPort, 115200, 8, SerialPort.ONE_STOP_BIT, SerialPort.NO_PARITY));
        d.cr("sendTest", "connect.isSuccessfull = " + connect(serialPort, receivedData -> d.cr("sendTest", "Read as '" + receivedData + "'")));
        d.cr("sendTest", "send.isSuccessfull = " + send(serialPort, "test me out"));
        d.cr("sendTest", "send.isSuccessfull = " + send(serialPort, "test me out"));
        d.cr("sendTest", "send.isSuccessfull = " + send(serialPort, "test me out"));
        d.cr("sendTest", "send.isSuccessfull = " + send(serialPort, "test me out"));
        d.cr("sendTest", "disconnect.isSuccessfull = " + disconnect(serialPort));
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
