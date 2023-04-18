package com.tugalsan.api.serialcom.server;

import com.tugalsan.api.log.server.TS_Log;
import com.tugalsan.api.thread.server.TS_ThreadCall;
import com.tugalsan.api.thread.server.TS_ThreadSafeLst;
import com.tugalsan.api.thread.server.TS_ThreadWait;
import com.tugalsan.api.thread.server.core.TS_ThreadCallParallelTimeoutException;
import java.time.Duration;
import java.util.Optional;
import java.util.concurrent.Callable;

public class TS_SerialComMessageBroker {

    final public static TS_Log d = TS_Log.of(true, TS_SerialComMessageBroker.class);

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
        if (d.infoEnable) {
            if (reply.startsWith("USAGE") || reply.startsWith("INFO")) {
                d.ci("onReply", reply);
                return;
            }
            if (reply.startsWith("ERROR")) {
                d.ce("onReply", reply);
                return;
            }
            d.cr("onReply", reply);
        }
    }

    public Optional<String> sendTheCommand_and_fetchMeReplyInMaxSecondsOf(String command, Duration maxDuration) {
        if (!con.send(command)) {
            d.ce("sendTheCommand_and_fetchMeReplyInMaxSecondsOf", command, "ERROR_SENDING");
            return Optional.empty();
        }
        Callable<String> callableReply = () -> {
            String reply = null;
            while (reply == null) {
                reply = replies.findFirst(val -> val.contains(command));
                TS_ThreadWait.of(Duration.ofSeconds(1));
            }
            return reply;
        };
        var run = TS_ThreadCall.single(maxDuration, callableReply);
        replies.removeAll(val -> val.contains(command));
        if (run.resultsForSuccessfulOnes.isEmpty() || run.resultsForSuccessfulOnes.get(0) == null) {
            run.exceptions.forEach(e -> {
                if (e instanceof TS_ThreadCallParallelTimeoutException) {
                    d.ce("sendTheCommand_and_fetchMeReplyInMaxSecondsOf", command, "ERROR_TIMEOUT");
                    return;
                }
                d.ct("sendTheCommand_and_fetchMeReplyInMaxSecondsOf->" + command, e);
            });
            return Optional.empty();
        }
        return Optional.of(run.resultsForSuccessfulOnes.get(0));
    }
}
