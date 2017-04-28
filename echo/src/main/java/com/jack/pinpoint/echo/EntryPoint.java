package com.jack.pinpoint.echo;

import javax.servlet.*;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * Created by jack on 17-4-27.
 */
public class EntryPoint implements Servlet {
    public void init(ServletConfig pa) throws ServletException {
        System.out.println("init");
    }
    public ServletConfig getServletConfig() {
        return null;
    }
    public void service(ServletRequest req, ServletResponse resp) throws ServletException, IOException {
        System.out.println("service it");
        myEntryPoint();
        PrintWriter pw=resp.getWriter();
        pw.println("Hello Webapp");
    }
    public String getServletInfo() {
        return "";
    }
    public void destroy() {
        System.out.println("destroy!");
    }
    public String myEntryPoint() {
        System.out.println("+++++++++++++++++++++++++++++ see here! in com.jack.pinpoint.jumper.EntryPoint.myEntryPoint()");
        return "This is my entry point";
    }
}
