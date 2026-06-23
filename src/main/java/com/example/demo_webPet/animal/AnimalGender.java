package com.example.demo_webPet.animal;

import com.example.demo_webPet.common.util.EnumUtils;

public enum AnimalGender {
    MALE, FEMALE;
    static AnimalGender from(String value) {
        return EnumUtils.parse(AnimalGender.class, value);
    }
}