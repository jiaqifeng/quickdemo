package com.jack.pinpoint.rabbitmq;

import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * Created by jack on 17-8-14.
 */
public class TopicSender  extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse response)
            throws ServletException, IOException {
        String message=req.getParameter("msg");
        if (message==null || "".equals(message))
            message="hello rabbit";

        String exchange=req.getParameter("exchange");
        if (exchange==null || "".equals(exchange))
            exchange="test-topic";

        String queuename=req.getParameter("queue");
        if (queuename==null || "".equals(queuename))
            queuename="queue-pp";

        try {
            System.out.println("TopicSender.doGet() ---- start");
            System.out.println("TopicSender.doGet() ---- exchange="+exchange+", queue="+queuename+", message="+message);
            ConnectionFactory factory = new ConnectionFactory();
            factory.setHost("localhost");

            Connection connection = factory.newConnection();
            Channel channel = connection.createChannel();
            System.out.println("TopicSender.doGet() ---- create channel is " + channel.getClass().getName());

            channel.exchangeDeclare(exchange, "topic");

            AMQP.BasicProperties.Builder builder = new AMQP.BasicProperties.Builder();
            channel.basicPublish(exchange, "test.topic", false, false, builder.appId("test").build(), message.getBytes());
            System.out.println("TopicSender.doGet() ---- Sent message:" + message);

            channel.close();
            connection.close();
        } catch (Exception e) {
            System.out.println("got execption " + e);
        }

        PrintWriter out = response.getWriter();

        out.println("send rabbitmq:" + message);
    }
}

