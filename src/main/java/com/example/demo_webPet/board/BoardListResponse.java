package com.example.demo_webPet.board;

import lombok.Getter;
import org.springframework.data.domain.Page;

@Getter
public class BoardListResponse {

    private static final int BOARD_GROUP_SIZE = 10;

    private final int startPage;
    private final int endPage;
    private final Page<BoardDto_forList> boardPage;

    public BoardListResponse(int currentPage, Page<BoardDto_forList> boardPage){
        this.startPage = (currentPage / BOARD_GROUP_SIZE) * BOARD_GROUP_SIZE;
        this.endPage = Math.min(
                startPage + BOARD_GROUP_SIZE - 1,
                boardPage.getTotalPages() - 1
        );
        this.boardPage = boardPage;
    }
}
