package com.tugalsan.api.serialcom.server;

import com.tugalsan.api.log.server.TS_Log;
import com.tugalsan.api.thread.server.TS_ThreadRunAllUntilFirstFail;
import com.tugalsan.api.thread.server.TS_ThreadSafeLst;
import com.tugalsan.api.thread.server.TS_ThreadWait;
import java.time.Duration;
import java.util.concurrent.Callable;

public class TS_SerialComMessageBroker {

    final private static TS_Log d = TS_Log.of(TS_SerialComMessageBroker.class);

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

    public String sendTheCommand_and_fetchMeReplyInMaxSecondsOf(String command, int maxDurationSecond) {
        if (!con.send(command)) {
            d.ce("send", "error", command);
            return null;
        }
        Callable<String> callableReply = () -> {
            String reply = null;
            while (reply == null) {
                reply = replies.findFirst(val -> val.contains(command));
                TS_ThreadWait.of(Duration.ofSeconds(1));
            }
            replies.removeAll(reply);
            return reply;
        };
        var run = TS_ThreadRunAllUntilFirstFail.of(Duration.ofSeconds(2), callableReply);
        if (run.resultsNotNull.isEmpty()) {
            run.exceptions.forEach(e -> d.ct("send->" + command, e));
            return null;
        }
        return run.resultsNotNull.get(0);
    }

}
