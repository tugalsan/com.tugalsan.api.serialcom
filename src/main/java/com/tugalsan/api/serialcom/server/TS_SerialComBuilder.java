package com.tugalsan.api.serialcom.server;

import com.fazecast.jSerialComm.SerialPort;
import com.tugalsan.api.function.client.TGS_Func_OutTyped_In1;
import com.tugalsan.api.function.client.TGS_Func_In1;

import com.tugalsan.api.serialcom.server.utils.TS_SerialComUtils;
import java.util.List;

public class TS_SerialComBuilder {

    public static TS_SerialComPort port(String comX) {
        return TS_SerialComPort.of(TS_SerialComUtils.list().stream().filter(p -> TS_SerialComUtils.namePort(p).contains(comX)).findAny().orElse(null));
    }

    public static TS_SerialComPort port(SerialPort serialPort) {
        return TS_SerialComPort.of(serialPort);
    }

    public static TS_SerialComPort port(TGS_Func_OutTyped_In1<SerialPort, List<SerialPort>> choose) {
        return TS_SerialComPort.of(choose.call(TS_SerialComUtils.list()));
    }

    public static TS_SerialComPort portFirst() {
        return portFirst(port -> {
        });
    }

    public static TS_SerialComPort portFirst(TGS_Func_In1<SerialPort> port) {
        var list = TS_SerialComUtils.list();
        var firstPort = list.isEmpty() ? null : list.get(0);
        port.run(firstPort);
        return TS_SerialComPort.of(firstPort);
    }
}
