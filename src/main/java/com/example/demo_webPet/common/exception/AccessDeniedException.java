package com.example.demo_webPet.common.exception;

import com.example.demo_webPet.common.error.ErrorCode;
import lombok.Getter;

@Getter
public final class AccessDeniedException extends BaseRuntimeException{

    private final String redirectUrl;

    public AccessDeniedException(ErrorCode code, String redirectUrl){
        super(code);
        this.redirectUrl = redirectUrl;
    }
}
