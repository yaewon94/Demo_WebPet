package com.example.demo_webPet.auth;

import com.example.demo_webPet.common.exception.BaseRuntimeException;
import com.example.demo_webPet.common.exception.ErrorCode;
import lombok.Getter;

@Getter
final class LoginFailException extends BaseRuntimeException {

    private final String redirectionPage;

    LoginFailException(ErrorCode code, String redirectionPage){
        super(code);
        this.redirectionPage = redirectionPage;
    }
}
