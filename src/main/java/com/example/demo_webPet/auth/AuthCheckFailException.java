package com.example.demo_webPet.auth;

import com.example.demo_webPet.common.exception.BaseRuntimeException;
import com.example.demo_webPet.common.exception.ErrorCode;
import lombok.Getter;

@Getter
final class AuthCheckFailException extends BaseRuntimeException {

    private final String urlPrefix;
    private final String redirectionUri;

    AuthCheckFailException(ErrorCode code, String urlPrefix, String redirectionUri){
        super(code);
        this.urlPrefix = urlPrefix;
        this.redirectionUri = redirectionUri;
    }
}
