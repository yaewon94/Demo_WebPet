package com.example.demo_webPet.common.interceptor;

import com.example.demo_webPet.common.constants.ModelParamConstants;
import com.example.demo_webPet.common.constants.UrlConstants;
import com.example.demo_webPet.common.session.SessionManager;
import com.example.demo_webPet.common.session.SessionUserDto;
import com.example.demo_webPet.user.LoginUserDto;
import com.example.demo_webPet.user.UserType;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

@Component
public final class ShelterUserOnlyInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request,
                             HttpServletResponse response,
                             Object handler) throws Exception {

        HttpSession session = request.getSession(false);
        SessionUserDto user = SessionManager.getLoginUser(session);
        if (user == null || user.type() != UserType.SHELTER) {
            // 알림 메세지
            request.getSession().setAttribute(ModelParamConstants.ERROR_MSG, "보호소 회원계정이 아닙니다");

            // 페이지 리다이렉션
            String uri = request.getRequestURI();
            response.sendRedirect(uri.startsWith(UrlConstants.URL_BOARD_ANIMAL_SHELTER_NOTICE_ROOT) ?
                    UrlConstants.URL_BOARD_ANIMAL_SHELTER_NOTICE_LIST : "/");
            return false;
        }

        return true;
    }
}
