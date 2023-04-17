package com.tugalsan.api.serialcom.server.test;

import com.tugalsan.api.log.server.*;
import com.tugalsan.api.serialcom.server.*;
import com.tugalsan.api.thread.server.TS_ThreadWait;
import java.time.Duration;
import java.util.stream.IntStream;

public class TS_SerialComTestJavaCode {

    final private static TS_Log d = TS_Log.of(TS_SerialComTestJavaCode.class);

    public static void test_autoClosable_customMessageBroker() {
        try (var con = TS_SerialComBuilder.portFirst()
                .baudRate_115200().dataBits_8().oneStopBit().parityNone()
                .onPortError(() -> d.ce("onPortError", "what2do?"))
                .onSetupError(() -> d.ce("onSetupError", "what2do?"))
                .onConnectError(() -> d.ce("onConnectError", "what2do?"))
                .onReply_useCustomMessageBroker(reply -> d.cr("onReply", reply))
                .connect_AutoClosable()) {
            d.cr("con.name()", con.name());
            IntStream.range(0, 5).forEach(i -> {
                d.cr("send.successful?", con.send("hello"));
                d.cr("send.successful?", con.send("naber?"));
                TS_ThreadWait.of(Duration.ofSeconds(1));
            });
        }
    }

    public static void test_use_customMessageBroker() {
        d.cr("builder.result",
                TS_SerialComBuilder.portFirst()
                        .baudRate_115200().dataBits_8().oneStopBit().parityNone()
                        .onPortError(() -> d.ce("onPortError", "what2do?"))
                        .onSetupError(() -> d.ce("onSetupError", "what2do?"))
                        .onConnectError(() -> d.ce("onConnectError", "what2do?"))
                        .onReply_useCustomMessageBroker(reply -> d.cr("onReply", reply))
                        .onSuccess_useAndClose_connection(con -> {
                            d.cr("con.name()", con.name());
                            IntStream.range(0, 5).forEach(i -> {
                                d.cr("send.successful?", con.send("hello"));
                                d.cr("send.successful?", con.send("naber?"));
                                TS_ThreadWait.of(Duration.ofSeconds(1));
                            });
                        })
        );
    }

    @Deprecated //StructuredTaskScope.ShutdownOnFailure not working :....(
    public static void test_use_defaultMessageBroker() {
        d.cr("builder.result",
                TS_SerialComBuilder.portFirst()
                        .baudRate_115200().dataBits_8().oneStopBit().parityNone()
                        .onPortError(() -> d.ce("onPortError", "what2do?"))
                        .onSetupError(() -> d.ce("onSetupError", "what2do?"))
                        .onConnectError(() -> d.ce("onConnectError", "what2do?"))
                        .onReply_useDefaultMessageBroker_withMaxMessageCount(10)
                        .onSuccess_useAndClose_defaultMessageBroker((con, mb) -> {
                            var cmd = TS_SerialComTestKinConyKC868_A32_R1_2.chipName();
                            d.cr("with broker", "calling...");
                            var reply = mb.sendTheCommand_and_fetchMeReplyInMaxSecondsOf(cmd, Duration.ofSeconds(10));
                            d.cr("with broker", cmd, reply);
                        })
        );
    }
}
