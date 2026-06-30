package com.example.demo_webPet.board.comment;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/board/comment")
@RequiredArgsConstructor
final class BoardCommentController {

    private final BoardCommentService commentService;

    @PostMapping("/add")
    public BoardCommentResponse addComment(@RequestBody @Valid BoardCommentWriteRequest request){
        // dto validation 오류는 GlobalExceptionRestApiExceptionHandler에서 처리
        return commentService.addComment(request);
    }
}
