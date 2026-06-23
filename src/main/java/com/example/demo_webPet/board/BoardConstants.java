package com.example.demo_webPet.board;

import org.springframework.data.domain.Sort;

public final class BoardConstants {

    private BoardConstants(){}

    public static final String DEFAULT_PAGE = "0";
    public static final int PAGE_SIZE = 2;
    public static final Sort SORT = Sort.by("id").descending();

    public static final String MODEL_PARAM_BOARD_TITLE_URL = "detailUrlPrefix";
    public static final String MODEL_PARAM_BOARD_LIST_RESPONSE = "boardListResponse";
}
