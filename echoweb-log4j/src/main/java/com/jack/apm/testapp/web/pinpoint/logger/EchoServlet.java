package com.jack.apm.testapp.web.pinpoint.logger;

import org.apache.log4j.Logger;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class EchoServlet extends HttpServlet {
    static Logger logger = Logger.getLogger(EchoServlet.class);
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse response) throws ServletException, IOException {
        BufferedReader in=new BufferedReader(new InputStreamReader(req.getInputStream()));
        StringBuilder sb = new StringBuilder();
        String line;
        if ((line = in.readLine()) != null)
            sb.append(line);
        PrintWriter out=response.getWriter();
        out.println("echo"+sb.toString());
        logger.info("EchoServlet.doGet done for log4j --------++++++++++++++++++---------------_++++++++++++++");
        System.out.println("EchoServlet.doGet done");
    }
}
