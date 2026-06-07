package com.example.demo_webPet.animal;

import com.example.demo_webPet.common.util.EnumUtils;

enum AnimalStatus {
    PROTECTING("보호 중"),
    ADOPTED("입양 완료");

    private final String label;

    AnimalStatus(String label){
        this.label = label;
    }

    static AnimalStatus from(String value) {
        return EnumUtils.parse(AnimalStatus.class, value);
    }
}