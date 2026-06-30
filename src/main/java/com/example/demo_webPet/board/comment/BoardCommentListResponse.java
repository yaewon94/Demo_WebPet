package com.example.demo_webPet.board.comment;

import com.example.demo_webPet.board.PagingResponse;
import org.springframework.data.domain.Page;

record BoardCommentListResponse(
        Page<BoardCommentResponse> comments,
        PagingResponse paging
) {
}
