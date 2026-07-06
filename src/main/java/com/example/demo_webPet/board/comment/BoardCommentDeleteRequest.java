package com.example.demo_webPet.board.comment;

import com.example.demo_webPet.board.BoardType;

record BoardCommentDeleteRequest(
        BoardType boardType,
        Long boardId,
        Long commentId,
        String password
) implements BoardCommentRequest{
}
