package com.example.demo_webPet.animal;

import com.example.demo_webPet.common.util.EnumUtils;
import lombok.Getter;

@Getter
enum AnimalSpecies {
    DOG("강아지"),
    CAT("고양이"),
    ETC("기타");

    private final String label;

    AnimalSpecies(String label){
        this.label = label;
    }

    static AnimalSpecies from(String value) {
        return EnumUtils.parse(AnimalSpecies.class, value);
    }
}