package com.example.demo_webPet.board.MissingAnimal;

import com.example.demo_webPet.animal.Animal;
import com.example.demo_webPet.animal.AnimalConstants;
import com.example.demo_webPet.animal.AnimalSpecies;
import com.example.demo_webPet.board.RescuedAnimal.BoardMode;
import com.example.demo_webPet.common.constants.CommonValidConstants;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;

import java.time.LocalDate;

public record MissingAnimalBoardWriteRequest(
        Long id,

        String mode,

        @NotBlank(message = CommonValidConstants.MSG_INPUT_TITLE)
        String title,

        @NotNull(message = AnimalConstants.INPUT_SPECIES_REQUIRED)
        AnimalSpecies species,

        @NotNull(message = AnimalConstants.INPUT_MISSING_DATE_REQUIRED)
        @PastOrPresent(message = AnimalConstants.ERROR_MISSING_DATE)
        LocalDate missingDate,

        @NotNull(message = AnimalConstants.INPUT_MISSING_LOCATION_REQUIRED)
        String missingLocation,

        // TODO
        //@NotNull(message = AnimalConstants.INPUT_IMAGE_REQUIRED)
        String imageUrl,

        String content
) {
    static MissingAnimalBoardWriteRequest getNewInstance(){
        return new MissingAnimalBoardWriteRequest(-1L, BoardMode.ADD.getValue(), null, null, null, null, null, null);
    }

    static MissingAnimalBoardWriteRequest from(MissingAnimalBoard board, BoardMode mode){
        Animal animal = board.getAnimal();
        return new MissingAnimalBoardWriteRequest(
                board.getId(),
                mode.getValue(),
                board.getTitle(),
                animal.getSpecies(),
                animal.getMissingDate(),
                animal.getMissingLocation(),
                board.getImgUrl(),
                board.getContent());
    }
}
