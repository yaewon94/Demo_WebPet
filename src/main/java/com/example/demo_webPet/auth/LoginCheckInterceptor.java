package com.example.demo_webPet.auth;

import com.example.demo_webPet.common.constants.UrlConstants;
import com.example.demo_webPet.common.output.ModelParamConstants;
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

            String uri = request.getRequestURI();
            // 디렉토리 거슬러 올라가는 것 방지 (보안)
            if (!uri.startsWith("/") || uri.contains("..")) {
                uri = "/";
            }

            String query = request.getQueryString();
            String fullUrl = uri;
            if (query != null && !query.isBlank()) {
                fullUrl += "?" + query;
            }

            request.getSession().setAttribute(ModelParamConstants.REDIRECT_URL, fullUrl);
            response.sendRedirect(UrlConstants.URL_LOGIN);
            return false;
        }

        return true;
    }
}
