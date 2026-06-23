package com.example.demo_webPet.auth;

import com.example.demo_webPet.common.constants.UrlConstants;
import com.example.demo_webPet.common.exception.ErrorCode;
import com.example.demo_webPet.common.output.view.ModelParamConstants;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

@Component
@RequiredArgsConstructor
public final class GuestOnlyInterceptor implements HandlerInterceptor {

    private final AuthService authService;

    @Override
    public boolean preHandle(HttpServletRequest request,
                             HttpServletResponse response,
                             Object handler) throws Exception {

        // 로그인한 상태에서 회원가입, 로그인 페이지 접속한 경우 호출됨
        if (authService.getLoginUser(request) != null) {
            // 알림 메세지
            request.getSession().setAttribute(ModelParamConstants.ALERT, ErrorCode.ERROR_ALREADY_LOGGED_IN.getMessage());

            // 직전 페이지로 이동
            String referer = request.getHeader("Referer");
            response.sendRedirect(referer != null ? referer : "/");
            return false;
        }

        return true;
    }
}
