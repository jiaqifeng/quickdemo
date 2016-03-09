package com.jack.maven.webapp;

import javax.servlet.*;
import java.io.*;

public class Hello implements Servlet {
    public void init(ServletConfig pa) throws ServletException {
        System.out.println("init");
    }
    public ServletConfig getServletConfig() {
        return null;
    }
    public void service(ServletRequest req, ServletResponse resp) throws ServletException, IOException {
        System.out.println("service it");
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
