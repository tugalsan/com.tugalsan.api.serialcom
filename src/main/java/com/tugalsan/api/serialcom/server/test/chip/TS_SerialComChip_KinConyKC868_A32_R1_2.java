package com.tugalsan.api.serialcom.server.test.chip;

import com.tugalsan.api.compiler.client.TGS_CompilerType1;
import com.tugalsan.api.log.server.TS_Log;
import com.tugalsan.api.serialcom.server.TS_SerialComBuilder;
import com.tugalsan.api.serialcom.server.TS_SerialComMessageBroker;
import java.time.Duration;
import java.util.concurrent.atomic.AtomicReference;

/*
    
REPLY: USAGE...
REPLY: INFO...
REPLY: ERROR...
    
USAGE: GENERAL------------------------------------------
USAGE: getChipName as (cmd) ex: !CHIP_NAME
USAGE: DIGITAL IN GET-----------------------------------
USAGE: getDigitalInAll as (cmd) ex: !DI_GET_ALL
USAGE: getDigitalInIdx as (cmd, pin1-32) ex: !DI_GET_IDX 1
USAGE: DIGITAL OUT GET----------------------------------
USAGE: getDigitalOutAll as (cmd) ex: !DO_GET_ALL
USAGE: getDigitalOutIdx as (cmd, pin1-32) ex: !DO_GET_IDX 1
USAGE: DIGITAL OUT SET----------------------------------
USAGE: setDigitalOutAllAsTrue as (cmd) ex: !DO_SET_ALL_TRUE
USAGE: setDigitalOutAllAsFalse as (cmd) ex: !DO_SET_ALL_FALSE
USAGE: setDigitalOutIdxTrue as (cmd, pin1-32) ex: !DO_SET_IDX_TRUE 1
USAGE: setDigitalOutIdxFalse as (cmd, pin1-32) ex: !DO_SET_IDX_FALSE 1
USAGE: DIGITAL OUT OSCILLATE---------------------------
USAGE: setDigitalOutOscillating as (cmd, pin1-32, secDuration, secGap, count) ex: !DO_SET_IDX_TRUE_UNTIL 12 2 1 5
USAGE: TIMER-------------------------------------------
USAGE: setTimer as (cmd, pin2-32step2, secDuration) ex: !TIMER_GET_ALL
USAGE: setTimer as (cmd, pin2-32step2, secDuration) ex: !TIMER_SET_IDX 5
 */
public class TS_SerialComChip_KinConyKC868_A32_R1_2 {

    final private static TS_Log d = TS_Log.of(TS_SerialComChip_KinConyKC868_A32_R1_2.class);

    private TS_SerialComChip_KinConyKC868_A32_R1_2(TS_SerialComMessageBroker mb, Duration timeout) {
        this.mb = mb;
        this.timeout = timeout;
    }
    final public TS_SerialComMessageBroker mb;
    final public Duration timeout;

    public static TS_SerialComChip_KinConyKC868_A32_R1_2 of(TS_SerialComMessageBroker mb, Duration timeout) {
        return new TS_SerialComChip_KinConyKC868_A32_R1_2(mb, timeout);
    }

    public static String call(TGS_CompilerType1<String, TS_SerialComChip_KinConyKC868_A32_R1_2> chip) {
        var brokerSize = 10;
        var duration = Duration.ofSeconds(10);
        var result = new Object() {
            String value;
        };
        TS_SerialComBuilder.portFirst()
                .baudRate_115200().dataBits_8().oneStopBit().parityNone()
                .onPortError(() -> d.ce("onPortError", "what2do?"))
                .onSetupError(() -> d.ce("onSetupError", "what2do?"))
                .onConnectError(() -> d.ce("onConnectError", "what2do?"))
                .onReply_useDefaultMessageBroker_withMaxMessageCount(brokerSize)
                .onSuccess_useAndClose_defaultMessageBroker((con, mb) -> {
                    var chipDriver = TS_SerialComChip_KinConyKC868_A32_R1_2.of(mb, duration);
                    result.value = chip.compile(chipDriver);
                });
        return result.value;
    }

    public String name() {
        if (name.get() == null) {
            var cmd = TS_SerialComChip_KinConyKC868_A32_R1_2_Utils.chipName();
            var reply = mb.sendTheCommand_and_fetchMeReplyInMaxSecondsOf(cmd, timeout);
            name.set(reply);
        }
        return name.get();
    }
    private AtomicReference<String> name = new AtomicReference(null);

}
