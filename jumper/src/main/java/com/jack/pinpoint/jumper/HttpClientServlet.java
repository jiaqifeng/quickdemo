package com.jack.pinpoint.jumper;

import org.apache.http.HttpEntity;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.HttpResponse;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.*;
import java.io.*;

public class HttpClientServlet implements Servlet {
    static Logger logger = LoggerFactory.getLogger(HttpClientServlet.class);

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
        logger.info(" -------------------- call echo using HttpClient --------------------");

        try {
            HttpClient httpClient = new DefaultHttpClient();
            HttpGet httpGet = new HttpGet(url);

        // enable below line, agent will send 2 SpanChunk and 1 SPAN to collector
        //for (int i=0;i<23;i++) {
            HttpResponse loginResponse = httpClient.execute(httpGet);

            if (loginResponse.getStatusLine().getStatusCode() == 200) {
                HttpEntity loginEntity = loginResponse.getEntity();
                loginEntityContent = EntityUtils.toString(loginEntity);
                System.out.println("got ok from " + url);
            } else {
                System.out.println("got failure from " + url);
            }
        //}
        } catch (IOException io) {
            System.out.println("got exception: "+io);
        }

        PrintWriter pw=resp.getWriter();
        pw.println(loginEntityContent);

        /*
        accessUrl("http://www.baidu.com");
        accessUrl("http://www.sohu.com");
        accessUrl("http://www.sina.com.cn");
        accessUrl("http://www.163.com");
        */
    }
    public void accessUrl(String url) {
        try {
            HttpClient httpClient = new DefaultHttpClient();
            HttpGet httpGet = new HttpGet(url);

            // enable below line, agent will send 2 SpanChunk and 1 SPAN to collector
            //for (int i=0;i<23;i++) {
            HttpResponse loginResponse = httpClient.execute(httpGet);

            if (loginResponse.getStatusLine().getStatusCode() == 200) {
                HttpEntity loginEntity = loginResponse.getEntity();
                System.out.println("got ok from " + url);
            } else {
                System.out.println("got failure from " + url);
            }
            //}
        } catch (IOException io) {
            System.out.println("got exception: "+io);
        }
    }
    public String getServletInfo() {
        return "";
    }
    public void destroy() {
        System.out.println("destroy!");
    }
}
