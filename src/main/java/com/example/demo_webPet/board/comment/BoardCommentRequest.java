package com.example.demo_webPet.board.comment;

import com.example.demo_webPet.board.BoardType;

interface BoardCommentRequest {
    BoardType boardType();
    Long boardId();
    Long commentId();
    String password(); // guest 전용
}