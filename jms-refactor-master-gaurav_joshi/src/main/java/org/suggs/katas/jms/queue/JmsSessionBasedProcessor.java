package org.suggs.katas.jms.queue;

import java.util.function.Function;

import javax.jms.Connection;
import javax.jms.JMSException;
import javax.jms.Session;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class JmsSessionBasedProcessor implements JmsProcessor<Session>{
    
    private static final int ACKNOWLEDGE_MODE = Session.AUTO_ACKNOWLEDGE;
    private static final boolean TRANSACTED = false;
    private static final Logger LOG = LoggerFactory.getLogger(JmsSessionBasedProcessor.class);
    
    private final JmsProcessor<Connection> connectionBasedProcessor;    
    
    public JmsSessionBasedProcessor(JmsProcessor<Connection> connectionBasedProcessor) {
        this.connectionBasedProcessor = connectionBasedProcessor;
    }

    @Override
    public String process(Function<Session, String> toProcess) {
        return connectionBasedProcessor.process(aConnection -> {
            Session session = null;
            try {
                session = aConnection.createSession(TRANSACTED, ACKNOWLEDGE_MODE);                
                return toProcess.apply(session);
            } catch (JMSException jmse) {
                LOG.error("Failed to create session on connection {}", aConnection);
                throw new IllegalStateException(jmse);
            } finally {
                if (session != null) {
                    try {
                        session.close();
                    } catch (JMSException jmse) {
                        LOG.warn("Failed to close session {}", session);
                        throw new IllegalStateException(jmse);
                    }
                }
            }
        });
    }
    
    

}
