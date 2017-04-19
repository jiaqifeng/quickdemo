package com.jack.pinpoint.jumper;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Properties;
import kafka.producer.KeyedMessage;
import kafka.producer.ProducerConfig;

public class OnceProducer extends Thread
{
  private final kafka.javaapi.producer.Producer<String, String> producer;
  private final String topic;
  private final Properties props = new Properties();
  private int messageNo = 0;

  public OnceProducer(String topic)
  {
    props.put("serializer.class", "kafka.serializer.StringEncoder");
    props.put("metadata.broker.list", "localhost:9092");
    // using HeadedMessage, wont use below 2 lines
//    props.put("key.serializer.class", "com.jack.maven.webapp.IntegerEncoder");
//    props.put("serializer.class", "com.jack.maven.webapp.PinpointEncoder");
    // Use random partitioner. Don't need the key type. Just set it to Integer.
    // The message is of type String.
    producer = new kafka.javaapi.producer.Producer<String, String>(new ProducerConfig(props));
    this.topic = topic;
    messageNo=1;
  }
  
  public void run() {
    while(true)
    {
      String messageStr = new String("Message_" + messageNo);
      producer.send(new KeyedMessage<String, String>(topic, messageStr));
      messageNo++;
    }
  }

  public String sendOnce() {
      SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
      String messageStr = new String("Message_" + df.format(new Date()));
      //producer.send(new KeyedMessage<Integer, String>(topic, (new Integer(1024)).byteValue(), messageStr));
      producer.send(new KeyedMessage<String, String>(topic, "key1", messageStr));
      //producer.send(new KeyedMessage<Integer, String>(topic, new Integer(1024), messageStr));
      messageNo++;
      producer.close();
      return messageStr;
  }

  public void sendOnceMessage(String message) {
    producer.send(new KeyedMessage<String, String>(topic, "key1", message));
    producer.close();
  }

  public static void main(String[] args) {
    OnceProducer onceProducer = new OnceProducer("topic2");
    String message=onceProducer.sendOnce();
  }
}
