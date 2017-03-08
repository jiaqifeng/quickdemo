package com.jack.pinpoint.springkafka.consumer;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.listener.MessageListener;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class MyMessageListener<K, V> implements MessageListener<K, V> {
    @Override
    public void onMessage(ConsumerRecord<K, V> data) {
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
