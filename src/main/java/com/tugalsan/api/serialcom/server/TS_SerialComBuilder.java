package com.tugalsan.api.serialcom.server;

import com.fazecast.jSerialComm.SerialPort;
import com.tugalsan.api.compiler.client.TGS_CompilerType1;
import com.tugalsan.api.executable.client.TGS_ExecutableType1;
import com.tugalsan.api.serialcom.server.utils.TS_SerialComUtils;
import java.util.List;

public class TS_SerialComBuilder {

    public static TS_SerialComPort port(SerialPort serialPort) {
        return TS_SerialComPort.of(serialPort);
    }

    public static TS_SerialComPort port(TGS_CompilerType1<SerialPort, List<SerialPort>> choose) {
        return TS_SerialComPort.of(choose.compile(TS_SerialComUtils.list()));
    }

    public static TS_SerialComPort portFirst() {
        return port(port -> {
        });
    }

    public static TS_SerialComPort port(TGS_ExecutableType1<SerialPort> port) {
        var list = TS_SerialComUtils.list();
        var firstPort = list.isEmpty() ? null : list.get(0);
        port.execute(firstPort);
        return TS_SerialComPort.of(firstPort);
    }
}
