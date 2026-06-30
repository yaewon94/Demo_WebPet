package com.example.demo_webPet.auth;

import com.example.demo_webPet.user.User;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class AuthService{

    public LoginUserDetail getUser() {

        Authentication auth =
                SecurityContextHolder.getContext().getAuthentication();

        if (auth == null || !(auth.getPrincipal() instanceof LoginUserDetail)) {
            return null;
        }

        return (LoginUserDetail) auth.getPrincipal();
    }

    public void autoLogin(User user) throws IllegalAccessException {

        if(getUser() != null){
            throw new IllegalAccessException("이미 로그인 상태입니다");
        }

        LoginUserDetail userDetails = new LoginUserDetail(user);
        Authentication auth =
                new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(auth);
    }
}
