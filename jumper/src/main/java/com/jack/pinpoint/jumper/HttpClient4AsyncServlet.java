package com.jack.pinpoint.jumper;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Future;

import org.apache.http.Consts;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.concurrent.BasicFuture;
import org.apache.http.concurrent.FutureCallback;
import org.apache.http.impl.nio.client.CloseableHttpAsyncClient;
import org.apache.http.impl.nio.client.HttpAsyncClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

/**
 * Created by jack on 17-11-13.
 */
public class HttpClient4AsyncServlet extends HttpServlet {
    static Logger logger = LoggerFactory.getLogger(HttpClient4AsyncServlet.class);

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse response)
            throws ServletException, IOException {
        logger.info(" -------------------- call echo using HttpClient 4 async --------------------");

        CloseableHttpAsyncClient httpClient = HttpAsyncClients.custom().useSystemProperties().build();
        httpClient.start();

        try {
            HttpPost httpRequest = new HttpPost("http://localhost:8099/echo/hello");

            List<NameValuePair> params = new ArrayList<NameValuePair>();
            params.add(new BasicNameValuePair("param1", "value1"));
            httpRequest.setEntity(new UrlEncodedFormEntity(params, Consts.UTF_8.name()));

            Future<HttpResponse> responseFuture = httpClient.execute(httpRequest, null);
            HttpResponse resp = (HttpResponse) responseFuture.get();

            if ((resp != null) && (resp.getEntity() != null)) {
                EntityUtils.consume(resp.getEntity());
            }
        } catch (Exception e) {
        } finally {
            httpClient.close();
        }


        PrintWriter out = response.getWriter();
        out.println("done");
    }
}
