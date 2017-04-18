package org.suggs.katas.jmsbroker.impl;

import java.util.function.Function;

import javax.jms.Connection;
import javax.jms.JMSException;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.activemq.broker.Broker;
import org.apache.activemq.broker.BrokerService;
import org.apache.activemq.broker.region.Destination;
import org.apache.activemq.broker.region.DestinationStatistics;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.suggs.katas.jms.queue.JmsProcessor;

public class ActiveMqBasedProcessor implements JmsProcessor<Connection> {
    
    private static final Logger LOG = LoggerFactory.getLogger(ActiveMqBasedProcessor.class);
    private final String aBrokerUrl;
    private BrokerService brokerService;
    
    public ActiveMqBasedProcessor(String aBrokerUrl) {
        this.aBrokerUrl = aBrokerUrl;
    }
    
    public void start() throws Exception {
        createEmbeddedBroker();
        startEmbeddedBroker();
    }    
    
    public void stop() throws Exception {
        if (brokerService == null) {
            throw new IllegalStateException("Cannot stop the broker from this API: " +
                    "perhaps it was started independently from this utility");
        }
        brokerService.stop();
        brokerService.waitUntilStopped();
    }
    
    @Override
    public String process(Function<Connection, String> toProcess) {
        Connection connection = null;
        try {
            ActiveMQConnectionFactory connectionFactory = new ActiveMQConnectionFactory(aBrokerUrl);
            connection = connectionFactory.createConnection();
            return toProcess.apply(connection);
        } catch (JMSException jmse) {
            LOG.error("failed to create connection to {}", aBrokerUrl);
            throw new IllegalStateException(jmse);
        } finally {
            if (connection != null) {
                try {
                    connection.close();
                } catch (JMSException jmse) {
                    LOG.warn("Failed to close connection to broker at []", aBrokerUrl);
                    throw new IllegalStateException(jmse);
                }
            }
        }
    }
    
    public DestinationStatistics getDestinationStatisticsFor(String aDestinationName) { 
        Broker regionBroker = brokerService.getRegionBroker();
        for (Destination destination : regionBroker.getDestinationMap().values()) {
            if (destination.getName().equals(aDestinationName)) {
                return destination.getDestinationStatistics();
            }
        }
        throw new IllegalStateException(String.format("Destination %s does not exist on broker at %s", aDestinationName, aBrokerUrl));
    }
    
    
    private void createEmbeddedBroker() throws Exception {
        brokerService = new BrokerService();
        brokerService.setPersistent(false);
        brokerService.addConnector(aBrokerUrl);
    }
    
    private void startEmbeddedBroker() throws Exception {
        brokerService.start();
    }

    

}
