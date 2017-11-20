package com.jack.pinpoint.rabbitmq;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.util.concurrent.TimeoutException;

import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.LongString;
import com.rabbitmq.client.SaslConfig;
import com.rabbitmq.client.SaslMechanism;
import com.rabbitmq.client.impl.LongStringHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;

public class RabbitmqSenderQpid extends HttpServlet {
    static Logger logger = LoggerFactory.getLogger(RabbitmqSender.class);

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse response)
            throws ServletException, IOException {
        String message=req.getParameter("msg");
        if (message==null || "".equals(message))
            message="hello rabbit";

        String exchange=req.getParameter("exchange");
        if (exchange==null || "".equals(exchange))
            exchange="test-pp";

        String queuename=req.getParameter("queue");
        if (queuename==null || "".equals(queuename))
            queuename="queue-pp";

        try {
            logger.info("RabbitmqSender.doGet() --------------- start --------------------");
            System.out.println("RabbitmqSender.doGet() ---- exchange="+exchange+", queue="+queuename+", message="+message);
            ConnectionFactory factory = new ConnectionFactory();
            factory.setHost("localhost");
            factory.setSaslConfig(new SaslConfig() {
                public SaslMechanism getSaslMechanism(String[] mechanisms) {
                    return new SaslMechanism() {
                        public String getName() {
                            return "ANONYMOUS";
                        }

                        public LongString handleChallenge(LongString challenge, String username, String password) {
                            return LongStringHelper.asLongString("");
                        }
                    };
                }
            });
            // enable below to test AutorecoveringChannel
            factory.setAutomaticRecoveryEnabled(true);

            Connection connection = factory.newConnection();
            Channel channel = connection.createChannel();
            System.out.println("RabbitmqSender.doGet() ---- create channel is " + channel.getClass().getName());

            channel.exchangeDeclare(exchange, "direct", false);
            channel.queueDeclare(queuename, false, false, false, null);
            channel.queueBind(queuename, exchange, "test");

            AMQP.BasicProperties.Builder builder = new AMQP.BasicProperties.Builder();
            channel.basicPublish(exchange, "test", false, false, builder.appId("test").build(), message.getBytes());
            System.out.println("RabbitmqSender.doGet() ---- Sent message:" + message);

            channel.close();
            connection.close();
        } catch (Exception e) {
            System.out.println("got execption " + e);
        }

        PrintWriter out = response.getWriter();

        out.println("send rabbitmq:" + message);
    }
}
