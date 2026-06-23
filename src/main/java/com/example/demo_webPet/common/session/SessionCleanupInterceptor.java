package com.example.demo_webPet.common.session;

import com.example.demo_webPet.common.output.ModelParamConstants;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

@Component
@RequiredArgsConstructor
public final class SessionCleanupInterceptor implements HandlerInterceptor{

    @Override
    public void postHandle(HttpServletRequest request,
                           HttpServletResponse response,
                           Object handler,
                           ModelAndView modelAndView) {

        HttpSession session = request.getSession(false);
        if (session != null) {
            session.removeAttribute(ModelParamConstants.REDIRECT_URL);
        }
    }

}
