package com.example.demo_webPet.auth;

import com.example.demo_webPet.common.constants.UrlConstants;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

@Component
@RequiredArgsConstructor
public final class LoginCheckInterceptor implements HandlerInterceptor {

    private final AuthService authService;

    @Override
    public boolean preHandle(HttpServletRequest request,
                             HttpServletResponse response,
                             Object handler) throws Exception {

        if (authService.getLoginUser(request) == null) {
            // 로그인 페이지로 리다이렉트
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
