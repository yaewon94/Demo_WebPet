package com.example.demo_webPet.common.session;

import com.example.demo_webPet.user.UserType;

// 유저 비밀번호 등 중요정보 세션에 저장되지 않게 분리
public record SessionUserDto(
        Long id,
        String userId,
        UserType type){
    public static final String SESSION_KEY = "SESSION_USER";
    public static final int MAX_LOGIN_TIME = 30 * 60;
}

