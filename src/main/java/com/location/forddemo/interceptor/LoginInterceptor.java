package com.location.forddemo.interceptor;

import com.location.forddemo.entity.User;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class LoginInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        System.out.println("进行拦截");
        //获取请求的RUi:去除http:localhost:8080这部分剩下的
        String uri = request.getRequestURI();
        System.out.println(uri);
        //UTL:除了login.jsp是可以公开访问的，其他的URL都进行拦截控制
        if (uri.indexOf("/githubLogin") >= 0 ||uri.indexOf("/login") >= 0||uri.indexOf("/callback") >= 0) {
            System.out.println("这是登录页面");
            return true;
        }
        //获取session
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");
        //判断session中是否有用户数据，如果有，则返回true，继续向下执行
        if (user != null) {
            System.out.println("有数据");

            return true;
        }
        //不符合条件的给出提示信息，并转发到登录页面
        response.sendRedirect("login");
        System.out.println("非法");

        return false;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {

    }
}
