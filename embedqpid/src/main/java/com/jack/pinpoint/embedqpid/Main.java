package com.jack.pinpoint.embedqpid;

import org.apache.qpid.server.BrokerOptions;
import org.apache.qpid.server.Broker;

public class Main {
    //static Logger logger = Logger.getLogger(Main.class);

    public static void main( String[] args ) {
        final Broker broker = new Broker();
        BrokerOptions brokerOptions = new BrokerOptions();
        brokerOptions.setConfigProperty("qpid.amqp_port", "5672");
        //brokerOptions.setConfigProperty("qpid.broker.defaultPreferenceStoreAttributes", "{\"type\": \"Noop\"}");
        brokerOptions.setConfigProperty("qpid.vhost", "localhost");
        //brokerOptions.setConfigProperty("auth", "no");
        //brokerOptions.setInitialConfigurationLocation("/tmp/qpid/qpid-config.json");
        brokerOptions.setConfigurationStoreType("Memory");

        // set work dir, sys prop "qpid.work_dir"
        System.setProperty("qpid.work_dir", "/tmp/qpidworktmp");
        System.setProperty("qpid.initialConfigurationLocation", "qpid.json");
        // set initial config, 2 ways
        //brokerOptions.setInitialConfigurationLocation(initialConfigLocation);// config.json path
        // default is getClassLoader().getResource(System.getProperty("qpid.initialConfigurationLocation"))
        brokerOptions.setStartupLoggedToSystemOut(false);

        System.out.println("--------------------- start qpid option="+brokerOptions.toString());
        try {
            broker.startup(brokerOptions);
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("--------------------- start qpid failed ");
            return;
        }
        System.out.println("--------------------- start qpid successed ");

        Runtime.getRuntime().addShutdownHook(new Thread() {
            @Override
            public void run() {
                broker.shutdown();
            }
        });

        while (true) {
            try {Thread.sleep(1 * 1000);} catch (InterruptedException e) {}
        }
    }
}
