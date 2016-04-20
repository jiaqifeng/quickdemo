package com.jack.kafka.examples;

import com.jack.kafka.wrapper.HeadedMessage;
import kafka.consumer.ConsumerConfig;
import kafka.consumer.ConsumerIterator;
import kafka.consumer.KafkaStream;
import kafka.javaapi.consumer.ConsumerConnector;
import kafka.message.MessageAndMetadata;
import kafka.serializer.StringDecoder;

import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

/**
 * Created by jack on 4/14/16.
 */
public class MessageDispatcher extends Thread {
    private final ConsumerConnector consumer;
    private final String topic;
    Map<String, MessageHandler> handlersMap=new HashMap<String, MessageHandler>(4);

    public MessageDispatcher()
    {
        consumer = kafka.consumer.Consumer.createJavaConsumerConnector(
                createConsumerConfig());
        topic="topic2";
        // select one of below line, 
        //handlersMap.put("topic2", new CommonMessageHandler("worker1"));    // the call topology end here
        handlersMap.put("topic2", new HttpClientMessageHandler("worker2"));  // call echo server to show more topology
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
        System.out.println("MessageDispatcher.run() start");
        ConsumerIterator<byte[], byte[]> it=null;
        Map<String, Integer> topicCountMap = new HashMap<String, Integer>();
        topicCountMap.put(topic, new Integer(1));
        Map<String, List<KafkaStream<byte[], byte[]>>> consumerMap = consumer.createMessageStreams(topicCountMap);
        KafkaStream<byte[], byte[]> stream =  consumerMap.get(topic).get(0);
        it = stream.iterator();

        while(it.hasNext()) {
            System.out.println("while loop once");
            MessageAndMetadata<byte[], byte[]> msg=it.next();
            String key=new String(msg.key());
            HeadedMessage data = new HeadedMessage();
            data.decode(new String(msg.message()));
            dispatch(topic, key, data);
        }
    }

    public boolean dispatch(String topic, String key, HeadedMessage msg) {
        System.out.println("MessageDispatcher.dispatch() enter ...");
        MessageHandler<String, String> handler=handlersMap.get(topic);
        if (handler!=null)
            handler.handle(key, msg.getMessage());
        return true;
    }

    public static void main(String[] args)
    {
        System.out.println("MessageDispatcher start");
        MessageDispatcher dispatcher = new MessageDispatcher();
        dispatcher.start();
    }
}
