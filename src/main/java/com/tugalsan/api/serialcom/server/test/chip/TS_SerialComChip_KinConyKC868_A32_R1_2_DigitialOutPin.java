package com.tugalsan.api.serialcom.server.test.chip;

import com.tugalsan.api.log.server.TS_Log;

public class TS_SerialComChip_KinConyKC868_A32_R1_2_DigitialOutPin {

    final private static TS_Log d = TS_Log.of(TS_SerialComChip_KinConyKC868_A32_R1_2_DigitialOutPin.class);

    private TS_SerialComChip_KinConyKC868_A32_R1_2_DigitialOutPin(TS_SerialComChip_KinConyKC868_A32_R1_2 chip, int pinNumber) {
        this.chip = chip;
        this.pinNumber = pinNumber;
    }
    final private TS_SerialComChip_KinConyKC868_A32_R1_2 chip;
    final private int pinNumber;

    public static TS_SerialComChip_KinConyKC868_A32_R1_2_DigitialOutPin of(TS_SerialComChip_KinConyKC868_A32_R1_2 chip, int pinNumber) {
        return new TS_SerialComChip_KinConyKC868_A32_R1_2_DigitialOutPin(chip, pinNumber);
    }

    public boolean getValueFromChip() {
        var cmd = TS_SerialComChip_KinConyKC868_A32_R1_2_CommandBuilder.getDigitalOut_fr1_to32(pinNumber);
        if (cmd.isEmpty()) {
            d.ce("getValueFromChip", "cmd.isEmpty()", "pinNumber", pinNumber);
            return false;
        }
        var reply = chip.mb.sendTheCommand_and_fetchMeReplyInMaxSecondsOf(cmd.get(), chip.timeout, chip.validReplyPrefix, true);
        if (reply.isEmpty()) {
            return false;
        }
        value = reply.get().equals("1");
        return value;
    }

    public boolean getValueFromBuffer() {
        return value;
    }
    private volatile boolean value = false;

    public boolean setValue(boolean value) {
        var cmd = TS_SerialComChip_KinConyKC868_A32_R1_2_CommandBuilder.setDigitalOut_fr1_to32(pinNumber, value);
        if (cmd.isEmpty()) {
            d.ce("setValue", "cmd.isEmpty()", "pinNumber", pinNumber, "value", value);
            return false;
        }
        var reply = chip.mb.sendTheCommand_and_fetchMeReplyInMaxSecondsOf(cmd.get(), chip.timeout, chip.validReplyPrefix, true);
        if (reply.isEmpty()) {
            return false;
        }
        var result = reply.get();
        d.ci("setValue", "value", value, "result", result);
        return result.endsWith(chip.validReplySuffixSet);
    }

    public void setValueImitate(boolean value) {
        this.value = value;
    }
}
