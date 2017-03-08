package com.jack.pinpoint.springkafka.producer;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class ProducerServlet extends HttpServlet{

  @Override
  protected void doGet(HttpServletRequest req, HttpServletResponse response)
    throws ServletException, IOException {
    PrintWriter out=response.getWriter();

    String message=req.getParameter("msg");

    System.out.println("ProducerServet.doGet done,msg="+message);
    SimpleProducer.sendOneMessage(message);
    out.println("this is kafka producer="+message);
  }
}

