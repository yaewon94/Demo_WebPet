package com.example.demo_webPet.common.session;

import jakarta.servlet.http.HttpSession;

public final class SessionManager {
    private SessionManager(){}

    public static void login(SessionUserDto dto, HttpSession session){
        session.setAttribute(SessionUserDto.SESSION_KEY, dto);
        session.setMaxInactiveInterval(SessionUserDto.MAX_LOGIN_TIME);
    }

    public static SessionUserDto getLoginUser(HttpSession session){
        return (SessionUserDto) session.getAttribute(SessionUserDto.SESSION_KEY);
    }
}
