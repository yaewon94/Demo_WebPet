package com.example.demo_webPet.board.MissingAnimal;

import com.example.demo_webPet.animal.AnimalConstants;
import com.example.demo_webPet.animal.AnimalSpecies;
import com.example.demo_webPet.common.constants.CommonValidConstants;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;

import java.time.LocalDate;

public record MissingAnimalBoardWriteRequest(
        Long id,

        @NotBlank(message = "제목을 입력하세요")
        String title,

        @NotNull(message = "동물 종류를 선택해주세요")
        AnimalSpecies species,

        @NotNull(message = "실종날짜를 입력해주세요")
        @PastOrPresent(message = "실종날짜를 제대로 입력해주세요")
        LocalDate missingDate,

        @NotBlank(message = "실종장소를 선택해주세요")
        String address1,

        @NotBlank(message = "실종장소를 선택해주세요")
        String address2,

        String content
) {
    static MissingAnimalBoardWriteRequest getNewInstance(){
        return new MissingAnimalBoardWriteRequest(null,  null, null, null, null, null, null);
    }
}
