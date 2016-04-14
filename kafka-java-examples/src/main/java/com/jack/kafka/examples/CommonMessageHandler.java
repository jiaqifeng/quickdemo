package com.jack.kafka.examples;

/**
 * Created by jack on 4/14/16.
 */
public class CommonMessageHandler implements MessageHandler<Integer, String> {
    public String name;
    public CommonMessageHandler(String name) {
        this.name=name;
    }
    public boolean handle(Integer key, String message) {
        System.out.println("CommonMessageHandler "+name+" handling -------- ["+message+"] --------");
        return true;
    }
}
