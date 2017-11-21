package com.jack.pinpoint.jumper;

// below is httpclient3 class
//import org.apache.http.HttpEntity;
import org.apache.commons.httpclient.HttpClient;
//import org.apache.http.client.methods.HttpGet;
//import org.apache.http.impl.client.DefaultHttpClient;
//import org.apache.http.HttpResponse;
//import org.apache.http.util.EntityUtils;

import org.apache.commons.httpclient.methods.GetMethod;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.*;
import java.io.*;

public class HttpClient3Servlet implements Servlet {
    static Logger logger = LoggerFactory.getLogger(HttpClient3Servlet.class);

    public void init(ServletConfig pa) throws ServletException {
        System.out.println("init");
    }
    public ServletConfig getServletConfig() {
        return null;
    }
    public void service(ServletRequest req, ServletResponse resp) throws ServletException, IOException {
        String url="http://localhost:8099/echo/hello";
        String loginEntityContent="could not get "+url;

        System.out.println("service it");
        logger.info(" -------------------- call echo using HttpClient 3 --------------------");

        try {
            HttpClient client = new HttpClient();
            GetMethod method = new GetMethod(url);

            int code = client.executeMethod(method);

            if (code == 200) {
                loginEntityContent = method.getResponseBodyAsString();
                System.out.println("got ok from " + url);
            } else {
                System.out.println("got failure from " + url);
            }

        } catch (IOException io) {
            System.out.println("got exception: "+io);
        }

        PrintWriter pw=resp.getWriter();
        pw.println(loginEntityContent);
    }

    public String getServletInfo() {
        return "";
    }
    public void destroy() {
        System.out.println("destroy!");
    }
}
