package com.jack.pinpoint.dubbo.server;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class EchoServiceImpl implements EchoService {
    static Logger logger = LoggerFactory.getLogger(EchoServiceImpl.class);

    public String echo(String msg){
        System.out.println("received " + msg);

        logger.info(" -------------------- handle message --------------------");

        //long time will cause dubbo client to try again
        if (System.getProperty("sleeplong") != null)
            try {Thread.sleep(1000 * 300);} catch (InterruptedException e) {};
	
        return "echo:"+msg ;
    }
} 
