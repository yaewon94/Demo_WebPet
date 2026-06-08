package com.example.demo_webPet.common.session;

import com.example.demo_webPet.user.LoginUserDto;
import jakarta.servlet.http.HttpSession;

public final class SessionManager {
    private SessionManager(){}

    public static void login(LoginUserDto loginUserDto, HttpSession session){
        SessionUserDto sessionUserDto = new SessionUserDto(loginUserDto.id(), loginUserDto.userId(), loginUserDto.type());
        session.setAttribute(SessionUserDto.SESSION_KEY, sessionUserDto);
        session.setMaxInactiveInterval(SessionUserDto.MAX_LOGIN_TIME);
    }

    public static SessionUserDto getLoginUser(HttpSession session){
        return (SessionUserDto) session.getAttribute(SessionUserDto.SESSION_KEY);
    }

    public static void logout(HttpSession session){
        session.removeAttribute(SessionUserDto.SESSION_KEY);
    }
}
