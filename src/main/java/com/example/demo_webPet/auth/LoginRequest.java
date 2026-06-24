package com.example.demo_webPet.auth;

import jakarta.validation.constraints.NotBlank;

record LoginRequest(
        @NotBlank(message = "아이디를 입력해주세요")
        String username,

        @NotBlank(message = "비밀번호를 입력해주세요")
        String password){

        static LoginRequest getNewInstance(){return new LoginRequest(null, null);}
}