package com.example.demo_webPet.Global;

import com.example.demo_webPet.user.DuplicatedUserIdException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(DuplicatedUserIdException.class)
    public ResponseEntity<ErrorResponseDto> handle(DuplicatedUserIdException e) {
        return ResponseEntity
                .status(HttpStatus.CONFLICT)
                .body(new ErrorResponseDto(e.getErrorCode().getCode(), e.getMessage()));
    }
}
