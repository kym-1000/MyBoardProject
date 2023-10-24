package com.youngmok.myboard.interceptors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;


public class AuthenticationInterceptor implements HandlerInterceptor {

    private static final Logger logger = LoggerFactory.getLogger(AuthenticationInterceptor.class);

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        HttpSession session = request.getSession();
        String username = (String) session.getAttribute("id");
        String requestURI = request.getRequestURI(); // 요청 URI를 가져옴

        // 로그인/로그아웃
        if(request.getRequestURI().startsWith("/login")){
            String referer = request.getHeader("Referer");
            String host = request.getHeader("host");
            logger.info(referer +" : "+host);
            if (referer == null || !referer.contains(host)) {
                response.sendRedirect("/");
                return false;
            }
            return true;
        }

        if (isUserLoggedIn(request)) {
            logger.info(username + " requested " + requestURI);
            return true; // 로그인된 사용자는 요청을 계속 진행
        } else {
            return redirectIfSessionError(response);
        }
    }

    private boolean isUserLoggedIn(HttpServletRequest request) {
        HttpSession session = request.getSession(false); // 세션이 없으면 새로 생성하지 않도록 설정
        return session != null && session.getAttribute("id") != null;
    }

    private boolean redirectIfSessionError(HttpServletResponse response) throws IOException {
        // 로그인되지 않은 사용자에 대한 처리
        logger.warn("세션 풀림 혹은 에러");
        response.sendRedirect("/login/loginForm?interceptorMsg=INTERCEPTOR_SESSION_ERR");
        return false;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        // 요청 처리 이후 작업
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        // 뷰 렌더링 후 작업
    }

}