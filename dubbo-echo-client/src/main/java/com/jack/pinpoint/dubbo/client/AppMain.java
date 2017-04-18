package com.jack.pinpoint.dubbo.client;

public class AppMain {  
    public static void main(String[] args) {  
        EchoClient client = new EchoClient();
        client.sayHello();
    }  
}
