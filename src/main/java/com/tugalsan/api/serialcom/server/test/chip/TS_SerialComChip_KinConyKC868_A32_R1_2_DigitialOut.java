package com.tugalsan.api.serialcom.server.test.chip;

public class TS_SerialComChip_KinConyKC868_A32_R1_2_DigitialOut {

    private TS_SerialComChip_KinConyKC868_A32_R1_2_DigitialOut(TS_SerialComChip_KinConyKC868_A32_R1_2 chip) {
        this.chip = chip;
    }
    final public TS_SerialComChip_KinConyKC868_A32_R1_2 chip;
    final private boolean pins[] = new boolean[32];

    public static TS_SerialComChip_KinConyKC868_A32_R1_2_DigitialOut of(TS_SerialComChip_KinConyKC868_A32_R1_2 chip) {
        return new TS_SerialComChip_KinConyKC868_A32_R1_2_DigitialOut(chip);
    }

    public boolean refreshBufferFromChip() {
        return false;
    }

    public boolean set(int pinNumber_fr1_to32, boolean value) {
        return false;
    }

    public boolean getFromChip(int pinNumber_fr1_to32) {

        return false;
    }

    public boolean getFrombuffer(int pinNumber_fr1_to32) {
        return pins[pinNumber_fr1_to32 - 1];
    }
}
