package com.example.demo_webPet.board;

import com.example.demo_webPet.common.output.FormatConstants;

import java.time.LocalDateTime;

public record BoardDto_forList(
        Long id,
        String title,
        String createdAt,
        String userName,
        String thumbnail
) {
    BoardDto_forList(Long id, String title, LocalDateTime createdAt, String userName, String thumbnail){
        this(id, title, createdAt.format(FormatConstants.YEAR_MONTH_DAY), userName, thumbnail);
    }
}