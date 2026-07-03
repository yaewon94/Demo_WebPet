package com.example.demo_webPet.user;

import com.example.demo_webPet.common.exception.BaseRuntimeException;
import com.example.demo_webPet.common.error.ErrorCode;
import lombok.Getter;

@Getter
class UserException extends BaseRuntimeException{

    private final CreateUserRequest request;

    UserException(ErrorCode code, CreateUserRequest request) {
        super(code);
        this.request = request;
    }
}
