package com.jack.pinpoint.jumper;

import com.ning.http.client.AsyncHttpClient;
import com.ning.http.client.Response;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.Servlet;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.concurrent.Future;

/**
 * Created by jack on 17-10-25.
 */
public class NingServlet implements Servlet {
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
        logger.info(" -------------------- call echo using Ning http client --------------------");

        AsyncHttpClient client = new AsyncHttpClient();
        try {
            Future<Response> f = client.preparePost(url).execute();
            Response response = f.get();
            if (response.getStatusCode() == 200) {
                loginEntityContent = response.getResponseBody();
                System.out.println("got ok from " + url);
            } else {
                System.out.println("got failure from " + url);
            }
        } catch (Exception e) {
            System.out.println("got failure exception " + e);
        } finally {
            client.close();
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
