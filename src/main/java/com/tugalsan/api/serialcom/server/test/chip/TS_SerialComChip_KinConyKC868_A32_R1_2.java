package com.tugalsan.api.serialcom.server.test.chip;

import com.tugalsan.api.callable.client.TGS_CallableType1;
import com.tugalsan.api.log.server.TS_Log;
import com.tugalsan.api.serialcom.server.TS_SerialComBuilder;
import com.tugalsan.api.serialcom.server.TS_SerialComMessageBroker;
import java.time.Duration;
import java.util.Optional;

public class TS_SerialComChip_KinConyKC868_A32_R1_2 {

    final private static TS_Log d = TS_Log.of(TS_SerialComChip_KinConyKC868_A32_R1_2.class);

    public static Duration defaultTimeoutDuration() {
        return Duration.ofSeconds(10);
    }

    private TS_SerialComChip_KinConyKC868_A32_R1_2(TS_SerialComMessageBroker mb, Duration timeout) {
        this.mb = mb;
        this.timeout = timeout;
        this.digitalIn = TS_SerialComChip_KinConyKC868_A32_R1_2_DigitialIn.of(this);
    }
    final public TS_SerialComMessageBroker mb;
    final public Duration timeout;
    final public TS_SerialComChip_KinConyKC868_A32_R1_2_DigitialIn digitalIn;

    public static TS_SerialComChip_KinConyKC868_A32_R1_2 of(TS_SerialComMessageBroker mb, Duration timeout) {
        return new TS_SerialComChip_KinConyKC868_A32_R1_2(mb, timeout);
    }

    public static Optional<String> call(TGS_CallableType1<Optional<String>, TS_SerialComChip_KinConyKC868_A32_R1_2> chip) {
        return call(chip, defaultTimeoutDuration());
    }

    public static Optional<String> call(TGS_CallableType1<Optional<String>, TS_SerialComChip_KinConyKC868_A32_R1_2> chip, Duration timeout) {
        var brokerSize = 10;
        var result = new Object() {
            Optional<String> value;
        };
        TS_SerialComBuilder.portFirst()
                .baudRate_115200().dataBits_8().oneStopBit().parityNone()
                .onPortError(() -> d.ce("onPortError", "what2do?"))
                .onSetupError(() -> d.ce("onSetupError", "what2do?"))
                .onConnectError(() -> d.ce("onConnectError", "what2do?"))
                .onReply_useDefaultMessageBroker_withMaxMessageCount(brokerSize)
                .onSuccess_useAndClose_defaultMessageBroker((con, mb) -> {
                    var chipDriver = TS_SerialComChip_KinConyKC868_A32_R1_2.of(mb, timeout);
                    result.value = chip.call(chipDriver);
                });
        return result.value;
    }

    public Optional<String> chipName() {
        if (chipName.isEmpty()) {
            var cmd = TS_SerialComChip_KinConyKC868_A32_R1_2_CommandBuilder.chipName();
            var reply = mb.sendTheCommand_and_fetchMeReplyInMaxSecondsOf(cmd, timeout);
            chipName = reply;
        }
        return chipName;
    }
    private volatile Optional<String> chipName = Optional.empty();
}
