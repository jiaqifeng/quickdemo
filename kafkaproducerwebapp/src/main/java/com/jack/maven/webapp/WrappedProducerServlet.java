package com.jack.maven.webapp;

import com.jack.kafka.wrapper.HeadedMessage;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by jack on 4/14/16.
 */
public class WrappedProducerServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse response)
            throws ServletException, IOException {

        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
        HeadedMessage hm=new HeadedMessage();
        hm.setMessage(new String("WrappedMessage created at " + df.format(new Date())));
        hm.setHeader("topic", "topic2");

        PrintWriter out=response.getWriter();
        OnceProducer producer = new OnceProducer("topic2");
        String msg=hm.encode();
        producer.sendOnceMessage(msg);
        out.println("send on topic2 on kafka of "+msg);

        System.out.println("ProducerServet.doGet done");
    }
}
