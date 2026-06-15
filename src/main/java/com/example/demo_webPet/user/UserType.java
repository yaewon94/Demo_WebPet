package com.example.demo_webPet.user;

import com.example.demo_webPet.common.util.EnumUtils;

public enum UserType{
    NORMAL, SHELTER;
    static UserType from(String value){
        return EnumUtils.parse(UserType.class, value);
    }
}