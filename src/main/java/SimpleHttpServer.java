package main.java;

import java.io.*;
import java.net.*;
import java.nio.Buffer;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

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

            String method = parts[0]; // GET POST
            String rawPath = parts[1]; // API path + queryParams
            String pathOnly = rawPath.split("\\?")[0]; // API path

            Map<String, String> queryParams = parseQueryParams(rawPath);
            Map<String, String> headers = parseHeaders(in);

            String responseBody;
            String status = "";
            switch (pathOnly) {
                case "/":
                    responseBody = "Welcome to my simple HTTP server!";
                    break;
                case "/hello":
                    String name = queryParams.getOrDefault("name", "stranger");
                    responseBody = "Hello "+name+"!";
                    status = "HTTP/1.1 200 OK";
                    break;
                case "/agent":
                    String userAgent = headers.getOrDefault("User-Agent", "unknown");
                    responseBody = "Your user agent is: "+ userAgent;
                    status = "HTTP/1.1 200 OK";
                    break;
                default:
                    responseBody = "404 Not Found";
                    status = "HTTP/1.1 404 Not Found";
            }

            String httpResponse = "Http/1.1 " + status + "\r\n" +
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

    private static Map<String, String> parseHeaders(BufferedReader in) throws IOException{
            Map<String, String> headers = new HashMap<>();
            String line;
            while ((line = in.readLine()) != null && !line.isEmpty()) {
                int idx = line.indexOf(":");
                if(idx != -1){
                    String headerName = line.substring(0, idx).trim();
                    String headerValue = line.substring(idx + 1).trim();
                    headers.put(headerName, headerValue);
                }
            }
            return headers;
    }

    private static Map<String, String> parseQueryParams(String rawPath){
        try {
            Map<String, String> queryParams = new HashMap<>();
            if (rawPath.contains("?")) {
                String queryString = rawPath.split("\\?", 2)[1];
                for (String param : queryString.split("&")) {
                    String[] kv = param.split("=");
                    if (kv.length == 2) {
                        queryParams.put(URLDecoder.decode(kv[0], "UTF-8"),
                                URLDecoder.decode(kv[1], "UTF-8"));
                    }
                }
            }

            return queryParams;
        }catch (UnsupportedEncodingException ex){
            ex.printStackTrace();
        }

        return new HashMap<>();
    }
}