package com.jack.pinpoint.jumper;

/**
 * Created by jack on 17-10-25.
 */

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;

import com.google.api.client.http.GenericUrl;
import com.google.api.client.http.HttpRequest;
import com.google.api.client.http.HttpRequestFactory;
import com.google.api.client.http.HttpRequestInitializer;
import com.google.api.client.http.HttpResponse;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;

public class GoogleHttpClientSyncServlet extends HttpServlet {

        @Override
        protected void doGet(HttpServletRequest req, HttpServletResponse response)
                throws ServletException, IOException {
                String url="http://localhost:8099/echo/hello";
                String msg="failed";

                HttpTransport NET_HTTP_TRANSPORT = new NetHttpTransport();
                HttpRequestFactory requestFactory = NET_HTTP_TRANSPORT.createRequestFactory(new HttpRequestInitializer() {
                        public void initialize(HttpRequest request) {
                        }
                });


                GenericUrl gurl = new GenericUrl(url);
                HttpRequest request = null;
                HttpResponse resp = null;
                try {
                        request = requestFactory.buildGetRequest(gurl);
                        resp = request.executeAsync().get();

                        InputStream ins = resp.getContent();
                        int count = 10;
                        byte[] buf = new byte[count];
                        int readCount = 0;
                        while (readCount < count) {
                                readCount += ins.read(buf, readCount, count - readCount);
                        }
                        msg = new String(buf);

                        resp.disconnect();
                } catch (Exception ignored) {
                } finally {
                        if (resp != null) {
                                resp.disconnect();
                        }
                }

                PrintWriter out=response.getWriter();
                out.println("get "+msg);
        }
}