package com.example.demo_webPet.common.error;

import lombok.Getter;

@Getter
public final class AccessDeniedResponse extends ErrorResponse{

    private final String errorMsg;
    private final String redirectUrl;

    public AccessDeniedResponse(String errorMsg, String redirectUrl){
        super(ErrorType.ACCESS_DENIED);
        this.errorMsg = errorMsg;
        this.redirectUrl = redirectUrl;
    }
}