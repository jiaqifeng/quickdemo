package com.jack.pinpoint.jumper;

import com.squareup.okhttp.Callback;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

/**
 * Created by jack on 17-10-25.
 */
public class OkAsyncServlet extends HttpServlet {
    static Logger logger = LoggerFactory.getLogger(OkAsyncServlet.class);
    //volatile String result;
    String result;

    @Override
    protected void doGet(HttpServletRequest req, final HttpServletResponse response)
            throws ServletException, IOException {
        String url="http://localhost:8099/echo/hello";

        Request request = new Request.Builder().url(url).build();
        OkHttpClient client = new OkHttpClient();
        logger.info(" -------------------- call echo using OkHttpClient async --------------------");

        // we have to use a latch, or the response may be closed before the Callback
        final CountDownLatch latch = new CountDownLatch(1);
        result = "undefined";
        client.newCall(request).enqueue(new Callback() {

            public void onFailure(Request request, IOException e) {
                /* I got interesting result here, when latch.countDown() called at next line instead
                 * after set result, the out.println may sometime return undefined. At least it is
                 * really ofter for one day, about 1/3 times it will apear.
                 * Even I add the volatile for result does not work, so I think it means after
                 * latch.countDown(), the tomcat io thread get to execute real quickly sometimes.
                 *
                 * With or without pinpoint agent, it happens too.
                 *
                 * If you use response here, it must be final, for response is a local variable in java,
                 * not like nodejs which is auto treated as closure, so I make result a outer class field.
                 */
                //latch.countDown();
                logger.info(" -------------------- call using OkHttpClient.enqueue onFailure --------------------");
                result = "failed";
                latch.countDown();
            }

            public void onResponse(Response resp) throws IOException {
                //latch.countDown();
                logger.info(" -------------------- call using OkHttpClient.enqueue onResponse --------------------");
                result = resp.body().string();
                latch.countDown();
            }
        });
        try {
            latch.await(5, TimeUnit.SECONDS);
        } catch (Exception e) {}
        PrintWriter out = response.getWriter();
        out.println("get echo "+result);
    }
}

