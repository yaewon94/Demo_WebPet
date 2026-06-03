package com.example.demo_webPet.user;

import jakarta.validation.constraints.NotBlank;

record LoginUserDto(
        Long id,

        @NotBlank(message = UserConstants.MSG_INPUT_ID_REQUIRED)
        String userId,

        @NotBlank(message = UserConstants.MSG_INPUT_PW_REQUIRED)
        String password){
    public static LoginUserDto getNewInstance() {
        return new LoginUserDto(-1L, null, null);
    }
}