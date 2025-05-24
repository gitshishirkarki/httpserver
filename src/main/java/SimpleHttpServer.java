package main.java;

import java.io.*;
import java.net.*;
import java.time.LocalDateTime;

public class SimpleHttpServer{
    public static void main(String[] args) throws IOException{
        int port = 8080;

        ServerSocket serverSocket = new ServerSocket(port);
        System.out.println("Server listening on port "+ port);

        while(true) {
            Socket clientSocket = serverSocket.accept();
            new Thread(() -> {
                try {
                    handleClient(clientSocket);
                }catch (IOException e){
                    e.printStackTrace();
                }
            }).start();
        }
    }

    private static void handleClient(Socket clientSocket) throws IOException{
        try (
            BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            BufferedWriter out = new BufferedWriter(new OutputStreamWriter(clientSocket.getOutputStream()));
        ){
            String requestLine = in.readLine();
            System.out.println("Request: " + requestLine);

            if (requestLine == null || requestLine.isEmpty()) return;

            String[] parts = requestLine.split(" ");
            if (parts.length < 2) return;

            String method = parts[0];
            String path = parts[1];

            String responseBody;
            switch (path) {
                case "/":
                    responseBody = "Welcome to my simple HTTP server!";
                    break;
                case "/hello":
                    responseBody = "Hello from the server!";
                    break;
                case "/time":
                    responseBody = LocalDateTime.now().toString();
                    break;
                default:
                    responseBody = "404 Not Found";
                    break;
            }

            String httpResponse = "Http/1.1 " + (path.equals("/") || path.equals("/hello") || path.equals("/time") ? "200 OK" : "404 Not Found") + "\r\n" +
                    "Content-Type: text/plain\r\n" +
                    "Content-Length:" + responseBody.length() + "\r\n" +
                    "\r\n" +
                    responseBody;

            out.write(httpResponse);
            out.flush();
        }catch (IOException ex){
            ex.printStackTrace();
        }finally {
                clientSocket.close();
        }
    }
}