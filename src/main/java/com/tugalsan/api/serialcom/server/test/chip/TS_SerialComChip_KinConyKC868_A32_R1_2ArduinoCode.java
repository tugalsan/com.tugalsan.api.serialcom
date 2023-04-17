package com.tugalsan.api.serialcom.server.test.chip;

public class TS_SerialComChip_KinConyKC868_A32_R1_2ArduinoCode {
    /* TODO
        - WRITE A TUTORIAL HOW TO SETUP ANDROID    
        - WRITE CLASS TA_DIHandler_SurfaceTreatmentBath16
    */
    
    
    /* 
 //------------------------------------ DEFINE -----------------------------------------------------------------------

#define TA_TimeHandler_DELAY_MS 20
#define TA_SerialConnection_WAIT_UNTIL_SECONDS 3
#define TA_SerialConnection_WAIT_UNTIL_CONNECTION true
#define TA_SerialConnection_WAIT_IN_BAUDRATE 115200
#define TA_SerialCommandFetcher_BUFFER_SIZE 60
#define INFO_TA_SerialCommandHandler false

//------------------------------------ STRING HANDLER -----------------------------------------------------------------------

//USAGE: stringHandler.isInt("ad") -> true/false
class TA_StringHandler {
public:
  TA_StringHandler();
  bool isInt(String st);
private:
};
TA_StringHandler::TA_StringHandler() {
}
bool TA_StringHandler::isInt(String str) {
  for (byte i = 0; i < str.length(); i++) {
    if (isDigit(str.charAt(i))) return true;
  }
  return false;
}
TA_StringHandler stringHandler;

//------------------------------------ STRING TOKENIZER -----------------------------------------------------------------------

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
  int _ptr;
};
TA_StringTokenizer::TA_StringTokenizer(String str, String del) {
  _str = str;
  _del = del;
  _ptr = 0;
}
bool TA_StringTokenizer::hasNext() {
  if (_ptr < _str.length()) {
    return true;
  } else {
    return false;
  }
}
String TA_StringTokenizer::nextToken() {
  if (_ptr >= _str.length()) {
    _ptr = _str.length();
    return "";
  }
  String result = "";
  int delIndex = _str.indexOf(_del, _ptr);
  if (delIndex == -1) {
    result = _str.substring(_ptr);
    _ptr = _str.length();
    return result;
  } else {
    result = _str.substring(_ptr, delIndex);
    _ptr = delIndex + _del.length();
    return result;
  }
}

//------------------------------------ TIME HANDLER -----------------------------------------------------------------------

//USAGE: void loop() { time.loop();
class TA_TimeHandler {
public:
  TA_TimeHandler();
  void loop();
  unsigned long current();
  unsigned long previous();
  unsigned long delta();
private:
  unsigned long _current;
  unsigned long _previous;
  unsigned long _delta;
};
TA_TimeHandler::TA_TimeHandler() {
  _current = millis();
  loop();
}
unsigned long TA_TimeHandler::current() {
  return _current;
}
unsigned long TA_TimeHandler::previous() {
  return _previous;
}
unsigned long TA_TimeHandler::delta() {
  return _delta;
}
void TA_TimeHandler::loop() {
  delay(TA_TimeHandler_DELAY_MS);
  _previous = _current;
  _current = millis();
  _delta = _current - _previous;
}
TA_TimeHandler timeHandler;

//------------------------------------ SERIAL CONNECTION -----------------------------------------------------------------------

class TA_SerialConnection {
public:
  TA_SerialConnection(bool waitUntilConnection, int baudRate);
  bool waitUntilConnection();
  void setup();
private:
  int _baudRate;
  bool _waitUntilConnection;
};
TA_SerialConnection::TA_SerialConnection(bool waitUntilConnection, int baudRate) {
  _waitUntilConnection = waitUntilConnection;
  _baudRate = _baudRate;
}
void TA_SerialConnection::setup() {
  Serial.begin(_baudRate);
  if (_waitUntilConnection) {
    while (!Serial)
      ;
  } else {
    while (!Serial && (millis() < TA_SerialConnection_WAIT_UNTIL_SECONDS * 1000))
      ;
  }
}
TA_SerialConnection serialConnection(TA_SerialConnection_WAIT_UNTIL_CONNECTION, TA_SerialConnection_WAIT_IN_BAUDRATE);


//------------------------------------ SERIAL COMMAND FETCHER -----------------------------------------------------------------------

//COMMADS SHOULD START WITH !
//COMMADS SHOULD END WITH WITH \n
//\r char is irrelevant at anywhere
class TA_SerialCommandFetcher {
public:
  TA_SerialCommandFetcher();
  int bufferSize();
  bool hasNext();
  String next();
private:
  int _bufferSize;
  char _buffer[TA_SerialCommandFetcher_BUFFER_SIZE];
  int _bufferIdx;
};
TA_SerialCommandFetcher::TA_SerialCommandFetcher() {
  _bufferSize = TA_SerialCommandFetcher_BUFFER_SIZE;
  int _bufferIdx = 0;
}
String TA_SerialCommandFetcher::next() {
  return String(_buffer);
}
bool TA_SerialCommandFetcher::hasNext() {
  bool lineFound = false;
  while (Serial.available() > 0) {
    char chr = Serial.read();
    if (_bufferIdx == 0 && chr != '!') {  //ignore
      continue;
    }
    if (chr == '\r' || (chr == '\n' && _bufferIdx == 0)) {  //ignore
      continue;
    }
    if (chr == '\n') {          //command received fully
      _buffer[_bufferIdx] = 0;  //ENDS STRING
      lineFound = true;
      _bufferIdx = 0;  //FOR THE NEXT ROUND
      continue;
    }
    if (_bufferIdx < _bufferSize && lineFound == false) {  //buffer up char
      _buffer[_bufferIdx++] = chr;
      continue;
    }
  }
  return lineFound;
}
TA_SerialCommandFetcher serialCommandFetcher;

//------------------------------------ CHIP HANDLER (TA_Chip_KinCony_KC868_A32_R1_2) -----------------------------------------------------------------------

//TA_Chip_KinCony_KC868_A32_R1_2
#include "PCF8574.h"
TwoWire _I2C_0 = TwoWire(0);
PCF8574 _pcf8574_I1(&_I2C_0, 0x24, 4, 5);
PCF8574 _pcf8574_I2(&_I2C_0, 0x25, 4, 5);
PCF8574 _pcf8574_I3(&_I2C_0, 0x21, 4, 5);
PCF8574 _pcf8574_I4(&_I2C_0, 0x22, 4, 5);
TwoWire _I2C_1 = TwoWire(1);
PCF8574 _pcf8574_R1(&_I2C_1, 0x24, 15, 13);
PCF8574 _pcf8574_R2(&_I2C_1, 0x25, 15, 13);
PCF8574 _pcf8574_R3(&_I2C_1, 0x21, 15, 13);
PCF8574 _pcf8574_R4(&_I2C_1, 0x22, 15, 13);
class TA_Chip_KinCony_KC868_A32_R1_2 {
public:
  TA_Chip_KinCony_KC868_A32_R1_2();
  String name();
  void setup();
  bool isValidPinNumber(int pinNumber);
  bool getDI_fr1_to32(int pinNumber);
  bool getDO_fr1_to32(int pinNumber);
  bool setDO_fr1_to32(int pinNumber, bool value);
private:
};
TA_Chip_KinCony_KC868_A32_R1_2::TA_Chip_KinCony_KC868_A32_R1_2() {
}
bool TA_Chip_KinCony_KC868_A32_R1_2::isValidPinNumber(int pinNumber) {
  return pinNumber >= 1 && pinNumber <= 32;
}
bool TA_Chip_KinCony_KC868_A32_R1_2::getDI_fr1_to32(int pinNumber) {
  if (!isValidPinNumber(pinNumber)) {
    return false;
  }
  if (pinNumber <= 8) {
    if (pinNumber == 1) return _pcf8574_I1.digitalRead(P0) == LOW;
    if (pinNumber == 2) return _pcf8574_I1.digitalRead(P1) == LOW;
    if (pinNumber == 3) return _pcf8574_I1.digitalRead(P2) == LOW;
    if (pinNumber == 4) return _pcf8574_I1.digitalRead(P3) == LOW;
    if (pinNumber == 5) return _pcf8574_I1.digitalRead(P4) == LOW;
    if (pinNumber == 6) return _pcf8574_I1.digitalRead(P5) == LOW;
    if (pinNumber == 7) return _pcf8574_I1.digitalRead(P6) == LOW;
    if (pinNumber == 8) return _pcf8574_I1.digitalRead(P7) == LOW;
  }
  if (pinNumber <= 16) {
    if (pinNumber == 9) return _pcf8574_I2.digitalRead(P0) == LOW;
    if (pinNumber == 10) return _pcf8574_I2.digitalRead(P1) == LOW;
    if (pinNumber == 11) return _pcf8574_I2.digitalRead(P2) == LOW;
    if (pinNumber == 12) return _pcf8574_I2.digitalRead(P3) == LOW;
    if (pinNumber == 13) return _pcf8574_I2.digitalRead(P4) == LOW;
    if (pinNumber == 14) return _pcf8574_I2.digitalRead(P5) == LOW;
    if (pinNumber == 15) return _pcf8574_I2.digitalRead(P6) == LOW;
    if (pinNumber == 16) return _pcf8574_I2.digitalRead(P7) == LOW;
  }
  if (pinNumber <= 24) {
    if (pinNumber == 17) return _pcf8574_I3.digitalRead(P0) == LOW;
    if (pinNumber == 18) return _pcf8574_I3.digitalRead(P1) == LOW;
    if (pinNumber == 19) return _pcf8574_I3.digitalRead(P2) == LOW;
    if (pinNumber == 20) return _pcf8574_I3.digitalRead(P3) == LOW;
    if (pinNumber == 21) return _pcf8574_I3.digitalRead(P4) == LOW;
    if (pinNumber == 22) return _pcf8574_I3.digitalRead(P5) == LOW;
    if (pinNumber == 23) return _pcf8574_I3.digitalRead(P6) == LOW;
    if (pinNumber == 24) return _pcf8574_I3.digitalRead(P7) == LOW;
  }
  if (pinNumber <= 32) {
    if (pinNumber == 25) return _pcf8574_I4.digitalRead(P0) == LOW;
    if (pinNumber == 26) return _pcf8574_I4.digitalRead(P1) == LOW;
    if (pinNumber == 27) return _pcf8574_I4.digitalRead(P2) == LOW;
    if (pinNumber == 28) return _pcf8574_I4.digitalRead(P3) == LOW;
    if (pinNumber == 29) return _pcf8574_I4.digitalRead(P4) == LOW;
    if (pinNumber == 30) return _pcf8574_I4.digitalRead(P5) == LOW;
    if (pinNumber == 31) return _pcf8574_I4.digitalRead(P6) == LOW;
    if (pinNumber == 32) return _pcf8574_I4.digitalRead(P7) == LOW;
  }
}
bool TA_Chip_KinCony_KC868_A32_R1_2::getDO_fr1_to32(int pinNumber) {
  if (!isValidPinNumber(pinNumber)) {
    return false;
  }
  if (pinNumber <= 8) {
    if (pinNumber == 1) return _pcf8574_R1.digitalRead(P0) == LOW;
    if (pinNumber == 2) return _pcf8574_R1.digitalRead(P1) == LOW;
    if (pinNumber == 3) return _pcf8574_R1.digitalRead(P2) == LOW;
    if (pinNumber == 4) return _pcf8574_R1.digitalRead(P3) == LOW;
    if (pinNumber == 5) return _pcf8574_R1.digitalRead(P4) == LOW;
    if (pinNumber == 6) return _pcf8574_R1.digitalRead(P5) == LOW;
    if (pinNumber == 7) return _pcf8574_R1.digitalRead(P6) == LOW;
    if (pinNumber == 8) return _pcf8574_R1.digitalRead(P7) == LOW;
  }
  if (pinNumber <= 16) {
    if (pinNumber == 9) return _pcf8574_R2.digitalRead(P0) == LOW;
    if (pinNumber == 10) return _pcf8574_R2.digitalRead(P1) == LOW;
    if (pinNumber == 11) return _pcf8574_R2.digitalRead(P2) == LOW;
    if (pinNumber == 12) return _pcf8574_R2.digitalRead(P3) == LOW;
    if (pinNumber == 13) return _pcf8574_R2.digitalRead(P4) == LOW;
    if (pinNumber == 14) return _pcf8574_R2.digitalRead(P5) == LOW;
    if (pinNumber == 15) return _pcf8574_R2.digitalRead(P6) == LOW;
    if (pinNumber == 16) return _pcf8574_R2.digitalRead(P7) == LOW;
  }
  if (pinNumber <= 24) {
    if (pinNumber == 17) return _pcf8574_R3.digitalRead(P0) == LOW;
    if (pinNumber == 18) return _pcf8574_R3.digitalRead(P1) == LOW;
    if (pinNumber == 19) return _pcf8574_R3.digitalRead(P2) == LOW;
    if (pinNumber == 20) return _pcf8574_R3.digitalRead(P3) == LOW;
    if (pinNumber == 21) return _pcf8574_R3.digitalRead(P4) == LOW;
    if (pinNumber == 22) return _pcf8574_R3.digitalRead(P5) == LOW;
    if (pinNumber == 23) return _pcf8574_R3.digitalRead(P6) == LOW;
    if (pinNumber == 24) return _pcf8574_R3.digitalRead(P7) == LOW;
  }
  if (pinNumber <= 32) {
    if (pinNumber == 25) return _pcf8574_R4.digitalRead(P0) == LOW;
    if (pinNumber == 26) return _pcf8574_R4.digitalRead(P1) == LOW;
    if (pinNumber == 27) return _pcf8574_R4.digitalRead(P2) == LOW;
    if (pinNumber == 28) return _pcf8574_R4.digitalRead(P3) == LOW;
    if (pinNumber == 29) return _pcf8574_R4.digitalRead(P4) == LOW;
    if (pinNumber == 30) return _pcf8574_R4.digitalRead(P5) == LOW;
    if (pinNumber == 31) return _pcf8574_R4.digitalRead(P6) == LOW;
    if (pinNumber == 32) return _pcf8574_R4.digitalRead(P7) == LOW;
  }
}
bool TA_Chip_KinCony_KC868_A32_R1_2::setDO_fr1_to32(int pinNumber, bool value) {
  if (!isValidPinNumber(pinNumber)) {
    return false;
  }
  if (pinNumber <= 8) {
    if (pinNumber == 1) _pcf8574_R1.digitalWrite(P0, value ? LOW : HIGH);
    if (pinNumber == 2) _pcf8574_R1.digitalWrite(P1, value ? LOW : HIGH);
    if (pinNumber == 3) _pcf8574_R1.digitalWrite(P2, value ? LOW : HIGH);
    if (pinNumber == 4) _pcf8574_R1.digitalWrite(P3, value ? LOW : HIGH);
    if (pinNumber == 5) _pcf8574_R1.digitalWrite(P4, value ? LOW : HIGH);
    if (pinNumber == 6) _pcf8574_R1.digitalWrite(P5, value ? LOW : HIGH);
    if (pinNumber == 7) _pcf8574_R1.digitalWrite(P6, value ? LOW : HIGH);
    if (pinNumber == 8) _pcf8574_R1.digitalWrite(P7, value ? LOW : HIGH);
    return true;
  }
  if (pinNumber <= 16) {
    if (pinNumber == 9) _pcf8574_R2.digitalWrite(P0, value ? LOW : HIGH);
    if (pinNumber == 10) _pcf8574_R2.digitalWrite(P1, value ? LOW : HIGH);
    if (pinNumber == 11) _pcf8574_R2.digitalWrite(P2, value ? LOW : HIGH);
    if (pinNumber == 12) _pcf8574_R2.digitalWrite(P3, value ? LOW : HIGH);
    if (pinNumber == 13) _pcf8574_R2.digitalWrite(P4, value ? LOW : HIGH);
    if (pinNumber == 14) _pcf8574_R2.digitalWrite(P5, value ? LOW : HIGH);
    if (pinNumber == 15) _pcf8574_R2.digitalWrite(P6, value ? LOW : HIGH);
    if (pinNumber == 16) _pcf8574_R2.digitalWrite(P7, value ? LOW : HIGH);
  }
  if (pinNumber <= 24) {
    if (pinNumber == 17) _pcf8574_R3.digitalWrite(P0, value ? LOW : HIGH);
    if (pinNumber == 18) _pcf8574_R3.digitalWrite(P1, value ? LOW : HIGH);
    if (pinNumber == 19) _pcf8574_R3.digitalWrite(P2, value ? LOW : HIGH);
    if (pinNumber == 20) _pcf8574_R3.digitalWrite(P3, value ? LOW : HIGH);
    if (pinNumber == 21) _pcf8574_R3.digitalWrite(P4, value ? LOW : HIGH);
    if (pinNumber == 22) _pcf8574_R3.digitalWrite(P5, value ? LOW : HIGH);
    if (pinNumber == 23) _pcf8574_R3.digitalWrite(P6, value ? LOW : HIGH);
    if (pinNumber == 24) _pcf8574_R3.digitalWrite(P7, value ? LOW : HIGH);
  }
  if (pinNumber <= 32) {
    if (pinNumber == 25) _pcf8574_R4.digitalWrite(P0, value ? LOW : HIGH);
    if (pinNumber == 26) _pcf8574_R4.digitalWrite(P1, value ? LOW : HIGH);
    if (pinNumber == 27) _pcf8574_R4.digitalWrite(P2, value ? LOW : HIGH);
    if (pinNumber == 28) _pcf8574_R4.digitalWrite(P3, value ? LOW : HIGH);
    if (pinNumber == 29) _pcf8574_R4.digitalWrite(P4, value ? LOW : HIGH);
    if (pinNumber == 30) _pcf8574_R4.digitalWrite(P5, value ? LOW : HIGH);
    if (pinNumber == 31) _pcf8574_R4.digitalWrite(P6, value ? LOW : HIGH);
    if (pinNumber == 32) _pcf8574_R4.digitalWrite(P7, value ? LOW : HIGH);
  }
  return true;
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
  if (!_pcf8574_I1.begin()) {
    Serial.print(error);
    Serial.println(F("I1"));
  }
  if (!_pcf8574_I2.begin()) {
    Serial.print(error);
    Serial.println(F("I2"));
  }
  if (!_pcf8574_I3.begin()) {
    Serial.print(error);
    Serial.println(F("I3"));
  }
  if (!_pcf8574_I4.begin()) {
    Serial.print(error);
    Serial.println(F("I4"));
  }
  if (!_pcf8574_R1.begin()) {
    Serial.print(error);
    Serial.println(F("R1"));
  }
  if (!_pcf8574_R2.begin()) {
    Serial.print(error);
    Serial.println(F("R2"));
  }
  if (!_pcf8574_R3.begin()) {
    Serial.print(error);
    Serial.println(F("R3"));
  }
  if (!_pcf8574_R4.begin()) {
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

//------------------------------------ SERIAL COMMAND HANDLER FOR CHIP HANDLER (TA_Chip_KinCony_KC868_A32_R1_2)------------------------------

class TA_SerialCommandHandler {
public:
  TA_SerialCommandHandler();
  void usage();
  void loop(unsigned long currentTime);
  void forEach(String command, unsigned long currentTime);
  int timerDuration[32];
private:
  bool _IfCommandNotValid(String command);
  bool _IfThereIsNoNextToken(TA_StringTokenizer tokens, String command, String errorLabel);
  bool _IfCommand_chipName(String command, String cmdName);
  bool _IfCommand_DIGetAll(String command, String cmdName);
  bool _IfCommand_DOGetAll(String command, String cmdName);
  bool _IfCommand_TimerGetAll(String command, String cmdName);
  bool _IfCommand_DOSetAllTrue(String command, String cmdName);
  bool _IfCommand_DOSetAllFalse(String command, String cmdName);
  bool _isNotValidPinNumber(String command, String pinNumberName, String errorConversion, String errorChipNotCompatible);
  bool _IfCommand_DIGetIdx(String command, String cmdName, int pinNumber);
  bool _IfCommand_DOGetIdx(String command, String cmdName, int pinNumber);
  bool _IfCommand_DOSetIdxTrue(String command, String cmdName, int pinNumber);
  bool _IfCommand_DOSetIdxFalse(String command, String cmdName, int pinNumber);
  bool _isNotValidInt(String command, String integerName, String errorLabel);
  bool _IfCommand_TimerSetIdx(String command, String cmdName, int pinNumber, int duration);
  bool _IfCommand_DOSetIdxTrueUntil(String command, String cmdName, int pinNumber, int duration, int gap, int count, unsigned long currentTime);
  void _error(String command, String errorLabel);
  unsigned long _oscillateStart[32];
  int _oscillateDuration[32];
  int _oscillateGap[32];
  int _oscillateCount[32];
};
TA_SerialCommandHandler::TA_SerialCommandHandler() {
  for (int i = 0; i < 32; i++) timerDuration[i] = 5;
}
bool TA_SerialCommandHandler::_IfCommand_TimerSetIdx(String command, String cmdName, int pinNumber, int duration) {
  Serial.println("testing...");
  if (!cmdName.equals("!TIMER_SET_IDX")) {
    return false;
  }
  Serial.print(F("REPLY_OF:"));
  Serial.print(command);
  Serial.println(F("->DONE"));
  timerDuration[pinNumber - 1] = duration;
  return true;
}
bool TA_SerialCommandHandler::_IfCommand_DOSetIdxTrueUntil(String command, String cmdName, int pinNumber, int duration, int gap, int count, unsigned long currentTime) {
  Serial.println("testing...");
  if (!cmdName.equals("!DO_SET_IDX_TRUE_UNTIL")) {
    return false;
  }
  Serial.print(F("REPLY_OF:"));
  Serial.print(command);
  Serial.println(F("->DONE"));
  _oscillateStart[pinNumber - 1] = currentTime;
  _oscillateDuration[pinNumber - 1] = duration * 1000;
  _oscillateGap[pinNumber - 1] = gap * 1000;
  _oscillateCount[pinNumber - 1] = count;
  return true;
}
bool TA_SerialCommandHandler::_isNotValidInt(String command, String integerName, String errorLabel) {
  if (!stringHandler.isInt(integerName)) {
    Serial.print(errorLabel);
    Serial.print(F(": "));
    Serial.println(command);
    return true;
  }
  return false;
}
bool TA_SerialCommandHandler::_IfCommandNotValid(String command) {
  if (command.startsWith(F("!"))) {
    return false;
  }
  Serial.print(F("ERROR_CMD_NOT_VALID: "));
  Serial.println(command);
  return true;
}
bool TA_SerialCommandHandler::_IfThereIsNoNextToken(TA_StringTokenizer tokens, String command, String errorLabel) {
  if (tokens.hasNext()) {
    return false;
  }
  Serial.print(errorLabel);
  Serial.print(F(": "));
  Serial.println(command);
  return true;
}
bool TA_SerialCommandHandler::_IfCommand_chipName(String command, String cmdName) {
  if (!cmdName.equals("!CHIP_NAME")) {
    return false;
  }
  Serial.print(F("REPLY_OF:"));
  Serial.print(command);
  Serial.print(F("->"));
  Serial.println(chip.name());
  return true;
}
bool TA_SerialCommandHandler::_IfCommand_TimerGetAll(String command, String cmdName) {
  if (!cmdName.equals("!TIMER_GET_ALL")) {
    return false;
  }
  Serial.print(F("REPLY_OF:"));
  Serial.print(command);
  Serial.print(F("->"));
  for (int i = 1; i <= 32; i++) {
    Serial.print(timerDuration[i]);
    Serial.print(F(" "));
  }
  Serial.println(F(""));
  return true;
}
bool TA_SerialCommandHandler::_IfCommand_DIGetAll(String command, String cmdName) {
  if (!cmdName.equals("!DI_GET_ALL")) {
    return false;
  }
  Serial.print(F("REPLY_OF:"));
  Serial.print(command);
  Serial.print(F("->"));
  for (int i = 1; i <= 32; i++) {
    Serial.print(chip.getDI_fr1_to32(i));
  }
  Serial.println(F(""));
  return true;
}
bool TA_SerialCommandHandler::_IfCommand_DOGetAll(String command, String cmdName) {
  if (!cmdName.equals("!DO_GET_ALL")) {
    return false;
  }
  Serial.print(F("REPLY_OF:"));
  Serial.print(command);
  Serial.print(F("->"));
  for (int i = 1; i <= 32; i++) {
    Serial.print(chip.getDO_fr1_to32(i));
  }
  Serial.println(F(""));
  return true;
}
bool TA_SerialCommandHandler::_IfCommand_DOSetAllTrue(String command, String cmdName) {
  if (!cmdName.equals("!DO_SET_ALL_TRUE")) {
    return false;
  }
  Serial.print(F("REPLY_OF:"));
  Serial.print(command);
  Serial.print(F("->"));
  bool result = true;
  for (int i = 1; i <= 32; i++) {
    bool innerResult = chip.setDO_fr1_to32(i, true);
    if (innerResult) {
      _oscillateCount[i - 1] = 0;
    }
    result = result && innerResult;
  }
  Serial.println(result ? F("->DONE") : F("->SKIPPED"));
  return true;
}
bool TA_SerialCommandHandler::_IfCommand_DOSetAllFalse(String command, String cmdName) {
  if (!cmdName.equals("!DO_SET_ALL_FALSE")) {
    return false;
  }
  Serial.print(F("REPLY_OF:"));
  Serial.print(command);
  Serial.print(F("->"));
  bool result = true;
  for (int i = 1; i <= 32; i++) {
    result = result && chip.setDO_fr1_to32(i, false);
  }
  Serial.println(result ? F("->DONE") : F("->SKIPPED"));
  return true;
}
bool TA_SerialCommandHandler::_isNotValidPinNumber(String command, String pinNumberName, String errorConversion, String errorChipNotCompatible) {
  if (!stringHandler.isInt(pinNumberName)) {
    Serial.print(errorConversion);
    Serial.print(F(": "));
    Serial.println(command);
    return true;
  }
  int pinNumber = pinNumberName.toInt();
  if (!chip.isValidPinNumber(pinNumber)) {
    Serial.print(errorChipNotCompatible);
    Serial.print(F(": "));
    Serial.println(command);
    return true;
  }
  return false;
}
bool TA_SerialCommandHandler::_IfCommand_DIGetIdx(String command, String cmdName, int pinNumber) {
  if (!cmdName.equals("!DI_GET_IDX")) {
    return false;
  }
  Serial.print(F("REPLY_OF:"));
  Serial.print(command);
  Serial.print(F("->"));
  Serial.println(chip.getDI_fr1_to32(pinNumber));
  return true;
}
bool TA_SerialCommandHandler::_IfCommand_DOGetIdx(String command, String cmdName, int pinNumber) {
  if (!cmdName.equals("!DO_GET_IDX")) {
    return false;
  }
  Serial.print(F("REPLY_OF:"));
  Serial.print(command);
  Serial.print(F("->"));
  Serial.println(chip.getDO_fr1_to32(pinNumber));
  return true;
}
void TA_SerialCommandHandler::_error(String command, String errorLabel) {
  Serial.print(errorLabel);
  Serial.print(F(": "));
  Serial.println(command);
}
bool TA_SerialCommandHandler::_IfCommand_DOSetIdxFalse(String command, String cmdName, int pinNumber) {
  if (!cmdName.equals("!DO_SET_IDX_FALSE")) {
    return false;
  }
  Serial.print(F("REPLY_OF:"));
  Serial.print(command);
  Serial.print(F("->"));
  if (chip.setDO_fr1_to32(pinNumber, false)) {
    Serial.println(F("DONE"));
    _oscillateCount[pinNumber - 1] = 0;
  } else {
    Serial.println(F("SKIPPED"));
  }
  return true;
}
bool TA_SerialCommandHandler::_IfCommand_DOSetIdxTrue(String command, String cmdName, int pinNumber) {
  if (!cmdName.equals("!DO_SET_IDX_TRUE")) {
    return false;
  }
  Serial.print(F("REPLY_OF:"));
  Serial.print(command);
  Serial.print(F("->"));
  if (chip.setDO_fr1_to32(pinNumber, true)) {
    Serial.println(F("DONE"));
    _oscillateCount[pinNumber - 1] = 0;
  } else {
    Serial.println(F("SKIPPED"));
  }
  return true;
}
void TA_SerialCommandHandler::loop(unsigned long currentTime) {
  for (int i = 0; i < 32; i++) {
    if (_oscillateCount[i] == 0) {
      continue;
    }
    bool pinState = chip.getDO_fr1_to32(i + 1);
    unsigned long period = _oscillateDuration[i] + _oscillateGap[i];
    unsigned long wholeDuration = period * _oscillateCount[i];
    unsigned long deltaDuration = currentTime - _oscillateStart[i];
    if (INFO_TA_SerialCommandHandler) {
      Serial.print("INFO_TA_SerialCommandHandler: OSCILLATE_CALC_PIN: ");
      Serial.print(i + 1);
      Serial.print(", cur:");
      Serial.print(currentTime);
      Serial.print(", state:");
      Serial.print(pinState);
      Serial.print(", dur:");
      Serial.print(_oscillateDuration[i]);
      Serial.print(", gap:");
      Serial.print(_oscillateGap[i]);
      Serial.print(", per:");
      Serial.print(period);
      Serial.print(", whlDur:");
      Serial.print(wholeDuration);
      Serial.print(", delDur:");
      Serial.println(deltaDuration);
    }
    if (deltaDuration > wholeDuration) {
      if (INFO_TA_SerialCommandHandler) {
        Serial.print("INFO_TA_SerialCommandHandler: OSCILLATE_RESET_PIN: ");
        Serial.println(i + 1);
      }
      _oscillateCount[i] = 0;
      if (pinState) {
        chip.setDO_fr1_to32(i + 1, false);
      }
      continue;
    }
    while (deltaDuration > period) {
      if (INFO_TA_SerialCommandHandler) {
        Serial.print("INFO_TA_SerialCommandHandler: OSCILLATE_DELTA_DURATION_SHORTENED: ");
        Serial.println(i + 1);
      }
      deltaDuration -= period;
    }
    if (INFO_TA_SerialCommandHandler) {
      Serial.print("INFO_TA_SerialCommandHandler: deltaDuration.short:");
      Serial.println(deltaDuration);
    }
    if (deltaDuration < _oscillateDuration[i] && !pinState) {
      if (INFO_TA_SerialCommandHandler) {
        Serial.print("INFO_TA_SerialCommandHandler: OSCILLATE_PIN_SET_TRUE: ");
        Serial.println(i + 1);
      }
      chip.setDO_fr1_to32(i + 1, true);
      continue;
    }
    if (deltaDuration > _oscillateDuration[i] && pinState) {
      if (INFO_TA_SerialCommandHandler) {
        Serial.print("INFO_TA_SerialCommandHandler: OSCILLATE_PIN_SET_FALSE: ");
        Serial.println(i + 1);
      }
      chip.setDO_fr1_to32(i + 1, false);
      continue;
    }
  }
}
void TA_SerialCommandHandler::usage() {
  Serial.println(F("USAGE: GENERAL------------------------------------------"));
  Serial.println(F("USAGE: getChipName as (cmd) ex: !CHIP_NAME"));
  Serial.println(F("USAGE: DIGITAL IN GET-----------------------------------"));
  Serial.println(F("USAGE: getDigitalInAll as (cmd) ex: !DI_GET_ALL"));
  Serial.println(F("USAGE: getDigitalInIdx as (cmd, pin1-32) ex: !DI_GET_IDX 1"));
  Serial.println(F("USAGE: DIGITAL OUT GET----------------------------------"));
  Serial.println(F("USAGE: getDigitalOutAll as (cmd) ex: !DO_GET_ALL"));
  Serial.println(F("USAGE: getDigitalOutIdx as (cmd, pin1-32) ex: !DO_GET_IDX 1"));
  Serial.println(F("USAGE: DIGITAL OUT SET----------------------------------"));
  Serial.println(F("USAGE: setDigitalOutAllAsTrue as (cmd) ex: !DO_SET_ALL_TRUE"));
  Serial.println(F("USAGE: setDigitalOutAllAsFalse as (cmd) ex: !DO_SET_ALL_FALSE"));
  Serial.println(F("USAGE: setDigitalOutIdxTrue as (cmd, pin1-32) ex: !DO_SET_IDX_TRUE 1"));
  Serial.println(F("USAGE: setDigitalOutIdxFalse as (cmd, pin1-32) ex: !DO_SET_IDX_FALSE 1"));
  Serial.println(F("USAGE: DIGITAL OUT OSCILLATE---------------------------"));
  Serial.println(F("USAGE: setDigitalOutOscillating as (cmd, pin1-32, secDuration, secGap, count) ex: !DO_SET_IDX_TRUE_UNTIL 12 2 1 5"));
  Serial.println(F("USAGE: TIMER-------------------------------------------"));
  Serial.println(F("USAGE: setTimer as (cmd, pin2-32step2, secDuration) ex: !TIMER_GET_ALL"));
  Serial.println(F("USAGE: setTimer as (cmd, pin2-32step2, secDuration) ex: !TIMER_SET_IDX 5"));
}
void TA_SerialCommandHandler::forEach(String command, unsigned long currentTime) {
  if (_IfCommandNotValid(command)) return;
  TA_StringTokenizer tokens(command, F(" "));
  if (_IfThereIsNoNextToken(tokens, command, F("ERROR_CMD_UNCOMPLETE"))) return;
  String cmdName = tokens.nextToken();
  if (INFO_TA_SerialCommandHandler) {
    Serial.print("INFO_TA_SerialCommandHandler:cmdName:");
    Serial.println(cmdName);
  }
  if (_IfCommand_chipName(command, cmdName)) return;
  if (_IfCommand_TimerGetAll(command, cmdName)) return;
  if (_IfCommand_DIGetAll(command, cmdName)) return;
  if (_IfCommand_DOGetAll(command, cmdName)) return;
  if (_IfCommand_DOSetAllTrue(command, cmdName)) return;
  if (_IfCommand_DOSetAllFalse(command, cmdName)) return;
  if (_IfThereIsNoNextToken(tokens, command, F("ERROR_CMD_PIN_NUMBER_NAME_UNCOMPLETE"))) return;
  String pinNumberName = tokens.nextToken();
  if (_isNotValidPinNumber(command, pinNumberName, "ERROR_CMD_PIN_NAME_NOT_NUMBER", "ERROR_CMD_PIN_NUMBER_NOT_VALID_NUMBER")) return;
  int pinNumber = pinNumberName.toInt();
  if (INFO_TA_SerialCommandHandler) {
    Serial.print("INFO_TA_SerialCommandHandler:pinNumber:");
    Serial.println(pinNumber);
  }
  if (_IfCommand_DIGetIdx(command, cmdName, pinNumber)) return;
  if (_IfCommand_DOGetIdx(command, cmdName, pinNumber)) return;
  if (_IfCommand_DOSetIdxFalse(command, cmdName, pinNumber)) return;
  if (_IfCommand_DOSetIdxTrue(command, cmdName, pinNumber)) return;
  if (_IfThereIsNoNextToken(tokens, command, F("ERROR_CMD_DURATION_NAME_UNCOMPLETE"))) return;
  String durationName = tokens.nextToken();
  if (_isNotValidInt(command, durationName, F("ERROR_CMD_DURATION_NAME_NOT_NUMBER"))) return;
  int duration = durationName.toInt();
  if (INFO_TA_SerialCommandHandler) {
    Serial.print("INFO_TA_SerialCommandHandler:duration:");
    Serial.println(duration);
  }
  if (_IfCommand_TimerSetIdx(command, cmdName, pinNumber, duration)) return;
  if (_IfThereIsNoNextToken(tokens, command, F("ERROR_CMD_GAP_NAME_UNCOMPLETE"))) return;
  String gapName = tokens.nextToken();
  if (_isNotValidInt(command, gapName, F("ERROR_CMD_GAP_NAME_NOT_NUMBER"))) return;
  int gap = gapName.toInt();
  if (INFO_TA_SerialCommandHandler) {
    Serial.print("INFO_TA_SerialCommandHandler:gap:");
    Serial.println(gap);
  }
  if (_IfThereIsNoNextToken(tokens, command, F("ERROR_CMD_COUNT_NAME_UNCOMPLETE"))) return;
  String countName = tokens.nextToken();
  if (_isNotValidInt(command, countName, F("ERROR_CMD_COUNT_NAME_NOT_NUMBER"))) return;
  int count = countName.toInt();
  if (INFO_TA_SerialCommandHandler) {
    Serial.print("INFO_TA_SerialCommandHandler:count:");
    Serial.println(count);
  }
  if (_IfCommand_DOSetIdxTrueUntil(command, cmdName, pinNumber, duration, gap, count, currentTime)) return;
  _error(command, F("ERROR_CMD_NOT_SUPPORTED"));
}
TA_SerialCommandHandler serialCommandHandler;

//------------------------------------ DI HANDLER FOR SURFACE TREATMEMT BATH 16 FOR SERIAL COMMAND HANDLER FOR CHIP HANDLER (TA_Chip_KinCony_KC868_A32_R1_2)------------------------------

//DI 0, 2, 4...32: manual start(with timer)/stop(stop the alarm)
//DI 1, 3, 5...31: sensor that detect sth in the bath
//DO 0, 2, 4...32: timer is running
//DO 1, 3, 5...31: alarm until [stop triggered] or [sth not in the bath anymore]
//TODO
class TA_DIHandler_SurfaceTreatmentBath16 {
public:
  TA_DIHandler_SurfaceTreatmentBath16();
  void loop(unsigned long currentTime);
private:
};
TA_DIHandler_SurfaceTreatmentBath16::TA_DIHandler_SurfaceTreatmentBath16() {
}
void TA_DIHandler_SurfaceTreatmentBath16::loop(unsigned long currentTime) {
}
TA_DIHandler_SurfaceTreatmentBath16 diHandler;

//------------------------------------ PROGRAM -----------------------------------------------------------------------

//GLOBALS SO FAR
//TA_stringHandler stringHandler;
//TA_TimeHandler timeHandler;
//TA_SerialConnection serialConnection(TA_SerialConnection_WAIT_UNTIL_CONNECTION, TA_SerialConnection_WAIT_IN_BAUDRATE);
//TA_SerialCommandFetcher serialCommandFetcher;
//TA_Chip_KinCony_KC868_A32_R1_2 chip;
//TA_SerialCommandHandler serialCommandHandler;
//TA_DIHandler_SurfaceTreatmentBath16 surfaceThreatmentBath;

//ARDUINO_MAIN
void setup() {
  serialConnection.setup();
  chip.setup();
  serialCommandHandler.usage();
}

//ARDUINO_THREAD
void loop() {
  timeHandler.loop();
  serialCommandHandler.loop(timeHandler.current());
  if (serialCommandFetcher.hasNext()) {
    serialCommandHandler.forEach(
      serialCommandFetcher.next(), timeHandler.current());
  }
  diHandler.loop(timeHandler.current());
}




     */
}
