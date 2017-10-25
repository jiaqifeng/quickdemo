package com.jack.pinpoint.jumper;

import com.squareup.okhttp.Callback;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.rmi.server.ExportException;

/**
 * Created by jack on 17-10-25.
 */
public class OkEnqueueServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, final HttpServletResponse response)
            throws ServletException, IOException {
        String url="http://localhost:8099/echo/hello";

        Request request = new Request.Builder().url(url).build();
        OkHttpClient client = new OkHttpClient();
        client.newCall(request).enqueue(new Callback() {
            
            public void onFailure(Request request, IOException e) {
                try {
                    PrintWriter out = response.getWriter();
                    out.println("get echo failed");
                } catch (Exception ee) {}
            }

            public void onResponse(Response resp) throws IOException {
                PrintWriter out=response.getWriter();
                out.println("get "+resp.body().string());
            }
        });
    }
}

