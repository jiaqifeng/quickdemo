package com.jack.pinpoint.echo;

import javax.servlet.*;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import org.apache.log4j.Logger;

/**
 * Created by jack on 17-5-5.
 */
public class ErrorServlet extends HttpServlet {
    static Logger logger = Logger.getLogger(ErrorServlet.class);

    public void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

		response.setStatus(500);
        //上面两行代码相当于下面这一句，会默认把状态码设置为302
        //response.sendRedirect("/servlet/Demo6");
    }
}

