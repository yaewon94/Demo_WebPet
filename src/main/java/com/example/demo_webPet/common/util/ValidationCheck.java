package com.example.demo_webPet.common.util;

import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

import java.time.LocalDate;
import java.util.List;

public final class ValidationCheck {

    private ValidationCheck() {}

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

    // DB에 넣기 직전 entity 에서 날짜 제한
    public static void validateNotFutureDate(LocalDate date, String fieldName){
        if (date != null && date.isAfter(LocalDate.now())) {
            throw new IllegalArgumentException(fieldName + "는 현재, 과거만 가능합니다 : " + date);
        }
    }
}
