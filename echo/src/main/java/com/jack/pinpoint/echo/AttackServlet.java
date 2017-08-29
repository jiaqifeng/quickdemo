package com.jack.pinpoint.echo;

import org.apache.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;

/**
 * Test attacktracker plugin when access system file and execute shell commond consider as attack
 * Created by jack on 17-8-29.
 */
public class AttackServlet extends HttpServlet {

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        System.out.println("-------------------------------------- AttackServlet.doGet");

        int loop=1;
        try {
            loop = Integer.parseInt(request.getParameter("loop"));
        } catch (NumberFormatException e) {}

        // bash: pwd
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

        // access files under /etc/
        for (int i=0;i<loop;i++) {
            try {
                File file = new File("/etc/file-"+i);
                file.listFiles();
                FileWriter fw = new FileWriter("Makefile.new");
                fw.close();
            } catch (Exception e) {
            }
        }

        PrintWriter out = response.getWriter();
        out.println(result);

        response.setStatus(200);
    }

    public static class TestPrint {
        public TestPrint() {
            System.out.println("this is TestPrint's constructor");
        }
    }
}
