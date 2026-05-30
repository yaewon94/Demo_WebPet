package com.example.demo_webPet.user;

import com.example.demo_webPet.Common.BaseCode;
import lombok.Getter;

@Getter
enum UserCode implements BaseCode {

    ERROR_DUPLICATED_USER_ID("E_U001", "이미 존재하는 아이디 입니다");

    private final String code;
    private final String message;

    UserCode(String code, String message) {
        this.code = code;
        this.message = message;
    }
}