package org.suggs.katas.jms.queue;

import java.util.function.Function;

import javax.jms.Connection;

public interface JmsProcessor<T> {

    String process(Function<T, String> toProcess);

}
