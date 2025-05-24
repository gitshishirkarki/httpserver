package main.java;

import java.io.*;
import java.net.*;
public class SimpleHttpServer{
    public static void main(String[] args) throws IOException{
        int port = 8080;

        ServerSocket serverSocket = new ServerSocket(port);
        System.out.println("Server listening on port "+ port);

        while(true){
            Socket clientSocket = serverSocket.accept();
            System.out.println("Accepted connection from "+ clientSocket.getInetAddress());

            BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            BufferedWriter out = new BufferedWriter(new OutputStreamWriter(clientSocket.getOutputStream()));

            String requestLine = in.readLine();
            System.out.println("Request: "+ requestLine);

            String httpResponse = "Http/1.1 200 OK\r\n" +
                    "Content-Type: text/plain\r\n" +
                    "Content-Length: 13\r\n" +
                    "\r\n" +
                    "Hello, World!";

            out.write(httpResponse);
            out.flush();
            clientSocket.close();
        }
    }
}