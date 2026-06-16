package com.example.demo_webPet.auth;

import com.example.demo_webPet.common.constants.UrlConstants;
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
        model.addAttribute("url", UrlConstants.URL_LOGIN);
        model.addAttribute("request", LoginRequest.getNewInstance());
        model.addAttribute("redirect", redirect);
        return UrlConstants.URL_LOGIN;
    }

    @PostMapping(UrlConstants.URL_LOGIN)
    public String login(@RequestParam(required = false)String redirect
            , @Valid @ModelAttribute("request") LoginRequest request
            , BindingResult bindingResult
            , Model model
            , HttpSession session){

        // 1. validation 검증
        if(bindingResult.hasErrors()){
            FieldError fieldError = bindingResult.getFieldError();
            if (fieldError != null) {
                model.addAttribute("errorMsg", fieldError.getDefaultMessage());
            }
            return UrlConstants.URL_LOGIN;
        }

        // 2. service 검증
        // 예외 발생할 경우 @ControllerAdvice에서 처리
        authService.login(request, session);

        // 페이지 이동
        if (redirect != null && !redirect.isEmpty()) {
            return "redirect:" + redirect;
        }
        return "redirect:/";
    }

    @PostMapping(UrlConstants.URL_LOGOUT)
    public String logout(HttpSession session){
        authService.logout(session);
        return "redirect:/";
    }
}
