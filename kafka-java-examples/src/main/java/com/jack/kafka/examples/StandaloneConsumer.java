package com.jack.kafka.examples;

/**
 * Created by jack on 16-3-28.
 */
public class StandaloneConsumer {
    public static void main(String[] args)
    {
        System.out.println("main start");
        Consumer consumerThread = new Consumer(KafkaProperties.topic2);
        consumerThread.start();
    }
}
