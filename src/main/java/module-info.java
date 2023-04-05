module com.tugalsan.api.serialcom {
    requires com.fazecast.jSerialComm;
    requires com.tugalsan.api.unsafe;
    requires com.tugalsan.api.executable;
    requires com.tugalsan.api.thread;
    requires com.tugalsan.api.log;
    exports com.tugalsan.api.serialcom.server;
}
