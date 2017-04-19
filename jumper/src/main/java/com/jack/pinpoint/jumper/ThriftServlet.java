package com.jack.pinpoint.jumper;

import org.apache.http.HttpEntity;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.HttpResponse;
import org.apache.http.util.EntityUtils;
import org.apache.thrift.*;
import org.apache.thrift.protocol.TJSONProtocol;
import org.apache.thrift.protocol.TProtocol;
import org.apache.thrift.transport.TIOStreamTransport;

import javax.servlet.*;
import java.io.*;
import java.util.Map;

public class ThriftServlet implements Servlet {
    public void init(ServletConfig pa) throws ServletException {
        System.out.println("init");
    }
    public ServletConfig getServletConfig() {
        return null;
    }
    public void service(ServletRequest req, ServletResponse resp) throws ServletException, IOException {
        System.out.println("service it");

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

        PrintWriter pw=resp.getWriter();
        pw.println("service done");
    }
    public String getServletInfo() {
        return "";
    }
    public void destroy() {
        System.out.println("destroy!");
    }

    interface MyInterface {
        void handle();
    }

    public static class MyInterfaceImpl implements MyInterface {
        public void handle() {
            String url="http://localhost:8099/echo/hello";
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
        }
    }
    public static class TalkServiceImpl implements TalkService.Iface {

        public String talk(String name) throws TException {
            System.out.println("================================================         TalkServiceImpl.talk() start");
            String url="http://localhost:8099/echo/hello";
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
    public static class MyProcessor extends TBaseProcessor {
        protected MyProcessor(Object iface, Map map) {
            super(iface, map);
        }

        public boolean process(TProtocol in, TProtocol out) throws TException {
            System.out.println("cool to see my process");
            MyInterfaceImpl impl=new MyInterfaceImpl();
            impl.handle();
            return true;
        }
    }
    public static class MyProcessFunction extends ProcessFunction<MyInterface, MyData> {

        public MyProcessFunction(String methodName) {
            super(methodName);
        }

        @Override
        protected boolean isOneway() {
            return false;
        }

        @Override
        public TBase getResult(MyInterface o, MyData tBase) throws TException {
            return null;
        }

        @Override
        public MyData getEmptyArgsInstance() {
            return null;
        }
    }
}
