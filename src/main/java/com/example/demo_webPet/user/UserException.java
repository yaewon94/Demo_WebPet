package com.example.demo_webPet.user;

import com.example.demo_webPet.Global.ErrorCode;
import lombok.Getter;

@Getter
public abstract class UserException extends RuntimeException{

    private final ErrorCode errorCode;

    protected UserException(ErrorCode errorCode) {
        super(errorCode.getMessage());
        this.errorCode = errorCode;
    }
}
