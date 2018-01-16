package com.jack.pinpoint.rabbitmq;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

/**
 * Created by jack on 18-1-16.
 */
@Component
public class SBReceiver {
    @RabbitListener(queues = "queue-pp")
    public void receiveMessage(String message){
        System.out.println("received message:" + message);
        new Exception("show stack").printStackTrace();
    }
}
