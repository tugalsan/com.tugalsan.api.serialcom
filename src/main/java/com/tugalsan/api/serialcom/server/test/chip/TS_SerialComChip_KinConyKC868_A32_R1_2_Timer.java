package com.tugalsan.api.serialcom.server.test.chip;

import com.tugalsan.api.log.server.TS_Log;

public class TS_SerialComChip_KinConyKC868_A32_R1_2_Timer {

    final private static TS_Log d = TS_Log.of(TS_SerialComChip_KinConyKC868_A32_R1_2_Timer.class);

    private TS_SerialComChip_KinConyKC868_A32_R1_2_Timer(TS_SerialComChip_KinConyKC868_A32_R1_2 chip) {
        this.chip = chip;
    }
    final private TS_SerialComChip_KinConyKC868_A32_R1_2 chip;
    final public int[] buffer = new int[32];

    public static TS_SerialComChip_KinConyKC868_A32_R1_2_Timer of(TS_SerialComChip_KinConyKC868_A32_R1_2 chip) {
        return new TS_SerialComChip_KinConyKC868_A32_R1_2_Timer(chip);
    }

    public boolean refreshAll() {
        var cmd = TS_SerialComChip_KinConyKC868_A32_R1_2_CommandBuilder.getTimer_All();
        var reply = chip.mb.sendTheCommand_and_fetchMeReplyInMaxSecondsOf(cmd, chip.timeout, chip.validReplyPrefix, true);
        if (reply.isEmpty()) {
            return false;
        }
        var allValues = reply.get();
        d.ce("refreshAll", "WHATTODO", allValues);
        return true;
//        if (allValues.length() != 32) {
//            d.ce("refreshAll", "ERROR_SIZE_NOT_32", reply, allValues);
//            return false;
//        }
//        IntStream.rangeClosed(1, 32).parallel().forEach(pinNumber_fr1_to32 -> {
//            timer(pinNumber_fr1_to32).setValueImitate(allValues.charAt(pinNumber_fr1_to32 - 1) == '1');
//        });
//        d.ci("refreshAll", "result", allValues);
//        return true;
    }

    public boolean set(int pinNumber_fr1_to32, int secDuration) {
        var cmd = TS_SerialComChip_KinConyKC868_A32_R1_2_CommandBuilder.setTimer_fr1_to32(pinNumber_fr1_to32, secDuration);
        if (cmd.isEmpty()) {
            return false;
        }
        var reply = chip.mb.sendTheCommand_and_fetchMeReplyInMaxSecondsOf(cmd.get(), chip.timeout, chip.validReplyPrefix, true);
        if (reply.isEmpty()) {
            return false;
        }
        var result = reply.get();
        d.ci("set", "result", result);
        return result.endsWith(chip.validReplySuffixSet);
    }
}
