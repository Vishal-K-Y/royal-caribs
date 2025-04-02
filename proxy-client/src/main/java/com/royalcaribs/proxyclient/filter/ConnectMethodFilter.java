package com.royalcaribs.proxyclient.filter;

import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class ConnectMethodFilter implements Filter {

        @Override
        public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
            HttpServletRequest httpRequest = (HttpServletRequest) request;
            HttpServletResponse httpResponse = (HttpServletResponse) response;

            if ("CONNECT".equalsIgnoreCase(httpRequest.getMethod())) {
                String targetUrl = httpRequest.getRequestURL().toString();
                if (!targetUrl.contains(".com")) {
                    httpResponse.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                    httpResponse.getWriter().write("Invalid URL in path");
                    return;
                }
                httpResponse.setStatus(HttpServletResponse.SC_OK);
                httpResponse.getWriter().write(targetUrl);
                return;
            }

            chain.doFilter(request, response);
        }
    }