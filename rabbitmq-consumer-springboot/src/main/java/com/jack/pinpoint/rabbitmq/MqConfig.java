package com.jack.pinpoint.rabbitmq;

import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Created by jack on 18-1-16.
 */
@Configuration
public class MqConfig {
    @Bean
    public Queue stringQueue() {
        return new Queue("queue-pp", false, false, false, null);
    }
    @Bean
    public ConnectionFactory connectionFactory() {
        CachingConnectionFactory connectionFactory = new CachingConnectionFactory();
        connectionFactory.setAddresses("127.0.0.1:5672");
        //connectionFactory.setUsername("guest");
        //connectionFactory.setPassword("guest");
        //connectionFactory.setVirtualHost("/");
        //connectionFactory.setPublisherConfirms(true); //必须要设置
        return connectionFactory;
    }
}
