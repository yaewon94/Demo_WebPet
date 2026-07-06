package com.example.demo_webPet.common.exception;

import com.example.demo_webPet.common.error.AccessDeniedResponse;
import com.example.demo_webPet.common.error.ValidationErrorResponse;
import com.example.demo_webPet.common.upload.FileUploadException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.ArrayList;
import java.util.List;

// assignableTypes를 명시하지 않고 @RestControllerAdvice만 사용하면
// Controller에서 던진 예외도 잡을 수 있다
// @RestControllerAdvice == @ControllerAdvice + @ResponseBody 이기 때문
@RestControllerAdvice
public class GlobalRestApiExceptionHandler {

    @ExceptionHandler(FileUploadException.class)
    public ResponseEntity<String> handle(FileUploadException e) {
        return ResponseEntity.badRequest().body(e.getMessage());
    }

    // service에서 dto validation 오류 예외를 던졌을 경우 호출
    @ExceptionHandler(CustomNotValidException.class)
    public ResponseEntity<ValidationErrorResponse> handle(CustomNotValidException e){
        List<String> errorMsgList = new ArrayList<>();
        e.getErrors().forEach(err->errorMsgList.add(err.getMessage()));

        return ResponseEntity.badRequest().body(
                new ValidationErrorResponse(errorMsgList));
    }

    // dto validation 오류났을 경우 spring이 MethodArgumentNotValidException 던짐
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ValidationErrorResponse> handle(MethodArgumentNotValidException e) {
        List<String> errorMsgList = new ArrayList<>();
        e.getBindingResult().getFieldErrors().forEach(err ->
                errorMsgList.add(err.getDefaultMessage()));

        return ResponseEntity.badRequest().body(
                new ValidationErrorResponse(errorMsgList));
    }

    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<AccessDeniedResponse> handle(AccessDeniedException e){
        String redirectionUrl = e.getRedirectUrl();
        if(redirectionUrl == null || redirectionUrl.isBlank()){
            redirectionUrl = "/";
        }

        return ResponseEntity.badRequest().body(
                new AccessDeniedResponse(e.getMessage(), redirectionUrl));
    }
}
