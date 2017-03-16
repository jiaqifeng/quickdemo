package com.jack.pinpoint.activemq.producer.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class ProducerController {
    private Logger logger =  LoggerFactory.getLogger(this.getClass());

    MessageSender sender=new MessageSender();
    
    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String info() {
        return "hello";
    }

    @RequestMapping(value = "/", method = RequestMethod.POST)
    @ResponseBody
    public String sendMessage() {
	//TODO: send a text message
	sender.send("cool");
        return "ok";
    }

}
