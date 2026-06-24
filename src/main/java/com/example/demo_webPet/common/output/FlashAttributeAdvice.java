package com.example.demo_webPet.common.output;

import jakarta.servlet.http.HttpSession;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

@ControllerAdvice
public class FlashAttributeAdvice {

    // @ModelAttribute : 모든 컨트롤러 전에 실행
    @ModelAttribute
    public void sessionToModel(HttpSession session, Model model) {

        /* Interceptor
           ↓ (alert 저장)
            session
           ↓ (다음 요청)
            @ControllerAdvice (@ModelAttribute)
           ↓
            model
           ↓
            view (alert 출력)
           ↓
            session 삭제*/
        String value = (String)session.getAttribute(ModelParamConstants.ALERT);
        if(value != null && !value.isEmpty()) {
            model.addAttribute(ModelParamConstants.ALERT, value);
            session.removeAttribute(ModelParamConstants.ALERT);
        }
        value = (String)session.getAttribute(ModelParamConstants.ERROR_MSG);
        if(value != null && !value.isEmpty()) {
            model.addAttribute(ModelParamConstants.ERROR_MSG, value);
            session.removeAttribute(ModelParamConstants.ERROR_MSG);
        }
    }
}
