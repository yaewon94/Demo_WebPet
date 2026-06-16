package com.example.demo_webPet.common.interceptor;

import com.example.demo_webPet.auth.LoginCheckInterceptor;
import com.example.demo_webPet.auth.ShelterUserOnlyInterceptor;
import com.example.demo_webPet.common.constants.UrlConstants;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@RequiredArgsConstructor
public class WebConfig implements WebMvcConfigurer {

    private final LoginCheckInterceptor loginCheckInterceptor;
    private final ShelterUserOnlyInterceptor shelterUserOnlyInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {

        // Interceptor.preHandle() -> Controller -> Interceptor.postHandle() -> view 렌더링
        // preHandle() 은 addInterceptor() 로 추가한 interceptor 순서로 호출
        // postHandle() 은 그 반대순서
        registry.addInterceptor(loginCheckInterceptor)
                .addPathPatterns(
                        UrlConstants.URL_LOGOUT,
                        UrlConstants.URL_BOARD_ANIMAL_SHELTER_NOTICE_ADD);
                /*.excludePathPatterns(
                        UrlConstants.URL_LOGIN,
                        UrlConstants.URL_SIGNUP);*/

        registry.addInterceptor(shelterUserOnlyInterceptor)
                .addPathPatterns(
                        UrlConstants.URL_BOARD_ANIMAL_SHELTER_NOTICE_ADD);
    }
}
