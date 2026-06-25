package com.example.demo_webPet.common;

import com.example.demo_webPet.common.upload.FileUploadException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(FileUploadException.class)
    public ResponseEntity<String> handle(FileUploadException e) {

        return ResponseEntity
                .badRequest()
                .body(e.getMessage());
    }
}
