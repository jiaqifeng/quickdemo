package com.jack.pinpoint.springkafka.consumer;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.listener.MessageListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;

public class MyMessageListener<K, V> implements MessageListener<K, V> {
    static Logger logger = LoggerFactory.getLogger(MyMessageListener.class);

    @Override
    public void onMessage(ConsumerRecord<K, V> data) {
	try { throw new IOException("jack test pinpont"); } catch (IOException e) { e.printStackTrace();};

        System.out.println("\n-------------------- received: " + data.value()+"\n");

        String echohost=System.getProperty("echohost");
        if (echohost==null || "".equals(echohost))
            echohost="localhost";

        try {
            InputStream inputStream = null;
            InputStreamReader inputStreamReader = null;
            BufferedReader reader = null;
            StringBuffer resultBuffer = new StringBuffer();
            String tempLine = null;

            logger.info("MyMessageListener -------- start access echo");

            URL url = new URL("http://"+echohost+":8099/echo/hello");
            System.out.println("\n-------------------- connect "+echohost+":8099/echo-websvr/hello\n");
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

            System.out.println("MyMessageListener get echo response" + resultBuffer.toString());
        } catch (Exception e) {
            System.out.println("MyMessageListener got exception:"+e);
            e.printStackTrace();
        }
    }
}
