package com.tugalsan.api.serialcom.server.test.chip;

import com.tugalsan.api.list.client.TGS_ListUtils;
import com.tugalsan.api.log.server.TS_Log;
import java.util.List;
import java.util.stream.IntStream;

public class TS_SerialComChip_KinConyKC868_A32_R1_2_DigitialOut {

    final private static TS_Log d = TS_Log.of(TS_SerialComChip_KinConyKC868_A32_R1_2_DigitialOut.class);

    private TS_SerialComChip_KinConyKC868_A32_R1_2_DigitialOut(TS_SerialComChip_KinConyKC868_A32_R1_2 chip) {
        this.chip = chip;
        IntStream.rangeClosed(1, 32).forEachOrdered(i -> {
            pins.add(TS_SerialComChip_KinConyKC868_A32_R1_2_DigitialOutPin.of(chip, i));
        });
    }
    final private TS_SerialComChip_KinConyKC868_A32_R1_2 chip;
    final private List<TS_SerialComChip_KinConyKC868_A32_R1_2_DigitialOutPin> pins = TGS_ListUtils.of();

    public static TS_SerialComChip_KinConyKC868_A32_R1_2_DigitialOut of(TS_SerialComChip_KinConyKC868_A32_R1_2 chip) {
        return new TS_SerialComChip_KinConyKC868_A32_R1_2_DigitialOut(chip);
    }

    public TS_SerialComChip_KinConyKC868_A32_R1_2_DigitialOutPin pin(int pinNumber_fr1_to32) {
        d.ci("pin", "pinNumber_fr1_to32", pinNumber_fr1_to32);
        return pins.get(pinNumber_fr1_to32 - 1);
    }

    public boolean refreshAll() {
        var cmd = TS_SerialComChip_KinConyKC868_A32_R1_2_CommandBuilder.getDigitalOut_All();
        var reply = chip.mb.sendTheCommand_and_fetchMeReplyInMaxSecondsOf(cmd, chip.timeout, chip.validReplyPrefix, true);
        if (reply.isEmpty()) {
            return false;
        }
//        var allValues = reply.get().substring("REPLY_OF:".length() + cmd.length() + "->".length());
        var allValues = reply.get();
        if (allValues.length() != 32) {
            d.ce("refreshAll", "ERROR_SIZE_NOT_32", reply, allValues);
            return false;
        }
        IntStream.rangeClosed(1, 32).parallel().forEach(pinNumber_fr1_to32 -> {
            pin(pinNumber_fr1_to32).setValueImitate(allValues.charAt(pinNumber_fr1_to32 - 1) == '1');
        });
        d.ci("refreshAll", "result", allValues);
        return true;
    }

    public boolean setAll(boolean value) {
        var cmd = TS_SerialComChip_KinConyKC868_A32_R1_2_CommandBuilder.setDigitalOut_All(value);
        var reply = chip.mb.sendTheCommand_and_fetchMeReplyInMaxSecondsOf(cmd, chip.timeout, chip.validReplyPrefix, true);
        if (reply.isEmpty()) {
            return false;
        }
        var result = reply.get();
        d.ci("setAll", "result", result);
        return result.endsWith(chip.validReplySuffixSet);
    }
}
