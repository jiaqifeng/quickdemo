package com.jack.pinpoint.addressbook;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.jack.pinpoint.addressbook.dao")
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
