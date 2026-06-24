package com.example.demo_webPet.auth;

import com.example.demo_webPet.common.constants.UrlConstants;
import com.example.demo_webPet.common.output.ModelParamConstants;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequiredArgsConstructor
final class AuthController {

    private final AuthService authService;

    @GetMapping(UrlConstants.URL_LOGIN)
    public String loginPage(@RequestParam(required = false)String redirect, Model model){
        model.addAttribute(ModelParamConstants.URL, UrlConstants.URL_LOGIN);
        model.addAttribute("request", LoginRequest.getNewInstance());
        return UrlConstants.URL_LOGIN;
    }
}
