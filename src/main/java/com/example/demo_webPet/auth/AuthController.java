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

    @PostMapping(UrlConstants.URL_LOGIN)
    public String login(
            @Valid @ModelAttribute("request") LoginRequest request
            , BindingResult bindingResult
            , Model model
            , HttpSession session){

        // validation 검증
        if(bindingResult.hasErrors()){
            FieldError fieldError = bindingResult.getFieldError();
            if (fieldError != null) {
                model.addAttribute(ModelParamConstants.ERROR_MSG, fieldError.getDefaultMessage());
                model.addAttribute("request", request);
            }
            return UrlConstants.URL_LOGIN;
        }

        // 리다이렉션 경로 체크
        String redirectUrl = (String) session.getAttribute(ModelParamConstants.REDIRECT_URL);
        if (redirectUrl == null || redirectUrl.equals("null")) {
            redirectUrl = "/";
        }

        // service 검증
        // 예외 발생할 경우 GlobalExceptionHandler 에서 처리
        authService.login(request, session, redirectUrl);

        // 페이지 이동
        return "redirect:" + redirectUrl;
    }

    @PostMapping(UrlConstants.URL_LOGOUT)
    public String logout(HttpSession session){
        authService.logout(session);
        return "redirect:/";
    }
}
