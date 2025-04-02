package com.royalcaribs.proxyserver.controller;

import com.royalcaribs.proxyserver.exception.InvalidURL;
import com.royalcaribs.proxyserver.service.ProxyServerService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.net.URI;

@RestController
@RequestMapping("/proxy")
class ProxyServerController {
    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private ProxyServerService proxyServerService;

    @GetMapping("/hello")
    public String getHello(){
        return "hello from proxy-server";
    }

    @GetMapping("/**")
    public ResponseEntity<String> handleGet(HttpServletRequest request){
        String targetUrl = proxyServerService.extractTargetUrl(request);

        // Log URLs for debugging
        System.out.println("Constructed Target URL: " + targetUrl);


        // Validate target URL
        if(!proxyServerService.checkURL(targetUrl)) throw new InvalidURL(targetUrl);

        // Forward the request
        try {
            ResponseEntity<String> response = restTemplate.getForEntity(targetUrl, String.class);
            return ResponseEntity.status(response.getStatusCode()).body(response.getBody());
        } catch (Exception e) {
            throw new RuntimeException("Error occurred while forwarding the request to target URL: " + targetUrl, e);
        }
    }

}
