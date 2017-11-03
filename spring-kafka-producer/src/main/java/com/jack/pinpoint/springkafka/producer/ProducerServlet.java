package com.jack.pinpoint.springkafka.producer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class ProducerServlet extends HttpServlet{
  static Logger logger = LoggerFactory.getLogger(ProducerServlet.class);

  @Override
  protected void doGet(HttpServletRequest req, HttpServletResponse response)
    throws ServletException, IOException {
    PrintWriter out=response.getWriter();

    String message=req.getParameter("msg");
    if (message==null || "".equals(message))
      message="hello kafka";

    String topic=req.getParameter("topic");
    if (topic==null || "".equals(topic))
      topic="spring-kafka-test";

    String host=req.getParameter("host");
    if (host==null || "".equals(host))
      host="localhost";

    System.out.println("ProducerServet.doGet done, host="+host+", topic="+topic+",msg="+message);
    logger.info("ProducerServet.doGet() ---------------- host=" + host + ", topic=" + topic + ",msg=" + message);
    SimpleProducer.sendOneMessage(host, topic, message);
    out.println("this is kafka producer="+message);
  }
}

