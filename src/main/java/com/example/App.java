package com.example;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpServer;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.charset.StandardCharsets;

public class App {
    public static void main(String[] args) throws IOException {
        int port = Integer.parseInt(System.getenv().getOrDefault("PORT", "8080"));
        HttpServer server = HttpServer.create(new InetSocketAddress(port), 0);

        server.createContext("/health", exchange ->
                respond(exchange, "text/plain", "OK"));

        server.createContext("/", exchange ->
                respond(exchange, "text/html",
                        "<h1>Hello from my sample web app!</h1>"));

        server.start();
        System.out.println("App listening on port " + port);
    }

    private static void respond(
            HttpExchange exchange, String contentType, String body) throws IOException {
        byte[] response = body.getBytes(StandardCharsets.UTF_8);
        exchange.getResponseHeaders().set("Content-Type", contentType + "; charset=UTF-8");
        exchange.sendResponseHeaders(200, response.length);

        try (var output = exchange.getResponseBody()) {
            output.write(response);
        }
    }
}