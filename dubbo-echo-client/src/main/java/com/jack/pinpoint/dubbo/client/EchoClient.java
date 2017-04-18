package com.jack.pinpoint.dubbo.client;

import com.jack.pinpoint.dubbo.server.EchoService;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class EchoClient {
    public String echo(String msg){
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext(
                new String[] { "applicationConsumer.xml" });

        context.start();
        EchoService service = (EchoService) context.getBean("echoService");
        String echo=service.echo(msg);
        System.out.println(echo);
        return echo;
    }
}
