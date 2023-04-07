package com.tugalsan.api.serialcom.server.test;

import com.tugalsan.api.log.server.*;
import com.tugalsan.api.serialcom.server.*;
import com.tugalsan.api.thread.server.TS_ThreadWait;
import java.time.Duration;
import java.util.stream.IntStream;

public class TS_SerialComTestJavaCode {
    
    final private static TS_Log d = TS_Log.of(TS_SerialComTestJavaCode.class);
    
    public static void testBuilder() {
        d.cr("testBuilder", "------------------------------------------");
        TS_SerialComBuilder.portFirst()
                .baudRate_115200().dataBits_8().oneStopBit().parityNone()
                .onConnectionError((successfulPort, successfulSetup, successfulConnect) -> {
                    d.ce("onError", "successfulPort", successfulPort);
                    d.ce("onError", "successfulSetup", successfulSetup);
                    d.ce("onError", "successfulConnect", successfulConnect);
                })
                .onReply(reply -> d.cr("onReply", reply))
                .onSucess_useAndDisconnect_the_(con -> {
                    d.cr("con.name()", con.name());
                    IntStream.range(0, 5).forEach(i -> {
                        d.cr("send.successful?", con.send("hello"));
                        d.cr("send.successful?", con.send("naber?"));
                        TS_ThreadWait.of(Duration.ofSeconds(1));
                    });
                });
    }
}
