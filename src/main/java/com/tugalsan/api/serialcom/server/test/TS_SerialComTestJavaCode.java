package com.tugalsan.api.serialcom.server.test;

import com.tugalsan.api.log.server.*;
import com.tugalsan.api.serialcom.server.*;
import com.tugalsan.api.serialcom.server.test.chip.*;

public class TS_SerialComTestJavaCode {

    final private static TS_Log d = TS_Log.of(TS_SerialComTestJavaCode.class);

    public static void test() {
        TS_SerialComMessageBroker.d.infoEnable = true;
        d.cr("test", "chipName", TS_SerialComChip_KinConyKC868_A32_R1_2.call(chip -> chip.chipName()));
        d.cr("test", "chipName", TS_SerialComChip_KinConyKC868_A32_R1_2.call(chip -> chip.digitalIn.refreshAll()));
    }
}
