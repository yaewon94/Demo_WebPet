package com.example.demo_webPet.animal;

import com.example.demo_webPet.common.util.EnumUtils;

public enum AnimalStatus {
    MISSING, PROTECTING, ADOPTED;

    public static AnimalStatus from(String value) {
        return EnumUtils.parse(AnimalStatus.class, value);
    }
}