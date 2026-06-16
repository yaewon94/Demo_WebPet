package com.example.demo_webPet.auth;

import com.example.demo_webPet.common.constants.UrlConstants;
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

    private static final String SESSION_KEY_USER_ID = "USER_ID";
    private static final int SESSION_MAX_LOGIN_TIME = 30 * 60;

    private final UserRepository userRepository;

    public void createLoginSession(HttpSession session, Long user_id){
        session.setAttribute(SESSION_KEY_USER_ID, user_id);
        session.setMaxInactiveInterval(SESSION_MAX_LOGIN_TIME);
    }

    public User getLoginUser(HttpSession session){
        if(session == null) return null;
        Long user_id = (Long)session.getAttribute(SESSION_KEY_USER_ID);
        if(user_id == null) return null;

        return userRepository.findById(user_id).orElse(null);
    }

    User getLoginUser(HttpServletRequest request){
        /*// HttpServletRequest 에 캐싱 여부 확인
        User user = (User)request.getAttribute("user");
        if(user != null) return user;*/

        // 세션 체크
        return getLoginUser(request.getSession(false));
        /*User user = getLoginUser(request.getSession(false));
        request.setAttribute("user", user);
        return user;*/
    }

    void login(LoginRequest request, HttpSession session){
        // 아이디 존재 여부 체크
        User user = userRepository
                .findByUserName(request.user_name())
                .orElseThrow(() ->
                        new AuthException(AuthCode.ERROR_USER_NAME_IS_NOT_EXIST, UrlConstants.URL_LOGIN));

        // 비밀번호 일치 여부 체크
        if (!user.getPassword().equals(request.password())) {
            throw new AuthException(AuthCode.ERROR_USER_PASSWORD_MISMATCH, UrlConstants.URL_LOGIN);
        }

        // 세션 저장
        createLoginSession(session, user.getId());
    }

    void logout(HttpSession session){
        session.removeAttribute(SESSION_KEY_USER_ID);
    }
}
