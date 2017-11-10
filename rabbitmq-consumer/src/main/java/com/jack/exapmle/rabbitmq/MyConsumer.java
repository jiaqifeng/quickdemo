package com.jack.exapmle.rabbitmq;

import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.DefaultConsumer;
import com.rabbitmq.client.Envelope;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

/**
 * Created by jack on 17-10-27.
 */
public class MyConsumer extends DefaultConsumer {
    final Logger logger = LoggerFactory.getLogger(this.getClass());

    public MyConsumer(Channel channel) {
        super(channel);
    }

    @Override
    public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties,
                               byte[] body) throws IOException {
        super.handleDelivery(consumerTag,envelope,properties,body);
        String message = new String(body, "UTF-8");
        logger.info("MyConsumer.handleDelivery() -------------------------------- handle message exchange=" + envelope.getExchange() + ", routingkey=" + envelope.getRoutingKey() + ", message=" + message);

        //new Exception("fengjiaqi: test in MyConsumer").printStackTrace();
    }

}
