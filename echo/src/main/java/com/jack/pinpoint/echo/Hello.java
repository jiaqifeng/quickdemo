package com.jack.pinpoint.echo;

import javax.servlet.*;
import java.io.*;

import org.apache.log4j.Logger;

public class Hello implements Servlet {
    static Logger logger = Logger.getLogger(Hello.class);

    public void init(ServletConfig pa) throws ServletException {
        System.out.println("init");
    }
    public ServletConfig getServletConfig() {
        return null;
    }
    public void service(ServletRequest req, ServletResponse resp) throws ServletException, IOException {
        System.out.println("service it");
        logger.info("Hello.service(): test log4j --------++++++++++++++++++---------------_++++++++++++++");
        PrintWriter pw=resp.getWriter();
        pw.println("Hello Webapp");
    }
    public String getServletInfo() {
        return "";
    }
    public void destroy() {
        System.out.println("destroy!");
    }
}
