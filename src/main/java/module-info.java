module com.tugalsan.api.serialcom {
    requires com.fazecast.jSerialComm;
    requires com.tugalsan.api.executable;
    requires com.tugalsan.api.thread;
    requires com.tugalsan.api.list;
    requires com.tugalsan.api.validator;
    requires com.tugalsan.api.log;
    requires com.tugalsan.api.compiler;
    requires com.tugalsan.api.coronator;
    requires com.tugalsan.api.unsafe;
    exports com.tugalsan.api.serialcom.server;
    exports com.tugalsan.api.serialcom.server.test;
}
