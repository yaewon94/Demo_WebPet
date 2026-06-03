package com.example.demo_webPet.common.session;

public record SessionUserDto(
        Long id,
        String userId){
    public static final String SESSION_KEY = "SESSION_USER";
    public static final int MAX_LOGIN_TIME = 30 * 60;
}
