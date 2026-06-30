package com.example.demo_webPet.board.comment;

import com.example.demo_webPet.common.output.FormatConstants;

import java.time.LocalDateTime;

record BoardCommentResponse(
        Long id,
        String userName,
        String content,
        String createdAt
) {
    BoardCommentResponse(Long id, String userName, String content, LocalDateTime createdAt){
        this(id, userName, content, createdAt.format(FormatConstants.YMD_HMS));
    }
}
