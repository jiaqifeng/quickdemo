package com.jack.pinpoint.addressbook;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@MapperScan("com.jack.pinpoint.addressbook.dao")
@ComponentScan(basePackages={"com.jack.pinpoint.addressbook", "com.jack.pinpoint.addressbook.controller"})
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class,args);
    }

    public void initdb() {
        try {
            String appfunctionSql = getClass().getResource("initdb.sql").toURI().toString();
        } catch (Exception e) {
            System.out.println("inidb failed for "+e);
            e.printStackTrace();
        }
    }
}
