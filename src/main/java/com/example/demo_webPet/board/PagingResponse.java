package com.example.demo_webPet.board;

import lombok.Getter;
import org.springframework.data.domain.Page;

@Getter
public class PagingResponse {

    private static final int BOARD_GROUP_SIZE = 10;
    private final int startPage;
    private final int endPage;
    private final String urlPrefix;

    public PagingResponse(String urlPrefix, int currentPage, int totalPageCount){
        this.startPage = (currentPage / BOARD_GROUP_SIZE) * BOARD_GROUP_SIZE;
        this.endPage = Math.min(
                startPage + BOARD_GROUP_SIZE - 1,
                totalPageCount - 1
        );
        this.urlPrefix = urlPrefix;
    }
}
