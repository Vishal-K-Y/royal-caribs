package com.royalcaribs.proxyserver.service;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Service;

@Service
public class ProxyServerService {

    public boolean checkURL(String url){
        return url.contains(".com") || url.contains(".org") || url.contains(".in") || url.contains(".co") || url.contains(".i0");
    }

    public String extractTargetUrl(HttpServletRequest request) {
        String path = request.getRequestURI().replace("/proxy/", ""); // Remove `/proxy` if present
        String query = request.getQueryString();
        return "http://" + path + (query != null ? "?" + query : "");
    }
}
