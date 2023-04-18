package com.tugalsan.api.serialcom.server.test.chip;

import java.util.Optional;

public class TS_SerialComChip_KinConyKC868_A32_R1_2_CommandBuilder {

    public static boolean isValidPinNumber(int pinNumber_fr1_to32) {
        return pinNumber_fr1_to32 >= 1 && pinNumber_fr1_to32 <= 32;
    }

//USAGE: GENERAL------------------------------------------
//USAGE: getChipName as (cmd) ex: !CHIP_NAME
    public static String chipName() {
        return "!CHIP_NAME";
    }

//USAGE: DIGITAL IN GET-----------------------------------
//USAGE: getDigitalInAll as (cmd) ex: !DI_GET_ALL
//USAGE: getDigitalInIdx as (cmd, pin1-32) ex: !DI_GET_IDX 1
    public static String getDigitalIn_All() {
        return "!DI_GET_ALL";
    }

    public static Optional<String> getDigitalIn_fr1_to32(int pinNumber_fr1_to32) {
        if (!isValidPinNumber(pinNumber_fr1_to32)) {
            return Optional.empty();
        }
        return Optional.of("!DI_GET_IDX %d".formatted(pinNumber_fr1_to32));
    }

//USAGE: DIGITAL OUT GET----------------------------------
//USAGE: getDigitalOutAll as (cmd) ex: !DO_GET_ALL
//USAGE: getDigitalOutIdx as (cmd, pin1-32) ex: !DO_GET_IDX 1
    public static String getDigitalOut_All() {
        return "!DO_GET_ALL";
    }

    public static Optional<String> getDigitalOut_fr1_to32(int pinNumber_fr1_to32) {
        if (!isValidPinNumber(pinNumber_fr1_to32)) {
            return Optional.empty();
        }
        return Optional.of("!DO_GET_IDX %d".formatted(pinNumber_fr1_to32));
    }
//USAGE: DIGITAL OUT SET----------------------------------
//USAGE: setDigitalOutAllAsTrue as (cmd) ex: !DO_SET_ALL_TRUE
//USAGE: setDigitalOutAllAsFalse as (cmd) ex: !DO_SET_ALL_FALSE
//USAGE: setDigitalOutIdxTrue as (cmd, pin1-32) ex: !DO_SET_IDX_TRUE 1
//USAGE: setDigitalOutIdxFalse as (cmd, pin1-32) ex: !DO_SET_IDX_FALSE 1

    public static String setDigitalOut_All(boolean value) {
        return value ? "!DO_SET_ALL_TRUE" : "!DO_SET_ALL_FALSE";
    }

    public static Optional<String> setDigitalOut_fr1_to32(int pinNumber_fr1_to32, boolean value) {
        if (!isValidPinNumber(pinNumber_fr1_to32)) {
            return Optional.empty();
        }
        return Optional.of((value ? "!DO_SET_IDX_TRUE %d" : "!DO_SET_IDX_FALSE %d").formatted(pinNumber_fr1_to32));
    }

//USAGE: DIGITAL OUT OSCILLATE---------------------------
//USAGE: setDigitalOutOscillating as (cmd, pin1-32, secDuration, secGap, count) ex: !DO_SET_IDX_TRUE_UNTIL 12 2 1 5
    public static Optional<String> setDigitalOut_Oscillating_fr1_to32(int pinNumber_fr1_to32, int secDuration, int secGap, int count) {
        if (!isValidPinNumber(pinNumber_fr1_to32)) {
            return Optional.empty();
        }
        return Optional.of("!DO_SET_IDX_TRUE_UNTIL %d %d %d %d".formatted(pinNumber_fr1_to32, secDuration, secGap, count));
    }

    //USAGE: TIMER-------------------------------------------
    //USAGE: getTimerAll as (cmd) ex: !TIMER_GET_ALL
    //USAGE: setTimer as (cmd, pin2-32step2, secDuration) ex: !TIMER_SET_IDX 5 2
    public static String getTimer_All() {
        return "!TIMER_GET_ALL";
    }

    public static Optional<String> setTimer_fr1_to32(int pinNumber_fr1_to32, int secDuration) {
        if (!isValidPinNumber(pinNumber_fr1_to32)) {
            return Optional.empty();
        }
        return Optional.of("!TIMER_SET_IDX %d %d".formatted(pinNumber_fr1_to32, secDuration));
    }
}
