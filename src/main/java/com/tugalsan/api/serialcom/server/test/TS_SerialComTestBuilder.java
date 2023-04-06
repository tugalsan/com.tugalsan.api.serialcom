package com.tugalsan.api.serialcom.server.test;

import com.tugalsan.api.log.server.*;
import com.tugalsan.api.serialcom.server.*;

public class TS_SerialComTestBuilder {

    final private static TS_Log d = TS_Log.of(TS_SerialComTestBuilder.class);

    public static void test() {
        TS_SerialComBuilder.portFirst()
                .baudRate_115200().dataBits_8()
                .oneStopBit().parityNone()
                .onReceived(command -> d.cr("onReceived", command))
                .connect()
                .useAndDisconnect(con -> {
                    con.send("hello");
                    con.send("naber?");
                });
    }
}
