package com.jack.pinpoint.dubbo.server;

public class EchoServiceImpl implements EchoService {
    public String echo(String msg){
        System.out.println("received " + msg);
        return "echo:"+msg ;
    }
} 
