package com.example.demo_webPet.common.interceptor;

import com.example.demo_webPet.common.constants.UrlConstants;
import com.example.demo_webPet.common.session.SessionManager;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

@Component
public final class LoginCheckInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request,
                             HttpServletResponse response,
                             Object handler) throws Exception {

        HttpSession session = request.getSession(false); // @param : 세션이 없는 경우 새로 만들지 말라는 뜻

        if (session == null || SessionManager.getLoginUser(session) == null) {
            String redirectUri = request.getRequestURI();

            // 디렉토리 거슬러 올라가는 것 방지 (보안)
            if (!redirectUri.startsWith("/") || redirectUri.contains("..")) {
                redirectUri = "/";
            }

            response.sendRedirect(UrlConstants.URL_LOGIN + "?redirect=" + redirectUri);
            return false;
        }

        return true;
    }
}
