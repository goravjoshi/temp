package org.suggs.katas.jms.queue.receiver;

import static java.util.concurrent.TimeUnit.SECONDS;

import java.util.concurrent.TimeUnit;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageConsumer;
import javax.jms.Queue;
import javax.jms.Session;
import javax.jms.TextMessage;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.suggs.katas.jms.queue.JmsProcessor;
import org.suggs.katas.jms.queue.sender.JmsSender;

public class JmsReceiver {
    
    private static final Logger LOG = LoggerFactory.getLogger(JmsSender.class);    
    private final JmsProcessor<Session> processor;

    public JmsReceiver(JmsProcessor<Session> processor) {
        this.processor = processor;
    }   
    
    public String receiveSingleMessage(String aDestinationName, int timeout, TimeUnit timeUnit) {        
        return processor.process(aSession -> sessionBasedProcessing(aSession, aDestinationName, SECONDS.toSeconds(timeout)));
    }
    
    private String sessionBasedProcessing(Session aSession, String aDestinationName, long timeOutInSeconds) {
        Queue queue = createQueue(aSession, aDestinationName);
        MessageConsumer consumer = createConsumer(aSession, queue);
        Message message = receiveMessage(consumer, timeOutInSeconds);        
        closeQuietly(consumer);
        return convertToString(message);
    }

    private String convertToString(Message message) {
        try {
            return ((TextMessage) message).getText();
        } catch (JMSException e) {
            LOG.error("failed to get message for message {}", message);
            throw new IllegalStateException(e);
        }
    }

    private Message receiveMessage(MessageConsumer consumer, long timeOutInSeconds) {
        try {
            Message message = consumer.receive(timeOutInSeconds);
            if (message == null) {
                throw new NoMessageReceivedException(String.format("No messages received from the broker within the %d timeout", timeOutInSeconds));
            }
            return message;
        } catch (JMSException e) {
            LOG.error("failed to send message for consumer {}", consumer);
            throw new IllegalStateException(e);
        }
    }
    
    private Queue createQueue(Session aSession, String aDestinationName) {
        try {
            Queue queue = aSession.createQueue(aDestinationName);
            return queue;
        } catch (JMSException e) {
            LOG.error("failed to create queue on session {}", aSession);
            throw new IllegalStateException(e);
        }
    }
    
    private MessageConsumer createConsumer(Session aSession, Queue queue) {
        try {
            MessageConsumer consumer = aSession.createConsumer(queue);
            return consumer;
        } catch (JMSException e) {
            LOG.error("failed to create consumer on queue {} on session {}", queue, aSession);
            throw new IllegalStateException(e);
        }
    }

    
    private void closeQuietly(MessageConsumer consumer) {
        try {
            consumer.close();
        } catch (JMSException e) {
            LOG.error("failed to close consumer {}", consumer, e);
        }
    }
    
    
}
