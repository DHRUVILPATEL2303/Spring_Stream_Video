package com.example.springstreambackend.config;
import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;






//@Component
//public class RateLimitingFilter implements Filter {
//
//    private final Map<String, AtomicInteger> requestCountsPerIpAddress = new ConcurrentHashMap<>();
//
//    private static final int MAX_REQUESTS_PER_MINUTE = 5;
//
//    @Override
//    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
//            throws IOException, ServletException {
//        HttpServletRequest httpServletRequest = (HttpServletRequest) request;
//        HttpServletResponse httpServletResponse = (HttpServletResponse) response;
//
//        String clientIpAddress = httpServletRequest.getRemoteAddr();
//
//        requestCountsPerIpAddress.putIfAbsent(clientIpAddress, new AtomicInteger(0));
//        AtomicInteger requestCount = requestCountsPerIpAddress.get(clientIpAddress);
//
//        int requests = requestCount.incrementAndGet();
//
//        if (requests > MAX_REQUESTS_PER_MINUTE) {
//            httpServletResponse.setStatus(HttpServletResponse.SC_SERVICE_UNAVAILABLE);
//            httpServletResponse.getWriter().write("Too many requests. Please try again later.");
//            return;
//        }
//
//        chain.doFilter(request, response);
//
//    }
//
//    @Override
//    public void init(FilterConfig filterConfig) throws ServletException {
//    }
//
//    @Override
//    public void destroy() {
//    }
//}
