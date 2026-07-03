package com.example.demo_webPet.common.error;

import lombok.Getter;

@Getter
public abstract class ErrorResponse {

    private final ErrorType type;

    public ErrorResponse(ErrorType type){
        this.type = type;
    }
}
