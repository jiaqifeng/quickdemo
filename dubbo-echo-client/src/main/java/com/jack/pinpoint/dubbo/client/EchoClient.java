package com.jack.pinpoint.dubbo.client;

import com.jack.pinpoint.dubbo.server.EchoService;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class EchoClient {
    public void sayHello(){
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext(
                new String[] { "applicationConsumer.xml" });

        context.start();
        EchoService service = (EchoService) context.getBean("echoService");
        System.out.println(service.echo("lisa"));
    }
}
