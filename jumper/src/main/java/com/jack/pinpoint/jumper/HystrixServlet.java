package com.jack.pinpoint.jumper;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class HystrixServlet extends HttpServlet{

  @Override
  protected void doGet(HttpServletRequest req, HttpServletResponse response)
    throws ServletException, IOException {
    PrintWriter out=response.getWriter();
    out.println(new EchoCommand("me").execute());
  }
}
