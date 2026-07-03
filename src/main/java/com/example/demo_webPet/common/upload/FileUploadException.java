package com.example.demo_webPet.common.upload;

import com.example.demo_webPet.common.exception.BaseRuntimeException;
import com.example.demo_webPet.common.error.ErrorCode;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public final class FileUploadException extends BaseRuntimeException {

    FileUploadException(ErrorCode code){
        super(code);
    }
}