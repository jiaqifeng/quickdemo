package com.jack.pinpoint.echo;

import org.apache.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.Enumeration;

/**
 * test getInputStream conflics with getParameter
 * after getParameter() the inputstream contains nothing.
 *
 * Created by jack on 17-11-1.
 */
public class PostParaServlet extends HttpServlet {
    static Logger logger = Logger.getLogger(ErrorServlet.class);

    public void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String name = "null";//req.getParameter("name");
        System.out.println("-------------------------------------- do get, request="+req.getClass().getName());
        System.out.println("-------------------------------------- do get, para name="+name);

        //testKeys(req);

        InputStream in = req.getInputStream();
        try {
            byte b[] = new byte[1024];
            in.read(b);
            in.close();
            System.out.println("-------------------------------------- do get, stream="+new String(b));
        } catch (Exception e) {
            System.out.println("-------------------------------------- do get, parse stream got "+e);
            e.printStackTrace();
        }

        PrintWriter out = resp.getWriter();
        out.println("name="+name);
        resp.setStatus(200);
    }

    public void testKeys(HttpServletRequest request) {
        Enumeration<?> attrs = request.getParameterNames();
        while (attrs.hasMoreElements()) {
            String key = attrs.nextElement().toString();
            Object value = request.getParameter(key);
            if (value != null) {
                System.out.println("fengjiaqi : ------------------------------------------------------ param "+key+"="+value.toString());
            } else {
                System.out.println("fengjiaqi : ------------------------------------------------------ param "+key+"=");
            }
        }
    }
}
