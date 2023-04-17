package com.tugalsan.api.serialcom.server;

import com.tugalsan.api.log.server.TS_Log;
import com.tugalsan.api.thread.server.TS_ThreadCall;
import com.tugalsan.api.thread.server.TS_ThreadSafeLst;
import com.tugalsan.api.thread.server.TS_ThreadWait;
import com.tugalsan.api.thread.server.core.TS_ThreadCallParallelTimeoutException;
import java.time.Duration;
import java.util.concurrent.Callable;

public class TS_SerialComMessageBroker {

    final private static TS_Log d = TS_Log.of(true, TS_SerialComMessageBroker.class);

    public TS_SerialComMessageBroker(int maxSize) {
        this.maxSize = maxSize;
    }
    final public TS_ThreadSafeLst<String> replies = new TS_ThreadSafeLst();
    final public int maxSize;

    public static TS_SerialComMessageBroker of(int maxSize) {
        return new TS_SerialComMessageBroker(maxSize);
    }

    public void setConnection(TS_SerialComConnection con) {
        this.con = con;
    }
    private TS_SerialComConnection con;

    public void onReply(String reply) {
        replies.add(reply);
        replies.cropToLengthFast(maxSize);
    }

    public String sendTheCommand_and_fetchMeReplyInMaxSecondsOf(String command, Duration maxDuration) {
        if (!con.send(command)) {
            d.ce("sendTheCommand_and_fetchMeReplyInMaxSecondsOf", command, "ERROR_SENDING");
            return null;
        }
        Callable<String> callableReply = () -> {
            String reply = null;
            while (reply == null) {
                reply = replies.findFirst(val -> val.contains(command));
                TS_ThreadWait.of(Duration.ofSeconds(1));
            }
            return reply;
        };
        var run = TS_ThreadCall.parallelUntilFirstFail(maxDuration, callableReply);
        replies.removeAll(val -> val.contains(command));
        if (run.resultsForSuccessfulOnes.isEmpty()) {
            run.exceptions.forEach(e -> {
                if (e instanceof TS_ThreadCallParallelTimeoutException) {
                    d.ce("sendTheCommand_and_fetchMeReplyInMaxSecondsOf", command, "ERROR_TIMEOUT");
                    return;
                }
                d.ct("sendTheCommand_and_fetchMeReplyInMaxSecondsOf->" + command, e);
            });
            return null;
        }
        return run.resultsForSuccessfulOnes.get(0);
    }

}
