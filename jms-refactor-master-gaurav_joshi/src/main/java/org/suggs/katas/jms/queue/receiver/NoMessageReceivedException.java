package org.suggs.katas.jms.queue.receiver;

public class NoMessageReceivedException extends RuntimeException {
    public NoMessageReceivedException(String reason) {
        super(reason);
    }
}
