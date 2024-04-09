module com.tugalsan.api.serialcom {
    requires com.fazecast.jSerialComm;
    requires com.tugalsan.api.thread;
    requires com.tugalsan.api.runnable;
    requires com.tugalsan.api.log;
    requires com.tugalsan.api.list;
    requires com.tugalsan.api.coronator;
    requires com.tugalsan.api.unsafe;
    requires com.tugalsan.api.stream;
    requires com.tugalsan.api.callable;
    requires com.tugalsan.api.union;
    requires com.tugalsan.api.validator;
    exports com.tugalsan.api.serialcom.server;
    exports com.tugalsan.api.serialcom.server.utils;
}
