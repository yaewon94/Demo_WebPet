package com.example.demo_webPet.common.util;

import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;

import java.util.List;

public final class ErrorCheck {

    private ErrorCheck() {}

    // 우선순위가 있는 필드의 에러탐색
    // @ return : error message
    public static String validationCheckInOrder(BindingResult bindingResult, String... fields) {

        if (bindingResult.hasErrors()) {
            List<ObjectError> errors = bindingResult.getAllErrors();
            for (String field : fields) {
                for (ObjectError error : errors) {
                    // 필드 에러
                    if (error instanceof FieldError fieldError) {
                        if (fieldError.getField().equals(field)) {
                            return fieldError.getDefaultMessage();
                        }
                    }
                    // 글로벌 에러
                    else{
                        return error.getDefaultMessage();
                    }
                }
            }
        }

        return null;
    }
}
