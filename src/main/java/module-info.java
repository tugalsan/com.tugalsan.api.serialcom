module com.tugalsan.api.serialcom {
    requires com.fazecast.jSerialComm;
    requires com.tugalsan.api.executable;
    requires com.tugalsan.api.thread;
    requires com.tugalsan.api.log;
    requires com.tugalsan.api.unsafe;
    exports com.tugalsan.api.serialcom.server;
}
