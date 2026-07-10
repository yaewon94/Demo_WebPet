package com.example.demo_webPet.board.comment;

import com.example.demo_webPet.board.BoardType;
import com.example.demo_webPet.board.PagingResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/board/comment")
@RequiredArgsConstructor
final class BoardCommentController {

    private final BoardCommentService commentService;

    @PostMapping("/add")
    public void addComment(@RequestBody @Valid BoardCommentWriteRequest request){
        // dto validation 오류는 GlobalExceptionRestApiExceptionHandler에서 처리
        commentService.addComment(request);
    }

    @GetMapping("/{boardType}/{boardId}")
    public BoardCommentListResponse getCommentList(
            @PathVariable BoardType boardType,
            @PathVariable Long boardId,
            @RequestParam(defaultValue = "0") int commentPage){

        Page<BoardCommentResponse> comments = commentService.getCommentList(boardType, boardId, commentPage);
        String urlPrefix = "/board/comment/" + boardType + "/" + boardId +
                "/?commentPage="+commentPage;

        return new BoardCommentListResponse(
                comments,
                new PagingResponse(urlPrefix, commentPage, comments.getTotalPages()));
    }

    @PostMapping("/modify")
    public void modifyComment(@RequestBody @Valid BoardCommentWriteRequest request) {
        // dto validation 오류는 GlobalExceptionRestApiExceptionHandler에서 처리
        commentService.modifyComment(request);
    }

    @PostMapping("/delete")
    public void deleteComment(@RequestBody @Valid BoardCommentDeleteRequest request) {
        // dto validation 오류는 GlobalExceptionRestApiExceptionHandler에서 처리
        commentService.deleteComment(request);
    }
}
