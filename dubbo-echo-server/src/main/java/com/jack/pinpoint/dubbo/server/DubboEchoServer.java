package com.jack.pinpoint.dubbo.server;

import org.springframework.context.support.ClassPathXmlApplicationContext;

public class DubboEchoServer {
    public static void main(String[] args) throws Exception {
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext(
                new String[] { "applicationProvider.xml" });

        context.start();
        while (true) {
            //System.out.println("press any key to exist");
            System.in.read();
        }
    }
}
