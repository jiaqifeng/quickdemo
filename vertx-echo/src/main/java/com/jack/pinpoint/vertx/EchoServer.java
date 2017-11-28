package com.jack.pinpoint.vertx;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Vertx;
import io.vertx.core.http.HttpServer;
import io.vertx.core.http.HttpServerRequest;
import io.vertx.core.http.HttpServerResponse;
import io.vertx.core.Handler;

public class EchoServer extends AbstractVerticle {
    public static void main(String[] args) {

        /*
         * We create an HTTP server instance, and we set a request handler on
         * it. The request handler will be called whenever a request arrives on
         * the server.
         * 
         * When that happens we are just going to set the content type to
         * text/plain, and write Hello World! and end the response.
         * 
         * We then tell the server to listen at port 8080 (default host is
         * localhost).
         * 
         * You can run this, and point your browser at http://localhost:8080 to
         * verify that it works as expected.
         */
        HttpServer server = Vertx.vertx().createHttpServer();

        server.requestHandler(new Handler<HttpServerRequest>() {

            public void handle(HttpServerRequest request) {

                // This handler gets called for each request that arrives on the
                // server
                HttpServerResponse response = request.response();
                response.putHeader("content-type", "text/plain");

                // Write to the response and end it
                response.end("Hello vertx!");
            }
        });

        server.listen(8070);

        // 或者直接下边这种
        // Vertx.vertx().createHttpServer().requestHandler(req ->
        // req.response().
        // end("Hello World!")).listen(8080);

    }

}
