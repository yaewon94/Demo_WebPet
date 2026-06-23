package com.example.demo_webPet.common.output;

import com.example.demo_webPet.auth.AuthService;
import com.example.demo_webPet.auth.LoginUserDto;
import com.example.demo_webPet.common.constants.UrlConstants;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

@ControllerAdvice
@RequiredArgsConstructor
public final class GlobalModelAttribute {

    private final AuthService authService;
    private final Environment environment;

    @ModelAttribute("applicationName")
    public String getApplicationName() {
        return environment.getProperty("spring.application.name");
    }

    @ModelAttribute("loginUser")
    public LoginUserDto getLoginUser(HttpSession session) {
        return authService.getLoginUser(session);
    }

    @ModelAttribute("url_login")
    public String getLoginUrl() {
        return UrlConstants.URL_LOGIN;
    }

    @ModelAttribute("url_signup")
    public String getSignupUrl() {
        return UrlConstants.URL_SIGNUP;
    }

    @ModelAttribute("url_logout")
    public String getLogoutUrl() {
        return UrlConstants.URL_LOGOUT;
    }
}
