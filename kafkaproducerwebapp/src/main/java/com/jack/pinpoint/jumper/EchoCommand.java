package com.jack.pinpoint.jumper;

import com.netflix.hystrix.HystrixCommand;
import com.netflix.hystrix.HystrixCommandGroupKey;
import com.netflix.hystrix.HystrixCommandProperties;

import org.apache.http.HttpEntity;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.HttpResponse;
import org.apache.http.util.EntityUtils;

import java.io.IOException;

/**
 * Created by Administrator on 2016/3/2.
 */
public class EchoCommand extends HystrixCommand<String> {
    private final String _name;

    public EchoCommand(String name)
    {
        super(HystrixCommandGroupKey.Factory.asKey("EchoService"));
        _name = new String(name);
    }

    @Override
    protected String run() {
        String url="http://localhost:8099/echo-websvr/hello";
        String loginEntityContent="could not get "+url;

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

        return loginEntityContent;
    }
}
