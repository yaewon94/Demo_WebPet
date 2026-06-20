package com.example.demo_webPet.animal;

import com.example.demo_webPet.common.util.EnumUtils;

enum AnimalStatus {
    MISSING, PROTECTING, ADOPTED;

    static AnimalStatus from(String value) {
        return EnumUtils.parse(AnimalStatus.class, value);
    }
}