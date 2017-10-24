package com.jack.pinpoint.dubbo.client;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

import org.apache.log4j.Logger;

/**
 * Created by jack on 17-4-18.
 */
public class DubboClientServlet extends HttpServlet {

    static Logger logger = Logger.getLogger(DubboClientServlet.class);

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse response)
            throws ServletException, IOException {
        PrintWriter out=response.getWriter();

        logger.info("DubboClientServlet.doGet(): --------++++++++++++++++++---------------_++++++++++++++");

        EchoClient client = new EchoClient();
        String echo=client.echo("lisa");
        System.out.println("got "+echo);
        out.println("got "+echo);
    }
}
