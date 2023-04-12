package com.tugalsan.api.serialcom.server.test;

public class TS_SerialComTestArduinoCode {
    /* 
   //USAGE: stringUtils.isInt("ad") -> true/false
class TA_StringUtils {
public:
  TA_StringUtils();
  bool isInt(String st);
private:
};
TA_StringUtils::TA_StringUtils() {
}
bool TA_StringUtils::isInt(String str) {
  for (byte i = 0; i < str.length(); i++) {
    if (isDigit(str.charAt(i))) return true;
  }
  return false;
}
TA_StringUtils stringUtils;

// USAGE
// TA_StringTokenizer tokens("aa:bb:cc:dd:ee", ":");
// while (tokens.hasNext()) {
//   Serial.println(tokens.nextToken());
// }
class TA_StringTokenizer {
public:
  TA_StringTokenizer(String str, String del);
  bool hasNext();
  String nextToken();
private:
  String _str;
  String _del;
  int ptr;
};
TA_StringTokenizer::TA_StringTokenizer(String str, String del) {
  _str = str;
  _del = del;
  ptr = 0;
}
bool TA_StringTokenizer::hasNext() {
  if (ptr < _str.length()) {
    return true;
  } else {
    return false;
  }
}
String TA_StringTokenizer::nextToken() {
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

//TIME
//USAGE: void loop() { time.next();
class TA_Time {
public:
  TA_Time();
  void nextLoop();
  unsigned long current();
  unsigned long previous();
  unsigned long delta();
private:
  unsigned long _current;
  unsigned long _previous;
  unsigned long _delta;
};
TA_Time::TA_Time() {
  _current = millis();
  nextLoop();
}
unsigned long TA_Time::current() {
  return _current;
}
unsigned long TA_Time::previous() {
  return _previous;
}
unsigned long TA_Time::delta() {
  return _delta;
}
void TA_Time::nextLoop() {
  _previous = _current;
  _current = millis();
  _delta = _current - _previous;
}
TA_Time time();

//SERIAL
#define TA_Serial_BUFFER_SIZE 30
#define TA_Serial_WAIT_IN_SECONDS 3
class TA_Serial {
public:
  TA_Serial(bool waitUntilConnection, int _baudRate);
  bool waitUntilConnection();
  int bufferSize();
  void setup();
  bool hasNext();
  String next();
private:
  int _baudRate;
  bool _waitUntilConnection;
  int _bufferSize;
  char _buffer[TA_Serial_BUFFER_SIZE];
  int _bufferIdx;
};
TA_Serial::TA_Serial(bool waitUntilConnection, int baudrate) {
  _waitUntilConnection = waitUntilConnection;
  _bufferSize = TA_Serial_BUFFER_SIZE;
  int _bufferIdx = 0;
}
void TA_Serial::setup() {
  Serial.begin(_baudRate);
  if (_waitUntilConnection) {
    while (!Serial)
      ;
  } else {
    while (!Serial && (millis() < TA_Serial_WAIT_IN_SECONDS * 1000))
      ;
  }
}
String TA_Serial::next() {
  return String(_buffer);
}
bool TA_Serial::hasNext() {
  bool lineFound = false;
  while (Serial.available() > 0) {
    char chr = Serial.read();
    if (chr == '\r') {         //ignore
    } else if (chr == '\n') {  //command received fully
      _buffer[_bufferIdx] = 0;
      lineFound = _bufferIdx > 0;
      _bufferIdx = 0;
    } else if (_bufferIdx < _bufferSize && lineFound == false) {  //buffer up char
      _buffer[_bufferIdx++] = chr;
    }
  }
  return lineFound;
}
TA_Serial serial(true, 115200);

//TA_Chip_KinCony_KC868_A32_R1_2
#include "PCF8574.h"
TwoWire _I2C_1 = TwoWire(0);
TwoWire _I2C_2 = TwoWire(1);
PCF8574 _pcf8574_R1(&_I2C_2, 0x24, 15, 13);
PCF8574 _pcf8574_R2(&_I2C_2, 0x25, 15, 13);
PCF8574 _pcf8574_R3(&_I2C_2, 0x21, 15, 13);
PCF8574 _pcf8574_R4(&_I2C_2, 0x22, 15, 13);
PCF8574 _pcf8574_I1(&_I2C_1, 0x24, 4, 5);
PCF8574 _pcf8574_I2(&_I2C_1, 0x25, 4, 5);
PCF8574 _pcf8574_I3(&_I2C_1, 0x21, 4, 5);
PCF8574 _pcf8574_I4(&_I2C_1, 0x22, 4, 5);
class TA_Chip_KinCony_KC868_A32_R1_2 {
public:
  TA_Chip_KinCony_KC868_A32_R1_2();
  String name();
  void setup();
  bool getDI_fr1_to32(int pinIdx);
  bool getDO_fr1_to32(int pinIdx);
  void setDO_fr1_to32(int pinIdx, bool value);
private:
};
TA_Chip_KinCony_KC868_A32_R1_2::TA_Chip_KinCony_KC868_A32_R1_2() {
}
bool TA_Chip_KinCony_KC868_A32_R1_2::getDI_fr1_to32(int pinIdx) {
  if (pinIdx <= 0) {
    return false;
  }
  if (pinIdx <= 8) {
    if (pinIdx == 1) return _pcf8574_I1.digitalRead(P0) == HIGH;
    if (pinIdx == 2) return _pcf8574_I1.digitalRead(P1) == HIGH;
    if (pinIdx == 3) return _pcf8574_I1.digitalRead(P2) == HIGH;
    if (pinIdx == 4) return _pcf8574_I1.digitalRead(P3) == HIGH;
    if (pinIdx == 5) return _pcf8574_I1.digitalRead(P4) == HIGH;
    if (pinIdx == 6) return _pcf8574_I1.digitalRead(P5) == HIGH;
    if (pinIdx == 7) return _pcf8574_I1.digitalRead(P6) == HIGH;
    if (pinIdx == 8) return _pcf8574_I1.digitalRead(P7) == HIGH;
  }
  if (pinIdx <= 16) {
    if (pinIdx == 9) return _pcf8574_I2.digitalRead(P0) == HIGH;
    if (pinIdx == 10) return _pcf8574_I2.digitalRead(P1) == HIGH;
    if (pinIdx == 11) return _pcf8574_I2.digitalRead(P2) == HIGH;
    if (pinIdx == 12) return _pcf8574_I2.digitalRead(P3) == HIGH;
    if (pinIdx == 13) return _pcf8574_I2.digitalRead(P4) == HIGH;
    if (pinIdx == 14) return _pcf8574_I2.digitalRead(P5) == HIGH;
    if (pinIdx == 15) return _pcf8574_I2.digitalRead(P6) == HIGH;
    if (pinIdx == 16) return _pcf8574_I2.digitalRead(P7) == HIGH;
  }
  if (pinIdx <= 24) {
    if (pinIdx == 17) return _pcf8574_I3.digitalRead(P0) == HIGH;
    if (pinIdx == 18) return _pcf8574_I3.digitalRead(P1) == HIGH;
    if (pinIdx == 19) return _pcf8574_I3.digitalRead(P2) == HIGH;
    if (pinIdx == 20) return _pcf8574_I3.digitalRead(P3) == HIGH;
    if (pinIdx == 21) return _pcf8574_I3.digitalRead(P4) == HIGH;
    if (pinIdx == 22) return _pcf8574_I3.digitalRead(P5) == HIGH;
    if (pinIdx == 23) return _pcf8574_I3.digitalRead(P6) == HIGH;
    if (pinIdx == 24) return _pcf8574_I3.digitalRead(P7) == HIGH;
  }
  if (pinIdx <= 32) {
    if (pinIdx == 25) return _pcf8574_I4.digitalRead(P0) == HIGH;
    if (pinIdx == 26) return _pcf8574_I4.digitalRead(P1) == HIGH;
    if (pinIdx == 27) return _pcf8574_I4.digitalRead(P2) == HIGH;
    if (pinIdx == 28) return _pcf8574_I4.digitalRead(P3) == HIGH;
    if (pinIdx == 29) return _pcf8574_I4.digitalRead(P4) == HIGH;
    if (pinIdx == 30) return _pcf8574_I4.digitalRead(P5) == HIGH;
    if (pinIdx == 31) return _pcf8574_I4.digitalRead(P6) == HIGH;
    if (pinIdx == 32) return _pcf8574_I4.digitalRead(P7) == HIGH;
  }
  return false;
}
bool TA_Chip_KinCony_KC868_A32_R1_2::getDO_fr1_to32(int pinIdx) {
  if (pinIdx <= 0) {
    return false;
  }
  if (pinIdx <= 8) {
    if (pinIdx == 1) return _pcf8574_R1.digitalRead(P0) == HIGH;
    if (pinIdx == 2) return _pcf8574_R1.digitalRead(P1) == HIGH;
    if (pinIdx == 3) return _pcf8574_R1.digitalRead(P2) == HIGH;
    if (pinIdx == 4) return _pcf8574_R1.digitalRead(P3) == HIGH;
    if (pinIdx == 5) return _pcf8574_R1.digitalRead(P4) == HIGH;
    if (pinIdx == 6) return _pcf8574_R1.digitalRead(P5) == HIGH;
    if (pinIdx == 7) return _pcf8574_R1.digitalRead(P6) == HIGH;
    if (pinIdx == 8) return _pcf8574_R1.digitalRead(P7) == HIGH;
  }
  if (pinIdx <= 16) {
    if (pinIdx == 9) return _pcf8574_R2.digitalRead(P0) == HIGH;
    if (pinIdx == 10) return _pcf8574_R2.digitalRead(P1) == HIGH;
    if (pinIdx == 11) return _pcf8574_R2.digitalRead(P2) == HIGH;
    if (pinIdx == 12) return _pcf8574_R2.digitalRead(P3) == HIGH;
    if (pinIdx == 13) return _pcf8574_R2.digitalRead(P4) == HIGH;
    if (pinIdx == 14) return _pcf8574_R2.digitalRead(P5) == HIGH;
    if (pinIdx == 15) return _pcf8574_R2.digitalRead(P6) == HIGH;
    if (pinIdx == 16) return _pcf8574_R2.digitalRead(P7) == HIGH;
  }
  if (pinIdx <= 24) {
    if (pinIdx == 17) return _pcf8574_R3.digitalRead(P0) == HIGH;
    if (pinIdx == 18) return _pcf8574_R3.digitalRead(P1) == HIGH;
    if (pinIdx == 19) return _pcf8574_R3.digitalRead(P2) == HIGH;
    if (pinIdx == 20) return _pcf8574_R3.digitalRead(P3) == HIGH;
    if (pinIdx == 21) return _pcf8574_R3.digitalRead(P4) == HIGH;
    if (pinIdx == 22) return _pcf8574_R3.digitalRead(P5) == HIGH;
    if (pinIdx == 23) return _pcf8574_R3.digitalRead(P6) == HIGH;
    if (pinIdx == 24) return _pcf8574_R3.digitalRead(P7) == HIGH;
  }
  if (pinIdx <= 32) {
    if (pinIdx == 25) return _pcf8574_R4.digitalRead(P0) == HIGH;
    if (pinIdx == 26) return _pcf8574_R4.digitalRead(P1) == HIGH;
    if (pinIdx == 27) return _pcf8574_R4.digitalRead(P2) == HIGH;
    if (pinIdx == 28) return _pcf8574_R4.digitalRead(P3) == HIGH;
    if (pinIdx == 29) return _pcf8574_R4.digitalRead(P4) == HIGH;
    if (pinIdx == 30) return _pcf8574_R4.digitalRead(P5) == HIGH;
    if (pinIdx == 31) return _pcf8574_R4.digitalRead(P6) == HIGH;
    if (pinIdx == 32) return _pcf8574_R4.digitalRead(P7) == HIGH;
  }
  return false;
}
void TA_Chip_KinCony_KC868_A32_R1_2::setDO_fr1_to32(int pinIdx, bool value) {
  if (pinIdx <= 0) {
    return;
  }
  if (pinIdx <= 8) {
    if (pinIdx == 1) _pcf8574_R1.digitalWrite(P6, value ? HIGH : LOW);
    if (pinIdx == 2) _pcf8574_R1.digitalWrite(P1, value ? HIGH : LOW);
    if (pinIdx == 3) _pcf8574_R1.digitalWrite(P2, value ? HIGH : LOW);
    if (pinIdx == 4) _pcf8574_R1.digitalWrite(P3, value ? HIGH : LOW);
    if (pinIdx == 5) _pcf8574_R1.digitalWrite(P4, value ? HIGH : LOW);
    if (pinIdx == 6) _pcf8574_R1.digitalWrite(P5, value ? HIGH : LOW);
    if (pinIdx == 7) _pcf8574_R1.digitalWrite(P6, value ? HIGH : LOW);
    if (pinIdx == 8) _pcf8574_R1.digitalWrite(P7, value ? HIGH : LOW);
    return;
  }
  if (pinIdx <= 16) {
    if (pinIdx == 9) _pcf8574_R2.digitalWrite(P0, value ? HIGH : LOW);
    if (pinIdx == 10) _pcf8574_R2.digitalWrite(P1, value ? HIGH : LOW);
    if (pinIdx == 11) _pcf8574_R2.digitalWrite(P2, value ? HIGH : LOW);
    if (pinIdx == 12) _pcf8574_R2.digitalWrite(P3, value ? HIGH : LOW);
    if (pinIdx == 13) _pcf8574_R2.digitalWrite(P4, value ? HIGH : LOW);
    if (pinIdx == 14) _pcf8574_R2.digitalWrite(P5, value ? HIGH : LOW);
    if (pinIdx == 15) _pcf8574_R2.digitalWrite(P6, value ? HIGH : LOW);
    if (pinIdx == 16) _pcf8574_R2.digitalWrite(P7, value ? HIGH : LOW);
    return;
  }
  if (pinIdx <= 24) {
    if (pinIdx == 17) _pcf8574_R3.digitalWrite(P0, value ? HIGH : LOW);
    if (pinIdx == 18) _pcf8574_R3.digitalWrite(P1, value ? HIGH : LOW);
    if (pinIdx == 19) _pcf8574_R3.digitalWrite(P2, value ? HIGH : LOW);
    if (pinIdx == 20) _pcf8574_R3.digitalWrite(P3, value ? HIGH : LOW);
    if (pinIdx == 21) _pcf8574_R3.digitalWrite(P4, value ? HIGH : LOW);
    if (pinIdx == 22) _pcf8574_R3.digitalWrite(P5, value ? HIGH : LOW);
    if (pinIdx == 23) _pcf8574_R3.digitalWrite(P6, value ? HIGH : LOW);
    if (pinIdx == 24) _pcf8574_R3.digitalWrite(P7, value ? HIGH : LOW);
    return;
  }
  if (pinIdx <= 32) {
    if (pinIdx == 25) _pcf8574_R4.digitalWrite(P0, value ? HIGH : LOW);
    if (pinIdx == 26) _pcf8574_R4.digitalWrite(P1, value ? HIGH : LOW);
    if (pinIdx == 27) _pcf8574_R4.digitalWrite(P2, value ? HIGH : LOW);
    if (pinIdx == 28) _pcf8574_R4.digitalWrite(P3, value ? HIGH : LOW);
    if (pinIdx == 29) _pcf8574_R4.digitalWrite(P4, value ? HIGH : LOW);
    if (pinIdx == 30) _pcf8574_R4.digitalWrite(P5, value ? HIGH : LOW);
    if (pinIdx == 31) _pcf8574_R4.digitalWrite(P6, value ? HIGH : LOW);
    if (pinIdx == 32) _pcf8574_R4.digitalWrite(P7, value ? HIGH : LOW);
    return;
  }
  return;
}
void TA_Chip_KinCony_KC868_A32_R1_2::setup() {
  for (int i = 0; i <= 7; i++) {
    _pcf8574_I1.pinMode(i, INPUT);
    _pcf8574_I2.pinMode(i, INPUT);
    _pcf8574_I3.pinMode(i, INPUT);
    _pcf8574_I4.pinMode(i, INPUT);
    _pcf8574_R1.pinMode(i, OUTPUT);
    _pcf8574_R2.pinMode(i, OUTPUT);
    _pcf8574_R3.pinMode(i, OUTPUT);
    _pcf8574_R4.pinMode(i, OUTPUT);
  }
  String error = F("ERROR_pcf8574_");
  if (_pcf8574_I1.begin() == false) {
    Serial.print(error);
    Serial.println(F("I1"));
  }
  if (_pcf8574_I2.begin() == false) {
    Serial.print(error);
    Serial.println(F("I2"));
  }
  if (_pcf8574_I3.begin() == false) {
    Serial.print(error);
    Serial.println(F("I3"));
  }
  if (_pcf8574_I4.begin() == false) {
    Serial.print(error);
    Serial.println(F("I4"));
  }
  if (_pcf8574_R1.begin() == false) {
    Serial.print(error);
    Serial.println(F("R1"));
  }
  if (_pcf8574_R2.begin() == false) {
    Serial.print(error);
    Serial.println(F("R2"));
  }
  if (_pcf8574_R3.begin() == false) {
    Serial.print(error);
    Serial.println(F("R3"));
  }
  if (_pcf8574_R4.begin() == false) {
    Serial.print(error);
    Serial.println(F("R4"));
  }
  for (int i = 0; i <= 7; i++) {
    _pcf8574_R1.digitalWrite(i, HIGH);
    _pcf8574_R2.digitalWrite(i, HIGH);
    _pcf8574_R3.digitalWrite(i, HIGH);
    _pcf8574_R4.digitalWrite(i, HIGH);
  }
}
String TA_Chip_KinCony_KC868_A32_R1_2::name() {
  return String(ARDUINO_BOARD);
}
TA_Chip_KinCony_KC868_A32_R1_2 chip;

//MAIN
void setup() {
  serial.setup();
  chip.setup();

  Serial.print(F("Chip.name: "));
  Serial.print(chip.name());
  Serial.print(F("\n"));

  Serial.println(F("type hello"));
}

//THREAD
void loop() {
  //time.nextLoop(); NOT WORKING, NEED HELP
  if (serial.hasNext()) forEach(serial.next());
  delay(20);
}

void forEach(String command) {
  //INIT
  if (!command.startsWith(F("!"))) {
    Serial.print(F("ERROR_CMD_TEMPLATE_UNKNOWN: "));
    Serial.println(command);
    return;
  }

  //COMMAND_NAME
  TA_StringTokenizer tokens(command, F(" "));
  if (!tokens.hasNext()) {
    Serial.print(F("ERROR_CMD_UNCOMPLETE: "));
    Serial.println(command);
    return;
  }
  String cmdName = tokens.nextToken();

  //IF COMMAND CHIP_NAME
  if (cmdName.equals("!CHIP_NAME")) {
    Serial.println(chip.name());
    return;
  }

  //PIN_NUMBER_NAME
  if (!tokens.hasNext()) {
    Serial.print(F("ERROR_CMD_PIN_NUMBER_NAME_UNCOMPLETE: "));
    Serial.println(command);
    return;
  }
  String pinNumberName = tokens.nextToken();

  //PIN_NUMBER
  if (!stringUtils.isInt(pinNumberName)) {
    Serial.print(F("ERROR_CMD_PIN_NAME_NOT_NUMBER: "));
    Serial.println(command);
    return;
  }
  int pinNumber = pinNumberName.toInt();

  //IF COMMAND DO_GET
  if (cmdName.equals("!DO_GET")) {
    Serial.print(command);
    Serial.print(F("->"));
    Serial.println(chip.getDO_fr1_to32(pinNumber));
    return;
  }

  //IF COMMAND DI_GET
  if (cmdName.equals("!DI_GET")) {
    Serial.print(command);
    Serial.print(F("->"));
    Serial.println(chip.getDI_fr1_to32(pinNumber));
    return;
  }

  if (cmdName.equals("!DO_SET_TRUE")) {
    chip.setDO_fr1_to32(pinNumber, true);
    Serial.print(command);
    Serial.println(F("-> DONE"));
    return;
  }

  if (cmdName.equals("!DO_SET_FALSE")) {
    chip.setDO_fr1_to32(pinNumber, false);
    Serial.print(command);
    Serial.println(F("-> DONE"));
    return;
  }

  Serial.print(F("ERROR_CMD_NOT SUPPORTED: "));
  Serial.println(command);
  return;
}


     */
}
