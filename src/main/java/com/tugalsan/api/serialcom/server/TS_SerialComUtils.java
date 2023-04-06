package com.tugalsan.api.serialcom.server;

import com.fazecast.jSerialComm.*;
import com.tugalsan.api.executable.client.*;
import com.tugalsan.api.log.server.TS_Log;
import com.tugalsan.api.thread.server.*;
import com.tugalsan.api.unsafe.client.TGS_UnSafe;

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
        serialPort.setComPortTimeouts(SerialPort.TIMEOUT_NONBLOCKING, 0, 0);
        serialPort.openPort();
        var killableThread = new TS_ThreadExecutable() {

            private void waitForNewData() {
                while (serialPort.bytesAvailable() == 0) {
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
                buffer.append(new String(bytes));
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

    public static void test() {
        var serialPort = list()[0];
        d.cr("test", "serialPort.name = " + TS_SerialComUtils.name(
                serialPort
        ));
        d.cr("test", "setup.isSuccessfull = " + TS_SerialComUtils.setup(
                serialPort,
                115200,
                8,
                SerialPort.ONE_STOP_BIT,
                SerialPort.NO_PARITY
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

    /* 
    //ARDUINO CODE
    //KC868_A32 NodeMCU

    //PIN-INIT
    #include "PCF8574.h"
    TwoWire I2Cone = TwoWire(0);
    TwoWire I2Ctwo = TwoWire(1);
    PCF8574 pcf8574_R1(&I2Ctwo, 0x24, 15, 13);
    PCF8574 pcf8574_R2(&I2Ctwo, 0x25, 15, 13);
    PCF8574 pcf8574_R3(&I2Ctwo, 0x21, 15, 13);
    PCF8574 pcf8574_R4(&I2Ctwo, 0x22, 15, 13);
    PCF8574 pcf8574_I1(&I2Cone, 0x24, 4, 5);
    PCF8574 pcf8574_I2(&I2Cone, 0x25, 4, 5);
    PCF8574 pcf8574_I3(&I2Cone, 0x21, 4, 5);
    PCF8574 pcf8574_I4(&I2Cone, 0x22, 4, 5);

    //TIME-INIT
    unsigned long currentTime = millis();  // Current time
    unsigned long previousTime = 0;        // Previous time
    const long timeoutTime = 2000;         // Define timeout time in milliseconds (example: 2000ms = 2s)

    //SERIAL-INIT
    int ms_serialWaitUntillConnection = 3000;  //0 for forever, default 3000
    #define serialbufferSize 50
    char serial_lineFound_buffer[serialbufferSize];
    int serial_lineFound_index = 0;

    //FLAGS-INIT
    int flag_read_di = 0;

    //MAIN-INIT
    void setup() {
      //MAIN-INIT-PIN
      for (int i = 0; i <= 7; i++) {
        pcf8574_I1.pinMode(i, INPUT);
        pcf8574_I2.pinMode(i, INPUT);
        pcf8574_I3.pinMode(i, INPUT);
        pcf8574_I4.pinMode(i, INPUT);
        pcf8574_R1.pinMode(i, OUTPUT);
        pcf8574_R2.pinMode(i, OUTPUT);
        pcf8574_R3.pinMode(i, OUTPUT);
        pcf8574_R4.pinMode(i, OUTPUT);
      }
      pcf8574_I1.begin();
      pcf8574_I2.begin();
      pcf8574_I3.begin();
      pcf8574_I4.begin();
      pcf8574_R1.begin();
      pcf8574_R2.begin();
      pcf8574_R3.begin();
      pcf8574_R4.begin();
      for (int i = 0; i <= 7; i++) {
        pcf8574_R1.digitalWrite(i, HIGH);
        pcf8574_R2.digitalWrite(i, HIGH);
        pcf8574_R3.digitalWrite(i, HIGH);
        pcf8574_R4.digitalWrite(i, HIGH);
      }

      //MAIN-INIT-SERIAL
      Serial.begin(115200);
      if (ms_serialWaitUntillConnection == 0) {
        while (!Serial)
          ;
      } else {
        while (!Serial && (millis() < ms_serialWaitUntillConnection))
          ;
      }

      //MAIN-INIT-CARDINFO
      Serial.print("Starting WebServer on ");
      Serial.print(String(ARDUINO_BOARD));
      Serial.print("\n");

      //MAIN-INIT-PRINT-INTERFACE
      Serial.println("type hello");
      Serial.println();
    }

    //LOOP
    void loop() {
      //LOOP-HANDLE SERIAL COMMANDS
      if (serial_lineFound()) serial_lineProcess(serial_lineFound_buffer);
    }

    //SERIAL-LINE FINDER
    boolean serial_lineFound() {
      boolean lineFound = false;
      while (Serial.available() > 0) {
        char charBuffer = Serial.read();
        if (charBuffer == '\n') {//command received fully
          serial_lineFound_buffer[serial_lineFound_index] = 0;
          lineFound = (serial_lineFound_index > 0);
          serial_lineFound_index = 0;
        } else if (charBuffer == '\r') {//ignore
        } else if (serial_lineFound_index < serialbufferSize && lineFound == false) {//buffer up char
          serial_lineFound_buffer[serial_lineFound_index++] = charBuffer;
        }
      }
      return lineFound;
    }

    //SERIAL-LINE PROCESSOR
    void serial_lineProcess(char* commandBuffer) {
      if (strstr(commandBuffer, "hello")) {
        Serial.println("hello2u2");
      } else {
        Serial.print("I dont understand you. You said: ");
        Serial.println(commandBuffer);
      }
    }

     */
}
