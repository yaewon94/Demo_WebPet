package com.example.demo_webPet.common;

import com.example.demo_webPet.auth.GuestOnlyInterceptor;
import com.example.demo_webPet.auth.LoginCheckInterceptor;
import com.example.demo_webPet.auth.NormalUserOnlyInterceptor;
import com.example.demo_webPet.auth.ShelterUserOnlyInterceptor;
import com.example.demo_webPet.common.constants.UrlConstants;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@RequiredArgsConstructor
public class WebConfig implements WebMvcConfigurer {

    private final GuestOnlyInterceptor guestOnlyInterceptor;
    private final LoginCheckInterceptor loginCheckInterceptor;
    private final ShelterUserOnlyInterceptor shelterUserOnlyInterceptor;
    private final NormalUserOnlyInterceptor normalUserOnlyInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {

        // Interceptor.preHandle() -> Controller -> Interceptor.postHandle() -> view 렌더링
        // preHandle() 은 addInterceptor() 로 추가한 interceptor 순서로 호출
        // postHandle() 은 그 반대순서
        registry.addInterceptor(guestOnlyInterceptor)
                .addPathPatterns(
                        UrlConstants.URL_LOGIN,
                        UrlConstants.URL_SIGNUP);

        registry.addInterceptor(loginCheckInterceptor)
                .addPathPatterns(
                        UrlConstants.URL_LOGOUT,
                        UrlConstants.URL_BOARD_MISSING_ANIMAL_ADD,
                        UrlConstants.URL_BOARD_MISSING_ANIMAL_MODIFY,
                        UrlConstants.URL_BOARD_MISSING_ANIMAL_DELETE,
                        UrlConstants.URL_BOARD_RESCUED_ANIMAL_ADD);
                /*.excludePathPatterns(
                        UrlConstants.URL_LOGIN,
                        UrlConstants.URL_SIGNUP);*/

        registry.addInterceptor(shelterUserOnlyInterceptor)
                .addPathPatterns(
                        UrlConstants.URL_BOARD_RESCUED_ANIMAL_ADD);

        registry.addInterceptor(normalUserOnlyInterceptor)
                .addPathPatterns(
                        UrlConstants.URL_BOARD_MISSING_ANIMAL_ADD,
                        UrlConstants.URL_BOARD_MISSING_ANIMAL_MODIFY,
                        UrlConstants.URL_BOARD_MISSING_ANIMAL_DELETE);
    }
}
