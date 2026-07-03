package com.example.demo_webPet.common.exception;

import com.example.demo_webPet.common.error.ErrorCode;
import lombok.Getter;

import java.util.List;

@Getter
public final class CustomNotValidException extends RuntimeException{

    private final List<ErrorCode> errors;

    public CustomNotValidException(ErrorCode code){
        if(code == null){
            throw new IllegalArgumentException("CustomNotValidException : [constructor parameter] errorCode is null");
        }
        this.errors = List.of(code);
    }

    public CustomNotValidException(List<ErrorCode> errorCodes){
        if(errorCodes == null || errorCodes.isEmpty()){
            throw new IllegalArgumentException("CustomNotValidException : [constructor parameter] errorCodes is null or empty");
        }
        this.errors = errorCodes;
    }
}
