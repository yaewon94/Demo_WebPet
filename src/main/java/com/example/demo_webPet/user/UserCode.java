package com.example.demo_webPet.user;

import com.example.demo_webPet.common.BaseCode;
import lombok.Getter;

@Getter
enum UserCode implements BaseCode {

    ERROR_DUPLICATED_USER_ID("E_U001", "이미 존재하는 아이디 입니다"),
    ERROR_USER_ID_IS_NOT_EXIST("E_U002", "존재하지 않는 아이디 입니다"),
    ERROR_USER_PASSWORD_MISMATCH("E_U003", "비밀번호가 일치하지 않습니다");

    private final String code;
    private final String message;

    UserCode(String code, String message) {
        this.code = code;
        this.message = message;
    }
}