package com.example.demo_webPet.common.exception;

import com.example.demo_webPet.common.error.ErrorCode;
import lombok.Getter;

@Getter
public abstract class BaseRuntimeException extends RuntimeException{

    private final ErrorCode errorCode;

    protected BaseRuntimeException(ErrorCode code){
        super(code.getMessage());
        this.errorCode = code;
    }
}
