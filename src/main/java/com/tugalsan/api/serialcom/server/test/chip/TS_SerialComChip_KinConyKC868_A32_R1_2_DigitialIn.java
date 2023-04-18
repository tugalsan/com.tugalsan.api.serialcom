package com.tugalsan.api.serialcom.server.test.chip;

import com.tugalsan.api.list.client.TGS_ListUtils;
import com.tugalsan.api.log.server.TS_Log;
import java.util.List;
import java.util.Optional;
import java.util.stream.IntStream;

public class TS_SerialComChip_KinConyKC868_A32_R1_2_DigitialIn {

    final private static TS_Log d = TS_Log.of(TS_SerialComChip_KinConyKC868_A32_R1_2_DigitialIn.class);

    private TS_SerialComChip_KinConyKC868_A32_R1_2_DigitialIn(TS_SerialComChip_KinConyKC868_A32_R1_2 chip) {
        this.chip = chip;
        IntStream.rangeClosed(1, 32).forEachOrdered(i -> {
            pins.add(TS_SerialComChip_KinConyKC868_A32_R1_2_DigitialInPin.of(chip, i));
        });
    }
    final private TS_SerialComChip_KinConyKC868_A32_R1_2 chip;
    final private List<TS_SerialComChip_KinConyKC868_A32_R1_2_DigitialInPin> pins = TGS_ListUtils.of();

    public static TS_SerialComChip_KinConyKC868_A32_R1_2_DigitialIn of(TS_SerialComChip_KinConyKC868_A32_R1_2 chip) {
        return new TS_SerialComChip_KinConyKC868_A32_R1_2_DigitialIn(chip);
    }

    public TS_SerialComChip_KinConyKC868_A32_R1_2_DigitialInPin pin(int pinNumber) {
        return pins.get(pinNumber);
    }

    public Optional<String> refreshAll() {
        var cmd = TS_SerialComChip_KinConyKC868_A32_R1_2_CommandBuilder.getDigitalIn_All();
        var reply = chip.mb.sendTheCommand_and_fetchMeReplyInMaxSecondsOf(cmd, chip.timeout);
        if (reply.isEmpty()){
            return reply;
        }
        if (reply.isPresent()){
            var allValues = reply.get().substring(cmd.length()+2);
            d.cr("refreshAll", "result", allValues);
        }
        return reply;
    }
}
