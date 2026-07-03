package com.example.demo_webPet.board;

import com.example.demo_webPet.common.exception.BaseRuntimeException;
import com.example.demo_webPet.common.error.ErrorCode;
import lombok.Getter;

// TODO : BoardDeniedException 삭제하고 AccessDeniedException 에서 처리
@Getter
public final class BoardDeniedException extends BaseRuntimeException {

    private final String prevUrl;

    public BoardDeniedException(ErrorCode code){
        super(code);
        this.prevUrl = null;
    }

    public BoardDeniedException(ErrorCode code, String prevUrl){
        super(code);
        this.prevUrl = prevUrl;
    }
}
