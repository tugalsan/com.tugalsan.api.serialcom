package com.tugalsan.api.serialcom.server.test;

import com.tugalsan.api.log.server.*;
import com.tugalsan.api.serialcom.server.*;
import com.tugalsan.api.serialcom.server.test.chip.*;
import java.util.Optional;
import java.util.stream.IntStream;

public class TS_SerialComTestJavaCode {

    final private static TS_Log d = TS_Log.of(TS_SerialComTestJavaCode.class);

    public static void test() {
        TS_SerialComMessageBroker.d.infoEnable = true;
        //USAGE: GENERAL------------------------------------------
        d.cr("test", "chipName", TS_SerialComChip_KinConyKC868_A32_R1_2.call(chip -> chip.chipName()));

        //USAGE: DIGITAL IN GET-----------------------------------
        d.cr("test", "digitalIn.refreshAll", TS_SerialComChip_KinConyKC868_A32_R1_2.call(chip -> Optional.of(chip.digitalIn.refreshAll())));
        if (false) {
            IntStream.rangeClosed(1, 32).forEachOrdered(pinNumber_fr1_to32 -> {
                d.cr("test", "chip.digitalIn.pin(" + pinNumber_fr1_to32 + ").getValueFromChip", TS_SerialComChip_KinConyKC868_A32_R1_2.call(chip -> {
                    return Optional.of(chip.digitalIn.pin(pinNumber_fr1_to32).getValueFromChip());
                }));
            });
        }

        //USAGE: DIGITAL OUT GET----------------------------------
        d.cr("test", "digitalOut.refreshAll", TS_SerialComChip_KinConyKC868_A32_R1_2.call(chip -> Optional.of(chip.digitalOut.refreshAll())));
        if (false) {
            IntStream.rangeClosed(1, 32).forEachOrdered(pinNumber_fr1_to32 -> {
                d.cr("test", "chip.digitalOut.pin(" + pinNumber_fr1_to32 + ").getValueFromChip", TS_SerialComChip_KinConyKC868_A32_R1_2.call(chip -> {
                    return Optional.of(chip.digitalOut.pin(pinNumber_fr1_to32).getValueFromChip());
                }));
            });
        }
        //USAGE: DIGITAL OUT SET----------------------------------
        //USAGE: setDigitalOutAllAsTrue as (cmd) ex: !DO_SET_ALL_TRUE
        d.cr("test", "digitalOut.refreshAll", TS_SerialComChip_KinConyKC868_A32_R1_2.call(chip -> Optional.of(chip.digitalOut.setAll(true))));
        //USAGE: setDigitalOutAllAsFalse as (cmd) ex: !DO_SET_ALL_FALSE
        d.cr("test", "digitalOut.refreshAll", TS_SerialComChip_KinConyKC868_A32_R1_2.call(chip -> Optional.of(chip.digitalOut.setAll(false))));
        //USAGE: setDigitalOutIdxTrue as (cmd, pin1-32) ex: !DO_SET_IDX_TRUE 1
        if (false) {
            IntStream.rangeClosed(1, 32).forEachOrdered(pinNumber_fr1_to32 -> {
                d.cr("test", "chip.digitalOut.pin(" + pinNumber_fr1_to32 + ").setValue(true)", TS_SerialComChip_KinConyKC868_A32_R1_2.call(chip -> {
                    return Optional.of(chip.digitalOut.pin(pinNumber_fr1_to32).setValue(true));
                }));
            });
        }
        //USAGE: setDigitalOutIdxFalse as (cmd, pin1-32) ex: !DO_SET_IDX_FALSE 1
        if (false) {
            IntStream.rangeClosed(1, 32).forEachOrdered(pinNumber_fr1_to32 -> {
                d.cr("test", "chip.digitalOut.pin(" + pinNumber_fr1_to32 + ").setValue(false)", TS_SerialComChip_KinConyKC868_A32_R1_2.call(chip -> {
                    return Optional.of(chip.digitalOut.pin(pinNumber_fr1_to32).setValue(false));
                }));
            });
        }
        //USAGE: DIGITAL OUT OSCILLATE---------------------------
        //USAGE: setDigitalOutOscillating as (cmd, pin1-32, secDuration, secGap, count) ex: !DO_SET_IDX_TRUE_UNTIL 12 2 1 5
        d.cr("test", "digitalOut.oscillate", TS_SerialComChip_KinConyKC868_A32_R1_2.call(chip -> Optional.of(chip.digitalOut.pin(12).oscillate(2, 1, 5))));

        //USAGE: TIMER-------------------------------------------
        //USAGE: setTimer as (cmd, pin2-32step2, secDuration) ex: !TIMER_GET_ALL
        //USAGE: setTimer as (cmd, pin2-32step2, secDuration) ex: !TIMER_SET_IDX 5
    }
}
