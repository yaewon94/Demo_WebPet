package com.example.demo_webPet.animal;

import com.example.demo_webPet.common.util.EnumUtils;

enum AnimalGender {
    MALE("수컷"),
    FEMALE("암컷");

    private final String label;

    AnimalGender(String label){
        this.label = label;
    }

    static AnimalGender from(String value) {
        return EnumUtils.parse(AnimalGender.class, value);
    }
}