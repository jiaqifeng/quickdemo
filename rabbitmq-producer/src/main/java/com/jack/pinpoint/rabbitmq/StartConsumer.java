package com.jack.pinpoint.rabbitmq;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.Consumer;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * Created by jack on 18-1-16.
 */
public class StartConsumer extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse response)
            throws ServletException, IOException {

        PrintWriter out = response.getWriter();

        try {
            String queuename = System.getProperty("queuename");
            if (queuename == null || "".equals(queuename))
                queuename = "queue-pp";

            ConnectionFactory factory = new ConnectionFactory();
            factory.setHost("localhost");
            Connection connection = factory.newConnection();
            Channel channel = connection.createChannel();

            channel.queueDeclare(queuename, false, false, false, null);
            System.out.println("Receiver.main() ---- Waiting for messages. To exit press CTRL+C");

            Consumer consumer = new MyConsumer(channel);
            channel.basicConsume(queuename, true, consumer);
        } catch (Exception e) {
            out.println("start failed: " + e);
            return;
        }
        out.println("start ok");
    }
}
