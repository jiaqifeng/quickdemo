package com.jack.maven.webapp;

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
    OnceProducer producer = new OnceProducer("topic2");
    String message=producer.sendOnce();
    System.out.println("ProducerServet.doGet done");
    out.println("this is kafka producer="+message);
  }
}

