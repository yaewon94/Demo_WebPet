package com.example.demo_webPet.common.output.view;

import com.example.demo_webPet.auth.AuthService;
import com.example.demo_webPet.common.constants.UrlConstants;
import com.example.demo_webPet.user.User;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

@ControllerAdvice
@RequiredArgsConstructor
public final class GlobalModelAttribute {

    private final AuthService authService;

    @ModelAttribute("loginUser")
    public User getLoginUser(HttpSession session) {
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
