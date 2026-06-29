package com.example.demo_webPet.board;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class BoardCommentService {

    private final BoardCommentRepository commentRepository;

    public Page<BoardCommentResponse> getCommentList(BoardType boardType, Long boardId, int page){
        Pageable pageable = PageRequest.of(page, BoardConstants.PAGE_SIZE);
        return commentRepository.findCommentList(boardType, boardId, pageable);
    }
}
