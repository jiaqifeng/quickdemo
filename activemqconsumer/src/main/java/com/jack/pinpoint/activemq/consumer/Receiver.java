package com.jack.pinpoint.activemq.consumer;

import java.io.IOException;
import java.util.concurrent.TimeoutException;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.Destination;
import javax.jms.MessageConsumer;
import javax.jms.Session;
import javax.jms.TextMessage;
import org.apache.activemq.ActiveMQConnection;
import org.apache.activemq.ActiveMQConnectionFactory;

public class Receiver
{
    private final static String QUEUE_NAME = "pptest";

    public static void main( String[] args ) {
        ConnectionFactory connectionFactory;
        Connection connection = null;
        Session session;
        Destination destination;
        MessageConsumer consumer;
        connectionFactory = new ActiveMQConnectionFactory(
                ActiveMQConnection.DEFAULT_USER,
                ActiveMQConnection.DEFAULT_PASSWORD,
                "tcp://localhost:61616");
        try {
	    while (true) {
		connection = connectionFactory.createConnection();
		connection.start();
		session = connection.createSession(Boolean.FALSE,
						   Session.AUTO_ACKNOWLEDGE);
		destination = session.createQueue(QUEUE_NAME);
		consumer = session.createConsumer(destination);
		while (true) {
		    //设置接收者接收消息的时间，为了便于测试，这里谁定为100s
		    TextMessage message = (TextMessage) consumer.receive(100000);
		    if (null != message) {
			System.out.println("recevie message:" + message.getText());
			onMessage(message.getText());
		    } else {
			break;
		    }
		}
	    }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (null != connection)
                    connection.close();
            } catch (Throwable ignore) {
            }
        }
    }

    public static void onMessage(String data) {
	try { throw new IOException("jack test pinpont"); } catch (IOException e) { e.printStackTrace();};
        System.out.println("\n-------------------- received: " + data+"\n");

        try {
            InputStream inputStream = null;
            InputStreamReader inputStreamReader = null;
            BufferedReader reader = null;
            StringBuffer resultBuffer = new StringBuffer();
            String tempLine = null;

            URL url = new URL("http://localhost:8099/echo-websvr/hello");//http://www.baidu.com");
            HttpURLConnection conn = (HttpURLConnection )url.openConnection();

            conn.setDoOutput(true);
            conn.setDoInput(true);
            conn.setUseCaches(false);
            conn.setConnectTimeout(2*1000);
            conn.setReadTimeout(2*1000);
            conn.setRequestMethod("GET");
            conn.connect();

            if (conn.getResponseCode() >= 300) {
                throw new Exception(
                        "HTTP Request is not success, Response code is " + conn.getResponseCode());
            }

            if (conn.getResponseCode() == HttpURLConnection.HTTP_OK) {
                inputStream = conn.getInputStream();
                inputStreamReader = new InputStreamReader(inputStream);
                reader = new BufferedReader(inputStreamReader);

                while ((tempLine = reader.readLine()) != null) {
                    resultBuffer.append(tempLine);
                    resultBuffer.append("\n");
                }
            }

//            OutputStream outs = conn.getOutputStream();
//            InputStream ins = conn.getInputStream();
//
//            int count = 10;
//            byte[] buf = new byte[count];
//            int readCount = 0;
//            while (readCount < count) {
//                readCount += ins.read(buf, readCount, count - readCount);
//            }
//            String msg = new String(buf);
            System.out.println("MyMessageListener get echo response" + resultBuffer.toString());
        } catch (Exception e) {
            System.out.println("MyMessageListener got exception:"+e);
            e.printStackTrace();
        }
    }
}
