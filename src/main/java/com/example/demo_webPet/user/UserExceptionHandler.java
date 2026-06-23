package com.example.demo_webPet.user;

import com.example.demo_webPet.common.constants.UrlConstants;
import com.example.demo_webPet.common.output.ModelParamConstants;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@ControllerAdvice(assignableTypes = UserController.class)
@Slf4j
class UserExceptionHandler {

    @ExceptionHandler(UserException.class)
    public String handle(UserException e, RedirectAttributes ra) {
        ra.addFlashAttribute(ModelParamConstants.ERROR_MSG, e.getMessage());
        ra.addFlashAttribute("request", e.getRequest());
        return "redirect:" + UrlConstants.URL_SIGNUP;
    }
}
