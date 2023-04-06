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
                    d.cr("con.success_portConnect()", con.success_portConnect());
                    d.cr("con.success_portPresent()", con.success_portPresent());
                    d.cr("con.success_portSetup()", con.success_portSetup());
                    d.cr("con.isConnected()", con.isConnected());
                    con.send("hello");
                    con.send("naber?");
                });
    }
}
