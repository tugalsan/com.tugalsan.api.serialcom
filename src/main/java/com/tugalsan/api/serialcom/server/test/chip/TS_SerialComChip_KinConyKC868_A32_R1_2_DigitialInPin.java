package com.tugalsan.api.serialcom.server.test.chip;

import com.tugalsan.api.log.server.TS_Log;
import java.time.Duration;

public class TS_SerialComChip_KinConyKC868_A32_R1_2_DigitialInPin {

    final private static TS_Log d = TS_Log.of(TS_SerialComChip_KinConyKC868_A32_R1_2_DigitialInPin.class);

    private TS_SerialComChip_KinConyKC868_A32_R1_2_DigitialInPin(TS_SerialComChip_KinConyKC868_A32_R1_2 chip, int pinNumber) {
        this.chip = chip;
        this.pinNumber = pinNumber;
    }
    final private TS_SerialComChip_KinConyKC868_A32_R1_2 chip;
    final private int pinNumber;

    public static TS_SerialComChip_KinConyKC868_A32_R1_2_DigitialInPin of(TS_SerialComChip_KinConyKC868_A32_R1_2 chip, int pinNumber) {
        return new TS_SerialComChip_KinConyKC868_A32_R1_2_DigitialInPin(chip, pinNumber);
    }

    public boolean getValueFromChip() {
        var cmd = TS_SerialComChip_KinConyKC868_A32_R1_2_CommandBuilder.getDigitalOut_fr1_to32(pinNumber);
        if (cmd.isEmpty()) {
            d.ce("getValueFromChip", "cmd.isEmpty()", "pinNumber", pinNumber);
            return false;
        }
        var reply = chip.mb.sendTheCommand_and_fetchMeReplyInMaxSecondsOf(cmd.get(), chip.timeout);
        return false;
    }

    public boolean getValueFromBuffer() {
        return value;
    }
    private volatile boolean value  = false;

    public boolean setValue(boolean value) {
        var cmd = TS_SerialComChip_KinConyKC868_A32_R1_2_CommandBuilder.setDigitalOut_fr1_to32(pinNumber, value);
        if (cmd.isEmpty()) {
            d.ce("setValue", "cmd.isEmpty()", "pinNumber", pinNumber, "value", value);
            return false;
        }
        var reply = chip.mb.sendTheCommand_and_fetchMeReplyInMaxSecondsOf(cmd.get(), chip.timeout);
//        if (reply.endsWith("->DONE")) {
//            valueBuffer.set(value);
//        }
        return value;
    }

    public void setValueImitate(boolean value) {
        this.value = value;
    }
}
