package com.jack.maven.webapp;

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

import java.util.HashMap;
import java.util.Map;

public class RabbitmqSender extends HttpServlet{
  private final static String QUEUE_NAME = "hello";
  @Override
  protected void doGet(HttpServletRequest req, HttpServletResponse response)
    throws ServletException, IOException {
      String message = "Error";
      try {
	  System.out.println("RabbitmqSender doGet()");
	  ConnectionFactory factory = new ConnectionFactory();
	  factory.setHost("localhost");
    
	  Connection connection = factory.newConnection();
	  Channel channel = connection.createChannel();
	  channel.queueDeclare(QUEUE_NAME, false, false, false, null);
	  message = "Hello RabbitMQ World";
	  AMQP.BasicProperties.Builder builder = new AMQP.BasicProperties.Builder();
	  channel.basicPublish("", QUEUE_NAME, builder.appId("test").build(), message.getBytes());
	  System.out.println(" [x] Sent '" + message + "'");
	  channel.close();
	  connection.close();
      } catch (Exception e) {
	  System.out.println("got execption "+e);
      }

    PrintWriter out=response.getWriter();

    out.println("send rabbitmq:"+message);
  }
}
