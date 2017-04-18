package org.suggs.katas.jms.queue.sender;

import javax.jms.JMSException;
import javax.jms.MessageProducer;
import javax.jms.Queue;
import javax.jms.Session;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.suggs.katas.jms.queue.JmsProcessor;

public class JmsSender {

    private static final Logger LOG = LoggerFactory.getLogger(JmsSender.class);
    
    private final JmsProcessor<Session> processor;    

    public JmsSender(JmsProcessor<Session> processor) {
        this.processor = processor;
    }

    public boolean sendMessage(String aDestinationName,
            final String aMessageToSend) {
        return Boolean.parseBoolean(processor.process(aSession -> sessionBasedProcessing(aSession, aDestinationName, aMessageToSend)));
    }
    
    private String sessionBasedProcessing(Session aSession, String aDestinationName, String aMessageToSend) {
        Queue queue = createQueue(aSession, aDestinationName);
        MessageProducer producer = createProducer(aSession, queue);
        sendMessage(aSession, producer, aMessageToSend);
        closeQuietly(producer);
        return "true";
    }

    private void closeQuietly(MessageProducer producer) {
        try {
            producer.close();
        } catch (JMSException e) {
            LOG.error("failed to close producer {}", producer, e);
        }
    }

    private void sendMessage(Session aSession, MessageProducer producer,
            String aMessageToSend) {
        try {
            producer.send(aSession.createTextMessage(aMessageToSend));
        } catch (JMSException e) {
            LOG.error("failed to send message for producer {} on session {}", producer, aSession);
            throw new IllegalStateException(e);
        }
    }

    private MessageProducer createProducer(Session aSession, Queue queue) {
        try {
            MessageProducer producer = aSession.createProducer(queue);
            return producer;
        } catch (JMSException e) {
            LOG.error("failed to create producer on queue {} on session {}", queue, aSession);
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

}
