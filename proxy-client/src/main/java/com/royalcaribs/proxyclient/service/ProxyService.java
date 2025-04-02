package com.royalcaribs.proxyclient.service;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Service;

import javax.net.ssl.SSLSocket;
import javax.net.ssl.SSLSocketFactory;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.URL;

@Service
public class ProxyService {

//    private String handleHttpsRequest(String targetUrl) {
//        try {
//            // Parse the target URL
//            URL url = new URL(targetUrl);
//            String host = url.getHost();
//            int port = url.getPort() == -1 ? 443 : url.getPort(); // Default to 443 if no port is specified
//
//            // Create an SSL socket
//            SSLSocketFactory sslSocketFactory = (SSLSocketFactory) SSLSocketFactory.getDefault();
//            SSLSocket sslSocket = (SSLSocket) sslSocketFactory.createSocket(host, port);
//
//            // Start the SSL handshake
//            sslSocket.startHandshake();
//
//            // Open input and output streams for communication
//            BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(sslSocket.getOutputStream()));
//            BufferedReader reader = new BufferedReader(new InputStreamReader(sslSocket.getInputStream()));
//
//            // Send an HTTPS GET request
//            writer.write("GET " + url.getPath() + " HTTP/1.1\r\n");
//            writer.write("Host: " + host + "\r\n");
//            writer.write("Connection: close\r\n");
//            writer.write("\r\n");
//            writer.flush();
//
//            // Read the server's response
//            StringBuilder response = new StringBuilder();
//            String line;
//            while ((line = reader.readLine()) != null) {
//                response.append(line).append("\n");
//            }
//
//            // Close streams and the socket
//            writer.close();
//            reader.close();
//            sslSocket.close();
//
//            // Return the server's response
//            return response.toString();
//
//        } catch (Exception e) {
//            // Handle errors (e.g., invalid URL or connection issues)
//            return "Error handling HTTPS request: " + e.getMessage();
//        }
//    }

    public String getPathFromRequest(HttpServletRequest request) {
        String query = request.getQueryString();
        String path = request.getRequestURL().toString() + (query != null ? "?" + query : "");
        return "/proxy/"+path.replace("http://", "");
    }
}
