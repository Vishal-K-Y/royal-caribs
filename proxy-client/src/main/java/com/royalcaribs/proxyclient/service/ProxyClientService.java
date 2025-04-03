package com.royalcaribs.proxyclient.service;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import java.io.*;
import java.net.Socket;

@Service
public class ProxyClientService {

//    private static final String PROXY_SERVER_HOST = "proxy-server";
//    private static final int PROXY_SERVER_PORT = 9090;

    @Value("${proxy.server.host}")
    private String PROXY_SERVER_HOST;

    @Value("${proxy.server.port}")
    private int PROXY_SERVER_PORT;


    public String getPathFromRequest(HttpServletRequest request) {
        String query = request.getQueryString();
        String path = request.getRequestURL().toString() + (query != null ? "?" + query : "");
        return "/proxy/"+path.replace("http://", "");
    }

    // Method to process requests from the queue in a worker thread
    public String processRequest(HttpServletRequest request) {
        String path = getPathFromRequest(request);
        System.out.println("Processing Request to: " + path);

        try (Socket socket = new Socket(PROXY_SERVER_HOST, PROXY_SERVER_PORT);
             PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
             BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()))) {

            // Forward the HTTP request
            out.println("GET " + path + " HTTP/1.1");
            out.println("Host: " + PROXY_SERVER_HOST + ":" + PROXY_SERVER_PORT);
            out.println("Connection: keep-alive");
            out.println();

            // Read and process the response
            StringBuilder response = new StringBuilder();
            String line;
            while ((line = in.readLine()) != null) {
                response.append(line).append("\n");
            }

            return response.toString();
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Error occurred while processing request to target URL: " + e);
        }
    }

}
