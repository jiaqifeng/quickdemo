package com.jack.pinpoint.dubbo.server;

public class EchoServiceImpl implements EchoService {
    public String echo(String msg){
        System.out.println("received " + msg);

	//long time will cause dubbo client to try again
        //try {Thread.sleep(1000 * 100);} catch (InterruptedException e) {};
	
        return "echo:"+msg ;
    }
} 
