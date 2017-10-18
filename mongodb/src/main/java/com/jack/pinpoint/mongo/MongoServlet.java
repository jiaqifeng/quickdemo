package com.jack.pinpoint.mongo;

import javax.servlet.*;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

import com.mongodb.Block;
import com.mongodb.MongoClient;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.MongoIterable;
import org.apache.log4j.Logger;
import org.bson.Document;

/**
 * Created by Jiaqi Feng on 17-10-11.
 */
public class MongoServlet extends HttpServlet {
    static Logger logger = Logger.getLogger(MongoServlet.class);

    public void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String op=request.getParameter("op");
        if (op == null || "".equals(op))
            op="insertone";
        System.out.println("-------------------------------------- do get op="+op);

        try {
            MongoClient mongoClient = new MongoClient("localhost", 27017);
            MongoDatabase db = mongoClient.getDatabase("testpp");
            System.out.println("-------------------------------------- Connect to database successfully");
            MongoIterable<String> cols=db.listCollectionNames();
            cols.forEach(new Block<String>() {
                public void apply(String s) {
                    System.out.println("person's collection "+s);
                }
            });
            //System.out.println("person's collection "+cols.);

            MongoCollection<Document> mc = db.getCollection("students");

            if (op.equals("insertone")) {
                Document user1 = new Document("name", "user1").append("age", 22).append("email", "10000@qq.com");
                System.out.println("-------------------------------------- insertOne");
                mc.insertOne(user1);
            } else if (op.equals("insertmany")) {
                List<Document> users=new ArrayList<Document>();
                users.add(new Document("name", "user-many1").append("age", 22).append("email", "20001@qq.com"));
                users.add(new Document("name", "user-many2").append("age", 22).append("email", "20002@qq.com"));
                System.out.println("-------------------------------------- insertMany");
                mc.insertMany(users);
            } else if (op.equals("findone")) {
                FindIterable<Document> it = mc.find(new Document("name", "jack"));
                System.out.println("-------------------------------------- find one"+it.first().toString());
            }
        } catch (Exception e) {
            System.err.println( e.getClass().getName() + ": " + e.getMessage() );
        }

        String result="ok";

        PrintWriter out = response.getWriter();
        out.println(result);

		response.setStatus(200);
    }

    // tigger the retransform
    public void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        System.out.println("-------------------------------------- do post");
        PrintWriter out = response.getWriter();
        out.println("service it");
    }
}

