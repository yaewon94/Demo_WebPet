package com.example.demo_webPet.auth;
import com.example.demo_webPet.common.exception.ErrorCode;
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

        if (authService.getUser().getUsertype() != UserType.SHELTER) {
            throw new AuthCheckFailException(
                    ErrorCode.ERROR_NOT_SHELTER_USER,
                    UrlConstants.URL_BOARD_RESCUED_ANIMAL_ROOT,
                    UrlConstants.URL_BOARD_RESCUED_ANIMAL_LIST);
        }

        return true;
    }
}
