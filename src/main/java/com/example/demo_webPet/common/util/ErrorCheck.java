package com.example.demo_webPet.common.util;

import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;

import java.util.List;

public final class ErrorCheck {

    private ErrorCheck() {}

    // 우선순위가 있는 필드의 에러탐색
    // @ return : error message
    public static FieldError getFirstFieldError(BindingResult bindingResult, String... fields) {

        List<FieldError> errorList = bindingResult.getFieldErrors();

        if (!errorList.isEmpty()) {
            for (String field : fields) {
                for (FieldError error : errorList) {
                    if (error.getField().equals(field)) {
                        return error;
                    }
                }
            }
        }
        return null;
    }
}
