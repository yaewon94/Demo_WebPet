package com.example.demo_webPet.Global;

import lombok.Getter;

@Getter
public enum ErrorCode {

    DUPLICATED_USER_ID("U001", "이미 존재하는 아이디 입니다");

    private final String code;
    private final String message;

    ErrorCode(String code, String message) {
        this.code = code;
        this.message = message;
    }
}