package com.tugalsan.api.serialcom.server;

import com.fazecast.jSerialComm.SerialPort;
import com.fazecast.jSerialComm.SerialPortDataListener;
import com.fazecast.jSerialComm.SerialPortEvent;
import com.tugalsan.api.thread.server.TS_ThreadWait;
import com.tugalsan.api.unsafe.client.TGS_UnSafe;

public class TS_SerialComUtils {

    public static void receiveTest() {
        var comPort = SerialPort.getCommPorts()[0];
        comPort.setComPortParameters(115200, 8, SerialPort.ONE_STOP_BIT, SerialPort.NO_PARITY);
        comPort.setComPortTimeouts(SerialPort.TIMEOUT_SCANNER, 0, 0);
        comPort.openPort();
        TGS_UnSafe.execute(() -> {
            while (true) {
                while (comPort.bytesAvailable() == 0) {
                    TS_ThreadWait.milliseconds(20);
                }
                var readBuffer = new byte[comPort.bytesAvailable()];
                var numRead = comPort.readBytes(readBuffer, readBuffer.length);
                System.out.println("Read " + numRead + " bytes.");
                comPort.writeBytes(readBuffer, numRead);
            }
        }, e -> {
            e.printStackTrace();
        });
        comPort.closePort();
    }

    public static void sendTest() {
        var comPort = SerialPort.getCommPorts()[0];
        comPort.setComPortParameters(9600, 8, SerialPort.ONE_STOP_BIT, SerialPort.NO_PARITY);
        comPort.setComPortTimeouts(SerialPort.TIMEOUT_SCANNER, 0, 0);
        comPort.openPort();
        comPort.addDataListener(new SerialPortDataListener() {
            @Override
            public int getListeningEvents() {
                return SerialPort.LISTENING_EVENT_DATA_AVAILABLE;
            }

            @Override
            public void serialEvent(SerialPortEvent event) {
                if (event.getEventType() != SerialPort.LISTENING_EVENT_DATA_AVAILABLE) {
                    return;
                }
                var newData = new byte[comPort.bytesAvailable()];
                var numRead = comPort.readBytes(newData, newData.length);
                System.out.println("Read " + numRead + " bytes. " + newData[0]);
            }
        });
        TS_ThreadWait.seconds(null, 2);//WAIT NEEDED
        var sendData = new byte[]{(byte) 0x53};
        comPort.writeBytes(sendData, 1);
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
