package com.example.demo_webPet.common.interceptor;

import com.example.demo_webPet.common.constants.ModelParamConstants;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

@Component
public final class ModelParamInterceptor implements HandlerInterceptor {

    @Override
    public void postHandle(
            HttpServletRequest request,
            HttpServletResponse response,
            Object handler,
            ModelAndView modelAndView
    ) {

        if (modelAndView == null) return;

        HttpSession session = request.getSession(false);
        if (session == null) return;

        // Session 지워지는 타이밍이랑 view 렌더링 타이밍이 모호해서 안전하게 model에 넣는 방식
        Object msg = session.getAttribute(ModelParamConstants.ERROR_MSG);
        if (msg != null) {
            modelAndView.addObject(ModelParamConstants.ERROR_MSG, msg);
            session.removeAttribute(ModelParamConstants.ERROR_MSG);
        }
    }
}
