package com.jack.pinpoint.echo;

import javax.servlet.*;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import org.apache.log4j.Logger;

/**
 * add profiler.entrypoint=java.lang.Runtime.exec,com.jack.pinpoint.echo.ErrorServlet.exec
 *
 * while this not work as expected
 *
 * Created by jack on 17-5-5.
 */
public class ErrorServlet extends HttpServlet {
    static Logger logger = Logger.getLogger(ErrorServlet.class);

    public void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        System.out.println("-------------------------------------- do get");
        exec();

		response.setStatus(200);
        PrintWriter out = response.getWriter();
        out.println("service it");
        //上面两行代码相当于下面这一句，会默认把状态码设置为302
        //response.sendRedirect("/servlet/Demo6");
    }
    public void exec() {
        System.out.println("fengjiaqi: in ErrorServlet.exec()");
    }

    public void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        System.out.println("-------------------------------------- do post");
        Process p=Runtime.getRuntime().exec("ls /home");
        BufferedReader bufferedReader=new BufferedReader(new InputStreamReader(p.getInputStream()));
        String ls;
        String result="";
        while ((ls=bufferedReader.readLine()) != null) {
            System.out.println(ls);
            result = result + "," + ls;
        }
        try {
            p.waitFor();
        } catch (InterruptedException e) {}
        PrintWriter out = response.getWriter();
        out.println(result);
    }
}

