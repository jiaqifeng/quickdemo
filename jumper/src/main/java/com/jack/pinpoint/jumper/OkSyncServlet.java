package com.jack.pinpoint.jumper;

/**
 * Created by jack on 17-10-25.
 */

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import com.squareup.okhttp.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class OkSyncServlet extends HttpServlet {
        static Logger logger = LoggerFactory.getLogger(OkSyncServlet.class);

        @Override
        protected void doGet(HttpServletRequest req, HttpServletResponse response)
                throws ServletException, IOException {
                String url="http://localhost:8099/echo/hello";

                logger.info(" -------------------- call echo using OkHttpClient sync --------------------");

                Request request = new Request.Builder().url(url).build();
                OkHttpClient client = new OkHttpClient();
                Response resp = client.newCall(request).execute();

                PrintWriter out=response.getWriter();
                out.println("get "+resp.body().string());
        }
}