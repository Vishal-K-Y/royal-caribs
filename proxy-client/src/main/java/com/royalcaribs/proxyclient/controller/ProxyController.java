package com.royalcaribs.proxyclient.controller;

import com.royalcaribs.proxyclient.service.ProxyClientService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@RestController
public class ProxyController {

    @Autowired
    private ProxyClientService proxyClientService;

        @GetMapping("/hello")
        public String getHello(){
            return "hello from proxy-client";
        }
    private final ExecutorService executorService = Executors.newSingleThreadExecutor();

    @GetMapping("/**")
    public ResponseEntity<String> handleProxyRequest(HttpServletRequest request) {
        try {
            // Use executor service to handle requests sequentially
            String response = executorService.submit(() -> proxyClientService.processRequest(request)).get();
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Failed to process the request.", e);
        }
    }
}
