package com.tugalsan.api.serialcom.server.test;

import com.tugalsan.api.log.server.*;
import static com.tugalsan.api.serialcom.server.test.chip.TS_SerialComChip_KinConyKC868_A32_R1_2.call;

public class TS_SerialComTestJavaCode {

    final private static TS_Log d = TS_Log.of(TS_SerialComTestJavaCode.class);

    public static void test() {
        d.cr("test", "conn", call(chip -> "OK"));
        d.cr("test", "name", call(chip -> chip.name()));
    }
}
