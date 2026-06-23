package com.example.demo_webPet.auth;

import com.example.demo_webPet.common.constants.UrlConstants;
import com.example.demo_webPet.common.output.ModelParamConstants;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.IOException;

@ControllerAdvice(assignableTypes = AuthController.class)
@Slf4j
class AuthExceptionHandler {

    @ExceptionHandler(LoginFailException.class)
    public String handle(LoginFailException e, RedirectAttributes ra) {

        log.error(e.getMessage(), e);

        ra.addFlashAttribute("errorMsg", e.getMessage());

        String redirect = e.getRedirectionPage();
        if(redirect == null || redirect.isEmpty()) return "redirect:" + UrlConstants.URL_LOGIN;
        else return "redirect:" + UrlConstants.URL_LOGIN + "?redirect=" + e.getRedirectionPage();
    }

    @ExceptionHandler(AuthCheckFailException.class)
    public void handle(AuthCheckFailException e, HttpServletRequest request, HttpServletResponse response) throws IOException {

        log.error(e.getMessage(), e);

        // js alert 메세지 추가
        request.getSession().setAttribute(ModelParamConstants.ALERT, e.getMessage());

        // 페이지 리다이렉션
        String uri = request.getRequestURI();
        if(e.getUrlPrefix() == null) response.sendRedirect("/");
        else response.sendRedirect(uri.startsWith(e.getUrlPrefix()) ? e.getRedirectionUri() : "/");
    }
}
