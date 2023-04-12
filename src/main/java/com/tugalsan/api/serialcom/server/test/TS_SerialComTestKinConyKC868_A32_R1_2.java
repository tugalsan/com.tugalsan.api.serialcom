package com.tugalsan.api.serialcom.server.test;

import java.util.Optional;

public class TS_SerialComTestKinConyKC868_A32_R1_2 {

    public static String chipName() {
        return "!CHIP_NAME";
    }

    public static boolean isValidPinNumber(int pinNumber_fr1_to32) {
        return pinNumber_fr1_to32 >= 1 && pinNumber_fr1_to32 <= 32;
    }

    public static Optional<String> setDO_fr1_to32(int pinNumber_fr1_to32, boolean value) {
        if (!isValidPinNumber(pinNumber_fr1_to32)) {
            return Optional.empty();
        }
        return Optional.of("!DO_SET_TRUE " + pinNumber_fr1_to32);
    }

    public static Optional<String> getDO_fr1_to32(int pinNumber_fr1_to32, boolean value) {
        if (!isValidPinNumber(pinNumber_fr1_to32)) {
            return Optional.empty();
        }
        return Optional.of("!DO_GET " + pinNumber_fr1_to32);
    }

    public static Optional<String> getDI_fr1_to32(int pinNumber_fr1_to32, boolean value) {
        if (!isValidPinNumber(pinNumber_fr1_to32)) {
            return Optional.empty();
        }
        return Optional.of("!DI_GET " + pinNumber_fr1_to32);
    }
}
