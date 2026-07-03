package com.example.demo_webPet.common.error;

import lombok.Getter;

import java.util.List;

@Getter
public final class ValidationErrorResponse extends ErrorResponse{

    List<String> errorMessages;

    public ValidationErrorResponse(List<String> errorMessages){
        super(ErrorType.VALIDATION_ERROR);
        if(errorMessages == null || errorMessages.isEmpty()){
            throw new IllegalArgumentException("[ValidationErrorResponse] errorMessages is empty");
        }
        this.errorMessages = errorMessages;
    }
}
