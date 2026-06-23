package com.example.demo_webPet.board;

import com.example.demo_webPet.common.output.FormatConstants;
import java.time.LocalDateTime;

public record BoardListResponse(
        Long id,
        String title,
        String createdAt
) {
    public BoardListResponse(Long id, String title, LocalDateTime createdAt) {
        this(id, title, createdAt.format(FormatConstants.YEAR_MONTH_DAY));
    }
}