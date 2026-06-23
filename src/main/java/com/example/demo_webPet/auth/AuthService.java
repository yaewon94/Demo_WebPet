package com.example.demo_webPet.auth;

import com.example.demo_webPet.common.exception.ErrorCode;
import com.example.demo_webPet.user.User;
import com.example.demo_webPet.user.UserRepository;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class AuthService {

    private static final String SESSION_KEY_USER = "LOGIN_USER";
    private static final int SESSION_MAX_LOGIN_TIME = 30 * 60;

    private final UserRepository userRepository;

    public void createLoginSession(HttpSession session, User user){
        session.setAttribute(SESSION_KEY_USER, LoginUserDto.from(user));
        session.setMaxInactiveInterval(SESSION_MAX_LOGIN_TIME);
    }

    public LoginUserDto getLoginUser(HttpSession session){
        if(session == null) return null;
        LoginUserDto dto = (LoginUserDto) session.getAttribute(SESSION_KEY_USER);
        if(dto == null) return null;
        return dto;
    }

    LoginUserDto getLoginUser(HttpServletRequest request){
        return getLoginUser(request.getSession(false));
    }

    void login(LoginRequest request, HttpSession session, String redirectPage){
        // 아이디 존재 여부 체크
        User user = userRepository
                .findByUserName(request.user_name())
                .orElseThrow(() ->
                        new LoginFailException(ErrorCode.ERROR_USER_NAME_IS_NOT_EXIST, redirectPage));

        // 비밀번호 일치 여부 체크
        if (!user.getPassword().equals(request.password())) {
            throw new LoginFailException(ErrorCode.ERROR_USER_PASSWORD_MISMATCH, redirectPage);
        }

        // 세션 저장
        createLoginSession(session, user);
    }

    void logout(HttpSession session){
        session.removeAttribute(SESSION_KEY_USER);
    }
}
