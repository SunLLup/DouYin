package com.example.springbootdouy.intercepeter;

import ch.qos.logback.classic.turbo.TurboFilter;
import com.sun.media.sound.SoftTuning;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class UserIntercepter implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String contextPath = request.getServletPath();
        System.out.println("请求地址"+contextPath);
        String token = request.getParameter("token");
        System.out.println("接收到token"+token);

        return true;
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        HandlerInterceptor.super.afterCompletion(request, response, handler, ex);
    }
}
