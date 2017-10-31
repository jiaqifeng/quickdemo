package com.jack.exapmle.rabbitmq;

import java.io.IOException;
import java.util.concurrent.TimeoutException;
 
import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.Consumer;
import com.rabbitmq.client.DefaultConsumer;
import com.rabbitmq.client.Envelope;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Receiver
{
    static Logger logger = LoggerFactory.getLogger(Receiver.class);

    public static void main( String[] args ) throws IOException, java.lang.InterruptedException, TimeoutException {
        String queuename=System.getProperty("queuename");
        if (queuename==null || "".equals(queuename))
            queuename="queue-pp";

        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("localhost");
        Connection connection = factory.newConnection();
        Channel channel = connection.createChannel();
 
        channel.queueDeclare(queuename, false, false, false, null);
        System.out.println("Receiver.main() ---- Waiting for messages. To exit press CTRL+C");
 
        Consumer consumer = new MyConsumer(channel);
        channel.basicConsume(queuename, true, consumer);
    }
}
