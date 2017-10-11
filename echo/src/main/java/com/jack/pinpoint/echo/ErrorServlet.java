package com.jack.pinpoint.echo;

import javax.servlet.*;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;

import org.apache.log4j.Logger;

/**
 * add profiler.entrypoint=java.lang.Runtime.exec,com.jack.pinpoint.echo.ErrorServlet.exec
 *
 * 1 http POST :8099/echo/error, cause UNIXProcess be retransformed.
 * 2 http :8099/echo/error while print out the first args
 *
 * Created by jack on 17-5-5.
 */
public class ErrorServlet extends HttpServlet {
    static Logger logger = Logger.getLogger(ErrorServlet.class);

    public void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        TestPrint tp = new TestPrint();

        System.out.println("-------------------------------------- do get");
        System.out.println("-------------------------------------- Runtime loader="+Runtime.getRuntime().getClass().getClassLoader()+",Runtime="+Runtime.getRuntime());

        Process p=Runtime.getRuntime().exec("pwd");
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

        File file = new File("/etc/Makefile");
        file.listFiles();
        FileWriter fw = new FileWriter("Makefile.new");
        fw.close();

        PrintWriter out = response.getWriter();
        out.println(result);

		response.setStatus(200);
    }

    // add profiler.entrypoint=java.lang.Runtime.exec,com.jack.pinpoint.echo.ErrorServlet.exec
    public void exec() {
        System.out.println("fengjiaqi: in ErrorServlet.exec()");
    }

    // tigger the retransform
    public void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        System.out.println("-------------------------------------- do post");
        exec();
        PrintWriter out = response.getWriter();
        out.println("service it");
    }
    public static class TestPrint {
        public TestPrint() {
            System.out.println("this is TestPrint's constructor");
        }
    }
}

