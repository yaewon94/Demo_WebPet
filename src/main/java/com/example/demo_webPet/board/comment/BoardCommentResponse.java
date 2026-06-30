package com.example.demo_webPet.board.comment;

import com.example.demo_webPet.common.output.FormatConstants;

import java.time.LocalDateTime;

public record BoardCommentResponse(
        Long id,
        String userName,
        String content,
        String createdAt
) {
    public BoardCommentResponse(Long id, String userName, String content, LocalDateTime createdAt){
        this(id, userName, content, createdAt.format(FormatConstants.YMD_HMS));
    }
}
