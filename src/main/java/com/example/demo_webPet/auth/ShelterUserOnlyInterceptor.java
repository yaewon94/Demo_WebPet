package com.example.demo_webPet.auth;

import com.example.demo_webPet.common.output.view.ModelParamConstants;
import com.example.demo_webPet.common.constants.UrlConstants;
import com.example.demo_webPet.user.UserType;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

@Component
@RequiredArgsConstructor
public final class ShelterUserOnlyInterceptor implements HandlerInterceptor {

    private final AuthService authService;

    @Override
    public boolean preHandle(HttpServletRequest request,
                             HttpServletResponse response,
                             Object handler) throws Exception {

        if (authService.getLoginUser(request).user_type()!= UserType.SHELTER) {
            // 알림 메세지
            request.getSession().setAttribute(ModelParamConstants.ERROR_MSG, AuthCode.ERROR_NOT_SHELTER_USER.getMessage());
            // 페이지 리다이렉션
            String uri = request.getRequestURI();
            response.sendRedirect(uri.startsWith(UrlConstants.URL_BOARD_RESCUED_ANIMAL_ROOT) ?
                    UrlConstants.URL_BOARD_RESCUED_ANIMAL_LIST : "/");
            return false;
        }

        return true;
    }
}
