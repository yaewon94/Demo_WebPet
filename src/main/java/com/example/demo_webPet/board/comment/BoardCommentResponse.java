package com.example.demo_webPet.board.comment;

import com.example.demo_webPet.common.output.FormatConstants;

import java.time.LocalDateTime;

record BoardCommentResponse(
        Long id,
        String userName,
        String content,
        String createdAt,
        boolean canUpdate,
        boolean isGuestComment
) {
    BoardCommentResponse(Long id, String userName, String content, LocalDateTime createdAt, boolean canUpdate, boolean isGuestComment){
        this(id, userName, content, createdAt.format(FormatConstants.YMD_HMS), canUpdate, isGuestComment);
    }
}
