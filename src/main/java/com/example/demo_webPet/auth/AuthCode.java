package com.example.demo_webPet.auth;

import com.example.demo_webPet.common.BaseCode;
import lombok.Getter;

@Getter
enum AuthCode implements BaseCode {

    ERROR_USER_NAME_IS_NOT_EXIST("E_Auth1", "존재하지 않는 아이디 입니다"),
    ERROR_USER_PASSWORD_MISMATCH("E_Auth2", "비밀번호가 일치하지 않습니다"),
    ERROR_USER_IS_NOT_EXIST("E_Auth3", "존재하지 않는 회원입니다"),
    ERROR_NOT_SHELTER_USER("E_Auth4", "보호소 회원만 이용 가능합니다"),
    ERROR_NOT_NORMAL_USER("E_Auth5", "일반회원만 이용 가능합니다");

    private final String code;
    private final String message;

    AuthCode(String code, String message) {
        this.code = code;
        this.message = message;
    }
}