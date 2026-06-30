package com.example.demo_webPet.board.comment;

import com.example.demo_webPet.board.BoardType;
import com.example.demo_webPet.common.constants.CommonValidConstants;
import jakarta.validation.constraints.NotBlank;

public record BoardCommentWriteRequest(
        BoardType boardType,
        Long boardId,

        // guest 인 경우에만 사용
        String userName,
        String password,

        @NotBlank(message = CommonValidConstants.MSG_INPUT_CONTENT)
        String content
) {
    public static BoardCommentWriteRequest getNewInstance(BoardType boardType, Long boardId){
        return new BoardCommentWriteRequest(boardType, boardId, null, null, null);
    }
}
