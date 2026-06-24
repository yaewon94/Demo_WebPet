package com.example.demo_webPet.board.MissingAnimal;

import com.example.demo_webPet.animal.AnimalSpecies;
import com.example.demo_webPet.animal.AnimalStatus;
import com.example.demo_webPet.common.output.FormatConstants;
import java.time.LocalDate;
import java.time.LocalDateTime;

record MissingAnimalBoardDetailResponse(
        Long id,
        AnimalStatus status,
        String title,
        String userName,
        String createdAt,
        AnimalSpecies species,
        String missingDate,
        String missingLocation,
        String content) {

    MissingAnimalBoardDetailResponse(
            Long id, AnimalStatus status, String title, String userName,
            LocalDateTime createdAt, AnimalSpecies species,
            LocalDate missingDate, String missingLocation, String content){
        this(id, status, title, userName,
                createdAt.format(FormatConstants.YMD_HMS), species,
                missingDate.format(FormatConstants.YEAR_MONTH_DAY), missingLocation, content);
    }
}
