package com.acefet.blog.interceptor;

import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.net.URLEncoder;

public class SessionInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

//        // 登录不拦截
        if (request.getRequestURI().equals("/login")
                || request.getRequestURI().equals("/admin/login")
                || request.getRequestURI().equals("/checklogin")) {
            return true;
        }

        // 验证session是否存在
        Object obj = request.getSession().getAttribute("user");
        if (obj == null) {
            response.sendRedirect("/login?url="+ request.getRequestURI());
            return false;
        }
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
                           ModelAndView modelAndView) throws Exception {
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
            throws Exception {
    }
}
