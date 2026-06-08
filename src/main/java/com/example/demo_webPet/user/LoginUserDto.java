package com.example.demo_webPet.user;

import jakarta.validation.constraints.NotBlank;

public record LoginUserDto(
        Long id,

        @NotBlank(message = UserConstants.MSG_INPUT_ID_REQUIRED)
        String userId,

        @NotBlank(message = UserConstants.MSG_INPUT_PW_REQUIRED)
        String password,

        // TODO : 제약사항, 2지선다
        UserType type){

    public static final String SESSION_KEY = "LOGIN_USER";
    public static final Integer SESSION_MAX_LOGIN_TIME = 30 * 60;

    static LoginUserDto getNewInstance() {
        return new LoginUserDto(-1L, null, null, UserType.NORMAL);
    }
}