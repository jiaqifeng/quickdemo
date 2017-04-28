package com.jack.pinpoint.echo;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.apache.thrift.TException;
import org.apache.thrift.TProcessor;
import org.apache.thrift.protocol.TJSONProtocol;
import org.apache.thrift.protocol.TProtocol;
import org.apache.thrift.transport.TIOStreamTransport;

import javax.servlet.*;
import java.io.*;

/**
 * Created by jack on 17-4-28.
 */
public class ThriftServlet implements Servlet {
    public void init(ServletConfig pa) throws ServletException {
        System.out.println("init");
    }
    public ServletConfig getServletConfig() {
        return null;
    }
    public void service(ServletRequest req, ServletResponse resp) throws ServletException, IOException {
        System.out.println("service it");

        doTalkService();

        PrintWriter pw=resp.getWriter();
        pw.println("service done");
    }
    public String getServletInfo() {
        return "";
    }
    public void destroy() {
        System.out.println("destroy!");
    }

    public String doTalkService() {
        TProcessor processor=new TalkService.Processor(new TalkServiceImpl());

        InputStream in=new ByteArrayInputStream(new String("[1,\"talk\",1,1,{\"1\":{\"str\":\"jack\"}}]").getBytes());;
        ByteArrayOutputStream out = new ByteArrayOutputStream();

        TIOStreamTransport e = new TIOStreamTransport(in, out);
        TProtocol inProtocol = new TJSONProtocol(e);
        TProtocol outProtocol = new TJSONProtocol(e);

        try {
            processor.process(inProtocol, outProtocol);
        } catch (TException e1) {
        }

        String str = out.toString();
        return str;
    }

    public static class TalkServiceImpl implements TalkService.Iface {

        public String talk(String name) throws TException {
            System.out.println("================================================         TalkServiceImpl.talk() start");
            String url="http://www.baidu.com";
            String loginEntityContent="could not get "+url;

            try {
                HttpClient httpClient = new DefaultHttpClient();
                HttpGet httpGet = new HttpGet(url);

                // enable below line, agent will send 2 SpanChunk and 1 SPAN to collector
                //for (int i=0;i<23;i++) {
                HttpResponse loginResponse = httpClient.execute(httpGet);

                if (loginResponse.getStatusLine().getStatusCode() == 200) {
                    HttpEntity loginEntity = loginResponse.getEntity();
                    loginEntityContent = EntityUtils.toString(loginEntity);
                    System.out.println("got ok from " + url);
                } else {
                    System.out.println("got failure from " + url);
                }
                //}
            } catch (IOException io) {
                System.out.println("got exception: "+io);
            }
            System.out.println("================================================         TalkServiceImpl.talk() end");
            return "talk with echo is cool"+loginEntityContent;
        }
    }
}
