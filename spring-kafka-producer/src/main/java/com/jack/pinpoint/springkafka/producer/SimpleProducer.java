package com.jack.pinpoint.springkafka.producer;

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
import org.springframework.kafka.support.SendResult;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.util.concurrent.ListenableFutureCallback;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

public class SimpleProducer {
    static final String host= "localhost:9092";//"172.19.11.197:9092";

    private static Map<String, Object> senderProps() {
        Map<String, Object> props = new HashMap<>();
        props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, host);
        props.put(ProducerConfig.RETRIES_CONFIG, 0);
        props.put(ProducerConfig.BATCH_SIZE_CONFIG, 16384);
        props.put(ProducerConfig.LINGER_MS_CONFIG, 1);
        props.put(ProducerConfig.BUFFER_MEMORY_CONFIG, 33554432);
        props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, IntegerSerializer.class);
        props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        props.put(ProducerConfig.ENABLE_APM_CONFIG, "true");
        return props;
    }

    private static KafkaTemplate<Integer, String> createTemplate() {
        Map<String, Object> senderProps = senderProps();
        ProducerFactory<Integer, String> pf =
                new DefaultKafkaProducerFactory<Integer, String>(senderProps);
        KafkaTemplate<Integer, String> template = new KafkaTemplate<>(pf);
        return template;
    }

    public static void sendOneMessage(String msg) {
        ContainerProperties containerProps = new ContainerProperties("spring-kafka-test");

        Map<String, Object> senderProps = senderProps();
        DefaultKafkaProducerFactory<Integer, String> dpf=new DefaultKafkaProducerFactory<Integer, String>(senderProps);
        ProducerFactory<Integer, String> pf = dpf;

        KafkaTemplate<Integer, String> template = new KafkaTemplate<>(dpf);

        System.out.println("\n-------------------- Start send message:"+msg);

        ListenableFuture<SendResult<Integer, String>> future=template.send("spring-kafka-test", 0, msg);
        future.addCallback(new ListenableFutureCallback<SendResult<Integer, String>>() {
            @Override
            public void onFailure(Throwable throwable) {
                System.out.println("\n-------------------- send message fail for "+throwable);
            }

            @Override
            public void onSuccess(SendResult<Integer, String> integerStringSendResult) {
                System.out.println("\n-------------------- Start message success");
            }
        });
        template.flush();

        try {
            future.get();
        } catch (Exception e) {
            System.out.println("future.get exception:"+e);
        }
        dpf.stop();

        System.out.println("\n-------------------- exit");
    }
}
