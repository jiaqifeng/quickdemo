package com.jack.exapmle.rabbitmq;

import com.rabbitmq.client.*;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * Created by jack on 17-8-14.
 */
public class TopicReceiver {
    public static void main( String[] args ) throws IOException, java.lang.InterruptedException, TimeoutException {
        String topicExchange=System.getProperty("topic");
        if (topicExchange==null || "".equals(topicExchange))
            topicExchange="test-topic";

        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("localhost");
        Connection connection = factory.newConnection();
        Channel channel = connection.createChannel();

        channel.exchangeDeclare(topicExchange, "topic");
        String queuename = channel.queueDeclare().getQueue();
        channel.queueBind(queuename, topicExchange, "test.*");

        System.out.println("Receiver.main() ---- queue name="+queuename+", Waiting for messages. To exit press CTRL+C");

        Consumer consumer = new DefaultConsumer(channel) {
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties,
                                       byte[] body) throws IOException {
                super.handleDelivery(consumerTag,envelope,properties,body);
                String message = new String(body, "UTF-8");
                System.out.println("Receiver.main() ---- Received exchange="+envelope.getExchange()+", routingkey="+envelope.getRoutingKey()+", message=" + message);
            }
        };
        channel.basicConsume(queuename, true, consumer);
    }
}
