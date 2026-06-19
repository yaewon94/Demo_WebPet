package com.example.demo_webPet.animal;

import com.example.demo_webPet.common.util.EnumUtils;
import lombok.Getter;

@Getter
public enum AnimalSpecies {
    DOG,CAT,ETC;

    public static AnimalSpecies from(String value) {
        return EnumUtils.parse(AnimalSpecies.class, value);
    }
}