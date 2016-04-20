package com.jack.kafka.examples;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

import java.io.IOException;

/**
 * Created by jack on 4/19/16.
 */
public class HttpClientMessageHandler implements MessageHandler<String, String> {
    public String name;
    public HttpClientMessageHandler(String name) {
        this.name=name;
    }
    public boolean handle(String key, String message) {
        System.out.println("CommonMessageHandler "+name+" handling -------- ["+message+"] --------");
        getUrl("http://localhost:8099/echo-websvr/hello");
        return true;
    }

    public boolean getUrl(String url) {
        //String url="http://localhost:8099/echo-websvr/hello";
        String loginEntityContent="";
        try {
            HttpClient httpClient = new DefaultHttpClient();
            HttpGet httpGet = new HttpGet(url);
            HttpResponse loginResponse = httpClient.execute(httpGet);

            if (loginResponse.getStatusLine().getStatusCode() == 200) {
                HttpEntity loginEntity = loginResponse.getEntity();
                loginEntityContent = EntityUtils.toString(loginEntity);
                System.out.println("got ok from " + url);
            } else {
                System.out.println("got failure from " + url);
            }
        } catch (IOException io) {
            System.out.println("got exception: "+io);
        }
        return true;
    }
}
