package com.example.demo_webPet.board;

import com.example.demo_webPet.common.exception.BaseRuntimeException;
import com.example.demo_webPet.common.exception.ErrorCode;
import lombok.Getter;

@Getter
public final class BoardDeniedException extends BaseRuntimeException {

    public BoardDeniedException(ErrorCode code){
        super(code);
    }
}
