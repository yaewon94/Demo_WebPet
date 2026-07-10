package com.example.demo_webPet.board;

import org.springframework.data.domain.Sort;

public final class BoardConstants {

    private BoardConstants(){}

    public static final String DEFAULT_PAGE = "0";
    public static final int PAGE_SIZE = 10;
    public static final Sort SORT = Sort.by("id").descending();

    public static final String MODEL_PARAM_BOARD_LIST = "boardList";
    public static final String MODEL_PARAM_BOARD_COMMENT_LIST = "commentList";
    public static final String MODEL_PARAM_BOARD_COMMENT_WRITE_REQUEST = "commentWriteRequest";
    public static final String MODEL_PARAM_PAGING = "paging";
}