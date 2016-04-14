package com.jack.exapmle.rabbitmq;

import java.io.IOException;
import java.util.concurrent.TimeoutException;
 
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

public class Sender
{
    private final static String QUEUE_NAME = "hello";

    public static void main( String[] args ) throws IOException, TimeoutException {
        System.out.println( "Hello World!" );
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("localhost");
 
        Connection connection = factory.newConnection();
        Channel channel = connection.createChannel();
        channel.queueDeclare(QUEUE_NAME, false, false, false, null);
        String message = "Hello RabbitMQ World!";
        channel.basicPublish("", QUEUE_NAME, null, message.getBytes());
        System.out.println(" [x] Sent '" + message + "'");
        channel.close();  
	connection.close();  
    }
}
