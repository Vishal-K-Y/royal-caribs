package com.royalcaribs.proxyclient.controller;

import com.royalcaribs.proxyclient.service.ProxyService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

@RestController
public class ProxyController {

    @Autowired
    private ProxyService proxyService;

        @GetMapping("/hello")
        public String getHello(){
            return "hello from proxy-client";
        }


    private static final String PROXY_SERVER_HOST = "proxy-server";
//private static final String PROXY_SERVER_HOST = "localhost";
    private static final int PROXY_SERVER_PORT = 9090;


    @GetMapping("/**")
    public ResponseEntity<String> handleProxyRequest(HttpServletRequest request){
        String path = proxyService.getPathFromRequest(request);
        System.out.println("Forwarding Request to: " + path);

        try (Socket socket = new Socket(PROXY_SERVER_HOST, PROXY_SERVER_PORT);
             PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
             BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()))) {

            // Send HTTP request
            out.println("GET " + path + " HTTP/1.1");
            out.println("Host: " + PROXY_SERVER_HOST + ":" + PROXY_SERVER_PORT);
//            out.println("Host: " + "localhost:"+PROXY_SERVER_PORT);
            out.println("Connection: keep-alive");
            out.println();

            // Read and print response
            StringBuilder response = new StringBuilder();
            String line;
            while ((line = in.readLine()) != null) {
                response.append(line).append("\n");
            }

            return ResponseEntity.ok().body(response.toString());
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Error occurred while forwarding the request to target URL: "+ e);
        }
    }
}
