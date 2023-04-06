package com.tugalsan.api.serialcom.server;

public class TS_SerialComStopBits {

    public static enum STOP_BITS {
        ONE_STOP_BIT, ONE_POINT_FIVE_STOP_BITS, TWO_STOP_BITS;
    }

    private TS_SerialComStopBits(TS_SerialComDataBits dataBits, STOP_BITS stopBits) {
        this.dataBits = dataBits;
        this.stopBits = stopBits;
    }
    final protected TS_SerialComDataBits dataBits;
    final protected STOP_BITS stopBits;

    public static TS_SerialComStopBits of(TS_SerialComDataBits dataBits, STOP_BITS stopBits) {
        return new TS_SerialComStopBits(dataBits, stopBits);
    }

    public TS_SerialComParity parity(TS_SerialComParity.PARITY parity) {
        return TS_SerialComParity.of(this, parity);
    }

    public TS_SerialComParity parityNone() {
        return TS_SerialComParity.of(this, TS_SerialComParity.PARITY.NO_PARITY);
    }

    public TS_SerialComParity parityEven() {
        return TS_SerialComParity.of(this, TS_SerialComParity.PARITY.EVEN_PARITY);
    }

    public TS_SerialComParity parityOdd() {
        return TS_SerialComParity.of(this, TS_SerialComParity.PARITY.ODD_PARITY);
    }
}
