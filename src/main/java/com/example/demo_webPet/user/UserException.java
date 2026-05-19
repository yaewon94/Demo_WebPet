package com.example.demo_webPet.user;

import lombok.Getter;

@Getter
public abstract class UserException extends RuntimeException{

    private final UserCode code;

    protected UserException(UserCode code) {
        super(code.getMessage());
        this.code = code;
    }
}
