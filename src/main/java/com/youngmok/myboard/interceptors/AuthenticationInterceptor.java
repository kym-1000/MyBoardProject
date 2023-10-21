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
            if (isUserLoggedIn(request)) {
                logger.info("로그인 정보 확인 계속진행");
                return true; // 로그인된 사용자는 요청을 계속 진행
            } else  {
                return redirectIfSessionError(request,response);
            }
    }

    private boolean isUserLoggedIn(HttpServletRequest request) {
        HttpSession session = request.getSession(false); // 세션이 없으면 새로 생성하지 않도록 설정
        return session != null && session.getAttribute("id") != null;
    }

    private boolean redirectIfSessionError(HttpServletRequest request,HttpServletResponse response) throws IOException {
        // 로그인되지 않은 사용자에 대한 처리
        logger.info("세션 풀림 혹은 에러");
        response.sendRedirect("/login/login?interceptorMsg=INTERCEPTOR_SESSION_ERR");
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