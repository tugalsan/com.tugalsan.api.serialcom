package com.tugalsan.api.serialcom.server.test;

import com.tugalsan.api.log.server.*;
import com.tugalsan.api.serialcom.server.*;
import com.tugalsan.api.serialcom.server.utils.TS_SerialComUtils;
import com.tugalsan.api.thread.server.TS_ThreadWait;
import java.util.stream.IntStream;

public class TS_SerialComTestBuilder {

    final private static TS_Log d = TS_Log.of(TS_SerialComTestBuilder.class);

    public static void test() {
        TS_SerialComBuilder.portFirst()
                .baudRate_115200().dataBits_8().oneStopBit().parityNone()
                .onReceived(command -> d.cr("onReceived", command))
                .use(con -> {
                    d.cr("con.name()", con.name());
                    d.cr("con.success_portConnect()", con.success_portConnect());
                    d.cr("con.success_portPresent()", con.success_portPresent());
                    d.cr("con.success_portSetup()", con.success_portSetup());
                    d.cr("con.isConnected()", con.isConnected());
                    IntStream.range(0, 5).forEach(i -> {
                        d.cr("send.successful?", con.send("hello"));
                        d.cr("send.successful?", con.send("naber?"));
                        TS_ThreadWait.seconds(null, 1);
                    });
                });
    }
}
