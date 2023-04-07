package com.tugalsan.api.serialcom.server.test;

public class TS_SerialComTestArduinoCode {
    /* 
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

//STRINGTOKENIZER-INIT
// USAGE
// StringTokenizer tokens("aa:bb:cc:dd:ee", ":");
// while (tokens.hasNext()) {
//   Serial.println(tokens.nextToken());
// }
class StringTokenizer {
public:
  StringTokenizer(String str, String del);
  boolean hasNext();
  String nextToken();
private:
  String _str;
  String _del;
  int ptr;
};
StringTokenizer::StringTokenizer(String str, String del) {
  _str = str;
  _del = del;
  ptr = 0;
}
boolean StringTokenizer::hasNext() {
  if (ptr < _str.length()) {
    return true;
  } else {
    return false;
  }
}
String StringTokenizer::nextToken() {
  if (ptr >= _str.length()) {
    ptr = _str.length();
    return "";
  }
  String result = "";
  int delIndex = _str.indexOf(_del, ptr);
  if (delIndex == -1) {
    result = _str.substring(ptr);
    ptr = _str.length();
    return result;
  } else {
    result = _str.substring(ptr, delIndex);
    ptr = delIndex + _del.length();
    return result;
  }
}

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
    if (charBuffer == '\n') {  //command received fully
      serial_lineFound_buffer[serial_lineFound_index] = 0;
      lineFound = (serial_lineFound_index > 0);
      serial_lineFound_index = 0;
    } else if (charBuffer == '\r') {                                               //ignore
    } else if (serial_lineFound_index < serialbufferSize && lineFound == false) {  //buffer up char
      serial_lineFound_buffer[serial_lineFound_index++] = charBuffer;
    }
  }
  return lineFound;
}

//SERIAL-LINE PROCESSOR
void serial_lineProcess(char* commandBuffer) {
  if (strstr(commandBuffer, "hello")) {
    Serial.println("hello2u2");
  } else if (commandBuffer[0] == '!') {
      StringTokenizer tokens(String(commandBuffer), " ");
      while (tokens.hasNext()) {
        Serial.println(tokens.nextToken());
      }
  } else {
    Serial.print("I dont understand you. You said: ");
    Serial.println(commandBuffer);
  }
}


     */
}
