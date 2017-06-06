package com.jack.pinpoint.jumper;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * Created by jack on 17-6-2.
 */
public class AsyncServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse response)
            throws ServletException, IOException {

        System.out.println("---------- fengjiaqi: start MyRunnable");
        new Thread(new MyRunnable()).start();
        System.out.println("---------- fengjiaqi: start MyRunnable successed");

        PrintWriter out=response.getWriter();
        out.println("done");
    }

    public static class MyRunnable implements Runnable {
        public MyRunnable() {
        }

        public void run() {
            System.out.println("---------- fengjiaqi: MyRunnable start to run");

            String url="http://localhost:8099/echo/hello";
            String loginEntityContent="could not get "+url;

            try {
                HttpClient httpClient = new DefaultHttpClient();
                HttpGet httpGet = new HttpGet(url);
                HttpResponse loginResponse = httpClient.execute(httpGet);

                if (loginResponse.getStatusLine().getStatusCode() == 200) {
                    HttpEntity loginEntity = loginResponse.getEntity();
                    loginEntityContent = EntityUtils.toString(loginEntity);
                    System.out.println("got ok from " + url);
                } else {
                    System.out.println("got failure from " + url);
                }
            } catch (IOException io) {
                System.out.println("got exception: "+io);
            }

            System.out.println("---------- fengjiaqi: MyRunnable run successed");
        }
    }
}
