package com.example.demo_webPet.common.output;

import jakarta.servlet.http.HttpSession;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

@ControllerAdvice
public class FlashAttributeAdvice {

    @ModelAttribute
    public void sessionToModel(HttpSession session, Model model) {
        // 세션에 저장된 일시적인 값들을 모델로 복사함
        // interceptor는 model, redirectAttribute에 접근불가능해서 session에만 값 저장할 수 있는데
        // session은 요청이 바뀌어도 값이 그대로 남아있기 때문
        String value = (String)session.getAttribute(ModelParamConstants.ALERT);
        if(value != null && !value.isEmpty()) {
            model.addAttribute(ModelParamConstants.ALERT, value);
            session.removeAttribute(ModelParamConstants.ALERT);
        }
    }
}
