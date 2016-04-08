package com.jack.kafka.examples;

import kafka.consumer.ConsumerConfig;
import kafka.consumer.ConsumerIterator;
import kafka.consumer.KafkaStream;
import kafka.javaapi.consumer.ConsumerConnector;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

/**
 * Created by jack on 4/6/16.
 */
public class PinpointConsumer extends Thread
{
    private final ConsumerConnector consumer;
    private final String topic;

    public PinpointConsumer(String topic)
    {
        consumer = kafka.consumer.Consumer.createJavaConsumerConnector(
                createConsumerConfig());
        this.topic = topic;
    }

    private static ConsumerConfig createConsumerConfig()
    {
        Properties props = new Properties();
        props.put("zookeeper.connect", KafkaProperties.zkConnect);
        props.put("group.id", KafkaProperties.topic2);
        props.put("zookeeper.session.timeout.ms", "400");
        props.put("zookeeper.sync.time.ms", "200");
        props.put("auto.commit.interval.ms", "1000");

        return new ConsumerConfig(props);

    }

    public void run() {
        System.out.println("run start");
        Map<String, Integer> topicCountMap = new HashMap<String, Integer>();
        topicCountMap.put(topic, new Integer(1));
        PinpointDecoder pinpointDecoder=new PinpointDecoder();
        IntegerDecoder integerDecoder=new IntegerDecoder();
        Map<String, List<KafkaStream<Integer, String>>> consumerMap = consumer.createMessageStreams(topicCountMap, integerDecoder, pinpointDecoder);
        KafkaStream<Integer, String> stream =  consumerMap.get(topic).get(0);
        ConsumerIterator<Integer, String> it = stream.iterator();
        System.out.println("before while loop");
        while(it.hasNext()) {
            String data = it.next().message();
            System.out.println("got message, length=" + data.length());
            System.out.println(new String(data));
        }
    }

    public static void main(String[] args)
    {
        System.out.println("main start");
        PinpointConsumer consumerThread = new PinpointConsumer(KafkaProperties.topic2);
        consumerThread.start();
    }
}
