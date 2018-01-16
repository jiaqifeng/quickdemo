package com.jack.pinpoint.rabbitmq;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages={"com.jack.pinpoint.rabbitmq"})
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class,args);
    }
}
