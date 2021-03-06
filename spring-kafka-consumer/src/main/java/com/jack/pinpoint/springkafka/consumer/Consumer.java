package com.jack.pinpoint.springkafka.consumer;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.IntegerDeserializer;
import org.apache.kafka.common.serialization.IntegerSerializer;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.kafka.core.*;
import org.springframework.kafka.listener.AbstractMessageListenerContainer;
import org.springframework.kafka.listener.BatchMessageListener;
import org.springframework.kafka.listener.KafkaMessageListenerContainer;
import org.springframework.kafka.listener.MessageListener;
import org.springframework.kafka.listener.config.ContainerProperties;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Consumer implements Runnable {

    static final String defaultHost= "localhost:9092";//"172.19.11.197:9092";
    private volatile boolean stop;

    public Consumer() {
        stop=false;
    }

    private static KafkaMessageListenerContainer<Integer, String> createContainer(
            ContainerProperties containerProps, String host) {
        Map<String, Object> props = consumerProps(host);
        DefaultKafkaConsumerFactory<Integer, String> cf =
                new DefaultKafkaConsumerFactory<Integer, String>(props);
        KafkaMessageListenerContainer<Integer, String> container =
                new KafkaMessageListenerContainer<>(cf, containerProps);
        return container;
    }

    private static Map<String, Object> consumerProps(String host) {
        Map<String, Object> props = new HashMap<>();
        if (host==null || "".equals(host))
            props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, defaultHost);
        else
            props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, host+":9092");
        props.put(ConsumerConfig.GROUP_ID_CONFIG, "spring-kafka-test");
        props.put(ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG, true);
        props.put(ConsumerConfig.AUTO_COMMIT_INTERVAL_MS_CONFIG, "100");
        props.put(ConsumerConfig.SESSION_TIMEOUT_MS_CONFIG, "15000");
        props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, IntegerDeserializer.class);
        props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        return props;
    }

    // wait RET to exit
    @Override
    public void run() {
        System.out.println("press s and ret to exit ...........");
	while (true) {
	    try {
		String s = new BufferedReader(new InputStreamReader(System.in)).readLine();
		if ("s".equals(s))
		    break;
	    } catch (IOException e) {}
	}
	stop=true;
    }

    public static void main(String[] args) throws InterruptedException {
        System.out.println("\n-------------------- KAFKA Consumer start --------------------");
        Consumer consumer=new Consumer();
        new Thread(consumer).start();

        String topic=System.getProperty("kafkatopic");
        if (topic==null || "".equals(topic))
            topic="spring-kafka-test";

        String host=System.getProperty("kafkahost");
        if (host==null || "".equals(topic))
            host="localhost";

        System.out.println("\n-------------------- KAFKA Consumer topic="+topic+", kafka host="+host);

        ContainerProperties containerProps = new ContainerProperties(topic);
        containerProps.setMessageListener(new MyMessageListener<Integer, String>());
//                new MessageListener<Integer, String>() {
//            public void onMessage(ConsumerRecord<Integer, String> message) {
//                System.out.println("\n-------------------- received: " + message.value()+"\n");
//            }
//        });
        KafkaMessageListenerContainer<Integer, String> container = createContainer(containerProps, host);
        container.setBeanName("testAuto");
        container.start();
        Thread.sleep(1000); // wait a bit for the container to start
        while (!consumer.stop) {
            try {Thread.sleep(1 * 1000);} catch (InterruptedException e) {}
        }
        container.stop();
        System.out.println("Stop container");
    }
}
