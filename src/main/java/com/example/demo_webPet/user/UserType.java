package com.example.demo_webPet.user;

import com.example.demo_webPet.common.util.EnumUtils;

public enum UserType{
    NORMAL("개인회원"),
    SHELTER("보호소 계정");

    private final String label;

    UserType(String label){
        this.label = label;
    }

    static UserType from(String value){
        return EnumUtils.parse(UserType.class, value);
    }
}